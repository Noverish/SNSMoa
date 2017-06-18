package kr.ac.korea.snsmoa.twitter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.article.ArticleView;


/**
 * Created by Noverish on 2016-05-30.
 */
public class TwitterArticleView extends ArticleView {
    private TwitterArticleItem item;

    private TextView screenName;
    private TextView retweetNum;
    private TextView favoriteNum;

    public TwitterArticleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    @Override
    protected int getLayoutResid() {
        return R.layout.view_twitter_article;
    }

    private void init() {
        screenName = (TextView) findViewById(R.id.twitter_article_view_screen_name);

        retweetNum = (TextView) findViewById(R.id.article_twitter_retweet_number);
        favoriteNum = (TextView) findViewById(R.id.article_twitter_favorite_number);

//        if(item.getLinkUrl() != null) {
//
//        } else {
//            linkLayout.setVisibility(GONE);
//
//            LinearLayout mediaLayout = (LinearLayout) findViewById(R.id.twitter_article_view_media_layout);
//            if(item.getVideoUrl() != null) {
//                mediaLayout.addView(new VideoWebView(context, item.getImageUrls().get(0), item.getVideoUrl()));
//            } else if(item.getImageUrls().size() != 0) {
//                for(String url : item.getImageUrls()) {
//                    ImageView image = new ImageView(context);
//                    Glide.with(context).load(url).into(image);
//                    mediaLayout.addView(image);
//                }
//            } else {
//                mediaLayout.setVisibility(GONE);
//            }
//        }
//
//        ImageView replyButton = (ImageView) findViewById(R.id.twitter_article_view_reply);
//        replyButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("url","https://mobile.twitter.com" + item.getReplyUrl());
//                context.startActivity(intent);
//            }
//        });
//
//        ImageView retweetButton = (ImageView) findViewById(R.id.twitter_article_view_retweet);
//        retweetButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("url","https://mobile.twitter.com" + item.getRetweetUrl());
//                context.startActivity(intent);
//            }
//        });
//
//        if(item.isRetweeted()) {
//            retweetButton.setImageResource(R.drawable.icon_twitter_arti_retweet_active);
//        }
//
//        ImageView favoriteButton = (ImageView) findViewById(R.id.twitter_article_view_favorite);
//        favoriteButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(TwitterArticleView.this.context, WebViewActivity.class);
//                intent.putExtra("url","https://mobile.twitter.com" + TwitterArticleView.this.item.getFavoriteUrl());
//                TwitterArticleView.this.context.startActivity(intent);
//            }
//        });
//
//        if(item.isFavorited()) {
//            favoriteButton.setImageResource(R.drawable.icon_twitter_arti_favorite_active);
//        }
    }

    public void setItem(TwitterArticleItem item) {
        articleItem = item;
        this.item = item;

        setCategory(item.getFullCategory());
        setHeader(item.getHeader());
        setProfileImg(item.getProfileImgUrl());
        setTitle(item.getTitle());
        setScreenName(item.getScreenName());
        setContent(item.getContent());
        setTime(item.getTimeString());
        setRetweetNum(item.getRetweetNum());
        setFavoriteNum(item.getFavoriteNum());
        setLinkInfo(item.getLinkImgUrl(), item.getLinkTitle(), item.getLinkContent());
    }

    public void setScreenName(String screenName) {
        this.screenName.setText(screenName);
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favoriteNum.setText(String.valueOf(favoriteNum));
    }

    public void setRetweetNum(int retweetNum) {
        this.retweetNum.setText(String.valueOf(retweetNum));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
//        OnPageFinishedListener listener = new OnPageFinishedListener() {
//            @Override
//            public void onPageFinished(HtmlParseWebView webView, String url) {
//                MainActivity.instance.setStatus(ARTICLE);
//                MainActivity.instance.changeVisibleLevel(MainActivity.LEVEL_ANOTHER);
//                MainActivity.instance.fab.setVisibility(INVISIBLE);
//            }
//        };
//        MainActivity.instance.anotherWebView.loadUrl(item.getArticleUrl(), null, null, listener, HtmlParseWebView.SNSType.Twitter);
    }
}
