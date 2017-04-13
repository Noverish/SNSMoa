package kr.ac.korea.snsmoa.article;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.asynctask.CategorizeAsyncTask;
import kr.ac.korea.snsmoa.webview.VideoWebView;
import kr.ac.korea.snsmoa.webview.WebViewActivity;

/**
 * Created by Noverish on 2017-01-12.
 */

public abstract class ArticleView extends LinearLayout {
    protected ArticleItem articleItem;
    protected Context context;

    protected TextView category;
    protected TextView header;
    protected ImageView profileImg;
    protected TextView title;
    protected TextView time;
    protected TextView content;

    protected LinearLayout linkLayout;
    protected ImageView linkImg;
    protected TextView linkTitle;
    protected TextView linkContent;

    protected LinearLayout mediaLayout;

    public ArticleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    protected abstract @LayoutRes int getLayoutResid();

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutResid(), this, true);

        category = (TextView) findViewById(R.id.article_category);
        header = (TextView) findViewById(R.id.article_header);
        content = (TextView) findViewById(R.id.article_content);
        profileImg = (ImageView) findViewById(R.id.article_profile_img);
        title = (TextView) findViewById(R.id.article_title);
        time = (TextView) findViewById(R.id.article_time);

        linkLayout = (LinearLayout) findViewById(R.id.article_link_layout);
        linkImg = (ImageView) findViewById(R.id.article_link_img);
        linkTitle = (TextView) findViewById(R.id.article_link_title);
        linkContent = (TextView) findViewById(R.id.article_link_content);

        mediaLayout = (LinearLayout) findViewById(R.id.article_media_layout);
    }

    protected void setCategory(String category) {
        if(category != null && !category.equals("")) {
//            this.category.setVisibility(View.VISIBLE);
            this.category.setText(category);
        } else {
//            this.category.setVisibility(View.GONE);
            this.category.setText("");
        }
    }

    protected void setHeader(String header) {
        if(header == null || header.equals("")) {
            this.header.setVisibility(View.GONE);
        } else {
            this.header.setVisibility(View.VISIBLE);
            this.header.setText(header);
        }
    }

    protected void setProfileImg(String profileImgUrl) {
        Glide.with(context).load(profileImgUrl).into(this.profileImg);
    }

    protected void setTitle(String title) {
        this.title.setText(title);
    }

    protected void setContent(String content) {
        this.content.setClickable(true);
        this.content.setMovementMethod(LinkMovementMethod.getInstance());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            this.content.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        else
            this.content.setText(Html.fromHtml(content));

        if(articleItem.getFullCategory() == null || articleItem.getFullCategory().equals("")) {
            category.setText("");
            new CategorizeAsyncTask(context, content.replaceAll("<[^>]*>", ""), new CategorizeAsyncTask.CategorizeListener() {
                @Override
                public void onCategorized(@Nullable String fullCategory) {
                    if (fullCategory != null) {
//                        category.setVisibility(View.VISIBLE);
                        category.setText(fullCategory);
                        articleItem.setFullCategory(fullCategory);
                    } else {
                        category.setText("");
//                        category.setVisibility(View.GONE);
                    }
                }
            }).execute();
        }
    }

    protected void setTime(String time) {
        this.time.setText(time);
    }

    protected void setLinkInfo(String imgUrl, String title, String content) {
        if(title == null || title.equals("")) {
            linkLayout.setVisibility(View.GONE);
        } else {
            linkLayout.setVisibility(View.VISIBLE);
            Glide.with(context).load(imgUrl).into(linkImg);
            linkTitle.setText(title);
            linkContent.setText(content);
        }
    }

    protected void setImgs(ArrayList<String> imgUrls) {
        if(imgUrls == null || imgUrls.size() == 0) {
            mediaLayout.setVisibility(View.GONE);
        } else {
            for(String url : imgUrls) {
                ImageView imageView = new ImageView(context);
                Glide.with(context).load(url).into(imageView);
                mediaLayout.addView(imageView);
            }
        }
    }

    protected void setVideo(String previewImgUrl, String videoUrl) {
        if(previewImgUrl != null && !previewImgUrl.equals("")) {
            VideoWebView videoWebView = new VideoWebView(context, previewImgUrl, videoUrl);
            mediaLayout.addView(videoWebView);
        }
    }

    protected void setArticleUrl(String url) {

    }

    protected void setPosterUrl(final String url) {
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
}
