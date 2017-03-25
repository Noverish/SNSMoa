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
    private OnItemLoaded onItemLoaded;

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

    public void setOnItemLoaded(OnItemLoaded onItemLoaded) {
        this.onItemLoaded = onItemLoaded;
    }

    public interface OnItemLoaded {
        void onItemLoaded(ArrayList<TwitterArticleItem> items);
    }

    private class TwitterHtmlAsyncTask extends AsyncTask<Void, Void, ArrayList<TwitterArticleItem>> {
        private String html;

        public TwitterHtmlAsyncTask(String html) {
            this.html = html;
        }

        @Override
        protected ArrayList<TwitterArticleItem> doInBackground(Void... params) {
            return TwitterHtmlProcessor.processArticle(html);
        }

        @Override
        protected void onPostExecute(ArrayList<TwitterArticleItem> twitterArticleItems) {
            if(twitterArticleItems.size() != 0) {
                if (onItemLoaded != null)
                    onItemLoaded.onItemLoaded(twitterArticleItems);
            } else {
                new RunnableAsyncTask(new Runnable() {
                    @Override
                    public void run() {
                        extractHtmlAsync();
                    }
                }).execute();
            }
        }
    }
}
