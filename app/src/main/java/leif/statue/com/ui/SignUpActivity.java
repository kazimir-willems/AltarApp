package leif.statue.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_signup)
    void onClickSignUp() {
        Intent intent = new Intent(SignUpActivity.this, SelectAltarActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.btn_terms_and_conditions)
    void onClickTerms() {
        Intent intent = new Intent(SignUpActivity.this, TermsActivity.class);

        startActivity(intent);
    }
}
