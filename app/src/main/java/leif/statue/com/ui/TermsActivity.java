package leif.statue.com.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import leif.statue.com.R;
import leif.statue.com.util.SharedPrefManager;
import leif.statue.com.util.URLManager;

public class TermsActivity extends AppCompatActivity {

    @BindView(R.id.content_view)
    WebView contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        ButterKnife.bind(this);

        String url = URLManager.getTermsURL() + SharedPrefManager.getInstance(this).getLanguage();
        contentView.loadUrl(url);
    }

    @OnClick(R.id.btn_back)
    void onClickBack() {
        finish();
    }
}
