package leif.statue.com.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.adapter.AltarAdapter;
import leif.statue.com.adapter.BuddhistAdapter;
import leif.statue.com.model.AltarItem;
import leif.statue.com.model.BuddhistItem;

public class SelectBuddhistActivity extends AppCompatActivity {

    private BuddhistAdapter adapter;

    @BindView(R.id.buddhist_list)
    GridView buddhistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_buddhist);

        ButterKnife.bind(this);

        adapter = new BuddhistAdapter(SelectBuddhistActivity.this);
        buddhistList.setAdapter(adapter);

        adapter.addAltarItem(new BuddhistItem());
        adapter.addAltarItem(new BuddhistItem());
        adapter.addAltarItem(new BuddhistItem());

        adapter.notifyDataSetChanged();

        buddhistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SelectBuddhistActivity.this, PreviewActivity.class);

                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }
}
