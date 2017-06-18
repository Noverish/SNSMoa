package kr.ac.korea.snsmoa.facebook;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.ac.korea.intelligentgallery.R;
import kr.ac.korea.snsmoa.article.ArticleView;


/**
 * Created by Noverish on 2016-07-17.
 */
public class FacebookArticleView extends ArticleView {
    private Context context;
    private FacebookArticleItem item;

    private TextView likeNum;
    private TextView commentNum;
    private TextView shareNum;
    private Button likeBtn;

    public FacebookArticleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    @Override
    protected int getLayoutResid() {
        return R.layout.view_facebook_article;
    }

    private void init() {
        likeNum = (TextView) findViewById(R.id.facebook_article_sympathy);
        commentNum = (TextView) findViewById(R.id.facebook_article_comment);
        shareNum = (TextView) findViewById(R.id.facebook_article_sharing);

//        likeBtn = (Button) findViewById(R.id.facebook_article_like_button);

//        likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(item.isLiked()) {
//                    Toast.makeText(context, "좋아요 취소는 아직 지원하지 않습니다", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    System.out.println(item.getLikeUrl());
//                    try {
//                        Document document = Jsoup.connect(item.getLikeUrl()).get();
//                        Log.i("Document",document.outerHtml());
//                        item.setLiked(true);
//                        makeButtonLiked();
//                    } catch (Exception ex) {
//                        Toast.makeText(context, "연결에 실패했습니다", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });

//        if(item.isLiked()) {
//            makeButtonLiked();
//        }
    }

    public void setItem(FacebookArticleItem item) {
        articleItem = item;
        this.item = item;

        setCategory(item.getFullCategory());
        setHeader(item.getHeader());
        setProfileImg(item.getProfileImgUrl());
        setTitle(item.getTitle());
        setContent(item.getContent());
        setTime(item.getTimeString());
        setLikeNum(item.getLikeNum());
        setCommentNum(item.getCommentNum());
        setShareNum(item.getSharingNum());
        setLinkInfo(item.getLinkImgUrl(), item.getLinkTitle(), item.getLinkContent());
    }

    public void setLikeNum(String likeNum) {
        this.likeNum.setText(likeNum);
    }

    public void setCommentNum(String commentNum) {
        this.commentNum.setText(commentNum);
    }

    public void setShareNum(String shareNum) {
        this.shareNum.setText(shareNum);
    }


//    private void makeButtonLiked() {
//        ImageView likeIcon = (ImageView) findViewById(R.id.article_facebook_like_icon);
//        TextView likeText = (TextView) findViewById(R.id.article_facebook_like_text);
//
//        likeIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_facebook_like_true));
//        likeText.setTextColor(ContextCompat.getColor(context, R.color.facebook));
//    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

//        OnPageFinishedListener listener = new OnPageFinishedListener() {
//            @Override
//            public void onPageFinished(HtmlParseWebView webView, String url) {
//                MainActivity.instance.setStatus(ARTICLE);
//                MainActivity.instance.changeVisibleLevel(MainActivity.LEVEL_ANOTHER);
//                MainActivity.instance.fab.setVisibility(INVISIBLE);
//            }
//        };
//        MainActivity.instance.anotherWebView.loadUrl(item.getArticleUrl(), null, null, listener, HtmlParseWebView.SNSType.Facebook);
    }
}
