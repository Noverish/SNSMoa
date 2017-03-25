package kr.ac.korea.snsmoa.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by Noverish on 2017-03-22.
 */

public class RunnableAsyncTask extends AsyncTask<Void, Void, Void> {
    private Runnable runnable;
    private long millis = 1000;

    public RunnableAsyncTask(@NonNull Runnable runnable) {
        this.runnable = runnable;
    }

    public RunnableAsyncTask(@NonNull Runnable runnable, long millis) {
        this.runnable = runnable;
        this.millis = millis;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(millis);
        } catch (Exception ex) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(runnable != null) {
            runnable.run();
        }
    }
}
