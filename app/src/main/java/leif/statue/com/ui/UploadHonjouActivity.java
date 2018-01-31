package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
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
import leif.statue.com.consts.CommonConsts;
import leif.statue.com.event.PayTotalEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.task.PayTotalTask;
import leif.statue.com.task.UploadHonjouTask;
import leif.statue.com.util.DateUtil;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.PayTotalResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

public class UploadHonjouActivity extends AppCompatActivity {

    private Uri croppedURI;

    private int buddhistId;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_honjou);

        ButterKnife.bind(this);

        buddhistId = getIntent().getIntExtra("butsugu", 0);
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    @OnClick(R.id.btn_take_photo)
    void onClickTakePhoto() {
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                croppedURI = result.getUri();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedURI);

                    Intent intent = new Intent(UploadHonjouActivity.this, ConfirmActivity.class);

                    SharedPrefManager.getInstance(this).saveHonjou(getStringImage(bitmap));

                    intent.putExtra("edit_flag", false);

                    startActivity(intent);

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
}
