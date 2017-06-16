package kr.ac.korea.snsmoa.rss;

import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Noverish on 2017-06-16.
 */

public class RSSClient {
    private static final int PAGE_SIZE = 10;
    private static final String CNN = "http://rss.cnn.com/rss/edition.rss";

    private OkHttpClient client = new OkHttpClient();
    private OnNewItemLoaded onNewItemLoaded;

    private ArrayList<RSSItem> rssItems = new ArrayList<>();
    private int nowPage = 0;

    private static RSSClient instance;
    public static RSSClient getInstance() {
        if(instance == null)
            instance = new RSSClient();

        return instance;
    }

    private RSSClient() {}

    public void initiate() {
        new InitTask().execute();
    }

    private class InitTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                Request request = new Request.Builder()
                        .url(CNN)
                        .build();

                Response response = client.newCall(request).execute();
                String html =  response.body().string();

                StringBuilder xmlStringBuilder = new StringBuilder();
                xmlStringBuilder.append(html);

                ByteArrayInputStream input =  new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(input);
                doc.getDocumentElement().normalize();

                String profileImgUrl = doc.getElementsByTagName("url").item(0).getTextContent();

                NodeList nList = doc.getElementsByTagName("item");
                for (int index = 0; index < nList.getLength(); index++) {
                    Node nNode = nList.item(index);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        RSSItem rssItem = new RSSItem();

                        Element eElement = (Element) nNode;

                        String title;
                        try {
                            title = eElement.getElementsByTagName("title").item(0).getTextContent();
                        } catch (Exception ex) {
                            title = "";
                        }

                        String content;
                        try {
                            content = eElement.getElementsByTagName("description").item(0).getTextContent();
                        } catch (Exception ex) {
                            content = "";
                        }

                        String timeString;
                        try {
                            timeString = eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                        } catch (Exception ex) {
                            timeString = "";
                        }

                        String imgUrl;
                        try {
                            imgUrl = ((Element) ((Element) eElement.getElementsByTagName("media:group").item(0)).getElementsByTagName("media:content").item(0)).getAttribute("url");
                        } catch (Exception ex) {
                            imgUrl = "";
                        }

                        rssItem.setProfileImgUrl(profileImgUrl);
                        rssItem.setTitle(title);
                        rssItem.setContent(content);
                        rssItem.setTimeString(timeString);
                        rssItem.setLinkImgUrl(imgUrl);

                        rssItems.add(rssItem);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(onNewItemLoaded != null)
                onNewItemLoaded.onNewRSSItemLoaded(new ArrayList<>(rssItems.subList(nowPage * PAGE_SIZE, (nowPage + 1) * PAGE_SIZE)));
        }
    }

    public void loadNextPage() {
        if(onNewItemLoaded != null)
            try {
                onNewItemLoaded.onNewRSSItemLoaded(new ArrayList<>(rssItems.subList(nowPage * PAGE_SIZE, (nowPage + 1) * PAGE_SIZE)));
            } catch (IndexOutOfBoundsException ex) {
                onNewItemLoaded.onNewRSSItemLoaded(new ArrayList<>(rssItems.subList(nowPage * PAGE_SIZE, rssItems.size())));
            }
    }

    public void setOnNewItemLoaded(OnNewItemLoaded onNewItemLoaded) {
        this.onNewItemLoaded = onNewItemLoaded;
    }

    public interface OnNewItemLoaded {
        void onNewRSSItemLoaded(ArrayList<RSSItem> newItems);
    }
}
