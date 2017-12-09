package leif.statue.com.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import leif.statue.com.R;

public class ConfirmActivity extends AppCompatActivity {

    private Uri croppedUri;
    @BindView(R.id.iv_preview)
    ImageView ivPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        croppedUri = (Uri) getIntent().getParcelableExtra("cropped_uri");

        ButterKnife.bind(this);

        showCorrectionDialog();
    }

    private void showCorrectionDialog() {
        LayoutInflater factory = LayoutInflater.from(ConfirmActivity.this);
        final View resultDialogView = factory.inflate(R.layout.dialog_correction_info, null);

        final AlertDialog infoDialog = new AlertDialog.Builder(ConfirmActivity.this).create();
        infoDialog.setView(resultDialogView);
        resultDialogView.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);

                startActivity(intent);

                infoDialog.dismiss();
                finish();
            }
        });
        resultDialogView.findViewById(R.id.btn_no_need).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });

        infoDialog.setCanceledOnTouchOutside(false);
        infoDialog.show();
    }
}