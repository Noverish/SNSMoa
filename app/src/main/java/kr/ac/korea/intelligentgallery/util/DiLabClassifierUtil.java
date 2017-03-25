package kr.ac.korea.intelligentgallery.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import kr.ac.korea.intelligentgallery.asynctask.ClassifyingInitializing;
import kr.ac.korea.intelligentgallery.data.ImageFile;
import kr.ac.korea.intelligentgallery.database.DatabaseCRUD;
import kr.ac.korea.intelligentgallery.database.util.DatabaseConstantUtil;
import kr.ac.korea.intelligentgallery.intelligence.Ranker.SemanticMatching;
import kr.ac.korea.mobide.classifier.CentroidClassifier;
import kr.ac.korea.mobide.knowledge_android.knowledge.CategoryNamingConverter;
import kr.ac.korea.mobide.knowledge_android.knowledge.DatabaseInitializer;
import kr.ac.korea.mobide.knowledge_android.knowledge.MNClassifier;
import kr.ac.korea.mobide.util.ScoreData;

public class DiLabClassifierUtil {
    public static CentroidClassifier centroidClassifier;
    public static MNClassifier mnClassifier;
    public static CategoryNamingConverter cNameConverter;
    public static DatabaseInitializer initializer;
    public static SemanticMatching semanticMatching;
    public static Integer K;

