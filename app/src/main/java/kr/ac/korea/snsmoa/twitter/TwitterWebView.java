package kr.ac.korea.snsmoa.twitter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.util.ArrayList;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.asynctask.RunnableAsyncTask;
import kr.ac.korea.snsmoa.webview.HtmlParseWebView;


/**
 * Created by Noverish on 2017-03-21.
 */

public class TwitterWebView extends HtmlParseWebView {
    private TwitterWebViewCallback twitterWebViewCallback;
    private boolean isNotLogined = false;

    public TwitterWebView(Context context) {
        super(context);
        init();
    }

    public TwitterWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        loadUrl(getContext().getString(R.string.twitter_init_url));
    }

    @Override
    public void onHtmlLoaded(WebView view, String html) {
        new TwitterHtmlAsyncTask(html).execute();
    }

    public void setTwitterWebViewCallback(TwitterWebViewCallback twitterWebViewCallback) {
        this.twitterWebViewCallback = twitterWebViewCallback;
    }

    public interface TwitterWebViewCallback {
        void isNotLogined();
        void userLogined();
        void onItemLoaded(ArrayList<TwitterArticleItem> items);
    }

    private class TwitterHtmlAsyncTask extends AsyncTask<Void, Void, ArrayList<TwitterArticleItem>> {
        private String html;

        public TwitterHtmlAsyncTask(String html) {
            this.html = html;
        }

        @Override
        protected ArrayList<TwitterArticleItem> doInBackground(Void... params) {
            if(TwitterHtmlProcessor.isNotLogined(html))
                return null;

            return TwitterHtmlProcessor.processArticle(html);
        }

        @Override
        protected void onPostExecute(ArrayList<TwitterArticleItem> twitterArticleItems) {
            if(twitterArticleItems == null) {
                if(twitterWebViewCallback != null)
                    twitterWebViewCallback.isNotLogined();
                isNotLogined = true;
                return;
            }

            if(twitterArticleItems.size() != 0) {
                if (twitterWebViewCallback != null)
                    twitterWebViewCallback.onItemLoaded(twitterArticleItems);
            } else {
                new RunnableAsyncTask(new Runnable() {
                    @Override
                    public void run() {
                        extractHtmlAsync();
                    }
                }).execute();
            }

            if(isNotLogined && twitterArticleItems.size() > 0) {
                if (twitterWebViewCallback != null)
                    twitterWebViewCallback.userLogined();
                isNotLogined = false;
            }
        }
    }
}
