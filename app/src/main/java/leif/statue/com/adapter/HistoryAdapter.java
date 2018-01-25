package leif.statue.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import leif.statue.com.R;
import leif.statue.com.model.AltarItem;
import leif.statue.com.model.CountsItem;
import leif.statue.com.ui.HistoryActivity;

public class HistoryAdapter extends BaseAdapter {

    private HistoryActivity parent;
    private ArrayList<CountsItem> items = new ArrayList<>();

    public HistoryAdapter(HistoryActivity parent) {
        this.parent = parent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CountsItem item = items.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.adapter_history, null);
        }

        final TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        final TextView tvCount = (TextView) convertView.findViewById(R.id.tv_count);

        tvDate.setText(item.getDate());

        String formatted = String.format(parent.getResources().getString(R.string.count_format), item.getCounts());

        tvCount.setText(formatted);

        return convertView;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public CountsItem getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(CountsItem item) {
        items.add(item);
    }

    public void addItems(ArrayList<CountsItem> items) {
        this.items = items;
    }
}
