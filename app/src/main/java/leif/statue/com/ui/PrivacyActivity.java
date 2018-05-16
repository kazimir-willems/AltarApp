package leif.statue.com.ui;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.URLManager;

public class PrivacyActivity extends AppCompatActivity {

    @BindView(R.id.content_view)
    WebView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        ButterKnife.bind(this);

        String url = URLManager.getPrivacyURL() + SharedPrefManager.getInstance(this).getLanguage();
        contentView.loadUrl(url);
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();

        Locale locale = new Locale(SharedPrefManager.getInstance(this).getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
