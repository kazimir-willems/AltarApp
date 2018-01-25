package leif.statue.com.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import leif.statue.com.event.GetAltarListEvent;
import leif.statue.com.event.LoginEvent;
import leif.statue.com.model.AltarItem;
import leif.statue.com.task.GetAltarListTask;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.vo.GetAltarResponseVo;
import leif.statue.com.vo.LoginResponseVo;

public class SelectAltarActivity extends AppCompatActivity {

    private AltarAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    private ArrayList<AltarItem> altarItems = new ArrayList<>();

    private ProgressDialog progressDialog;

    private boolean bEditFlag = false;

    @BindView(R.id.altar_list)
    GridView altarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_altar);

        ButterKnife.bind(this);

        bEditFlag = getIntent().getBooleanExtra("edit_altar", false);

        adapter = new AltarAdapter(SelectAltarActivity.this);
        altarList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        altarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AltarItem item = altarItems.get(i);

                Intent intent = new Intent(SelectAltarActivity.this, SelectBuddhistActivity.class);

                intent.putExtra("edit_altar", bEditFlag);
                intent.putExtra("altar", item);

                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.str_processing));

        startGetAltarList();
    }

    @Subscribe
    public void onGetAltarListEvent(GetAltarListEvent event) {
        hideProgressDialog();

        GetAltarResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            if(responseVo.success == 1) {
                try {
                    JSONArray jsonAltarArray = new JSONArray(responseVo.data);

                    for(int i = 0; i < jsonAltarArray.length(); i++) {
                        JSONObject jsonAltarItem = jsonAltarArray.getJSONObject(i);
                        AltarItem item = new AltarItem();

                        item.setId(jsonAltarItem.getInt("id"));
                        item.setUrl(jsonAltarItem.getString("url"));
                        item.setTheme(jsonAltarItem.getString("theme"));
                        item.setUid(jsonAltarItem.getString("theme_uid"));

                        altarItems.add(item);
                        adapter.addAltarItem(item);
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

    private void startGetAltarList() {
        progressDialog.show();

        GetAltarListTask task = new GetAltarListTask();
        task.execute(SharedPrefManager.getInstance(this).getLanguage());
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
