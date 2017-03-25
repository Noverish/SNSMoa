package kr.ac.korea.snsmoa.facebook;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Noverish on 2016-09-27.
 */
public class FacebookClient implements FacebookWebView.OnItemLoaded {
//    private FacebookArticleCallback callback;
    private Context context;
    private FacebookWebView webView;
    private boolean isLogined;

    private OnNewItemLoaded onNewItemLoaded;

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
        this.webView.setOnItemLoaded(this);
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

    public interface OnNewItemLoaded {
        void onNewFacebookItemLoaded(ArrayList<FacebookArticleItem> newItems);
    }





    //    public void setCallback(FacebookArticleCallback callback) {
//        this.callback = callback;
//    }

//    public void loadFirstPage() {
//        OnHtmlLoadSuccessListener loaded = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                if (Jsoup.parse(htmlCode).select("button[name=\"login\"][class=\"_54k8 _56bs _56b_ _56bw _56bu\"]").size() == 0) {
//                    Log.i("<facebook load first>","Facebook login");
//
//                    ArrayList<FacebookArticleItem> newItems = FacebookHtmlCodeProcessor.processArticle(htmlCode);
//                    newItems.removeAll(showedItems);
//                    showedItems.addAll(newItems);
//
//                    isLogined = true;
//                    if(callback != null)
//                        callback.onSuccess(newItems);
//                } else {
//                    Log.i("<facebook load first>","Facebook not login");
//
//                    isLogined = false;
//                    if(callback != null)
//                        callback.onNotLogin();
//                }
//            }
//        };
//        webView.loadUrl(context.getString(R.string.facebook_url), loaded, null, null, HtmlParseWebView.SNSType.Facebook);
//    }
//
//    public void loadNextPage() {
//        OnHtmlLoadSuccessListener loaded = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                if (Jsoup.parse(htmlCode).select("button[name=\"login\"][class=\"_54k8 _56bs _56b_ _56bw _56bu\"]").size() == 0) {
//                    Log.i("<facebook load next>","Facebook login");
//
//                    ArrayList<FacebookArticleItem> newItems = FacebookHtmlCodeProcessor.processArticle(htmlCode);
//                    newItems.removeAll(showedItems);
//                    showedItems.addAll(newItems);
//
//                    isLogined = true;
//                    if(callback != null)
//                        callback.onSuccess(newItems);
//                } else {
//                    Log.i("<facebook load next>","Facebook not login");
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
//    public void getNotification(final FacebookNotificationCallback callback) {
//        OnHtmlLoadSuccessListener listener = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                webView.goBack();
//                callback.onSuccess(FacebookHtmlCodeProcessor.processNotification(htmlCode));
//            }
//        };
//        webView.loadUrl("https://m.facebook.com/notifications.php?more", listener, null, null, HtmlParseWebView.SNSType.Facebook);
//    }
//
//    public void getMessage(final FacebookMessageCallback callback) {
//        OnHtmlLoadSuccessListener listener = new OnHtmlLoadSuccessListener() {
//            @Override
//            public void onHtmlLoadSuccess(HtmlParseWebView webView, String htmlCode) {
//                webView.goBack();
//                callback.onSuccess(FacebookHtmlCodeProcessor.processMessage(htmlCode));
//            }
//        };
//        webView.loadUrl("https://m.facebook.com/messages/?more",listener, null, null, HtmlParseWebView.SNSType.Facebook);
//    }
//
//    public void post(String content) {
//        webView.setContentInTextArea(content, "_1svy _2ya3 _3jce _26wa", new Runnable() {
//            @Override
//            public void run() {
//                webView.clickButton("_54k8 _56bs _56bu", new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.println("clicked");
//                    }
//                });
//            }
//        });
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
//    public interface FacebookArticleCallback {
//        void onSuccess(ArrayList<FacebookArticleItem> items);
//        void onNotLogin();
//    }
//
//    public interface FacebookNotificationCallback {
//        void onSuccess(ArrayList<FacebookNotificationItem> items);
//        void onNotLogin();
//    }
//
//    public interface FacebookMessageCallback {
//        void onSuccess(ArrayList<FacebookMessageItem> items);
//        void onNotLogin();
//    }
}
