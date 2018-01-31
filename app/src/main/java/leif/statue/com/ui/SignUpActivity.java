package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.theartofdev.edmodo.cropper.CropImage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.AltarApplication;
import leif.statue.com.R;
import leif.statue.com.consts.CommonConsts;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.task.PayTotalTask;
import leif.statue.com.task.SignUpTask;
import leif.statue.com.util.DateUtil;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.StringUtil;
import leif.statue.com.vo.SignUpResponseVo;

public class SignUpActivity extends AppCompatActivity {

    private Animation shake;
    private ProgressDialog progressDialog;

    @BindView(R.id.edt_mail_address)
    TextInputEditText edtMailAddress;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.chk_receive_notification)
    CheckBox chkNotice;
    @BindView(R.id.radio_annual_plan)
    RadioButton radioAnnualPlan;
    @BindView(R.id.radio_monthly_plan)
    RadioButton radioMonthlyPlan;
    @BindView(R.id.language_spinner)
    Spinner languageSpinner;
    @BindView(R.id.prefecture_spinner)
    Spinner prefectureSpinner;
    @BindView(R.id.age_spinner)
    Spinner ageSpinner;
    @BindView(R.id.radio_gender_male)
    RadioButton radioMale;
    @BindView(R.id.radio_gender_female)
    RadioButton radioFemale;

    private String mailAddress;
    private String password;
    private String language = "ja";
    private int prefecture = 1;
    private int age = 1;
    private int gender = 1;
    private int isNotice = 1;
    private int plan = 1;
    private int selLanguage = 0;

    private boolean bSubscribed = false;

    private String subscriptionID = CommonConsts.PRODUCT_ANNUAL_PLAN;

    BillingProcessor bp;

    private static final String MERCHANT_ID=null;

    private String orderId = "";
    private String purchaseDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        shake = AnimationUtils.loadAnimation(this, R.anim.edittext_shake);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
        progressDialog.setCanceledOnTouchOutside(false);

        selLanguage = getIntent().getIntExtra("language", 0);
        if(selLanguage == 0) {
            language = "ja";
            languageSpinner.setSelection(0);
        } else {
            language = "en";
            languageSpinner.setSelection(1);
        }

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        language = "ja";

                        /*Locale locale = new Locale("ja");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());*/

                        break;
                    case 1:
                        language = "en";

                        /*locale = new Locale("en");
                        Locale.setDefault(locale);
                        config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());*/

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prefectureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prefecture = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                age = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        chkNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    isNotice = 1;
                } else {
                    isNotice = 0;
                }
            }
        });

        radioAnnualPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan = 1;
                subscriptionID = CommonConsts.PRODUCT_ANNUAL_PLAN;
            }
        });

        radioMonthlyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan = 2;
                subscriptionID = CommonConsts.PRODUCT_MONTHLY_PLAN;
            }
        });

        radioMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = 1;
            }
        });

        radioFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = 2;
            }
        });

        if(!BillingProcessor.isIabServiceAvailable(this)) {
            Log.v("STAW", "In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }

        bp = new BillingProcessor(this, CommonConsts.IN_APP_BILLING_RSA_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                bSubscribed = true;

                purchaseDate = DateUtil.timeStampToDate(details.purchaseInfo.purchaseData.purchaseTime);
                orderId = details.purchaseInfo.purchaseData.orderId;

                startSignUp();
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
                        orderId = subAnnual.purchaseInfo.purchaseData.orderId;
                    } else if (subMonthly != null) {
                        purchaseDate = DateUtil.timeStampToDate(subMonthly.purchaseInfo.purchaseData.purchaseTime);
                        orderId = subMonthly.purchaseInfo.purchaseData.orderId;
                    }
                }
            }
            @Override
            public void onPurchaseHistoryRestored() {
                Log.v("STAW", "HISTORY");
            }
        });
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

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }

    @Subscribe
    public void onSignUpEvent(SignUpEvent event) {
        hideProgressDialog();
        SignUpResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                Locale locale = new Locale(language);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                AltarApplication.userId = responseVo.user_id;

                SharedPrefManager.getInstance(this).saveLanguage(language);
                SharedPrefManager.getInstance(this).saveEmailAddress(responseVo.email);
                SharedPrefManager.getInstance(this).saveAge(responseVo.age);
                SharedPrefManager.getInstance(this).savePrefecture(responseVo.prefecture);
                SharedPrefManager.getInstance(this).saveNotice(responseVo.is_notice);
                SharedPrefManager.getInstance(this).saveGender(responseVo.gender);
                SharedPrefManager.getInstance(this).savePlan(responseVo.plan);

                SharedPrefManager.getInstance(this).saveLogin(true);
                SharedPrefManager.getInstance(this).saveUserId(responseVo.user_id);

                SharedPrefManager.getInstance(this).savePassword(password);

                Intent intent = new Intent(SignUpActivity.this, SelectAltarActivity.class);

                intent.putExtra("edit_altar", false);

                startActivity(intent);
                finish();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @OnClick(R.id.btn_signup)
    void onClickSignUp() {
        /*Intent intent = new Intent(SignUpActivity.this, SelectAltarActivity.class);

        startActivity(intent);*/
        mailAddress = edtMailAddress.getText().toString();
        password = edtPassword.getText().toString();

        if(!checkMailAddress() && !checkPassword()) {
            showErrorMessage(getResources().getString(R.string.input_mail_password));
            return;
        }
        if(!checkMailAddress()) {
            showErrorMessage(getResources().getString(R.string.input_mail_address));
            return;
        }
        if(!checkPassword()) {
            showErrorMessage(getResources().getString(R.string.input_password));
            return;
        }

        if(bSubscribed) {
            startSignUp();
        } else {
            bp.subscribe(this, subscriptionID);
        }
    }

    @OnClick(R.id.btn_terms_and_conditions)
    void onClickTerms() {
        Intent intent = new Intent(SignUpActivity.this, TermsActivity.class);

        intent.putExtra("language", language);

        startActivity(intent);
    }

    private void startSignUp() {
        progressDialog.show();

        SignUpTask task = new SignUpTask();
        task.execute(mailAddress, password, language, String.valueOf(prefecture), String.valueOf(age), String.valueOf(gender), String.valueOf(isNotice), String.valueOf(plan), SharedPrefManager.getInstance(this).getDeviceToken(), orderId);
    }

    private boolean checkMailAddress() {
        if (StringUtil.isEmpty(mailAddress)) {
            showInfoNotice(edtMailAddress);
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
        Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
