package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.AltarApplication;
import leif.statue.com.R;
import leif.statue.com.event.LoginEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.task.UploadHonjouTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

public class UploadHonjouActivity extends AppCompatActivity {

    private Uri croppedURI;
    private ProgressDialog progressDialog;

    @BindView(R.id.iv_image)
    ImageView ivImage;

    private int buddhistId;
    private Bitmap bitmap;

    @BindView(R.id.btn_take_photo)
    Button btnTakePhoto;
    @BindView(R.id.btn_upload_own)
    Button btnUploadOwn;
    @BindView(R.id.btn_upload_admin)
    Button btnUploadAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_honjou);

        ButterKnife.bind(this);

        buddhistId = getIntent().getIntExtra("butsugu", 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    @OnClick(R.id.btn_choose_photo)
    void onClickChoosePhoto() {

    }

    @OnClick(R.id.btn_take_photo)
    void onClickTakePhoto() {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @OnClick(R.id.btn_upload_own)
    void onClickUploadOwn() {
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedURI);

            String imgText = getStringImage(bitmap);

            progressDialog.show();

            UploadHonjouTask task = new UploadHonjouTask();
            task.execute(String.valueOf(AltarApplication.userId), imgText, String.valueOf(buddhistId), String.valueOf(0), SharedPrefManager.getInstance(this).getLanguage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_upload_admin)
    void onClickUploadAdmin() {

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
    public void onUploadHonjouEvent(UploadHonjouEvent event) {
        hideProgressDialog();
        UploadHonjouResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                Intent intent = new Intent(UploadHonjouActivity.this, ConfirmActivity.class);

                intent.putExtra("honzon", getStringImage(bitmap));
                intent.putExtra("edit_flag", false);

                startActivity(intent);
                finish();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                croppedURI = result.getUri();

                /*btnTakePhoto.setVisibility(View.GONE);
                btnUploadOwn.setVisibility(View.VISIBLE);
                btnUploadAdmin.setVisibility(View.VISIBLE);*/

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedURI);

                    String imgText = getStringImage(bitmap);

                    progressDialog.show();

                    UploadHonjouTask task = new UploadHonjouTask();
                    task.execute(String.valueOf(AltarApplication.userId), imgText, String.valueOf(buddhistId), String.valueOf(0), SharedPrefManager.getInstance(UploadHonjouActivity.this).getLanguage());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(UploadHonjouActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
