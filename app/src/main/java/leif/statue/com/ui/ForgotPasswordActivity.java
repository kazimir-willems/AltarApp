package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.AltarApplication;
import leif.statue.com.R;
import leif.statue.com.event.ForgotPasswordEvent;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.task.ForgotPasswordTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.StringUtil;
import leif.statue.com.vo.ForgotPasswordResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.edt_mail_address)
    TextInputEditText edtMailAddress;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;

    private String mailAddress;
    private String password;

    private Animation shake;
    private ProgressDialog progressDialog;

    private String language;
    private int selLanguage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ButterKnife.bind(this);

        shake = AnimationUtils.loadAnimation(this, R.anim.edittext_shake);

        selLanguage = getIntent().getIntExtra("language", 0);
        if(selLanguage == 0) {
            language = "ja";
        } else {
            language = "en";
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
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
    public void onForgotPasswordEvent(ForgotPasswordEvent event) {
        hideProgressDialog();
        ForgotPasswordResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                AltarApplication.userId = responseVo.user_id;

                SharedPrefManager.getInstance(this).saveLanguage(language);
                SharedPrefManager.getInstance(this).saveEmailAddress(responseVo.email);
                SharedPrefManager.getInstance(this).saveAge(responseVo.age);
                SharedPrefManager.getInstance(this).savePrefecture(responseVo.prefecture);
                SharedPrefManager.getInstance(this).saveNotice(responseVo.is_notice);
                SharedPrefManager.getInstance(this).saveGender(responseVo.gender);

                SharedPrefManager.getInstance(this).saveLogin(true);
                SharedPrefManager.getInstance(this).saveUserId(responseVo.user_id);

                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @OnClick(R.id.btn_save)
    void onClickSave() {
        mailAddress = edtMailAddress.getText().toString();
        password = edtPassword.getText().toString();

        if(!checkMailAddress()) return;
        if(!checkPassword()) return;

        startForgotPassword();
    }

    private void startForgotPassword() {
        progressDialog.show();

        ForgotPasswordTask task = new ForgotPasswordTask();
        task.execute(mailAddress, password, language);
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
        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
