package kr.ac.korea.snsmoa.facebook;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import kr.ac.korea.snsmoa.util.Essentials;

import static kr.ac.korea.snsmoa.util.Essentials.getMatches;

/**
 * Created by Noverish on 2016-08-21.
 */
public class FacebookHtmlProcessor {
    /*
        헤더있는 _55wo _5rgr _5gh8
        헤더없는 _55wo _5rgr _5gh8 async_like
        커뮤니티 추천 _57_6 fullwidth carded _58vs _5o5d _5o5c _t26 _45js _29d0 _51s _55wr acw apm
        페이지를 좋아합니다 _55wo _5rgr _5gh8 async_like
        비어있음 _55wo _5rgr _5gh8 _35au
         */

    public static ArrayList<FacebookArticleItem> processArticle(String htmlCode) {
        ArrayList<FacebookArticleItem> items = new ArrayList<>();

        Document document = Jsoup.parse(htmlCode);
        Elements articles = document.select("article");

        Log.i("<facebook article>","facebook article is " + articles.size());

        for(Element article : articles) {
            FacebookArticleItem item = new FacebookArticleItem();

            if(article.classNames().contains("_35au"))
                continue;
            if(article.select("header[class=\"_5rgs _5sg5\"]").size() > 0)
                //추천 게시물
                continue;
            if(article.classNames().contains("fullwidth"))
                // 누가 좋아합니다 하고 좌우로 스크롤 할 수 있는 페이지 추천 글 _57_6 fullwidth carded _58vs _5o5d _5o5c _t26 _45js _29d0 _51s _55wr acw apm
                continue;
            if(article.classNames().contains("_d2r"))
                //보이지 않는 무언가
                continue;
            if(article.classNames().containsAll(Arrays.asList("_56be", "_4hkg", "_5rgr", "_5s1m", "async_like")))
                //내부 아티클 _56be _4hkg _5rgr _5s1m async_like
                continue;
            if(article.classNames().containsAll(Arrays.asList("_2hrn", "_5t8z", "acw", "apm")))
                //보이지 않는 무언가의 내부 아티클 (누가 무슨 페이지를 좋아합니다 인 듯하다) _2hrn _5t8z acw apm
                continue;
            if(article.select("article").size() > 1)
                //내부 아티클 있으면 건너 뜀
                continue;

            Elements headerPart = article.select("header[class=\"_4g33 _ung _5qc1\"]").select("h3[class=\"_52jd _52jb _52jg _5qc3\"]");
            item.setHeader(Essentials.unicodeToString(headerPart.outerHtml().replaceAll("<[^>]*>","")));

            Elements titlePart = article.select("header[class=\"_4g33 _5qc1\"]");

            Elements profileImage = titlePart.select("i.img.profpic");
            item.setProfileImgUrl(extractImageUrl(profileImage, htmlCode));

            Elements title = titlePart.select("h3._52jd._52jb._5qc3"); //제목에 행동이 있는 경우 _52jd _52jb _52jg _5qc3 이고 이름만 있는 경우 _52jd _52jb _52jh _5qc3 이다.
            item.setTitle(Essentials.unicodeToString(title.outerHtml().replaceAll("<[^>]*>","")));
            item.setPosterUrl("https://m.facebook.com" + title.select("a").attr("href"));

            Elements timeLocationPart = titlePart.select("div._52jc._5qc4._24u0").select("a");
            if(timeLocationPart.size() == 1) {
                item.setTimeString(Essentials.unicodeToString(timeLocationPart.get(0).outerHtml().replaceAll("<[^>]*>","")));
                item.setTimeMillis(stringToMillisInFacebook(item.getTimeString()));
            } else if(timeLocationPart.size() == 2) {
                item.setLocation(Essentials.unicodeToString(timeLocationPart.get(1).outerHtml().replaceAll("<[^>]*>","")));
            }

            Element content = article.select("div[class=\"_5rgt _5nk5 _5msi\"]").select("span").first();
            content.select("[class=\"text_exposed_show\"]").remove();
            item.setContent(Essentials.unicodeToString(content.outerHtml().replaceAll("<[^>]*>","").trim()));

            Elements articleUrlEle = content.select("a[class=\"_5msj\"]");
            item.setArticleUrl("https://m.facebook.com" + articleUrlEle.attr("href"));

            Elements mediaPart = article.select("div._5rgu._27x0");
            Elements linkEle = mediaPart.select("section[class=\"_55wo _4o58 _4prr _11cc touchable _5t8z _4o5j _4o5k\"]");

            if(linkEle.size() > 0) {
                Element linkImgEle = linkEle.select("i[class=\"img img _4s0y\"]").first();
                Element linkTitleEle = linkEle.select("h1[class=\"_52jd _52jh _4o51 _hm6\"]").first();
                Element linkContentEle = linkEle.select("div[class=\"_52jc _24u0 _1xvv _5tg_\"]").first();
                Element linkUrlEle = linkEle.select("a[class=\"_4o50 touchable\"]").first();

                if(linkImgEle != null) {
                    item.setLinkImgUrl(extractImageUrl(linkImgEle, htmlCode));
                }

                item.setLinkTitle(Essentials.unicodeToString(linkTitleEle.html()));
                item.setLinkContent(linkContentEle.html().replaceAll("[\\s]*<[^>]*>[\\s]*", ""));
                item.setLinkUrl(linkUrlEle.attr("href"));
            } else {
                Elements imageElements = mediaPart.select("a._39pi, a._26ih");
                for (Element imageElement : imageElements)
                    item.addImgUrl(extractImageUrl(imageElement.select("i"), htmlCode));

                Elements videoElements;
                if ((videoElements = mediaPart.select("div._53mw._4gbu")).size() > 0) { //동영상만 올라옴
                    String videoUrl = extractImageUrl(videoElements, htmlCode);
                    String imageUrl = extractImageUrl(videoElements.select("i"), htmlCode);

                    item.setVideoData(imageUrl, videoUrl);
                } else if ((videoElements = mediaPart.select("div._53mw")).size() > 0) { //동영상과 사진 같이 올라움
                    System.out.println(videoElements.outerHtml());
                }
            }

            Elements likeButton = article.select("a._15ko._5a-2.touchable");
            item.setLiked(Boolean.parseBoolean(likeButton.attr("aria-pressed")));
            item.setLikeUrl(likeButton.attr("data-uri"));

            Element numberBar = article.select("div[class=\"_rnk _2eo- _1e6\"]").first();

            Element likeNum = numberBar.select("div[class=\"_1g06\"]").first();
            if(likeNum != null)
                item.setLikeNum(Essentials.unicodeToString(likeNum.html()));

            if(numberBar.select("span[class=\"_1j-c\"]").size() >= 1) {
                Element commentNum = numberBar.select("span[class=\"_1j-c\"]").get(0);
                item.setCommentNum(Essentials.unicodeToString(commentNum.html()));
            }

            if(numberBar.select("span[class=\"_1j-c\"]").size() >= 2) {
                Element shareNum = numberBar.select("span[class=\"_1j-c\"]").get(1);
                item.setSharingNum(Essentials.unicodeToString(shareNum.html()));
            }

            items.add(item);
        }

        return items;
    }

