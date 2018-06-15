package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.AltarApplication;
import leif.statue.com.R;
import leif.statue.com.consts.CommonConsts;
import leif.statue.com.db.HistoryDB;
import leif.statue.com.event.LoginEvent;
import leif.statue.com.model.CountsItem;
import leif.statue.com.task.LoginTask;
import leif.statue.com.util.DateUtil;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.StringUtil;
import leif.statue.com.vo.LoginResponseVo;

public class LoginActivity extends AppCompatActivity {

    private Animation shake;
    private ProgressDialog progressDialog;

    private String id;
    private String password;

    @BindView(R.id.edt_id)
    TextInputEditText edtID;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.language_spinner)
    Spinner languageSpin;

    private int selLanguage = 0;

    private String subscriptionID = CommonConsts.PRODUCT_ANNUAL_PLAN;

    BillingProcessor bp;

    private static final String MERCHANT_ID=null;

    private String orderId = "";
    private String purchaseDate = "";

    private boolean bSubscribed = false;

    private String expireDate = "";

    private int plan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        Log.v("Logout Flag", String.valueOf(getIntent().getBooleanExtra("logout_flag", false)));

        String curLanguage = SharedPrefManager.getInstance(this).getLanguage();
        if(curLanguage.equals("ja")) {
            languageSpin.setSelection(0);
            selLanguage = 0;
        } else {
            languageSpin.setSelection(1);
            selLanguage = 1;
        }

        Locale locale = new Locale(curLanguage);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        if(bundle != null && !getIntent().getBooleanExtra("logout_flag", false)) {
            Log.v("Bundle", bundle.toString());
            if(getIntent().getExtras().get("google.message_id") != null) {
                Intent intent = new Intent(LoginActivity.this, ConfirmActivity.class);

                intent.putExtra("update_honzon", true);

                startActivity(intent);
                finish();

                return;
            }
        }

        boolean bExpire = getIntent().getBooleanExtra("expire_flag", false);
        if(bExpire) {
            Toast.makeText(LoginActivity.this, R.string.msg_login_expire, Toast.LENGTH_LONG).show();
        }

        shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.edittext_shake);

        if(SharedPrefManager.getInstance(this).getFirstLogin()) {
            /*String currentLanguage = Locale.getDefault().getLanguage();
            SharedPrefManager.getInstance(this).saveLanguage(Locale.getDefault().getLanguage());*/
            SharedPrefManager.getInstance(this).saveFirstLogin(false);
        }

        languageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        selLanguage = 0;

                        Locale locale = new Locale("ja");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());

                        SharedPrefManager.getInstance(LoginActivity.this).saveLanguage("ja");

                        break;
                    case 1:
                        selLanguage = 1;

                        locale = new Locale("en");
                        Locale.setDefault(locale);
                        config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());

                        SharedPrefManager.getInstance(LoginActivity.this).saveLanguage("en");

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(!AltarApplication.isRunning) {
            AltarApplication.isRunning = true;

            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("restart_flag", true);

            startActivity(intent);

            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
        progressDialog.setCanceledOnTouchOutside(false);

        if(!BillingProcessor.isIabServiceAvailable(this)) {
            Log.v("STAW", "In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }

        bp = new BillingProcessor(this, CommonConsts.IN_APP_BILLING_RSA_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                bSubscribed = true;

                purchaseDate = DateUtil.timeStampToDate(details.purchaseInfo.purchaseData.purchaseTime);
                expireDate = DateUtil.getExpireDate(details.purchaseInfo.purchaseData.purchaseTime.getTime(), plan);

                orderId = details.purchaseInfo.purchaseData.orderId;

                startLogin();
            }
            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
                Log.v("STAW", String.valueOf(errorCode));
            }
            @Override
            public void onBillingInitialized() {
                Log.v("STAW", "Initialized");
                TransactionDetails subAnnual = bp.getSubscriptionTransactionDetails(CommonConsts.PRODUCT_ANNUAL_PLAN);
                TransactionDetails subMonthly = bp.getSubscriptionTransactionDetails(CommonConsts.PRODUCT_MONTHLY_PLAN);

                if((subAnnual != null && subAnnual.purchaseInfo.purchaseData.autoRenewing) || (subMonthly != null && subMonthly.purchaseInfo.purchaseData.autoRenewing)) {
                    bSubscribed = true;
                    if(subAnnual != null) {
                        purchaseDate = DateUtil.timeStampToDate(subAnnual.purchaseInfo.purchaseData.purchaseTime);
                        expireDate = DateUtil.getExpireDate(subAnnual.purchaseInfo.purchaseData.purchaseTime.getTime(), 1);
                        orderId = subAnnual.purchaseInfo.purchaseData.orderId;
                        plan = 1;
                    } else if (subMonthly != null) {
                        purchaseDate = DateUtil.timeStampToDate(subMonthly.purchaseInfo.purchaseData.purchaseTime);
                        expireDate = DateUtil.getExpireDate(subMonthly.purchaseInfo.purchaseData.purchaseTime.getTime(), 2);
                        orderId = subMonthly.purchaseInfo.purchaseData.orderId;
                        plan = 2;
                    }

                    Log.v("Expire Date", expireDate);
                }
            }
            @Override
            public void onPurchaseHistoryRestored() {
                Log.v("STAW", "HISTORY");
            }
        });

        /*if(SharedPrefManager.getInstance(this).getLogin()) {
            AltarApplication.userId = SharedPrefManager.getInstance(this).getUserId();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            startActivity(intent);
            finish();
        }*/

        edtID.setText(SharedPrefManager.getInstance(this).getEmailAddress());
        edtPassword.setText(SharedPrefManager.getInstance(this).getPassword());
    }

    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onLoginEvet(LoginEvent event) {
        hideProgressDialog();
        LoginResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {

                SharedPrefManager.getInstance(this).saveFirstLogin(false);

                HistoryDB db = new HistoryDB(LoginActivity.this);
                int count = db.getTotalCount();

                if(count == 0) {
                    db.removeAllDatas();
                    try {
                        JSONArray jsonArray = new JSONArray(responseVo.count_history);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonItem = jsonArray.getJSONObject(i);
                            CountsItem item = new CountsItem();
                            item.setCounts(jsonItem.getInt("count"));
                            item.setDate(jsonItem.getString("date"));

                            db.addItem(item);
                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

                AltarApplication.userId = responseVo.user_id;

                SharedPrefManager.getInstance(this).saveEmailAddress(responseVo.email);
                SharedPrefManager.getInstance(this).saveAge(responseVo.age);
                SharedPrefManager.getInstance(this).savePrefecture(responseVo.prefecture);
                SharedPrefManager.getInstance(this).saveNotice(responseVo.is_notice);
                SharedPrefManager.getInstance(this).saveGender(responseVo.gender);

                SharedPrefManager.getInstance(this).saveLogin(true);
                SharedPrefManager.getInstance(this).saveUserId(responseVo.user_id);
                SharedPrefManager.getInstance(this).savePlan(responseVo.plan);

                SharedPrefManager.getInstance(this).saveBuddhistImage(responseVo.confirm);
                SharedPrefManager.getInstance(this).saveHonjou(responseVo.honzon);
                SharedPrefManager.getInstance(this).saveMusicLevel(responseVo.music);

                SharedPrefManager.getInstance(this).saveCompleteHonzon(responseVo.last_img);

                SharedPrefManager.getInstance(this).savePassword(password);

                SharedPrefManager.getInstance(this).saveLoginTime(new Date().getTime());

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                startActivity(intent);
                finish();
            } else if (responseVo.success == 2) {
                showSubscriptionDialog();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @OnClick(R.id.btn_signup)
    void onClickSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

        intent.putExtra("language", selLanguage);

        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void onClickLogin() {
        id = edtID.getText().toString();
        password = edtPassword.getText().toString();

        if(!checkID()) return;
        if(!mailAddressValidate()) return;
        if(!checkPassword()) return;

        //if(bSubscribed) {
            startLogin();
        //}
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }

    private void showSubscriptionDialog() {
        String[] subItems = {getResources().getString(R.string.annual_amount), getResources().getString(R.string.monthly_amount)};

        new AlertDialog.Builder(this)
                .setMessage(R.string.msg_recharge)
                .setSingleChoiceItems(subItems, 0, null)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        plan = selectedPosition + 1;
                        if(plan == 1) {
                            subscriptionID = CommonConsts.PRODUCT_ANNUAL_PLAN;
                            bp.subscribe(LoginActivity.this, subscriptionID);
                        } else if (plan == 2) {
                            subscriptionID = CommonConsts.PRODUCT_MONTHLY_PLAN;
                            bp.subscribe(LoginActivity.this, subscriptionID);
                        }
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @OnClick(R.id.tv_forgot_password)
    void onClickForgotPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);

        intent.putExtra("language", selLanguage);

        startActivity(intent);
    }

    private void startLogin() {
        progressDialog.show();

        String isCancel = "";
        if(bSubscribed)
            isCancel = "0";
        else
            isCancel = "1";

        LoginTask task = new LoginTask();
        task.execute(id, password, SharedPrefManager.getInstance(this).getLanguage(), SharedPrefManager.getInstance(this).getDeviceToken(), isCancel, expireDate, String.valueOf(plan));
    }

    private boolean checkID() {
        if (StringUtil.isEmpty(id)) {
            showInfoNotice(edtID);
            return false;
        }

        return true;
    }

    private boolean mailAddressValidate() {
        if(!StringUtil.isValidEmail(id)) {
            showInfoNotice(edtID);
            return false;
        }

        return true;
    }

    private boolean checkPassword() {
        if (StringUtil.isEmpty(password)) {
            showInfoNotice(edtPassword);
            return false;
        }

        return true;
    }

    private void showInfoNotice(TextInputEditText target) {
        target.startAnimation(shake);
        if (target.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
