package kr.ac.korea.snsmoa.facebook;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import kr.ac.korea.snsmoa.MainActivity;

/**
 * Created by Noverish on 2016-09-27.
 */
public class FacebookClient implements FacebookWebView.FacebookWebViewCallback {
//    private FacebookArticleCallback callback;
    private Context context;
    private FacebookWebView webView;
    private boolean isLogined;

    private OnNewItemLoaded onNewItemLoaded;
    private OnIsNotLogined onIsNotLogined;
    private OnUserLogined onUserLogined;

    private ArrayList<FacebookArticleItem> showedItems = new ArrayList<>();
    
    private static FacebookClient instance;
    public static FacebookClient getInstance() {
        if(instance == null)
            instance = new FacebookClient();
        
        return instance;
    }
    
    private FacebookClient() {}
    
    public void setWebView(FacebookWebView webView) {
        this.webView = webView;
        this.context = webView.getContext();
        this.webView.setFacebookWebViewCallback(this);
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
    public void isNotLogined() {
        if(onIsNotLogined != null)
            onIsNotLogined.isNotLogined();
    }

    @Override
    public void onItemLoaded(ArrayList<FacebookArticleItem> items) {
        items.removeAll(showedItems);

        if(onNewItemLoaded != null)
            onNewItemLoaded.onNewFacebookItemLoaded(items);

        showedItems.addAll(items);
    }

    public void loadNextPage() {
        System.out.println("FacebookClient loadNextPage");
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
        void onNewFacebookItemLoaded(ArrayList<FacebookArticleItem> newItems);
    }
    public interface OnIsNotLogined {
        void isNotLogined();
    }
    public interface OnUserLogined {
        void onUserLogined();
    }
}
