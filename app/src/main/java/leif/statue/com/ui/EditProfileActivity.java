package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.AltarApplication;
import leif.statue.com.R;
import leif.statue.com.event.EditProfileEvent;
import leif.statue.com.event.SignUpEvent;
import leif.statue.com.task.EditProfileTask;
import leif.statue.com.task.SignUpTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.StringUtil;
import leif.statue.com.vo.EditProfileResponseVo;
import leif.statue.com.vo.SignUpResponseVo;

public class EditProfileActivity extends AppCompatActivity {

    private Animation shake;
    private ProgressDialog progressDialog;

    @BindView(R.id.edt_mail_address)
    TextInputEditText edtMailAddress;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.chk_receive_notification)
    CheckBox chkNotice;
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
    private int selLanguage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ButterKnife.bind(this);

        mailAddress = SharedPrefManager.getInstance(this).getEmailAddress();
        prefecture = SharedPrefManager.getInstance(this).getPrefecture();
        age = SharedPrefManager.getInstance(this).getAge();
        gender = SharedPrefManager.getInstance(this).getGender();
        isNotice = SharedPrefManager.getInstance(this).getNotice();

        prefectureSpinner.setSelection(prefecture - 1);
        ageSpinner.setSelection(age - 1);

        switch(gender) {
            case 1:
                radioMale.setChecked(true);
                break;
            case 2:
                radioFemale.setChecked(true);
                break;
        }

        switch(isNotice) {
            case 1:
                chkNotice.setChecked(true);
                break;
            case 0:
                chkNotice.setChecked(false);
                break;
        }

        edtMailAddress.setText(mailAddress);

        shake = AnimationUtils.loadAnimation(this, R.anim.edittext_shake);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));

        if(SharedPrefManager.getInstance(this).getLanguage().equals("ja")) {
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

                        break;
                    case 1:
                        language = "en";

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
    public void onEditProfileEvent(EditProfileEvent event) {
        hideProgressDialog();
        EditProfileResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                AltarApplication.userId = responseVo.user_id;

                SharedPrefManager.getInstance(this).saveEmailAddress(responseVo.email);
                SharedPrefManager.getInstance(this).saveAge(responseVo.age);
                SharedPrefManager.getInstance(this).savePrefecture(responseVo.prefecture);
                SharedPrefManager.getInstance(this).saveNotice(responseVo.is_notice);
                SharedPrefManager.getInstance(this).saveGender(responseVo.gender);

                SharedPrefManager.getInstance(this).saveLanguage(language);

                Locale locale = new Locale(language);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());

                showSuccessMessage();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @OnClick(R.id.btn_save)
    void onClickSave() {
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

        startEditProfile();
    }

    private void startEditProfile() {
        progressDialog.show();

        EditProfileTask task = new EditProfileTask();
        task.execute(mailAddress, password, language, String.valueOf(prefecture), String.valueOf(age), String.valueOf(gender), String.valueOf(isNotice), SharedPrefManager.getInstance(this).getDeviceToken());
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
        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccessMessage() {
        Toast.makeText(EditProfileActivity.this, R.string.save_profile_successfully, Toast.LENGTH_SHORT).show();
    }
}
