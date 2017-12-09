package leif.statue.com.ui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_menu)
    ImageButton btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_history)
    void onClickHistory() {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);

        startActivity(intent);
    }

    @OnClick(R.id.btn_speed)
    void onClickSpeed() {
        showSpeedAdjustmentDialog();
    }

    private void showSpeedAdjustmentDialog() {
        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        final View resultDialogView = factory.inflate(R.layout.dialog_speed_adjustment, null);

        final AlertDialog infoDialog = new AlertDialog.Builder(MainActivity.this).create();
        infoDialog.setView(resultDialogView);
        resultDialogView.findViewById(R.id.btn_slow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });
        resultDialogView.findViewById(R.id.btn_fast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });

        infoDialog.show();
    }

    @OnClick(R.id.btn_menu)
    void onClickMenu() {
        PopupMenu popup = new PopupMenu(MainActivity.this, btnMenu);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.menu_contact_us:
                        Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);

                        startActivity(intent);
                        break;
                    case R.id.menu_terms_of_service:
                        intent = new Intent(MainActivity.this, TermsActivity.class);

                        startActivity(intent);
                        break;
                    case R.id.menu_about_payment:
                        intent = new Intent(MainActivity.this, AboutPaymentActivity.class);

                        startActivity(intent);
                        break;
                    case R.id.menu_notation:
                        intent = new Intent(MainActivity.this, NotationActivity.class);

                        startActivity(intent);
                        break;
                    case R.id.menu_privacy_policy:
                        intent = new Intent(MainActivity.this, PrivacyActivity.class);

                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }
}
