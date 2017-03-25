package kr.ac.korea.snsmoa.facebook;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.util.ArrayList;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.webview.HtmlParseWebView;


/**
 * Created by Noverish on 2017-03-21.
 */

public class FacebookWebView extends HtmlParseWebView {
    private OnItemLoaded onItemLoaded;

    public FacebookWebView(Context context) {
        super(context);
        init();
    }

    public FacebookWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        loadUrl(getContext().getString(R.string.facebook_init_url));
    }

    @Override
    public void onHtmlLoaded(WebView view, String html) {
        new FacebookHtmlAsyncTask(html).execute();
    }

    public void setOnItemLoaded(OnItemLoaded onItemLoaded) {
        this.onItemLoaded = onItemLoaded;
    }

    public interface OnItemLoaded {
        void onItemLoaded(ArrayList<FacebookArticleItem> items);
    }

    private class FacebookHtmlAsyncTask extends AsyncTask<Void, Void, ArrayList<FacebookArticleItem>> {
        private String html;

        public FacebookHtmlAsyncTask(String html) {
            this.html = html;
        }

        @Override
        protected ArrayList<FacebookArticleItem> doInBackground(Void... params) {
            return FacebookHtmlProcessor.processArticle(html);
        }

        @Override
        protected void onPostExecute(ArrayList<FacebookArticleItem> facebookArticleItems) {
            if(onItemLoaded != null)
                onItemLoaded.onItemLoaded(facebookArticleItems);
        }
    }
}
