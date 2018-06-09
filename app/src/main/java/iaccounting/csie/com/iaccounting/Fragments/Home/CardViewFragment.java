package iaccounting.csie.com.iaccounting.Fragments.Home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import iaccounting.csie.com.iaccounting.Adapters.CardRecyViewAdapter;
import iaccounting.csie.com.iaccounting.Home.Diary;
import iaccounting.csie.com.iaccounting.R;
import iaccounting.csie.com.iaccounting.Utils.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zixuan Zhao on 3/7/17.
 */

public class CardViewFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardRecyViewAdapter cardRecyViewAdapter;

    private List<Diary> diaries;
    private DataBaseManager dbManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager = new DataBaseManager(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_cardview, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CardViewFragment:::", "onPause");
        dbManager.closeDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CardViewFragment:::", "onResume");

        AcquireDiary acquireDiary = new AcquireDiary();
        acquireDiary.execute();
        Log.d("CardViewFragment:::", "diaries--"+diaries.size());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("CardViewFragment:::", "onDestroy");

    }

    public void initViews(View view){
        diaries = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_home_card);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        cardRecyViewAdapter = new CardRecyViewAdapter(diaries, view.getContext());
        cardRecyViewAdapter.setClickListener(new CardRecyViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                TextView date = (TextView) view.findViewById(R.id.tv_recy_hm_date);
                Log.d("CardViewFragment:::", "" + date.getText().toString());
            }
        });
        recyclerView.setAdapter(cardRecyViewAdapter);
    }

    private void setDataDiary() {
        dbManager.queryAll(diaries);
    }


    private class CheckToday extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            dbManager.openReadableDatabase();
            return null;
        }
    }


    private class AcquireDiary extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            dbManager.openReadableDatabase();
            setDataDiary();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //homeFragPager.updateView(currPos);
            cardRecyViewAdapter.notifyDataSetChanged();
        }
    }

}
