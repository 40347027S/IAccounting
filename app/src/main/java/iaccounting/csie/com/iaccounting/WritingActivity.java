package iaccounting.csie.com.iaccounting;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import iaccounting.csie.com.iaccounting.Home.Diary;
import iaccounting.csie.com.iaccounting.Utils.DataBaseManager;
import iaccounting.csie.com.iaccounting.Utils.Tools;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WritingActivity extends AppCompatActivity {

    private TextView weather;
    private TextView emotion;
    private TextView diaryText;
    private Toolbar toolbar;

    private Timer timer;
    private TimerTask autoSave;
    private TimerTask closeDatabase;

    private DataBaseManager dbManager;

    private boolean isNotWritten;
    private String[] editedText = new String[6];
    private String today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        today = Tools.getCurrentDate();
        Log.d("WrittingAcitivity:::", today);

        dbManager = new DataBaseManager(getApplicationContext());
        initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("WritingActivity:::", "onPause");
        cancelAutoSaving();                                     //cancel timer task autosaving

        SavingDiary savingDiary = new SavingDiary();            //save edited text
        savingDiary.execute(getEditedText());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WritingActivity:::", "onResume");
        loadEditedText();                                             //load and set text from database to views
        autoSaving();                                           //start autosaving again
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("WritingActivity:::", "onDestroy");

        cancelAutoSaving();                                     //make sure cancel autosaving task
        //closingDatabase();
        boolean flag = dbManager.closeDatabase();
        Log.d("WritingActivity:::", "CLOSE DATABASE? "+flag);
    }

    /**
     * Initialize neccessary views.
     */
    private void initViews(){
        initToolbar();

        weather = (TextView) findViewById(R.id.et_wrt_weather);
        emotion = (TextView) findViewById(R.id.et_wrt_emot);
        diaryText = (TextView) findViewById(R.id.et_wrt_txt);
    }

    /**
     * Initialize toolbar view with corresponding views
     */
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tb_wrt);
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

    /**
     * Get neccessary data from view preparing to be saved
     * @return An array of String type.
     */
    private String[] getEditedText(){
        String strCreateDate = "";
        if(isNotWritten){
            strCreateDate = Tools.getCurrentDateTime();
        }
        String strLastDate = new Date().toString();
        String strWeather = weather.getText().toString();
        String strEmotion = emotion.getText().toString();
        String strContext = diaryText.getText().toString();

        String texts[] = {today, strCreateDate, strLastDate, strWeather, strEmotion, strContext};
        return texts;
    }

    /**
     * Set text for views
     */
    private void loadEditedText(){
        LoadingDiary load = new LoadingDiary();
        load.execute();
    }

    /**
     * Setup timer for autosaving.
     */
    private void autoSaving(){
        autoSave = new TimerTask() {
            @Override
            public void run() {
                String texts[] = getEditedText();
                SavingDiary save = new SavingDiary();
                save.execute(texts);
            }
        };

        timer = new Timer();
        timer.schedule(autoSave, 5000, 15000);
    }

    private void cancelAutoSaving(){
        timer.cancel();
    }

    private void closingDatabase(){
        closeDatabase = new TimerTask() {
            @Override
            public void run() {
                boolean flag = dbManager.closeDatabase();
                Log.d("WritingActivity:::", "CLOSE DATABASE? "+flag);
            }
        };

        timer = new Timer();
        timer.schedule(closeDatabase, 100000);
    }

    private class LoadingDiary extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            dbManager.openWritableDatabase();
            if(dbManager.queryToday(today)){
                isNotWritten = false;
                Diary d = dbManager.query(today);
                editedText[0] = today;
                editedText[1] = d.getCreateDateTime();
                editedText[2] = d.getLastDateTime();
                editedText[3] = d.getWeather();
                editedText[4] = d.getEmotion();
                editedText[5] = d.getDiaryText();
                Log.d("WrittingActivity:::", editedText[5]);
            }else{
                isNotWritten = true;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            weather.setText(editedText[3]);
            emotion.setText(editedText[4]);
            diaryText.setText(editedText[5]);
        }
    }

    private class SavingDiary extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            dbManager.openWritableDatabase();
            if(isNotWritten){
                boolean flagInsert = dbManager.insert(getEditedText());
                isNotWritten = false;
                Log.d("WrittingActivity:::", "INSERT--" + flagInsert);
            }else{
                boolean flagUpdate = dbManager.update(getEditedText());
                Log.d("WrittingActivity:::", "UPDATE--" + flagUpdate);
            }

            return null;
        }
    }
}
