package leif.statue.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;

public class AboutPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_payment);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }
}
