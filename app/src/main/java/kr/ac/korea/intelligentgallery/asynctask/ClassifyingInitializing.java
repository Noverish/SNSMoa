package kr.ac.korea.intelligentgallery.asynctask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import kr.ac.korea.intelligentgallery.data.ImageFile;
import kr.ac.korea.intelligentgallery.foursquare.Foursquare;
import kr.ac.korea.intelligentgallery.util.DebugUtil;
import kr.ac.korea.intelligentgallery.util.DiLabClassifierUtil;
import kr.ac.korea.intelligentgallery.util.ExifUtil;
import kr.ac.korea.intelligentgallery.util.FileUtil;
import kr.ac.korea.mobide.util.ScoreData;


public class ClassifyingInitializing extends AsyncTask<String, String, String> {

    private Context mContext;

    public ClassifyingInitializing(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        ArrayList<ScoreData> categoryList = DiLabClassifierUtil.centroidClassifier.topK(DiLabClassifierUtil.K, "");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        DebugUtil.showDebug("ClassifyingWhenExternalImagesExistAsyncTask, onPreExecute() ");
    }
}