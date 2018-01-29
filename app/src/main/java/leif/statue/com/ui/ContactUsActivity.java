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
import leif.statue.com.event.ContactUsEvent;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.task.ContactUsTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.StringUtil;
import leif.statue.com.vo.ContactUsResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class ContactUsActivity extends AppCompatActivity {

    private Animation shake;
    private ProgressDialog progressDialog;

    @BindView(R.id.edt_mail_address)
    TextInputEditText edtMailAddress;
    @BindView(R.id.edt_reenter_mail_address)
    TextInputEditText edtReMailAddress;
    @BindView(R.id.edt_name)
    TextInputEditText edtName;
    @BindView(R.id.edt_content)
    TextInputEditText edtContent;

    private String mailAddress;
    private String reMailAddress;
    private String name;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ButterKnife.bind(this);

        shake = AnimationUtils.loadAnimation(this, R.anim.edittext_shake);
        progressDialog = new ProgressDialog(this);
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

    @Subscribe
    public void onContactUsEvent(ContactUsEvent event) {
        hideProgressDialog();
        ContactUsResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                Intent intent = new Intent(ContactUsActivity.this, ContactConfirmActivity.class);

                startActivity(intent);

                finish();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    @OnClick(R.id.btn_send)
    void onClickSend() {
        name = edtName.getText().toString();
        mailAddress = edtMailAddress.getText().toString();
        reMailAddress = edtReMailAddress.getText().toString();
        content = edtContent.getText().toString();

        if(!checkName()) return;
        if(!checkMailAddress()) return;
        if(!checkConfirmMailAddress()) return;
        if(!checkContent()) return;
        if(!mailAddress.equals(reMailAddress)) {
            showInfoNotice(edtMailAddress);
            return;
        }

        progressDialog.show();

        ContactUsTask task = new ContactUsTask();
        task.execute(name, mailAddress, SharedPrefManager.getInstance(this).getLanguage(), content);
    }

    private boolean checkName() {
        if (StringUtil.isEmpty(name)) {
            showInfoNotice(edtName);
            return false;
        }

        return true;
    }

    private boolean checkMailAddress() {
        if (StringUtil.isEmpty(mailAddress)) {
            showInfoNotice(edtMailAddress);
            return false;
        }

        return true;
    }

    private boolean checkConfirmMailAddress() {
        if (StringUtil.isEmpty(reMailAddress)) {
            showInfoNotice(edtReMailAddress);
            return false;
        }

        return true;
    }

    private boolean checkContent() {
        if (StringUtil.isEmpty(content)) {
            showInfoNotice(edtContent);
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
        Toast.makeText(ContactUsActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccessMessage() {
        Toast.makeText(ContactUsActivity.this, R.string.sent_content_successfully, Toast.LENGTH_SHORT).show();
    }
}