    public static void init(Context context){
        DiLabClassifierUtil.initializer = new DatabaseInitializer(context);
        DiLabClassifierUtil.cNameConverter = new CategoryNamingConverter(2);
        DiLabClassifierUtil.mnClassifier = new MNClassifier(3, 2);
        DiLabClassifierUtil.centroidClassifier = CentroidClassifier.getClassifier(DiLabClassifierUtil.initializer.getTargetPath(), "sigmaBase030.db");
        DiLabClassifierUtil.semanticMatching = new SemanticMatching(context);
        DiLabClassifierUtil.K = 5;

        new ClassifyingInitializing(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");

    }

    public static LinkedHashMap<Integer, String> classifySpecificImageFile(String inputText, Integer _DATA) {

        ArrayList<ScoreData> categoryList;
        ArrayList<String> categoriesArrayList = new ArrayList<>();
        ArrayList<Integer> categoryIDsArrayList = new ArrayList<>();

        String textViewString = "";
        int rank = 1;

        categoryList = centroidClassifier.topK(K, inputText);
        String rawQuery = "insert or ignore into " + DatabaseConstantUtil.TABLE_INTELLIGENT_GALLERY_NAME + "(" + DatabaseConstantUtil.COLUMN_AUTO_INCREMENT_KEY + ", " + DatabaseConstantUtil.COLUMN_DID + ", " + DatabaseConstantUtil.COLUMN_CATEGORY_ID + ", " + DatabaseConstantUtil.COLUMN_RANK + ", " + DatabaseConstantUtil.COLUMN_SCORE + ") values ";

        DiLabClassifierUtil.mnClassifier = new MNClassifier(3, 2);

        for (ScoreData scoreData : categoryList) {
            int cID = scoreData.getID();
            double score = scoreData.getScore();
            String cNameOriginal = centroidClassifier.getCategoryName(cID);
            String cName = cNameConvertWrapper(cNameOriginal);

            categoriesArrayList.add(cName);
            categoryIDsArrayList.add(cID);

            //inserting DB
            textViewString = textViewString + "\n" + "id : " + _DATA + "\n categoryId : " + cID + "\n rank : " + rank + "\n categoryName : " + cNameOriginal + " (" + cName + ") : \n score: " + score;

            rawQuery += "(null, '" + _DATA + "', " + cID + ", " + rank + ", " + score+"),";
            rank++;
        }

        //MNClassifer 적용하여 rank 0에 넣기 / score를 1.0으로 저장할 예정
        LinkedHashMap<Integer, String> top0Info = new LinkedHashMap<>();
        String[] categories = new String[categoriesArrayList.size()];
        for (int i = 0; i < categoriesArrayList.size(); i++) {
            categories[i] = categoriesArrayList.get(i);
        }

        try { // todo 시연용 임시 예외처리
            String top0Name = DiLabClassifierUtil.mnClassifier.classifying(categories);
            Integer top0ID = categoryIDsArrayList.get(0);

            /* 동일한 이름의 카테고리에 대해서 같은 Category ID를 가지도록 하는 코드 todo UbiComp 시연용 */
            ArrayList<Integer> cIDList = DatabaseCRUD.selectCategoryIDList();
            ArrayList<String> cNameList = DatabaseCRUD.selectCategoryNameList();

            // 축약된 cName이 이미 존재하는지 여부를 체크하고, 존재하는 경우 cID를 현재 존재하는 이름의 ID로 바꾸어 저장함.
            for(int i=0; i < cNameList.size(); i++) {
                if(cNameList.get(i).equals(top0Name)) {
//                    Log.i("setCategory: ", cNameList.get(i) + " - " + top0Name);
//                    Log.i("setCategory: ", cIDList.get(i) + " - " + top0ID);
                    top0ID = cIDList.get(i);
                    break;
                }
            }

//            if (categories != null && categories.length >= 0) {
//                for (int i = categories.length - 1; i >= 0; i--) {
//                    DebugUtil.showDebug("DiLabClassifierUtil, i::" + i +", ::categories[i]::" + categories[i]);
//                    if(!TextUtil.isNull(top0Name)){
//                        if (categories[i].contains(top0Name)) {
//                            top0ID = categoryIDsArrayList.get(i);
//                            DebugUtil.showDebug("DiLabClassifierUtil, top0ID::" + top0ID +", ::top0Name::" + top0Name);
//                        }
//                    }
//                }
//            }
            /* 동일한 이름의 카테고리에 대해서 같은 Category ID를 가지도록 하는 코드 todo UbiComp 시연용 */

            top0Info.put(top0ID, top0Name);
            DebugUtil.showDebug("DiLabClassifierUtil, top0ID::" + top0ID +", ::top0Name::" + top0Name);

            rawQuery += "(null, '" + _DATA + "', " + top0ID + ", " + 0 + ", " + 1.0 +");";

            DebugUtil.showDebug("DiLabClassifierUtil, classifyViewItems() rawQuery : " + rawQuery);
            DatabaseCRUD.execRawQuery(rawQuery);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return top0Info;
    }

    // 외부에서 사진을 지웠을 때 같은 상황에서 사용하는 함수
    public static void deleteUselessDB(Context context){
        ArrayList<Integer> dbImages = new ArrayList<>();
        ArrayList<ImageFile> mediaImages = new ArrayList<>();
        ArrayList<Integer> uselessDids = new ArrayList<>();
        dbImages = DatabaseCRUD.getImagesIdsInInvertedIndexDb();
        mediaImages = FileUtil.getImagesHavingGPSInfo(context);
        uselessDids = dbImages;
        int dbImageCnt = dbImages.size();
        int mediaImageCnt = mediaImages.size();

        DebugUtil.showDebug("zzz", "ClassifyingUsingAsyncTask, ", "" + dbImageCnt);
        DebugUtil.showDebug("zzz", "ClassifyingUsingAsyncTask, ", "" + mediaImageCnt);
        // 만일 외부에서 사진을 지워서 디비의 개수가 쿼리에 있는 분류해야할 사진의 개수보다 많다면
        if (mediaImageCnt < dbImageCnt) {
            DebugUtil.showDebug("zzz, 외부에서 사진을 지워서 디비에 불필요한 이미지가 저장된 상태");

            for (int i = dbImageCnt -1; i >= 0; i--) {
                DebugUtil.showDebug("zzz, dbImages.get("+i + ") :: " + dbImages.get(i) + "===============");
                for(int j = 0; j < mediaImageCnt; j ++) {
                    DebugUtil.showDebug("zzz, mediaImages.get("+j + ").getId :: " + mediaImages.get(j).getId());
                    if(dbImages.get(i).equals(mediaImages.get(j).getId()) ){
                        DebugUtil.showDebug("zzz. did 같음");
                        uselessDids.remove(i);
                        break;
                    }
                }
            }

        }
        for(Integer uselessImageId : uselessDids){
            DebugUtil.showDebug("zzz, uselessImage 지워야하는 이미지 :: " + uselessImageId);
            DatabaseCRUD.deleteSpecificIdQuery(uselessImageId);
        }
    }

    // 최종 카테고리 축약이름을 변경하는 메소드
    public static String cNameConvertWrapper(String original) {
        String result;
        result = cNameConverter.convert(original);
        if(result.equals("뉴스")) { result = "교육"; }

        // 영문화 작업을 위한 임시코드
//        switch(result) {
//            case "맥주": result = "Beer"; break;
//            case "음주": result = "Drink"; break;
//            case "겨울스포츠": result = "Winter Sports"; break;
//            case "텔레비전": result = "Television"; break;
//            case "음악": result = "Music"; break;
//            case "운송": result = "Transportation"; break;
//            case "음식": result = "Food"; break;
//            case "가정용품": result = "Home and Garden"; break;
//            case "박물관": result = "Museums"; break;
//            case "시각예술": result = "Visual Arts"; break;
//            case "교육": result = "Education"; break;
//
//            case "사무용품": result = "Office Products"; break;
//        }

        return result;
    }

    // 임시 유틸리티 메소드(오현택)
    public static String cNameFromID(Integer id) {
        String cNameOriginal = DiLabClassifierUtil.centroidClassifier.getCategoryName(id);

        return DiLabClassifierUtil.cNameConvertWrapper(cNameOriginal);
    }
}
