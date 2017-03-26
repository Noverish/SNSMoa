package kr.ac.korea.snsmoa.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.korea.intelligentgallery.util.DiLabClassifierUtil;
import kr.ac.korea.mobide.util.ScoreData;
import kr.ac.korea.snsmoa.MainActivity;

/**
 * Created by Noverish on 2017-03-25.
 */

public class CategorizeAsyncTask extends AsyncTask<Void, Void, String> {
    private static HashMap<String, String> classifyCached = new HashMap<>();

    private CategorizeListener categorizeListener;
    private Context context;
    private String input;

    public CategorizeAsyncTask(Context context, String input) {
        this(context, input, null);
    }

    public CategorizeAsyncTask(Context context, String input, CategorizeListener categorizeListener) {
        this.context = context;
        this.input = input;
        this.categorizeListener = categorizeListener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(classifyCached.keySet().contains(input)) {
            return classifyCached.get(input);
        } else {
            ArrayList<ScoreData> scoreDatas = DiLabClassifierUtil.centroidClassifier.topK(1, input);
            if(scoreDatas.size() == 0) {
                classifyCached.put(input, null);
            } else {
                classifyCached.put(input, DiLabClassifierUtil.centroidClassifier.getCategoryName(scoreDatas.get(0).getID()));
            }

            return classifyCached.get(input);
        }
    }

    @Override
    protected void onPostExecute(String fullCategory) {
        if(fullCategory != null) {
            Intent braodcast = new Intent(MainActivity.AddCategoryToMenuRecevier.ACTION);
            braodcast.putExtra(MainActivity.AddCategoryToMenuRecevier.INTENT_KEY, fullCategory.split("/")[1]);
            context.sendBroadcast(braodcast);
        } else {

        }

        if (categorizeListener != null)
            categorizeListener.onCategorized(fullCategory);
    }

    public interface CategorizeListener {
        void onCategorized(@Nullable String fullCategory);
    }
}
