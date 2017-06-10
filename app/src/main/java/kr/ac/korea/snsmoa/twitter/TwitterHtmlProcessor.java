package kr.ac.korea.snsmoa.twitter;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;

import kr.ac.korea.snsmoa.util.Essentials;


/**
 * Created by Noverish on 2016-09-18.
 */
public class TwitterHtmlProcessor {
    public static ArrayList<TwitterArticleItem> processArticle(String html) {
        ArrayList<TwitterArticleItem> items = new ArrayList<>();

        Document document = Jsoup.parse(html);
        Elements articles = document.select("div._1_KafmK5");

        Log.i("<twitter article>","twitter article is " + articles.size());

        for(Element article : articles) {
            TwitterArticleItem item = new TwitterArticleItem();

            Elements headerEle = article.select("div[class=\"_1axCTvm5 _2rKrV7oY _3f2NsD-H\"]");
            Element profileEle = article.select("img[class=\"_16WH7Eyq\"]").first();
            Element timeEle = article.select("time").first();
            Elements nameEle = article.select("span[class=\"Fe7ul3Lt _3ZSf8YGw _32vFsOSj _2DggF3sL _3WJqTbOE\"]");
            Elements screenNameEle = article.select("span[class=\"_1Zp5zVT9 _1rTfukg4\"]");
            Elements contentEle = article.select("span[class=\"Fe7ul3Lt _10YWDZsG _1rTfukg4 _2DggF3sL\"]");
            Element replyEle = article.select("button.RQ5ECnGZ._1m0pnxeJ").get(0);
            Element retweetEle = article.select("button.RQ5ECnGZ._1m0pnxeJ").get(1);
            Element likeEle = article.select("button.RQ5ECnGZ._1m0pnxeJ").get(2);
            Element dmEle = article.select("button.RQ5ECnGZ._1m0pnxeJ").get(3);
            Elements articleUrlEle = timeEle.parents();

            if(profileEle == null) {
                return new ArrayList<>();
            }

            contentEle.select("[aria-hidden=\"true\"]").remove();

            String header = headerEle.html();
            String profileImg = profileEle.attr("src");
            String timeDetail = timeEle.attr("aria-label");
            String timeStr = timeEle.html();
            String name = nameEle.html().replaceAll("<[^>]*>","");
            String screenName = screenNameEle.html();
            String content = contentEle.html();
            boolean isRetweeted = retweetEle.attr("data-testid").contains("un");
            boolean isLiked = likeEle.attr("data-testid").contains("un");
            int retweetNum = Integer.parseInt(retweetEle.select("span._1H8Mn9AA").html().equals("") ? "0" : retweetEle.select("span._1H8Mn9AA").html().replaceAll("\\D",""));
            int likeNum = Integer.parseInt(likeEle.select("span._1H8Mn9AA").html().equals("") ? "0" : likeEle.select("span._1H8Mn9AA").html().replaceAll("\\D",""));
            String articleUrl = "https://mobile.twitter.com" + articleUrlEle.attr("href");

            header = Essentials.unicodeToString(header);
            timeStr = Essentials.unicodeToString(timeStr);
            name = Essentials.unicodeToString(name);
            content = Essentials.unicodeToString(content);
            content = content.replaceAll("href=\"/","href=\"https://mobile.twitter.com/");

            item.setHeader(header);
            item.setProfileImgUrl(profileImg);
            item.setTimeMillis(stringToMillisInTwitter(timeDetail));
            item.setTimeString(timeStr);
            item.setTitle(name);
            item.setScreenName(screenName);
            item.setContent(content);
            item.setRetweeted(isRetweeted);
            item.setRetweetNum(retweetNum);
            item.setFavorited(isLiked);
            item.setFavoriteNum(likeNum);
            item.setArticleUrl(articleUrl);

            Element linkEle = article.select("a[class=\"_3kGl_FG7\"]").first();
            if(linkEle != null) {
                Element linkImgEle = linkEle.select("div.MLZaeRvv._1Yv_hemU").first(); //if i1xnVt31 exist link layout is horizontal
                Element linkTitleEle = linkEle.select("span[class=\"Fe7ul3Lt _2p1VUcTE _2DggF3sL _1HXcreMa\"]").first();
                Element linkContentEle = linkEle.select("span[class=\"Fe7ul3Lt _2DggF3sL _34Ymm628\"]").first();

                item.setLinkImgUrl(extractImgUrl(linkImgEle, html));
                item.setLinkTitle(Essentials.unicodeToString(linkTitleEle.html()));
                item.setLinkContent(Essentials.unicodeToString(linkContentEle.html()));
                item.setLinkUrl(linkEle.attr("href"));
            }

//            Elements mediaEle;
//            if((mediaEle = article.select("a[class=\"_3kGl_FG7\"]")).size() != 0) { //link
//                Elements linkImgEle = mediaEle.select("div[class=\"MLZaeRvv _1Yv_hemU i1xnVt31\"]");
//                Elements linkContentEle = mediaEle.select("span[class=\"Fe7ul3Lt _2DggF3sL _1HXcreMa\"]");
//                Elements linkDomainEle = mediaEle.select("span[class=\"Fe7ul3Lt _2DggF3sL _34Ymm628\"]");
//
//                String linkUrl = mediaEle.attr("href");
//                String linkImg = Essentials.getMatches("url[(][^)]*[)]",linkImgEle.outerHtml()).replaceAll("url[(]|[)]","").replaceAll("&amp;","&");
//                String linkContent = Essentials.unicodeToString(linkContentEle.html());
//                String linkDomain = Essentials.unicodeToString(linkDomainEle.html());
//
//                item.addImageUrl(linkImg);
//                item.setLinkUrl(linkUrl);
//                item.setLinkContent(linkContent);
////                item.setLinkDomain(linkDomain);
//            } else if ((mediaEle = article.select("div[class=\"_2zP-4IzO _3f2NsD-H\"]")).size() != 0) { //video
//                String videoImg = mediaEle.select("img").first().attr("src");
//                String videoUrl = mediaEle.select("a").first().attr("href");
//
//                item.addImageUrl(videoImg);
//                item.setVideoUrl(videoUrl);
//            } else if ((mediaEle = article.select("div[class=\"_2di_LxCm\"]")).size() != 0) { //image
//                for(Element imgEle : mediaEle.select("img")) {
//                    item.addImageUrl(imgEle.attr("src"));
//                }
//            }

            items.add(item);
        }

        return items;
    }

