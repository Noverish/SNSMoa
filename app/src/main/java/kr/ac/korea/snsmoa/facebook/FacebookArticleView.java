package kr.ac.korea.snsmoa.facebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
 * Created by Noverish on 2016-07-17.
 */
public class FacebookArticleView extends FrameLayout implements View.OnClickListener{
    private Context context;
    private FacebookArticleItem item;

    private TextView category;
    private TextView header;
    private ImageView profileImg;
    private TextView title;
    private TextView time;
    private TextView content;
    private LinearLayout linkLayout;
    private ImageView linkImg;
    private TextView linkTitle;
    private TextView linkContent;
    private TextView likeNum;
    private TextView commentNum;
    private TextView shareNum;
    private LinearLayout mediaLayout;
    private Button likeBtn;

    public FacebookArticleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_facebook_article, this);

        category = (TextView) findViewById(R.id.article_facebook_classification);
        header = (TextView) findViewById(R.id.facebook_article_header);
        profileImg = (ImageView) findViewById(R.id.facebook_article_profile_img);
        title = (TextView) findViewById(R.id.facebook_article_title);
        time = (TextView) findViewById(R.id.facebook_article_time);
        content = (TextView) findViewById(R.id.facebook_article_content);
        linkLayout = (LinearLayout) findViewById(R.id.facebook_article_link_layout);
        linkImg = (ImageView) findViewById(R.id.facebook_article_link_img);
        linkTitle = (TextView) findViewById(R.id.facebook_article_link_title);
        linkContent = (TextView) findViewById(R.id.facebook_article_link_content);
        likeNum = (TextView) findViewById(R.id.facebook_article_sympathy);
        commentNum = (TextView) findViewById(R.id.facebook_article_comment);
        shareNum = (TextView) findViewById(R.id.facebook_article_sharing);
        mediaLayout = (LinearLayout) findViewById(R.id.facebook_article_media_layout);
//        likeBtn = (Button) findViewById(R.id.facebook_article_like_button);

//        likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(item.isLiked()) {
//                    Toast.makeText(context, "좋아요 취소는 아직 지원하지 않습니다", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    System.out.println(item.getLikeUrl());
//                    try {
//                        Document document = Jsoup.connect(item.getLikeUrl()).get();
//                        Log.i("Document",document.outerHtml());
//                        item.setLiked(true);
//                        makeButtonLiked();
//                    } catch (Exception ex) {
//                        Toast.makeText(context, "연결에 실패했습니다", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

//        if(item.isLiked()) {
//            makeButtonLiked();
//        }
    }

    public void setItem(FacebookArticleItem item) {
        this.item = item;

        setHeader(item.getHeader());
        setProfileImg(item.getProfileImgUrl());
        setTitle(item.getTitle());
        setContent(item.getContent());
        setTime(item.getTimeString());
        setLikeNum(item.getLikeNum());
        setCommentNum(item.getCommentNum());
        setShareNum(item.getSharingNum());

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

    public void setTime(String time) {
        this.time.setText(time);
    }

    public void setContent(String content) {
        this.content.setText(content);
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

    public void setLikeNum(String likeNum) {
        this.likeNum.setText(likeNum);
    }

    public void setCommentNum(String commentNum) {
        this.commentNum.setText(commentNum);
    }

    public void setShareNum(String shareNum) {
        this.shareNum.setText(shareNum);
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


//    private void makeButtonLiked() {
//        ImageView likeIcon = (ImageView) findViewById(R.id.article_facebook_like_icon);
//        TextView likeText = (TextView) findViewById(R.id.article_facebook_like_text);
//
//        likeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_facebook_like_true));
//        likeText.setTextColor(ContextCompat.getColor(context, R.color.facebook));
//    }

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
//        MainActivity.instance.anotherWebView.loadUrl(item.getArticleUrl(), null, null, listener, HtmlParseWebView.SNSType.Facebook);
    }
}
