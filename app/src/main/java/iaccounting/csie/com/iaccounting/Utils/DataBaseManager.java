package iaccounting.csie.com.iaccounting.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import iaccounting.csie.com.iaccounting.Home.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zixuan Zhao on 2/23/17.
 */

public class DataBaseManager {

    private DataBaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    private static final String DB_NAME = "diary";
    private static final String TB_NAME = "tb_diaries";
    private static final String COL_DATE = "date";
    private static final String COL_CRE_DT = "createdatetime";
    private static final String COL_MOD_DT = "modifydatetime";
    private static final String COL_WEATHER = "weather";
    private static final String COL_EMOTION = "emotion";
    private static final String COL_DIARYTEXT = "diarytext";

    /**
     * Constructor
     * @param context
     */
    public DataBaseManager(Context context){
        dbHelper = new DataBaseOpenHelper(context, DB_NAME);
    }

    /**
     * Open database in Read and Write model
     */
    public void openWritableDatabase(){
        if(db == null || !db.isOpen())
            db = dbHelper.getWritableDatabase();
    }

    /**
     * Open database in Only Read model
     */
    public void openReadableDatabase(){
        if(db == null || !db.isOpen())
            db = dbHelper.getReadableDatabase();
    }

    /**
     * Close database
     * @return
     */
    public boolean closeDatabase(){
        if(db != null){
            db.close();
        }
        return !db.isOpen();
    }

    /**
     * Insert diary info
     * @param date
     * @param createDateTime
     * @param modifyDateTime
     * @param weather
     * @param emotion
     * @param diaryText
     * @return If sucess to insert, return true, otherwise false
     */
    public boolean insert(String date, String createDateTime, String modifyDateTime, String weather, String emotion, String diaryText){

        /*
        String sql = "INSERT INTO diary(date, weather, emotion, diaryText) VALUES ("
                + date + ','
                + weather + ","
                + date + ","
                + emotion + ","
                + diaryText + ");";
        db.execSQL(sql);
        */

        ContentValues cv = new ContentValues();
        cv.put(COL_DATE, date);
        cv.put(COL_CRE_DT, createDateTime);
        cv.put(COL_MOD_DT, modifyDateTime);
        cv.put(COL_WEATHER, weather);
        cv.put(COL_EMOTION, emotion);
        cv.put(COL_DIARYTEXT, diaryText);

        long id = db.insert(TB_NAME, null, cv);
        return (id != -1);
    }

    public boolean insert(String diary[]){
        if(diary == null){
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(COL_DATE, diary[0]);
        cv.put(COL_CRE_DT, diary[1]);
        cv.put(COL_MOD_DT, diary[2]);
        cv.put(COL_WEATHER, diary[3]);
        cv.put(COL_EMOTION, diary[4]);
        cv.put(COL_DIARYTEXT, diary[5]);

        long id = db.insert(TB_NAME, null, cv);
        return (id != -1);
    }

    /**
     * Update diary info by date
     * @param date
     * @param modifyDateTime
     * @param weather
     * @param emotion
     * @param diaryText
     * @return if success to update, return true, otherwise false
     */
    public boolean update(String date, String modifyDateTime, String weather, String emotion, String diaryText){
        ContentValues cv = new ContentValues();
        cv.put(COL_MOD_DT, modifyDateTime);
        cv.put(COL_WEATHER, weather);
        cv.put(COL_EMOTION, emotion);
        cv.put(COL_DIARYTEXT, diaryText);

        String args[] = {date};
        int rows = db.update(TB_NAME, cv, COL_DATE + " = ?", args);

        return (rows != 0);
    }

    public boolean update(String diary[]){
        if(diary == null){
            return false;
        }
        ContentValues cv = new ContentValues();
        cv.put(COL_MOD_DT, diary[2]);
        cv.put(COL_WEATHER, diary[3]);
        cv.put(COL_EMOTION, diary[4]);
        cv.put(COL_DIARYTEXT, diary[5]);

        String args[] = {diary[0]};
        int rows = db.update(TB_NAME, cv, COL_DATE + " = ?", args);

        return (rows != 0);
    }

    /**
     * Delete diaryinfo by date
     * @param date
     * @return
     */
    public boolean delete(String date){
        /*
        String sql = "DELETING FROM diary WHERE date=" + date +";";
        db.execSQL(sql);
        */
        String args[] = {date};
        int rows = db.delete(TB_NAME, COL_DATE + " = ?", args);
        return (rows != 0);
    }

    /**
     * Query one diary info by date
     * @param date
     * @return diary object with queried info
     */
    public Diary query(String date){
        Diary d = null;
        String projection[] = {
                COL_CRE_DT,
                COL_MOD_DT,
                COL_WEATHER,
                COL_EMOTION,
                COL_DIARYTEXT
        };

        String args[] = {date};
        Cursor cursor = db.query(
                TB_NAME,
                projection,
                COL_DATE + " = ?",
                args,
                null,
                null,
                COL_DATE + " DESC"
        );

        if(cursor.moveToFirst()){
            d = new Diary(
                    cursor.getString(cursor.getColumnIndex(COL_CRE_DT)),
                    cursor.getString(cursor.getColumnIndex(COL_MOD_DT)),
                    cursor.getString(cursor.getColumnIndex(COL_WEATHER)),
                    cursor.getString(cursor.getColumnIndex(COL_EMOTION)),
                    cursor.getString(cursor.getColumnIndex(COL_DIARYTEXT))
            );
        }
        cursor.close();
        return d;
    }


    /**
     * Query all diary info and add all to data list
     * @param datalist
     */
    public void queryAll(List<Diary> datalist){
        String projection[] = {
                COL_CRE_DT,
                COL_MOD_DT,
                COL_WEATHER,
                COL_EMOTION,
                COL_DIARYTEXT
        };

        Cursor cursor = db.query(
                TB_NAME,
                projection,
                null,
                null,
                null,
                null,
                COL_CRE_DT + " DESC"
        );
        //Log.d("DataBaseManager:::", "cursor count--"+cursor.getCount());

        datalist.clear();
        if(cursor.moveToFirst()){
            do{
                Diary d = new Diary(
                        cursor.getString(cursor.getColumnIndex(COL_CRE_DT)),
                        cursor.getString(cursor.getColumnIndex(COL_MOD_DT)),
                        cursor.getString(cursor.getColumnIndex(COL_WEATHER)),
                        cursor.getString(cursor.getColumnIndex(COL_EMOTION)),
                        cursor.getString(cursor.getColumnIndex(COL_DIARYTEXT))
                );
                //Log.d("DataBaseManager:::", "datalist time--"+d.getCreateDateTime());
                datalist.add(d);
            }while(cursor.moveToNext());
        }
        cursor.close();
        //Log.d("DataBaseManager:::", "datalist--"+datalist.size());
    }

    public boolean queryToday(String today){

        String[] args = {today};
        Cursor cursor = db.query(
                TB_NAME,
                null,
                COL_DATE + " = ?",
                args,
                null,
                null,
                COL_DATE + " DESC"
        );
        //Log.d("DataBaseManager:::", "cursor count--"+cursor.getCount());

        return cursor.moveToFirst();
    }

}