    public static String extractImgUrl(Element element, String originHtml) {
        String damaged = Essentials.getMatches("url[(][^)]*[)]", element.outerHtml());
        String key = Essentials.getMatches("\\d+", damaged);
        String extraced = Essentials.getMatches("\"[^\"]*" + key + "[^\"]*\"", originHtml);
        return extraced.replaceAll("\"","");
    }

    public static long stringToMillisInTwitter(String str) {
        try {
            Calendar calendar = Calendar.getInstance();

            String[] values = str.split("[^0-9]+");
            calendar.set(
                    Integer.valueOf(values[0]),
                    Integer.valueOf(values[1]),
                    Integer.valueOf(values[2]),
                    Integer.valueOf(values[3]),
                    Integer.valueOf(values[4]),
                    Integer.valueOf(values[5])
            );

            calendar.set(Calendar.AM_PM, str.contains("오전") ? Calendar.AM : Calendar.PM);

            return calendar.getTimeInMillis();
        } catch (Exception ex) {
            Log.d("<time>",str);
//            ex.printStackTrace();
            return 0;
        }
    }

    public static boolean isNotLogined(String html) {
        return Jsoup.parse(html).select("button[class=\"MmJh82_T _2UWZhODR lSM1Fder _2Rz0TobF _1pzUva68 _1qpWiIkN _3f2NsD-H\"]").size() > 0;
    }
}
