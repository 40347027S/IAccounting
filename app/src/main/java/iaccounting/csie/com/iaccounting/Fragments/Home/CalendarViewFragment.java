package iaccounting.csie.com.iaccounting.Fragments.Home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import iaccounting.csie.com.iaccounting.Home.Diary;
import iaccounting.csie.com.iaccounting.R;
import iaccounting.csie.com.iaccounting.Utils.DataBaseManager;
import iaccounting.csie.com.iaccounting.Utils.Tools;

import java.util.Calendar;

/**
 * Created by Zixuan Zhao on 3/7/17.
 */

public class CalendarViewFragment extends Fragment implements CalendarView.OnDateChangeListener{

    private CalendarView calendarView;
    private TextView dateTextView;
    private TextView diaryTextView;

    private Diary diary;
    private DataBaseManager dbManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DataBaseManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_calendar, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month, dayOfMonth);
        Log.d("CalendarViewFragment:::", Tools.setDateFormat(c));

        LoadDiaries loadDiaries = new LoadDiaries();
        loadDiaries.execute(Tools.setDateFormat(c));

    }

    private void initViews(View view){
        calendarView = (CalendarView) view.findViewById(R.id.cv_fg_cal_cal);
        dateTextView = (TextView) view.findViewById(R.id.tv_fg_cal_date);
        diaryTextView = (TextView) view.findViewById(R.id.tv_fg_cal_txt);

        calendarView.setOnDateChangeListener(this);
    }

    private class LoadDiaries extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            dbManager.openReadableDatabase();
            diary = dbManager.query(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(diary != null){
                dateTextView.setText(diary.getCreateDateTime());
                diaryTextView.setText(diary.getDiaryText());
            }
        }
    }
}
