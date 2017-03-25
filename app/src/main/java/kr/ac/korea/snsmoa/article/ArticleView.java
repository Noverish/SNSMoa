package kr.ac.korea.snsmoa.article;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Noverish on 2017-01-12.
 */

public class ArticleView extends LinearLayout {
    private ArticleItem item;

    public ArticleView(Context context, ArticleItem item) {
        super(context);
        this.item = item;
    }

    public void setVisiblityByCategory(String category) {
        if(category.equals("전체보기")) {
            setVisibility(VISIBLE);
        } else {
            if (item.getCategory().equals(category)) {
                setVisibility(VISIBLE);
            } else {
                setVisibility(GONE);
            }
        }
    }
}
