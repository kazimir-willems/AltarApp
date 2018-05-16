package leif.statue.com.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.db.HistoryDB;
import leif.statue.com.event.CancelMembershipEvent;
import leif.statue.com.event.CheckUpdatedHonzonEvent;
import leif.statue.com.model.CountsItem;
import leif.statue.com.task.CancelMembershipTask;
import leif.statue.com.task.CheckUpdatedHonzonTask;
import leif.statue.com.util.DateUtil;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.CancelMembershipResponseVo;
import leif.statue.com.vo.CheckUpdatedHonzonResponseVo;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_menu)
    ImageButton btnMenu;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_altar)
    ImageView ivAltar;
    @BindView(R.id.iv_honjou)
    ImageView ivHonjou;

    private static final String IMAGEVIEW_TAG = "icon bitmap";

    int windowwidth;
    int windowheight;

    private MediaPlayer player;
    private String curDate;

    private boolean bConstant = false;
    private boolean bStart = false;

    private boolean bSpeedShow = false;
    private boolean bSpeedDialogShow = false;

    private Handler speedHandler = new Handler();
    private Runnable speedRunnable = new Runnable() {
        @Override
        public void run() {
            if(bSpeedDialogShow) {
                if (!bSpeedShow) {
                    switch (curSpeed) {
                        case 1:
                            btnOne.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnOne.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 2:
                            btnTwo.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnTwo.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 3:
                            btnThree.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnThree.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 4:
                            btnFour.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnFour.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 5:
                            btnFive.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnFive.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 6:
                            btnSix.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnSix.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 7:
                            btnSeven.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnSeven.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 8:
                            btnEight.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnEight.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 9:
                            btnNine.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnNine.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                        case 10:
                            btnTen.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_selected_speed));
                            btnTen.setTextColor(getResources().getColor(R.color.colorWhite));
                            break;
                    }

                    bSpeedShow = !bSpeedShow;
                } else {
                    switch (curSpeed) {
                        case 1:
                            btnOne.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnOne.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 2:
                            btnTwo.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnTwo.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 3:
                            btnThree.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnThree.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 4:
                            btnFour.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnFour.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 5:
                            btnFive.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnFive.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 6:
                            btnSix.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnSix.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 7:
                            btnSeven.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnSeven.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 8:
                            btnEight.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnEight.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 9:
                            btnNine.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnNine.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                        case 10:
                            btnTen.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
                            btnTen.setTextColor(getResources().getColor(R.color.colorBlack));
                            break;
                    }

                    bSpeedShow = !bSpeedShow;
                }

                if(!bSpeedShow)
                    speedHandler.postDelayed(speedRunnable, 200);
                else
                    speedHandler.postDelayed(speedRunnable, 200 + 160 * (10 - curSpeed));
            }
        }
    };

    private int count;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            playAudio();
        }
    };

    private Handler countHandler = new Handler();
    private Runnable countRunnable = new Runnable() {
        @Override
        public void run() {
            increaseCount();
        }
    };

    private int curSpeed = 5;
    private int tempSpeed = 5;

    private TextView btnOne;
    private TextView btnTwo;
    private TextView btnThree;
    private TextView btnFour;
    private TextView btnFive;
    private TextView btnSix;
    private TextView btnSeven;
    private TextView btnEight;
    private TextView btnNine;
    private TextView btnTen;

    private HistoryDB historyDB;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new MediaPlayer();

        ButterKnife.bind(this);

        curSpeed = SharedPrefManager.getInstance(this).getSpeed();

        historyDB = new HistoryDB(this);
        curDate = DateUtil.getCurDate();
        count = historyDB.fetchItemByDate(curDate).get(0).getCounts();

        String formatted = String.format(getResources().getString(R.string.count_format), count);

        tvDate.setText(DateUtil.getCurDate());
        tvCount.setText(formatted);

