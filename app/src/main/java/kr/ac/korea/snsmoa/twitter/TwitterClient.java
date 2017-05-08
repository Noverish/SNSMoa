package kr.ac.korea.snsmoa.twitter;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import kr.ac.korea.snsmoa.MainActivity;

/**
 * Created by Noverish on 2016-09-27.
 */
public class TwitterClient implements TwitterWebView.TwitterWebViewCallback {
    private TwitterWebView webView;
    private Context context;
    private boolean isNewerVersion;
    private boolean isLogined;
    private String userScreenName;
    private ArrayList<TwitterArticleItem> showedItems = new ArrayList<>();
    private OnNewItemLoaded onNewItemLoaded;
    private OnIsNotLogined onIsNotLogined;
    private OnUserLogined onUserLogined;

    private static TwitterClient instance;
    public static TwitterClient getInstance() {
        if(instance == null)
            instance = new TwitterClient();

        return instance;
    }

    private TwitterClient() {}

    public void setWebView(TwitterWebView webView) {
        this.webView = webView;
        this.context = webView.getContext();
        this.webView.setTwitterWebViewCallback(this);
    }

    @Override
    public void isNotLogined() {
        if(onIsNotLogined != null)
            onIsNotLogined.isNotLogined();
    }

    @Override
    public void userLogined() {
        Intent braodcast = new Intent(MainActivity.ChangeScreenReceiver.ACTION);
        braodcast.putExtra(MainActivity.ChangeScreenReceiver.INTENT_KEY, MainActivity.Screen.home);
        context.sendBroadcast(braodcast);

        if(onUserLogined != null)
            onUserLogined.onUserLogined();
    }

    @Override
    public void onItemLoaded(ArrayList<TwitterArticleItem> items) {
        items.removeAll(showedItems);

        if(onNewItemLoaded != null)
            onNewItemLoaded.onNewTwitterItemLoaded(items);

        showedItems.addAll(items);
    }

    public void loadNextPage() {
        System.out.println("TwitterClient loadNextPage");
        webView.scrollBottom();
        webView.extractHtmlAsync();
    }


    public void setOnNewItemLoaded(OnNewItemLoaded onNewItemLoaded) {
        this.onNewItemLoaded = onNewItemLoaded;
    }
    public void setOnIsNotLogined(OnIsNotLogined onIsNotLogined) {
        this.onIsNotLogined = onIsNotLogined;
    }
    public void setOnUserLogined(OnUserLogined onUserLogined) {
        this.onUserLogined = onUserLogined;
    }

    public interface OnNewItemLoaded {
        void onNewTwitterItemLoaded(ArrayList<TwitterArticleItem> newItems);
    }
    public interface OnIsNotLogined {
        void isNotLogined();
    }
    public interface OnUserLogined {
        void onUserLogined();
    }
}
