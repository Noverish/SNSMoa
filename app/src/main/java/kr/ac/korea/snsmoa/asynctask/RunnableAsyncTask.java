package kr.ac.korea.snsmoa.asynctask;

import android.os.AsyncTask;

/**
 * Created by Noverish on 2017-03-22.
 */

public class RunnableAsyncTask extends AsyncTask<Void, Void, Void> {
    private Runnable background;
    private Runnable postExecute;
    private long millis;

    public RunnableAsyncTask(Runnable postExecute) {
        this(postExecute, 1000);
    }

    public RunnableAsyncTask(Runnable postExecute, Runnable background) {
        this.postExecute = postExecute;
        this.background = background;
    }

    public RunnableAsyncTask(Runnable postExecute, long millis) {
        this.postExecute = postExecute;
        this.millis = millis;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            if (background == null)
                Thread.sleep(millis);
            else
                background.run();
        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(postExecute != null) {
            postExecute.run();
        }
    }
}
