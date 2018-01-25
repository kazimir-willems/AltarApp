package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vipul.hp_hp.library.Layout_to_Image;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.event.UploadCompleteEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.task.UploadCompleteTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.UploadCompleteResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

public class ConfirmActivity extends AppCompatActivity {

    @BindView(R.id.iv_preview)
    ImageView ivPreview;
    @BindView(R.id.iv_honjou)
    ImageView ivHonjou;
    @BindView(R.id.honzon_layout)
    RelativeLayout honzonLayout;

    private static final String IMAGEVIEW_TAG = "icon bitmap";

    private boolean bEditFlag;

    int windowwidth;
    int windowheight;

    private int _xDelta;
    private int _yDelta;

    Layout_to_Image layoutImage;

    private Bitmap completeHonzon;
    private String strCompleteHonzon;
    private String strHonzon;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        ButterKnife.bind(this);

        bEditFlag = getIntent().getBooleanExtra("edit_flag", false);

        if(!bEditFlag) {
            strHonzon = getIntent().getStringExtra("honzon");
        } else {
            strHonzon = SharedPrefManager.getInstance(this).getHonjou();
        }

        ImageLoader.getInstance().displayImage(SharedPrefManager.getInstance(this).getBuddhistImage(), ivPreview);

        Bitmap bitmap = StringToBitMap(strHonzon);

        ivHonjou.setImageBitmap(bitmap);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivHonjou.getLayoutParams();

        Display display = getWindowManager().getDefaultDisplay();

        layoutParams.leftMargin = display.getWidth() / 2 - 30;

        ivHonjou.setLayoutParams(layoutParams);

        ivHonjou.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();

                switch(event.getAction() & MotionEvent.ACTION_MASK)
                {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) ivHonjou.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivHonjou.getLayoutParams();

                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;

                        ivHonjou.setLayoutParams(layoutParams);

                        break;
                    default:
                        break;
                }
                return true;
            }
        });

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
    public void onUploadCompleteEvent(UploadCompleteEvent event) {
        hideProgressDialog();
        UploadCompleteResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                showCorrectionDialog();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    private void showCorrectionDialog() {
        LayoutInflater factory = LayoutInflater.from(ConfirmActivity.this);
        final View resultDialogView = factory.inflate(R.layout.dialog_correction_info, null);

        final AlertDialog infoDialog = new AlertDialog.Builder(ConfirmActivity.this).create();
        infoDialog.setView(resultDialogView);
        resultDialogView.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(ConfirmActivity.this).saveHonjou(strHonzon);
                SharedPrefManager.getInstance(ConfirmActivity.this).saveCompleteHonzon(strCompleteHonzon);

                Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

                infoDialog.dismiss();
                finish();
            }
        });
        resultDialogView.findViewById(R.id.btn_no_need).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(ConfirmActivity.this).saveHonjou(strHonzon);
                SharedPrefManager.getInstance(ConfirmActivity.this).saveCompleteHonzon(strCompleteHonzon);

                Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

                infoDialog.dismiss();
                finish();
            }
        });

        infoDialog.setCanceledOnTouchOutside(false);
        infoDialog.show();
    }

    @OnClick(R.id.btn_back)
    void onClickFinish() {
        finish();
    }

    @OnClick(R.id.btn_confirm)
    void onClickConfirm() {
        convertImage();

        progressDialog.show();

        UploadCompleteTask task = new UploadCompleteTask();
        task.execute(String.valueOf(SharedPrefManager.getInstance(this).getUserId()), strCompleteHonzon, SharedPrefManager.getInstance(this).getLanguage());
    }

    private void convertImage() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) honzonLayout.getLayoutParams();
        honzonLayout.setDrawingCacheEnabled(true);
        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
//        honzonLayout.measure(honzonLayout.getMeasuredWidth(), honzonLayout.getMeasuredHeight());

        honzonLayout.layout(params.leftMargin, params.topMargin, honzonLayout.getWidth(), honzonLayout.getHeight());

        completeHonzon = Bitmap.createBitmap(honzonLayout.getDrawingCache());
        honzonLayout.setDrawingCacheEnabled(false); // clear drawing cache

        strCompleteHonzon = getStringImage(completeHonzon);
        /*layoutImage = new Layout_to_Image(ConfirmActivity.this, honzonLayout);
        completeHonzon = layoutImage.convert_layout();*/

//        SharedPrefManager.getInstance(this).saveCompleteHonzon(getStringImage(completeHonzon));
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(ConfirmActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}