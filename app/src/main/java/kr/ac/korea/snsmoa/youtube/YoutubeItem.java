package kr.ac.korea.snsmoa.youtube;

import com.google.api.services.youtube.model.Video;

import kr.ac.korea.snsmoa.article.ArticleItem;

/**
 * Created by Noverish on 2017-06-16.
 */

public class YoutubeItem extends ArticleItem {
    private String imgUrl;

    public YoutubeItem(Video video) {
        super();
        this.imgUrl = video.getSnippet().getThumbnails().getHigh().getUrl();
        this.title = video.getSnippet().getTitle();
        this.content = video.getSnippet().getDescription();
        this.timeString = video.getSnippet().getPublishedAt().toString();
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
