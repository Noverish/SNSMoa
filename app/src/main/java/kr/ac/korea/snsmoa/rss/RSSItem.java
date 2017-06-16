package kr.ac.korea.snsmoa.rss;

import kr.ac.korea.snsmoa.article.ArticleItem;

/**
 * Created by Noverish on 2017-06-16.
 */

public class RSSItem extends ArticleItem {
    @Override
    public String getContentForCategorize() {
        if(content.equals(""))
            return title;
        else
            return title + " - " + content;
    }
}
