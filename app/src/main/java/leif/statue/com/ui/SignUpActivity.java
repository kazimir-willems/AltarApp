package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.task.SignUpTask;
import leif.statue.com.util.StringUtil;
import leif.statue.com.vo.SignUpResponseVo;

public class SignUpActivity extends AppCompatActivity {

    private Animation shake;
    private ProgressDialog progressDialog;

    @BindView(R.id.edt_mail_address)
    TextInputEditText edtMailAddress;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.edt_language)
    TextInputEditText edtLanguage;
    @BindView(R.id.edt_prefectures)
    TextInputEditText edtPrefecture;
    @BindView(R.id.edt_age)
    TextInputEditText edtAge;
    @BindView(R.id.gender_spinner)
    Spinner genderSpinner;
    @BindView(R.id.chk_receive_notification)
    CheckBox chkNotice;
    @BindView(R.id.radio_annual_plan)
    RadioButton radioAnnualPlan;
    @BindView(R.id.radio_monthly_plan)
    RadioButton radioMonthlyPlan;

    private String mailAddress;
    private String password;
    private String language;
    private String prefecture;
    private String age;
    private int gender = 1;
    private int isNotice = 0;
    private int plan = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        shake = AnimationUtils.loadAnimation(this, R.anim.edittext_shake);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = i + 1;
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
            }
        });

        radioMonthlyPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plan = 2;
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

    @Subscribe
    public void onSignUpEvent(SignUpEvent event) {
        hideProgressDialog();
        SignUpResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                Intent intent = new Intent(SignUpActivity.this, SelectAltarActivity.class);

                startActivity(intent);
                finish();
            } else {

            }
        } else {

        }
    }

    @OnClick(R.id.btn_signup)
    void onClickSignUp() {
        mailAddress = edtMailAddress.getText().toString();
        password = edtPassword.getText().toString();
        language = edtLanguage.getText().toString();
        prefecture = edtPrefecture.getText().toString();
        age = edtAge.getText().toString();

        if(!checkMailAddress()) return;
        if(!checkPassword()) return;
        if(!checkLanguage()) return;
        if(!checkPrefecture()) return;
        if(!checkAge()) return;

        startSignUp();
    }

    @OnClick(R.id.btn_terms_and_conditions)
    void onClickTerms() {
        Intent intent = new Intent(SignUpActivity.this, TermsActivity.class);

        startActivity(intent);
    }

    private void startSignUp() {
        progressDialog.show();

        SignUpTask task = new SignUpTask();
        task.execute(mailAddress, password, language, prefecture, age, String.valueOf(gender), String.valueOf(isNotice), String.valueOf(plan));
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

    private boolean checkLanguage() {
        if (StringUtil.isEmpty(language)) {
            showInfoNotice(edtLanguage);
            return false;
        }

        return true;
    }

    private boolean checkPrefecture() {
        if (StringUtil.isEmpty(prefecture)) {
            showInfoNotice(edtPrefecture);
            return false;
        }

        return true;
    }

    private boolean checkAge() {
        if (StringUtil.isEmpty(age)) {
            showInfoNotice(edtAge);
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
}
