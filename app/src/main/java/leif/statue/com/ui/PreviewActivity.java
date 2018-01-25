package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.AltarApplication;
import leif.statue.com.R;
import leif.statue.com.event.LoginEvent;
import leif.statue.com.event.ThemeChangeEvent;
import leif.statue.com.model.BuddhistItem;
import leif.statue.com.task.ThemeChangeTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.ThemeChangeResponseVo;

public class PreviewActivity extends AppCompatActivity {

    private BuddhistItem buddhistItem;

    @BindView(R.id.iv_preview)
    ImageView ivPreview;

    private boolean bEditFlag = false;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ButterKnife.bind(this);

        bEditFlag = getIntent().getBooleanExtra("edit_altar", false);

        buddhistItem = (BuddhistItem) getIntent().getSerializableExtra("buddhist");
        ImageLoader.getInstance().displayImage(buddhistItem.getConfirm(), ivPreview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
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
    public void onThemeChangeEvent(ThemeChangeEvent event) {
        hideProgressDialog();
        ThemeChangeResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                SharedPrefManager.getInstance(this).saveBuddhistImage(buddhistItem.getConfirm());
                SharedPrefManager.getInstance(this).saveMusicLevel(responseVo.music);

                if(!bEditFlag) {
                    Intent intent = new Intent(PreviewActivity.this, UploadHonjouActivity.class);

                    intent.putExtra("butsugu", buddhistItem.getId());

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PreviewActivity.this, ConfirmActivity.class);

                    intent.putExtra("edit_flag", bEditFlag);

                    startActivity(intent);
                }
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

    @OnClick(R.id.btn_confirm_screen)
    void onClickConfirm() {
        startThemeChange();
    }

    private void startThemeChange() {
        progressDialog.show();

        ThemeChangeTask task = new ThemeChangeTask();
        task.execute(String.valueOf(SharedPrefManager.getInstance(this).getUserId()), String.valueOf(buddhistItem.id), SharedPrefManager.getInstance(this).getLanguage());
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(PreviewActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}
