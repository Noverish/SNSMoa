package kr.ac.korea.snsmoa.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import kr.ac.korea.intelligentgallery.R;


/**
 * Created by Noverish on 2016-09-18.
 */
public class WebViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        HtmlParseWebView webView = (HtmlParseWebView) findViewById(R.id.activity_web_view);
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}
