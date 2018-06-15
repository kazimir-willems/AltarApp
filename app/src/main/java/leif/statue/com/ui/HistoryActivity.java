package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.adapter.HistoryAdapter;
import leif.statue.com.db.HistoryDB;
import leif.statue.com.event.SaveHistoryEvent;
import leif.statue.com.event.UploadHonjouEvent;
import leif.statue.com.model.CountsItem;
import leif.statue.com.task.SaveHistoryTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.SaveHistoryResponseVo;
import leif.statue.com.vo.UploadHonjouResponseVo;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.history_list)
    ListView historyList;

    private ArrayList<CountsItem> historyItems = new ArrayList<>();
    private HistoryAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    private ProgressDialog progressDialog;

    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

        adapter = new HistoryAdapter(HistoryActivity.this);
        historyList.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));
        progressDialog.setCanceledOnTouchOutside(false);

        refreshItems();
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
    public void onSaveHistoryEvent(SaveHistoryEvent event) {
        hideProgressDialog();
        SaveHistoryResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                if(responseVo.overflow == 0) {
                    Toast.makeText(HistoryActivity.this, R.string.save_profile_successfully, Toast.LENGTH_SHORT).show();
                } else if(responseVo.overflow == 1) {
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(HistoryActivity.this);

                    builder.setMessage(R.string.msg_overwrite)
                            .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startSaveHistory(1);
                                }
                            })
                            .setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
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

    @OnClick(R.id.btn_send)
    void onClickSend() {
        JSONArray jsonArray = new JSONArray();
        try {
            for(int i = 0; i < historyItems.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("count", historyItems.get(i).getCounts());
                jsonObject.put("date", historyItems.get(i).getDate());

                jsonArray.put(jsonObject);
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        data = jsonArray.toString();
        Log.v("History Data", data);

        startSaveHistory(0);
    }

    private void startSaveHistory(int overflow) {
        progressDialog.show();

        SaveHistoryTask task = new SaveHistoryTask();
        task.execute(String.valueOf(SharedPrefManager.getInstance(this).getUserId()), SharedPrefManager.getInstance(this).getLanguage(), data, String.valueOf(overflow));
    }

    private void refreshItems() {
        HistoryDB historyDB = new HistoryDB(this);
        historyItems = historyDB.fetchLastHistory();

        adapter.addItems(historyItems);
        adapter.notifyDataSetChanged();
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(HistoryActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
