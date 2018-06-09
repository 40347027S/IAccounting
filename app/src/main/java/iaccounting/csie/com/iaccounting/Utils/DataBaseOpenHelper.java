package iaccounting.csie.com.iaccounting.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zixuan Zhao on 2/23/17.
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    public DataBaseOpenHelper(Context context, String dbName){
        super(context, dbName, null, DB_VERSION);
    }

    public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    /*
    Database db_diaries:
    date: date when diary created; PrimaryKey;
    createdatetime: date time when diary created;
    modifydatetime: date time when diary last modified;
    weather: weather;
    emotion: emotion;
    diarytext: diarytext;
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_diaries(date TEXT PRIMARY KEY, createdatetime TEXT, modifydatetime TEXT, weather TEXT, emotion TEXT, diarytext TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
