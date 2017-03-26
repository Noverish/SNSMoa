package kr.ac.korea.snsmoa.twitter;


import kr.ac.korea.snsmoa.article.ArticleItem;


/**
 * Created by Noverish on 2016-09-18.
 */
public class TwitterArticleItem extends ArticleItem {
    private String screenName;

    private String replyUrl;

    private int retweetNum;
    private String retweetUrl;
    private boolean isRetweeted;

    private int favoriteNum;
    private String favoriteUrl;
    private boolean isFavorited;


    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setReplyUrl(String replyUrl) {
        this.replyUrl = replyUrl;
    }

    public void setRetweetNum(int retweetNum) {
        this.retweetNum = retweetNum;
    }

    public void setRetweetUrl(String retweetUrl) {
        this.retweetUrl = retweetUrl;
    }

    public void setRetweeted(boolean retweeted) {
        isRetweeted = retweeted;
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public void setFavoriteUrl(String favoriteUrl) {
        this.favoriteUrl = favoriteUrl;
    }

    public void setFavorited(boolean favorited) {
        isFavorited = favorited;
    }


    public String getScreenName() {
        return screenName;
    }

    public String getReplyUrl() {
        return replyUrl;
    }

    public int getRetweetNum() {
        return retweetNum;
    }

    public String getRetweetUrl() {
        return retweetUrl;
    }

    public boolean isRetweeted() {
        return isRetweeted;
    }

    public int getFavoriteNum() {
        return favoriteNum;
    }

    public String getFavoriteUrl() {
        return favoriteUrl;
    }

    public boolean isFavorited() {
        return isFavorited;
    }
}
