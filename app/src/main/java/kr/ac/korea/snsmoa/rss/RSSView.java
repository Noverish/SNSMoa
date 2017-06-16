package kr.ac.korea.snsmoa.rss;

import android.content.Context;

import com.bumptech.glide.Glide;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.article.ArticleView;

/**
 * Created by Noverish on 2017-06-16.
 */

public class RSSView extends ArticleView {
    public RSSView(Context context) {
        super(context);
    }

    public void setItem(RSSItem item) {
        this.articleItem = item;

        setProfileImg(item.getProfileImgUrl());
        setTitle(item.getTitle());
        setContent(item.getContent());
        setTime(item.getTimeString());
        Glide.with(context).load(item.getLinkImgUrl()).into(linkImg);
    }

    @Override
    protected int getLayoutResid() {
        return R.layout.view_rss;
    }
}
