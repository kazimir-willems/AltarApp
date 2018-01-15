package leif.statue.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.model.BuddhistItem;

public class PreviewActivity extends AppCompatActivity {

    private BuddhistItem buddhistItem;

    @BindView(R.id.iv_preview)
    ImageView ivPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ButterKnife.bind(this);

        buddhistItem = (BuddhistItem) getIntent().getSerializableExtra("buddhist");
        ImageLoader.getInstance().displayImage(buddhistItem.getConfirm(), ivPreview);
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
