package kr.ac.korea.snsmoa.article;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by Noverish on 2016-10-06.
 */

public class ArticleItem {
    protected SNSType snsType;
    protected String fullCategory;
    protected long timeMillis;

    protected String posterUrl;
    protected String articleUrl;

    protected String header;
    protected String title;
    protected String timeString;
    protected String content;
    protected String profileImgUrl;
    protected ArrayList<String> imgUrls = new ArrayList<>();
    protected Pair<String, String> videoData;

    protected String linkImgUrl;
    protected String linkTitle;
    protected String linkContent;
    protected String linkUrl;

    public void setSnsType(SNSType snsType) {
        this.snsType = snsType;
    }

    public void setFullCategory(String fullCategory) {
        this.fullCategory = fullCategory;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void setImgUrls(ArrayList<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public void addImgUrl(String imgUrl) {
        this.imgUrls.add(imgUrl);
    }

    public void setVideoData(String previewImgUrl, String videoUrl) {
        this.videoData = new Pair<>(previewImgUrl, videoUrl);
    }

    public void setLinkImgUrl(String linkImgUrl) {
        this.linkImgUrl = linkImgUrl;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public void setLinkContent(String linkContent) {
        this.linkContent = linkContent;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }


    public SNSType getSnsType() {
        return snsType;
    }

    public String getFullCategory() {
        return fullCategory;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public String getTimeString() {
        return timeString;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getHeader() {
        return header;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public ArrayList<String> getImgUrls() {
        return imgUrls;
    }

    public Pair<String, String> getVideoData() {
        return videoData;
    }

    public String getLinkImgUrl() {
        return linkImgUrl;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public String getLinkContent() {
        return linkContent;
    }

    public String getLinkUrl() {
        return linkUrl;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArticleItem that = (ArticleItem) o;

        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
