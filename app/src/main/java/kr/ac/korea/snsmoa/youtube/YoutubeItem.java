package kr.ac.korea.snsmoa.youtube;

import com.google.api.services.youtube.model.Video;

import kr.ac.korea.snsmoa.article.ArticleItem;

/**
 * Created by Noverish on 2017-06-16.
 */

public class YoutubeItem extends ArticleItem {
    private String imgUrl;
    private String name;

    public YoutubeItem(Video video) {
        super();
        this.imgUrl = video.getSnippet().getThumbnails().getHigh().getUrl();
        this.title = video.getSnippet().getTitle();
        this.content = video.getSnippet().getDescription();
        this.timeString = video.getSnippet().getPublishedAt().toString();
        this.name = video.getSnippet().getChannelTitle();
    }

    public String getContentForCategorize() {
        return title + " - " + content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }
}