    private static String extractImageUrl(Elements damagedElement, String originHtml) {
        String key = getMatches("oh=[0-9a-z]+",damagedElement.outerHtml());
        if(key.equals(""))
            key = getMatches("[\\d]+_[\\d]+_[\\d]+",damagedElement.outerHtml());
        if(key.equals("")) {
            key = getMatches("d=\"[^&]+&", damagedElement.outerHtml());

            if(key.equals("")) {
                System.out.println("empty key - " + damagedElement.outerHtml());
                return "";
            }

            key = key.substring(3);
        }

        return getMatches("https[^\"]*" + key +"[^\"]*",originHtml);
    }

    private static String extractImageUrl(Element damagedElement, String originHtml) {
        return extractImageUrl(new Elements(damagedElement), originHtml);
    }

    public static long stringToMillisInFacebook(String str) {
        Calendar calendar = Calendar.getInstance();
        String tmp;

        if(!(tmp = getMatches("[\\d]+분",str)).equals(""))
            calendar.add(Calendar.MINUTE, -Integer.parseInt(tmp.replaceAll("[\\D]","")));

        if(!(tmp = getMatches("[\\d]+시간",str)).equals(""))
            calendar.add(Calendar.HOUR, -Integer.parseInt(tmp.replaceAll("[\\D]","")));

        if(str.contains("어제"))
            calendar.add(Calendar.DATE, -1);

        if(str.contains("오전"))
            calendar.set(Calendar.AM_PM, Calendar.AM);
        else if(str.contains("오후"))
            calendar.set(Calendar.AM_PM, Calendar.PM);

        if(!(tmp = getMatches("[\\d]+:[\\d]+", str)).equals("")) {
            calendar.set(Calendar.HOUR, Integer.parseInt(tmp.split(":")[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(tmp.split(":")[1]));
        }

        if(!(tmp = getMatches("[\\d]+일", str)).equals(""))
            calendar.set(Calendar.DATE, Integer.parseInt(tmp.replaceAll("[\\D]","")));

        if(!(tmp = getMatches("[\\d]+월", str)).equals(""))
            calendar.set(Calendar.MONTH, Integer.parseInt(tmp.replaceAll("[\\D]","")));

        if(!(tmp = getMatches("[\\d]+년", str)).equals(""))
            calendar.set(Calendar.YEAR, Integer.parseInt(tmp.replaceAll("[\\D]","")));

        return calendar.getTimeInMillis();
    }
}
