package leif.statue.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    @OnClick(R.id.btn_confirm_screen)
    void onClickConfirm() {
        Intent intent = new Intent(PreviewActivity.this, UploadHonjouActivity.class);

        startActivity(intent);
    }
}
