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
import leif.statue.com.ui.SelectAltarActivity;

public class AltarAdapter extends BaseAdapter {

    private SelectAltarActivity mParent;
    private ArrayList<AltarItem> altarItems = new ArrayList<>();

    public AltarAdapter(SelectAltarActivity parent) {
        this.mParent = parent;
    }

    @Override
    public int getCount() {
        return altarItems.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    public void addAltarItem(AltarItem item) {
        altarItems.add(item);
    }

    public void clear() {
        altarItems.clear();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AltarItem altarItem = altarItems.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mParent);
            convertView = layoutInflater.inflate(R.layout.adapter_altar, null);
        }

        final ImageView ivAltar = (ImageView) convertView.findViewById(R.id.iv_altar);
        final TextView tvTheme = (TextView) convertView.findViewById(R.id.tv_altar_type);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        mParent.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int valueInPixels = (int) mParent.getResources().getDimension(R.dimen.activity_horizontal_margin);

        ViewGroup.LayoutParams layoutParams = ivAltar.getLayoutParams();
        layoutParams.width = (width - valueInPixels * 2 - 96);
        layoutParams.height = layoutParams.width;

        ivAltar.setLayoutParams(layoutParams);

        ImageLoader.getInstance().displayImage(altarItem.getUrl(), ivAltar);
        tvTheme.setText(altarItem.getTheme());

        return convertView;
    }
}
