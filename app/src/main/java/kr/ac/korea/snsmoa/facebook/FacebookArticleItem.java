package kr.ac.korea.snsmoa.facebook;

import android.util.Pair;

import java.util.ArrayList;

import kr.ac.korea.snsmoa.article.ArticleItem;


/**
 * Created by Noverish on 2016-07-17.
 */
public class FacebookArticleItem extends ArticleItem {
    private String header;
    private String profileImgUrl;
    private String title;
    private String location;
    private String content;
    private ArrayList<String> imageUrls;
    private Pair<String, String> video = null;
    private String likeNum;
    private String commentNum;
    private String sharingNum;

    private String likeUrl;
    private boolean isLiked = false;


    public void setHeader(String header) {
        this.header = header;
    }

    public void setProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setVideo(Pair<String, String> video) {
        this.video = video;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public void setSharingNum(String sharingNum) {
        this.sharingNum = sharingNum;
    }

    public void setLikeUrl(String likeUrl) {
        this.likeUrl = likeUrl;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }


    public String getHeader() {
        return header;
    }

    public String getProfileImgUrl() {
        return profileImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public Pair<String, String> getVideo() {
        return video;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public String getSharingNum() {
        return sharingNum;
    }

    public String getLikeUrl() {
        return likeUrl;
    }

    public boolean isLiked() {
        return isLiked;
    }


    @Override
    public String toString() {
        return "\n|\n|\nFacebookArticleItem{" +
                "\nheader='" + header + '\'' +
                ", \nprofileImgUrl='" + profileImgUrl + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \nlocation='" + location + '\'' +
                ", \ncontent='" + content + '\'' +
                ", \nimageUrls=" + imageUrls +
                ", \nvideo=" + video +
                ", \nlikeNum='" + likeNum + '\'' +
                ", \ncommentNum='" + commentNum + '\'' +
                ", \nsharingNum='" + sharingNum + '\'' +
                ", \nlikeUrl='" + likeUrl + '\'' +
                ", \nisLiked=" + isLiked +
                "} " + super.toString();
    }
}
