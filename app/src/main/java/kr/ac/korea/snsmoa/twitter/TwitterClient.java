package kr.ac.korea.snsmoa.twitter;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Noverish on 2016-09-27.
 */
public class TwitterClient implements TwitterWebView.OnItemLoaded {
    private TwitterWebView webView;
    private Context context;
    private boolean isNewerVersion;
    private boolean isLogined;
    private String userScreenName;
    private ArrayList<TwitterArticleItem> showedItems = new ArrayList<>();
    private OnNewItemLoaded onNewItemLoaded;

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
        this.webView.setOnItemLoaded(this);
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

    public interface OnNewItemLoaded {
        void onNewTwitterItemLoaded(ArrayList<TwitterArticleItem> newItems);
    }

    //    public void setCallback(TwitterArticleCallback callback) {
//        this.callback = callback;
//    }
//
//    public void loadFirstPage() {
//        showedItems.clear();
//        OnHtmlLoadSuccessListener loaded = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                if(Jsoup.parse(htmlCode).select("html[class=\"AppPage CorePage\"]").size() == 0) {
//                    Log.i("<twitter load first>","Twitter login");
//
//                    ArrayList<TwitterArticleItem> newItems = TwitterHtmlProcessor.processArticle(htmlCode);
//                    newItems.removeAll(showedItems);
//                    showedItems.addAll(newItems);
//
//                    isLogined = true;
//                    if(callback != null)
//                        callback.onSuccess(newItems);
//                } else {
//                    Log.i("<twitter load first>","Twitter not login");
//
//                    isLogined = false;
//                    if(callback != null)
//                        callback.onNotLogin();
//                }
//            }
//        };
//        webView.loadUrl("https://mobile.twitter.com/home", loaded, null, null, HtmlParseWebView.SNSType.Twitter);
//    }
//
//    public void loadNextPage() {
//        OnHtmlLoadSuccessListener loaded = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                if(Jsoup.parse(htmlCode).select("html[class=\"AppPage CorePage\"]").size() == 0) {
//                    Log.i("<twitter load next>","Twitter login");
//
//                    ArrayList<TwitterArticleItem> newItems = TwitterHtmlProcessor.processArticle(htmlCode);
//                    newItems.removeAll(showedItems);
//                    showedItems.addAll(newItems);
//
//                    isLogined = true;
//                    if(callback != null)
//                        callback.onSuccess(newItems);
//                } else {
//                    Log.i("<twitter load next>","Twitter not login");
//
//                    isLogined = false;
//                    if(callback != null)
//                        callback.onNotLogin();
//                }
//            }
//        };
//        webView.scrollBottom(loaded);
//    }
//
//    public void getNotification(final TwitterNotificationCallback callback) {
//        OnHtmlLoadSuccessListener listener = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                webView.goBack();
//                callback.onSuccess(TwitterHtmlProcessor.processNotification(htmlCode));
//            }
//        };
//        webView.loadUrl("https://mobile.twitter.com/notifications", listener, null, null, HtmlParseWebView.SNSType.Twitter);
//    }
//
//    public void getMessage(final TwitterMessageCallback callback) {
//        OnHtmlLoadSuccessListener listener = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                webView.goBack();
//                callback.onSuccess(TwitterHtmlProcessor.processMessage(htmlCode));
//            }
//        };
//        webView.loadUrl("https://mobile.twitter.com/messages",listener, null, null, HtmlParseWebView.SNSType.Twitter);
//    }
//
//    public void post(final String content) {
//        webView.loadUrl("https://mobile.twitter.com/compose/tweet", new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView1, String htmlCode) {
//                webView.setContentInTextArea(content, "_2wjpwbis _1YGC8xFq _2RmultvD _1VqMahaT _2Z8UymHS", new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.clickButton("MmJh82_T _1xFtK706 SpbPGaHr _2Rz0TobF", null);
//                    }
//                });
//            }
//        }, null, null, HtmlParseWebView.SNSType.Twitter);
//    }
//
//    public boolean isLogined() {
//        return isLogined;
//    }
//
//    public void setLogined(boolean logined) {
//        isLogined = logined;
//    }
//
//    public String getUserScreenName() {
//        return userScreenName;
//    }
//
//    public void setUserScreenName(String userScreenName) {
//        this.userScreenName = userScreenName;
//    }
//
//
//    public interface TwitterArticleCallback {
//        void onSuccess(ArrayList<TwitterArticleItem> items);
//        void onNotLogin();
//    }
//
//    public interface TwitterNotificationCallback {
//        void onSuccess(ArrayList<TwitterNotificationItem> items);
//        void onNotLogin();
//    }
//
//    public interface TwitterMessageCallback {
//        void onSuccess(ArrayList<TwitterMessageItem> items);
//        void onNotLogin();
//    }
}
