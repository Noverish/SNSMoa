package kr.ac.korea.snsmoa.youtube;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.article.ArticleView;

/**
 * Created by Noverish on 2017-06-16.
 */

public class YoutubeView extends ArticleView {
    private ImageView imageView;
    private TextView name;

    public YoutubeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.view_youtube_image);
        name = (TextView) findViewById(R.id.article_name);
    }

    public void setItem(YoutubeItem item) {
        this.articleItem = item;

        Glide.with(getContext()).load(item.getImgUrl()).into(imageView);
        setTitle(item.getTitle());
        setContent(item.getContent());
        setTime(item.getTimeString());
        name.setText(item.getName());
    }

    @Override
    protected int getLayoutResid() {
        return R.layout.view_youtube;
    }
}
