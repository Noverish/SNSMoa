package kr.ac.korea.snsmoa;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.ac.korea.snsmoa.article.ArticleItem;
import kr.ac.korea.snsmoa.asynctask.CategorizeAsyncTask;
import kr.ac.korea.snsmoa.facebook.FacebookArticleItem;
import kr.ac.korea.snsmoa.facebook.FacebookArticleView;
import kr.ac.korea.snsmoa.facebook.FacebookClient;
import kr.ac.korea.snsmoa.twitter.TwitterArticleItem;
import kr.ac.korea.snsmoa.twitter.TwitterArticleView;
import kr.ac.korea.snsmoa.twitter.TwitterClient;
import kr.ac.korea.snsmoa.util.GridSpacingItemDecoration;


/**
 * Created by Noverish on 2017-03-22.
 */

public class CustomRecyclerView extends RecyclerView implements FacebookClient.OnNewItemLoaded, TwitterClient.OnNewItemLoaded {
    private ArrayList<ArticleItem> allItems = new ArrayList<>();
    private ArrayList<ArticleItem> items = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private int numOfLoaded = 2;
    private String category = "ALL";

    public CustomRecyclerView(Context context) {
        super(context);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setAdapter(new CustomAdapter());
        setLayoutManager(layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addItemDecoration(new GridSpacingItemDecoration(1, 20, false));
        addOnScrollListener(new CustomScrollListener());
        TwitterClient.getInstance().setOnNewItemLoaded(this);
        FacebookClient.getInstance().setOnNewItemLoaded(this);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
        public static final int FACEBOOK = 0;
        public static final int TWITTER = 1;

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case FACEBOOK:
                    return new CustomViewHolder(new FacebookArticleView(getContext()));
                case TWITTER:
                    return new CustomViewHolder(new TwitterArticleView(getContext()));
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            if(holder.itemView instanceof FacebookArticleView && items.get(position) instanceof FacebookArticleItem) {
                ((FacebookArticleView) holder.itemView).setItem((FacebookArticleItem) items.get(position));
            } else if(holder.itemView instanceof TwitterArticleView && items.get(position) instanceof TwitterArticleItem) {
                ((TwitterArticleView) holder.itemView).setItem((TwitterArticleItem) items.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            if(items.get(position) instanceof FacebookArticleItem)
                return FACEBOOK;
            if(items.get(position) instanceof TwitterArticleItem)
                return TWITTER;
            return -1;
        }
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class CustomScrollListener extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if(dy > 0 && numOfLoaded >= 2) {
                if (items.size() - layoutManager.findFirstCompletelyVisibleItemPosition() < 5) {
                    numOfLoaded = 0;

                    FacebookClient.getInstance().loadNextPage();
                    TwitterClient.getInstance().loadNextPage();
                }
            }
        }
    }

    @Override
    public void onNewFacebookItemLoaded(ArrayList<FacebookArticleItem> newItems) {
        Log.i("<facebook article>","new facebook article is " + newItems.size());

        addItems(newItems);
        numOfLoaded++;
    }

    @Override
    public void onNewTwitterItemLoaded(ArrayList<TwitterArticleItem> newItems) {
        Log.i("<twitter article>","new twitter article is " + newItems.size());

        addItems(newItems);
        numOfLoaded++;
    }

    private <T extends ArticleItem> void addItems(ArrayList<T> newItems) {
        allItems.addAll(newItems);

        if(category.equals("ALL")) {
            items.addAll(newItems);
            getAdapter().notifyDataSetChanged();
        } else {
            for (ArticleItem item : newItems)
                if(item.getFullCategory() != null && !item.getFullCategory().equals("")) {
                    if (category.equals(item.getFullCategory().split("/")[1])) {
                        items.add(item);
                        getAdapter().notifyItemInserted(items.size() - 1);
                    }
                } else {
                    new CategorizeAsyncTask(getContext(), item.getContent(), new PreCategorizeListener(item)).execute();
                }
        }
    }

    public void setCategory(String category) {
        if(!this.category.equals(category)) {
            this.category = category;

            items.clear();
            if (category.equals("ALL")) {
                items.addAll(allItems);
            } else {
                for (ArticleItem item : allItems)
                    if (item.getFullCategory() != null && !item.getFullCategory().equals("")) {
                        if (category.equals(item.getFullCategory().split("/")[1])) {
                            items.add(item);
                        }
                    } else {
                        new CategorizeAsyncTask(getContext(), item.getContent(), new PreCategorizeListener(item)).execute();
                    }
            }
            getAdapter().notifyDataSetChanged();
        }
    }

    private class PreCategorizeListener implements CategorizeAsyncTask.CategorizeListener {
        private ArticleItem item;

        private PreCategorizeListener(ArticleItem item) {
            this.item = item;
        }

        @Override
        public void onCategorized(@Nullable String fullCategory) {
            if(fullCategory != null) {
                if (category.equals(fullCategory.split("/")[1])) {
                    item.setFullCategory(fullCategory);
                    items.add(item);
                    getAdapter().notifyItemInserted(items.size() - 1);
                }
            } else {

            }
        }
    }
}
