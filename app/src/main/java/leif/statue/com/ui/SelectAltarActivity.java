package leif.statue.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.adapter.AltarAdapter;
import leif.statue.com.model.AltarItem;

public class SelectAltarActivity extends AppCompatActivity {

    private AltarAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.altar_list)
    GridView altarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_altar);

        ButterKnife.bind(this);

        adapter = new AltarAdapter(SelectAltarActivity.this);
        altarList.setAdapter(adapter);

        adapter.addAltarItem(new AltarItem());
        adapter.addAltarItem(new AltarItem());
        adapter.addAltarItem(new AltarItem());

        adapter.notifyDataSetChanged();

        altarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SelectAltarActivity.this, SelectBuddhistActivity.class);

                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }
}
