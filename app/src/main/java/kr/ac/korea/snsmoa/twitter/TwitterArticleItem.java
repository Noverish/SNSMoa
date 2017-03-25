package kr.ac.korea.snsmoa.twitter;


import java.util.ArrayList;

import kr.ac.korea.snsmoa.article.ArticleItem;


/**
 * Created by Noverish on 2016-09-18.
 */
public class TwitterArticleItem extends ArticleItem {
    private String header = "";
    private String profileImageUrl = null;
    private String name = "";
    private String screenName = "";
    private String content = "";
    private ArrayList<String> imageUrls = new ArrayList<>();
    private String videoUrl = null;
    private String replyUrl = null;
    private boolean retweeted = false;
    private String retweetUrl = null;
    private int retweetNumber = 0;
    private boolean favorited = false;
    private String favoriteUrl = null;
    private int favoriteNumber = 0;

    void setHeader(String header) {
        this.header = header;
    }

    void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    void setName(String name) {
        this.name = name;
    }

    void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    void setContent(String content) {
        this.content = content;
    }

    void addImageUrl(String imageUrl) {
        imageUrls.add(imageUrl);
    }

    void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    void setReplyUrl(String replyUrl) {
        this.replyUrl = replyUrl;
    }

    void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    void setRetweetUrl(String retweetUrl) {
        this.retweetUrl = retweetUrl;
    }

    void setRetweetNumber(int retweetNumber) {
        this.retweetNumber = retweetNumber;
    }

    void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    void setFavoriteUrl(String favoriteUrl) {
        this.favoriteUrl = favoriteUrl;
    }

    void setFavoriteNumber(int favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }


    public String getHeader() {
        return header;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getReplyUrl() {
        return replyUrl;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public String getRetweetUrl() {
        return retweetUrl;
    }

    public int getRetweetNumber() {
        return retweetNumber;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public String getFavoriteUrl() {
        return favoriteUrl;
    }

    public int getFavoriteNumber() {
        return favoriteNumber;
    }

    @Override
    public String toString() {
        return "TwitterArticleItem{" +
                "header='" + header + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", name='" + name + '\'' +
                ", screenName='" + screenName + '\'' +
                ", content='" + content + '\'' +
                ", imageUrls=" + imageUrls +
                ", videoUrl='" + videoUrl + '\'' +
                ", replyUrl='" + replyUrl + '\'' +
                ", retweeted=" + retweeted +
                ", retweetUrl='" + retweetUrl + '\'' +
                ", retweetNumber=" + retweetNumber +
                ", favorited=" + favorited +
                ", favoriteUrl='" + favoriteUrl + '\'' +
                ", favoriteNumber=" + favoriteNumber +
                "} " + super.toString();
    }
}
