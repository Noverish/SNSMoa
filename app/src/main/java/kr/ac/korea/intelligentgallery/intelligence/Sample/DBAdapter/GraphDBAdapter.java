package kr.ac.korea.intelligentgallery.intelligence.Sample.DBAdapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import kr.ac.korea.mobide.util.ScoreData;


public class GraphDBAdapter {


	private static final String TABLE_GRAPH = "links";

	private static final String LINKS_KEY_CATEGORYIDA = "categoryidA";
	private static final String LINKS_KEY_CATEGORYIDB = "categoryidB";
	private static final String LINKS_KEY_RELEVANCE = "relevance";

	private SQLiteDatabase m_db;

	public GraphDBAdapter() {}

	public boolean openDB(String a_strDBFilepath) {
		try {
			this.m_db = SQLiteDatabase.openDatabase(a_strDBFilepath, null, SQLiteDatabase.OPEN_READONLY);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close() {
		this.m_db.close();		
	}
	/**
	 * ??
	 * @param   a_categoryID
	 * @param   N
	 * @return
	 * @since   Sigma1.0
	 */
	public ScoreData[] getTopNRelevanceCategory(int a_categoryID, int N) {
		String[] columns = {LINKS_KEY_CATEGORYIDB, LINKS_KEY_RELEVANCE};
		String selection = LINKS_KEY_CATEGORYIDA + "=?";
		String[] selectionArgs = {"" + a_categoryID};
		String OrderBy	= "relevance DESC LIMIT 0, " + N;
		Cursor result_cursor = this.m_db.query(TABLE_GRAPH, columns, selection, selectionArgs, null, null, OrderBy);
		if(result_cursor.moveToFirst()) {
			ArrayList<ScoreData> arrScoreDatas = new ArrayList<ScoreData>();
			do {
				int nCategoryIDB = result_cursor.getInt(0);
				double dblTRelevance = result_cursor.getDouble(1);
				ScoreData scoreData = new ScoreData(nCategoryIDB, dblTRelevance);
				arrScoreDatas.add(scoreData);
			} while(result_cursor.moveToNext());
			result_cursor.close();
			return arrScoreDatas.toArray(new ScoreData[0]);
		}else{
			result_cursor.close();
			return null;
		}
	}
}
