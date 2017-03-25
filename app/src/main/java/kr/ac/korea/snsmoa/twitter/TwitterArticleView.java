package kr.ac.korea.snsmoa.twitter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.webview.VideoWebView;
import kr.ac.korea.snsmoa.webview.WebViewActivity;


/**
 * Created by Noverish on 2016-05-30.
 */
public class TwitterArticleView extends FrameLayout implements View.OnClickListener{
    private Context context;
    private TwitterArticleItem item;

    private TextView category;
    private TextView header;
    private ImageView profileImg;
    private TextView title;
    private TextView screenName;
    private TextView time;
    private TextView content;
    private LinearLayout linkLayout;
    private ImageView linkImg;
    private TextView linkTitle;
    private TextView linkContent;

    private TextView retweetNum;
    private TextView favoriteNum;

    private LinearLayout mediaLayout;

    public TwitterArticleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_twitter_article, this, true);

        category = (TextView) findViewById(R.id.twitter_article_view_classification);
        header = (TextView) findViewById(R.id.twitter_article_view_header);
        content = (TextView) findViewById(R.id.twitter_article_view_content);
        profileImg = (ImageView) findViewById(R.id.twitter_article_view_profile_image_view);
        title = (TextView) findViewById(R.id.twitter_article_view_name);
        screenName = (TextView) findViewById(R.id.twitter_article_view_screen_name);
        time = (TextView) findViewById(R.id.twitter_article_view_time);

        linkLayout = (LinearLayout) findViewById(R.id.twitter_article_view_link_layout);
        linkImg = (ImageView) findViewById(R.id.twitter_article_view_link_img);
        linkTitle = (TextView) findViewById(R.id.twitter_article_view_link_content);
        linkContent = (TextView) findViewById(R.id.twitter_article_view_link_domain);

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
        this.item = item;

        setHeader(item.getHeader());
        setProfileImg(item.getProfileImageUrl());
        setTitle(item.getName());
        setScreenName(item.getScreenName());
        setContent(item.getContent());
        setTime(item.getTimeString());
        setRetweetNum(item.getRetweetNumber());
        setFavoriteNum(item.getFavoriteNumber());

        if(item.getLinkTitle() != null) {
            setLinkInfo(item.getLinkImgUrl(), item.getLinkTitle(), item.getLinkContent());
        } else {
            linkLayout.setVisibility(View.GONE);
        }
    }

    public void setHeader(String header) {
        if(header == null || header.equals(""))
            this.header.setVisibility(GONE);
        else
            this.header.setText(header);
    }

    public void setProfileImg(String profileImgUrl) {
        Glide.with(context).load(profileImgUrl).into(this.profileImg);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setScreenName(String screenName) {
        this.screenName.setText(screenName);
    }

    public void setTime(String time) {
        this.time.setText(time);
    }

    public void setContent(String content) {
        this.content.setClickable(true);
        this.content.setMovementMethod(LinkMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.content.setText(Html.fromHtml(item.getContent(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            this.content.setText(Html.fromHtml(item.getContent()));
        }
        //TODO Content to Category
    }

    public void setLinkInfo(String imgUrl, String title, String content) {
        this.linkLayout.setVisibility(VISIBLE);
        Glide.with(context).load(imgUrl).into(this.linkImg);
        this.linkTitle.setText(title);
        this.linkContent.setText(content);
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        if(imgUrls != null) {
            for (String url : imgUrls) {
                ImageView image = new ImageView(context);
                Glide.with(context).load(url).into(image);
                mediaLayout.addView(image);
            }
        }
    }

    public void setvideo(String snapShotUrl, String videoUrl) {
        VideoWebView videoWebView = new VideoWebView(context, snapShotUrl, videoUrl);
        mediaLayout.addView(videoWebView);
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favoriteNum.setText(String.valueOf(favoriteNum));
    }

    public void setRetweetNum(int retweetNum) {
        this.retweetNum.setText(String.valueOf(retweetNum));
    }

    public void setArticleUrl(String url) {

    }

    public void setPosterUrl(final String url) {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        };

        this.profileImg.setOnClickListener(listener);
        this.title.setOnClickListener(listener);
    }

    @Override
    public void onClick(View view) {
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
