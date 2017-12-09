package leif.statue.com.ui;

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
import android.widget.Spinner;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.StringUtil;

public class LoginActivity extends AppCompatActivity {

    private Animation shake;

    private String id;
    private String password;

    @BindView(R.id.edt_id)
    TextInputEditText edtID;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.language_spinner)
    Spinner languageSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.edittext_shake);

        if(SharedPrefManager.getInstance(this).getFirstLogin()) {
            String currentLanguage = Locale.getDefault().getLanguage();
            SharedPrefManager.getInstance(this).saveLanguage(Locale.getDefault().getLanguage());
        }

        languageSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 0:
                        Locale locale = new Locale("ja");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());

                        break;
                    case 1:
                        locale = new Locale("en");
                        Locale.setDefault(locale);
                        config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config,
                                getBaseContext().getResources().getDisplayMetrics());

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String curLanguage = SharedPrefManager.getInstance(this).getLanguage();
        if(curLanguage.equals("en")) {
            languageSpin.setSelection(1);
        } else if (curLanguage.equals("ja")) {
            languageSpin.setSelection(0);
        }
    }

    @OnClick(R.id.btn_signup)
    void onClickSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void onClickLogin() {
        id = edtID.getText().toString();
        password = edtPassword.getText().toString();

        if(!checkID()) return;
        if(!mailAddressValidate()) return;
        if(!checkPassword()) return;

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        startActivity(intent);
        finish();
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
}