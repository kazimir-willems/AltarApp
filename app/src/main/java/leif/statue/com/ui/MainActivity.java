package leif.statue.com.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.util.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_menu)
    ImageButton btnMenu;
    @BindView(R.id.btn_start)
    Button btnStart;

    private MediaPlayer player;

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

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            playAudio();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new MediaPlayer();

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
                setSpeedButton();
            }
        });

        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 2;
                setSpeedButton();
            }
        });

        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 3;
                setSpeedButton();
            }
        });

        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 4;
                setSpeedButton();
            }
        });

        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 5;
                setSpeedButton();
            }
        });

        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 6;
                setSpeedButton();
            }
        });

        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 7;
                setSpeedButton();
            }
        });

        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 8;
                setSpeedButton();
            }
        });

        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 9;
                setSpeedButton();
            }
        });

        btnTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curSpeed = 10;
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
                    case R.id.menu_contact_us:
                        intent = new Intent(MainActivity.this, ContactUsActivity.class);

                        startActivity(intent);
                        break;
                    case R.id.menu_terms_of_service:
                        intent = new Intent(MainActivity.this, TermsActivity.class);

                        intent.putExtra("language", SharedPrefManager.getInstance(MainActivity.this).getLanguage());

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

    @OnClick(R.id.btn_bell)
    void onClickBell() {
        playAudio();
    }

    @OnClick(R.id.btn_start)
    void onClickStart() {
        /*bStart = !bStart;
        if(bStart)
            bConstant = true;
        else
            bConstant = false;

        if(!bStart) {
            btnStart.setText(getResources().getString(R.string.btn_start));
            if(player.isPlaying()) {
                player.stop();

                return;
            }
        } else {
            btnStart.setText(getResources().getString(R.string.btn_stop));
            handler.post(runnable);
        }*/
    }

    private void playAudio() {
        if(player.isPlaying())
            player.stop();
//        if(!player.isPlaying()) {
        try {
            player.reset();
            AssetFileDescriptor afd = getAssets().openFd("audio_bass.wav");
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//            player.setPlaybackParams(player.getPlaybackParams().setSpeed(0.2f + 0.16f * curSpeed));
            player.prepare();
            player.start();

            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if(bStart && bConstant) {
                        handler.postDelayed(runnable, 200);
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        }
    }

    public void playWav(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int minBufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
                int bufferSize = 4096;
                AudioTrack at = new AudioTrack(AudioManager.STREAM_MUSIC, 80000, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, minBufferSize, AudioTrack.MODE_STREAM);

                int i = 0;
                byte[] s = new byte[bufferSize];
                try {
                    InputStream fin = getAssets().open("audio_bass.wav");
                    DataInputStream dis = new DataInputStream(fin);

                    at.play();
                    while((i = dis.read(s, 0, bufferSize)) > -1){
                        at.write(s, 0, i);

                    }
                    at.stop();
                    at.release();
                    dis.close();
                    fin.close();

                } catch (FileNotFoundException e) {
                    // TODO
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO
                    e.printStackTrace();
                }

                if(bStart && bConstant) {
                    handler.postDelayed(runnable, 200);
                }
            }
        });
    }
}
