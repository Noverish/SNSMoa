package kr.ac.korea.snsmoa.asynctask;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.ArrayList;

import kr.ac.korea.intelligentgallery.util.DiLabClassifierUtil;
import kr.ac.korea.mobide.util.ScoreData;
import kr.ac.korea.snsmoa.MainActivity;

/**
 * Created by Noverish on 2017-03-25.
 */

public class ClassifyAsyncTask extends AsyncTask<Void, Void, String> {
    private ClassifyListener classifyListener;
    private String input;
    private TextView textView;

    public ClassifyAsyncTask(TextView textView, String input) {
        this(textView, input, null);
    }

    public ClassifyAsyncTask(TextView textView, String input, ClassifyListener classifyListener) {
        this.input = input;
        this.textView = textView;
        this.classifyListener = classifyListener;
    }

    @Override
    protected void onPreExecute() {
        textView.setText("");
    }

    @Override
    protected String doInBackground(Void... params) {
        ArrayList<ScoreData> scoreDatas = DiLabClassifierUtil.centroidClassifier.topK(1, input);
        return DiLabClassifierUtil.centroidClassifier.getCategoryName(scoreDatas.get(0).getID());
    }

    @Override
    protected void onPostExecute(String result) {
        Intent braodcast = new Intent(MainActivity.AddCategoryToMenuRecevier.ACTION);
        braodcast.putExtra(MainActivity.AddCategoryToMenuRecevier.INTENT_KEY, result.split("/")[1]);
        textView.getContext().sendBroadcast(braodcast);

        textView.setText(result);
        if(classifyListener != null)
            classifyListener.onClassified(result);
    }

    public interface ClassifyListener {
        void onClassified(String result);
    }
}
