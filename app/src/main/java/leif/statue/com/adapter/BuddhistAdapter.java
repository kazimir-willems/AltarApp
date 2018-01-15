package leif.statue.com.adapter;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import leif.statue.com.R;
import leif.statue.com.model.AltarItem;
import leif.statue.com.model.BuddhistItem;
import leif.statue.com.ui.SelectAltarActivity;
import leif.statue.com.ui.SelectBuddhistActivity;

public class BuddhistAdapter extends BaseAdapter {

    private SelectBuddhistActivity mParent;
    private ArrayList<BuddhistItem> buddhistItems = new ArrayList<>();

    public BuddhistAdapter(SelectBuddhistActivity parent) {
        this.mParent = parent;
    }

    @Override
    public int getCount() {
        return buddhistItems.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public void addBuddhistItem(BuddhistItem item) {
        buddhistItems.add(item);
    }

    public void clear() {
        buddhistItems.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BuddhistItem buddhistItem = buddhistItems.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mParent);
            convertView = layoutInflater.inflate(R.layout.adapter_buddhist, null);
        }

        final ImageView ivBuddhist = (ImageView) convertView.findViewById(R.id.iv_buddhist);
        final TextView tvBuddhist = (TextView) convertView.findViewById(R.id.tv_buddhist_type);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mParent.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int valueInPixels = (int) mParent.getResources().getDimension(R.dimen.activity_horizontal_margin);

        ViewGroup.LayoutParams layoutParams = ivBuddhist.getLayoutParams();
        layoutParams.width = (width - valueInPixels * 2 - 96);
        layoutParams.height = layoutParams.width;

        ivBuddhist.setLayoutParams(layoutParams);

        tvBuddhist.setText(buddhistItem.getButsudan());
        ImageLoader.getInstance().displayImage(buddhistItem.getUrl(), ivBuddhist);

        return convertView;
    }
}