//        ImageLoader.getInstance().displayImage(SharedPrefManager.getInstance(this).getBuddhistImage(), ivAltar);
        Bitmap bitmap = StringToBitMap(SharedPrefManager.getInstance(this).getCompleteHonzon());

        ivAltar.setImageBitmap(bitmap);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        Log.v("MainActivity", "Resumed");
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onCancelMembershipEvent(CancelMembershipEvent event) {
        hideProgressDialog();
        CancelMembershipResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                SharedPrefManager.getInstance(MainActivity.this).saveLogin(false);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            } else {
                showErrorMessage(responseVo.error_msg);
            }
        } else {

        }
    }

    @Subscribe
    public void onCheckUpdatedHonzonEvent(CheckUpdatedHonzonEvent event) {
        hideProgressDialog();
        CheckUpdatedHonzonResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                if(responseVo.is_update == 1) {
                    Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);

                    intent.putExtra("update_honzon", true);

                    startActivity(intent);
                } else {
                    startCount();
                }
            } else {
                startCount();
            }
        } else {
            startCount();
        }
    }

    @OnClick(R.id.btn_inc_one)
    void onClickIncOne() {
        incOne();
    }

    @OnClick(R.id.btn_dec_one)
    void onClickDecOne() {
        decOne();
    }

    @OnClick(R.id.btn_inc_ten)
    void onClickIncTen() {
        incTen();
    }

    @OnClick(R.id.btn_dec_ten)
    void onClickDecTen() {
        decTen();
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
        tempSpeed = curSpeed;

        bSpeedDialogShow = true;

        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        final View speedDialogView = factory.inflate(R.layout.dialog_speed_adjustment, null);

        btnOne = (TextView) speedDialogView.findViewById(R.id.tv_one);
        btnTwo = (TextView) speedDialogView.findViewById(R.id.tv_two);
        btnThree = (TextView) speedDialogView.findViewById(R.id.tv_three);
        btnFour = (TextView) speedDialogView.findViewById(R.id.tv_four);
        btnFive = (TextView) speedDialogView.findViewById(R.id.tv_five);
        btnSix = (TextView) speedDialogView.findViewById(R.id.tv_six);
        btnSeven = (TextView) speedDialogView.findViewById(R.id.tv_seven);
        btnEight = (TextView) speedDialogView.findViewById(R.id.tv_eight);
        btnNine = (TextView) speedDialogView.findViewById(R.id.tv_nine);
        btnTen = (TextView) speedDialogView.findViewById(R.id.tv_ten);

        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 1;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 2;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 3;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 4;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 5;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 6;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 7;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 8;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 9;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        btnTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 10;
                SharedPrefManager.getInstance(MainActivity.this).saveSpeed(curSpeed);
                setSpeedButton();
            }
        });

        final AlertDialog infoDialog = new AlertDialog.Builder(MainActivity.this).create();
        infoDialog.setView(speedDialogView);
        speedDialogView.findViewById(R.id.btn_slow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curSpeed == 1)
                    return;
                curSpeed--;

                setSpeedButton();
            }
        });
        speedDialogView.findViewById(R.id.btn_fast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curSpeed == 10)
                    return;
                curSpeed++;

                setSpeedButton();
            }
        });

        infoDialog.setButton(Dialog.BUTTON_NEGATIVE, getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                curSpeed = tempSpeed;
                infoDialog.dismiss();
            }
        });

        infoDialog.setButton(Dialog.BUTTON_POSITIVE, getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                infoDialog.dismiss();
            }
        });

        infoDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bSpeedDialogShow = false;
            }
        });

        setSpeedButton();

        infoDialog.show();
        speedHandler.post(speedRunnable);
    }

    private void setSpeedButton() {
        btnOne.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnTwo.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnThree.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnFour.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnFive.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnSix.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnSeven.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnEight.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnNine.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));
        btnTen.setBackgroundColor(getResources().getColor(R.color.colorTransWhite));

        btnOne.setTextColor(getResources().getColor(R.color.colorBlack));
        btnTwo.setTextColor(getResources().getColor(R.color.colorBlack));
        btnThree.setTextColor(getResources().getColor(R.color.colorBlack));
        btnFour.setTextColor(getResources().getColor(R.color.colorBlack));
        btnFive.setTextColor(getResources().getColor(R.color.colorBlack));
        btnSix.setTextColor(getResources().getColor(R.color.colorBlack));
        btnSeven.setTextColor(getResources().getColor(R.color.colorBlack));
        btnEight.setTextColor(getResources().getColor(R.color.colorBlack));
        btnNine.setTextColor(getResources().getColor(R.color.colorBlack));
        btnTen.setTextColor(getResources().getColor(R.color.colorBlack));
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
                    case R.id.menu_edit_profile:
                        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);

                        startActivity(intent);
                        break;
                    case R.id.menu_edit_image:
                        intent = new Intent(MainActivity.this, UploadHonjouActivity.class);

                        intent.putExtra("butsugu", SharedPrefManager.getInstance(MainActivity.this).getBuddhistId());

                        startActivity(intent);
                        break;
                    case R.id.menu_edit_theme:
                        intent = new Intent(MainActivity.this, SelectAltarActivity.class);

                        intent.putExtra("edit_altar", true);

                        startActivity(intent);

                        break;
                    case R.id.menu_contact_us:
                        intent = new Intent(MainActivity.this, ContactUsActivity.class);

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
                    case R.id.menu_logout:
                        startLogout(false);
                        break;
                    case R.id.menu_cancel_membership:
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(MainActivity.this);

                        builder.setMessage(R.string.msg_cancel_membership)
                                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog.show();

                                        CancelMembershipTask task = new CancelMembershipTask();
                                        task.execute(String.valueOf(SharedPrefManager.getInstance(MainActivity.this).getUserId()), SharedPrefManager.getInstance(MainActivity.this).getLanguage());
                                    }
                                })
                                .setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                        break;
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    private void startLogout(boolean expire) {
        SharedPrefManager.getInstance(MainActivity.this).saveLogin(false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("expire_flag", expire);
        intent.putExtra("logout_flag", true);

        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_bell)
    void onClickBell() {
        playAudio();
    }

    @OnClick(R.id.btn_start)
    void onClickStart() {
        if (!bStart) {
            if(SharedPrefManager.getInstance(this).getHonzonUpdate()) {
                checkUpdatedHonzon();
            } else {
                startCount();
            }
        } else {
            startCount();
        }
    }

    private void checkUpdatedHonzon() {
        progressDialog.show();

        CheckUpdatedHonzonTask task = new CheckUpdatedHonzonTask();
        task.execute(String.valueOf(SharedPrefManager.getInstance(this).getUserId()), SharedPrefManager.getInstance(this).getLanguage());
    }

    private void startCount() {
        bStart = !bStart;
        if (bStart)
            bConstant = true;
        else
            bConstant = false;

        if (!bStart) {
            btnStart.setText(getResources().getString(R.string.btn_start));
        } else {
            if(DateUtil.getDiffTime(SharedPrefManager.getInstance(this).getLoginTime(), new Date().getTime()) / 60 / 24 >= 7) {
                startLogout(true);
                return;
            }
            btnStart.setText(getResources().getString(R.string.btn_stop));
            countHandler.postDelayed(countRunnable, 200 + 160 * (10 - curSpeed) + 200);
        }
    }

    private void playAudio() {
        String audioFileName = "low_sound.wav";
        if(SharedPrefManager.getInstance(this).getMusicLevel() == 1) {
            audioFileName = "low_sound.wav";
        } else {
            audioFileName = "high_sound.wav";
        }
        if(player.isPlaying())
            player.stop();
        try {
            player.reset();
            AssetFileDescriptor afd = getAssets().openFd(audioFileName);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void increaseCount() {
        if(bStart && bConstant) {
            count++;
            countHandler.postDelayed(countRunnable, 200 + 160 * (10 - curSpeed) + 200);

            CountsItem item = new CountsItem(curDate, count);
            historyDB.updateItem(item);

            String formatted = String.format(getResources().getString(R.string.count_format), count);

            tvCount.setText(formatted);
        }
    }

    private void incOne() {
        count++;
        if(count >= 9999999)
            count = 9999999;

        CountsItem item = new CountsItem(curDate, count);
        historyDB.updateItem(item);

        String formatted = String.format(getResources().getString(R.string.count_format), count);

        tvCount.setText(formatted);
    }

    private void decOne() {
        count--;
        if(count <= 0)
            count = 0;

        CountsItem item = new CountsItem(curDate, count);
        historyDB.updateItem(item);

        String formatted = String.format(getResources().getString(R.string.count_format), count);

        tvCount.setText(formatted);
    }

    private void incTen(){
        count = count + 10;
        if(count >= 9999999)
            count = 9999999;

        CountsItem item = new CountsItem(curDate, count);
        historyDB.updateItem(item);

        String formatted = String.format(getResources().getString(R.string.count_format), count);

        tvCount.setText(formatted);
    }

    private void decTen() {
        count = count - 10;
        if(count <= 0)
            count = 0;
        CountsItem item = new CountsItem(curDate, count);
        historyDB.updateItem(item);

        String formatted = String.format(getResources().getString(R.string.count_format), count);

        tvCount.setText(formatted);
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
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
