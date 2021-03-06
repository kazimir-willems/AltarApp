package leif.statue.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;

public class ContactConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_confirm);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }
}
