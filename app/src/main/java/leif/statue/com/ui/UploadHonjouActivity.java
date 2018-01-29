package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.anjlab.android.iab.v3.SkuDetails;
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
import leif.statue.com.event.LoginEvent;
import leif.statue.com.event.PayTotalEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.task.PayTotalTask;
import leif.statue.com.task.UploadHonjouTask;
import leif.statue.com.util.DateUtil;
import leif.statue.com.util.IabHelper;
import leif.statue.com.util.IabResult;
import leif.statue.com.util.Inventory;
import leif.statue.com.util.Purchase;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.LoginResponseVo;
import leif.statue.com.vo.PayTotalResponseVo;
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

    IabHelper mHelper;

    private boolean bSubscribed = false;

    private static final int RC_REQUEST = 10001;

    private String subscriptionID = CommonConsts.PRODUCT_ANNUAL_PLAN;

    BillingProcessor bp;

    private int modifyFlag = 0;
//    IabBroadcastReceiver mBroadcastReceiver;

    private static final String MERCHANT_ID=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_honjou);

        ButterKnife.bind(this);

        buddhistId = getIntent().getIntExtra("butsugu", 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
        progressDialog.setCanceledOnTouchOutside(false);

        if(SharedPrefManager.getInstance(this).getPlan() == 1) {
            subscriptionID = CommonConsts.PRODUCT_ANNUAL_PLAN;
        } else if (SharedPrefManager.getInstance(this).getPlan() == 2) {
            subscriptionID = CommonConsts.PRODUCT_MONTHLY_PLAN;
        }

        if(!BillingProcessor.isIabServiceAvailable(this)) {
            Log.v("STAW", "In-app billing service is unavailable, please upgrade Android Market/Play to version >= 3.9.16");
        }

        bp = new BillingProcessor(this, CommonConsts.IN_APP_BILLING_RSA_KEY, MERCHANT_ID, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {
                progressDialog.show();

                PayTotalTask task = new PayTotalTask();
                task.execute(String.valueOf(SharedPrefManager.getInstance(UploadHonjouActivity.this).getUserId()), SharedPrefManager.getInstance(UploadHonjouActivity.this).getLanguage(), DateUtil.timeStampToDate(details.purchaseInfo.purchaseData.purchaseTime), details.purchaseInfo.purchaseData.orderId, String.valueOf(SharedPrefManager.getInstance(UploadHonjouActivity.this).getPlan()));

                bSubscribed = true;
            }
            @Override
            public void onBillingError(int errorCode, @Nullable Throwable error) {
                Log.v("STAW", String.valueOf(errorCode));
            }
            @Override
            public void onBillingInitialized() {
                Log.v("STAW", "Initialized");
                TransactionDetails subAnnual = bp.getSubscriptionTransactionDetails(CommonConsts.PRODUCT_ANNUAL_PLAN);
                TransactionDetails subMonthly = bp.getSubscriptionTransactionDetails(CommonConsts.PRODUCT_MONTHLY_PLAN);

                if(subAnnual != null || subMonthly != null) {
                    bSubscribed = true;
                }
            }
            @Override
            public void onPurchaseHistoryRestored() {
                Log.v("STAW", "HISTORY");
            }
        });
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
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
        modifyFlag = 0;
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
        modifyFlag = 1;
        if(bSubscribed) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedURI);

                String imgText = getStringImage(bitmap);

                progressDialog.show();

                UploadHonjouTask task = new UploadHonjouTask();
                task.execute(String.valueOf(AltarApplication.userId), imgText, String.valueOf(buddhistId), String.valueOf(1), SharedPrefManager.getInstance(this).getLanguage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bp.subscribe(this, subscriptionID);
        }
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
                if(modifyFlag == 0) {
                    Intent intent = new Intent(UploadHonjouActivity.this, ConfirmActivity.class);

                    SharedPrefManager.getInstance(this).saveHonjou(getStringImage(bitmap));
//                intent.putExtra("honzon", getStringImage(bitmap));
                    intent.putExtra("edit_flag", false);

                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(UploadHonjouActivity.this, MainActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @Subscribe
    public void onPaytotalEvent(PayTotalEvent event) {
        hideProgressDialog();
        PayTotalResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                btnUploadAdmin.performClick();
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

                btnTakePhoto.setVisibility(View.GONE);
                btnUploadOwn.setVisibility(View.VISIBLE);
                btnUploadAdmin.setVisibility(View.VISIBLE);

                /*try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), croppedURI);

                    String imgText = getStringImage(bitmap);

                    progressDialog.show();

                    UploadHonjouTask task = new UploadHonjouTask();
                    task.execute(String.valueOf(AltarApplication.userId), imgText, String.valueOf(buddhistId), String.valueOf(0), SharedPrefManager.getInstance(UploadHonjouActivity.this).getLanguage());

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

            }
        }

        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
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
