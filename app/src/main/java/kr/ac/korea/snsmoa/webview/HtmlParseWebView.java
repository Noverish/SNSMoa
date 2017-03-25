package kr.ac.korea.snsmoa.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import kr.ac.korea.snsmoa.asynctask.RunnableAsyncTask;

/**
 * Created by Noverish on 2017-03-21.
 */

public abstract class HtmlParseWebView extends WebView {
    public HtmlParseWebView(Context context) {
        super(context);
        init();
    }

    public HtmlParseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        getSettings().setJavaScriptEnabled(true);

        WebSettings webSettings = getSettings();
        webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 4.4.2; IM-A920S Build/KVT49L) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.85 Mobile Safari/537.36");
        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(getContext())
                        .setTitle("AlertDialog")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
        });
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {}
    public void onPageFinished(WebView view, String url) {}
    public void onLoadResource(WebView view, String url) {}
    public void onHtmlLoaded(WebView view, String html) {}

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            HtmlParseWebView.this.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            HtmlParseWebView.this.onPageFinished(view, url);
            extractHtmlAsync();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            HtmlParseWebView.this.onLoadResource(view, url);
        }
    }

    @Override
    public void loadUrl(String url) {
        setWebViewClient(new CustomWebViewClient());
        super.loadUrl(url);
    }

    public void extractHtmlAsync() {
        System.out.println("extractHtmlAsync start " + getClass().getSimpleName());
        evaluateJavascript(
                "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String html) {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                String html = params[0];
                                html = html.replaceAll("(\\\\){1,7}\"", "\"");
                                html = html.replaceAll("&amp;", "&");
                                html = html.replaceAll("(\\\\){0,2}&quot;", "\"");
                                html = html.replaceAll("(\\\\){2,3}x3C", "<");
                                html = html.replaceAll("(\\\\){1,2}u003C", "<");
                                html = html.replaceAll("(\\\\){1,2}u003E", ">");
                                html = html.replaceAll("(\\\\){1,2}/", "/");
                                return html;
                            }

                            @Override
                            protected void onPostExecute(String html) {
                                onHtmlLoaded(HtmlParseWebView.this, html);
                            }
                        }.execute(html);
                    }
                });
    }

    public void scrollBottom() {
        setWebViewClient(new CustomWebViewClient());
        scrollTo(0, computeVerticalScrollRange());
    }
}
