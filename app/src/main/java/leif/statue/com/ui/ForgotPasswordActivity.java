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

    private String mailAddress;

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

        progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
        progressDialog.setCanceledOnTouchOutside(false);
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

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    @Subscribe
    public void onForgotPasswordEvent(ForgotPasswordEvent event) {
        hideProgressDialog();
        ForgotPasswordResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                Toast.makeText(ForgotPasswordActivity.this, R.string.sent_content_successfully, Toast.LENGTH_SHORT).show();

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

        if(!checkMailAddress()) return;

        startForgotPassword();
    }

    private void startForgotPassword() {
        progressDialog.show();

        ForgotPasswordTask task = new ForgotPasswordTask();
        task.execute(mailAddress, language);
    }

    private boolean checkMailAddress() {
        if (StringUtil.isEmpty(mailAddress)) {
            showInfoNotice(edtMailAddress);
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
