package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
import leif.statue.com.adapter.AltarAdapter;
import leif.statue.com.adapter.BuddhistAdapter;
import leif.statue.com.event.GetAltarListEvent;
import leif.statue.com.event.GetBuddhistListEvent;
import leif.statue.com.model.AltarItem;
import leif.statue.com.model.BuddhistItem;
import leif.statue.com.task.GetBuddhistListTask;
import leif.statue.com.vo.GetAltarResponseVo;
import leif.statue.com.vo.GetBuddhistResponseVo;

public class SelectBuddhistActivity extends AppCompatActivity {

    private BuddhistAdapter adapter;

    @BindView(R.id.buddhist_list)
    GridView buddhistList;

    private AltarItem altarItem;

    private ProgressDialog progressDialog;

    private ArrayList<BuddhistItem> buddhistItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_buddhist);

        ButterKnife.bind(this);

        altarItem = (AltarItem) getIntent().getSerializableExtra("altar");

        adapter = new BuddhistAdapter(SelectBuddhistActivity.this);
        buddhistList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        buddhistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BuddhistItem item = buddhistItems.get(i);

                Intent intent = new Intent(SelectBuddhistActivity.this, PreviewActivity.class);

                intent.putExtra("buddhist", item);

                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));

        startGetBuddhist();
    }

    @Subscribe
    public void onGetBuddhistListEvent(GetBuddhistListEvent event) {
        hideProgressDialog();

        GetBuddhistResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                try {
                    JSONArray jsonBuddhistArray = new JSONArray(responseVo.data);

                    for(int i = 0; i < jsonBuddhistArray.length(); i++) {
                        JSONObject jsonBuddhistItem = jsonBuddhistArray.getJSONObject(i);
                        BuddhistItem item = new BuddhistItem();

                        item.setId(jsonBuddhistItem.getInt("id"));
                        item.setUrl(jsonBuddhistItem.getString("url"));
                        item.setButsudan(jsonBuddhistItem.getString("butsudan"));
                        item.setConfirm(jsonBuddhistItem.getString("confirm"));

                        buddhistItems.add(item);
                        adapter.addBuddhistItem(item);
                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            } else {

            }
        } else {

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

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    private void startGetBuddhist() {
        progressDialog.show();

        GetBuddhistListTask task = new GetBuddhistListTask();
        task.execute(altarItem.getTheme());
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
