package kr.ac.korea.snsmoa.facebook;

import kr.ac.korea.snsmoa.article.ArticleItem;


/**
 * Created by Noverish on 2016-07-17.
 */
public class FacebookArticleItem extends ArticleItem {
    private String location;

    private String likeNum;
    private String likeUrl;
    private boolean isLiked;

    private String commentNum;
    private String commentUrl;

    private String sharingNum;
    private String sharingUrl;

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public void setLikeUrl(String likeUrl) {
        this.likeUrl = likeUrl;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }

    public void setSharingNum(String sharingNum) {
        this.sharingNum = sharingNum;
    }

    public void setSharingUrl(String sharingUrl) {
        this.sharingUrl = sharingUrl;
    }


    public String getLocation() {
        return location;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public String getLikeUrl() {
        return likeUrl;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public String getSharingNum() {
        return sharingNum;
    }

    public String getSharingUrl() {
        return sharingUrl;
    }
}
