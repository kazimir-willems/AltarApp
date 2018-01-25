package leif.statue.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.adapter.HistoryAdapter;
import leif.statue.com.db.HistoryDB;
import leif.statue.com.model.CountsItem;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.history_list)
    ListView historyList;

    private ArrayList<CountsItem> historyItems = new ArrayList<>();
    private HistoryAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ButterKnife.bind(this);

//        historyList.setHasFixedSize(true);
//        mLinearLayoutManager = new LinearLayoutManager(HistoryActivity.this);
//        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        historyList.setLayoutManager(mLinearLayoutManager);
//        historyList.addItemDecoration(new DividerItemDecoration(HistoryActivity.this, DividerItemDecoration.VERTICAL_LIST));

        adapter = new HistoryAdapter(HistoryActivity.this);
        historyList.setAdapter(adapter);

        refreshItems();
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    private void refreshItems() {
        HistoryDB historyDB = new HistoryDB(this);
        historyItems = historyDB.fetchLastHistory();

        adapter.addItems(historyItems);
        adapter.notifyDataSetChanged();
    }
}
