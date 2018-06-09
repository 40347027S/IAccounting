package iaccounting.csie.com.iaccounting;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//import iaccounting.csie.com.iaccounting.Adapters.CardRecyViewAdapter;
import iaccounting.csie.com.iaccounting.Adapters.HomeFragPagerAdapter;
import iaccounting.csie.com.iaccounting.Home.Diary;
import iaccounting.csie.com.iaccounting.Utils.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    public static final int PAGE_CARD = 0;
    public static final int PAGE_CALENDAR = 1;
    public static final int PAGE_COUNT = 2;

    private Toolbar toolbar;

    private ViewPager viewPager;
    private HomeFragPagerAdapter homeFragPager;

    private FloatingActionButton newDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d("HomeActivity:::", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("HomeActivity:::", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d("HomeActivity:::", "onDestroy");
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private void initViews() {

        //initToolbar();
        initViewpager();

        newDiary = (FloatingActionButton) findViewById(R.id.fab_home_new);
        newDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WritingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tb_home);
        toolbar.inflateMenu(R.menu.menu_home_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_settings:
                        Log.d("HomeActivity:::", "home_Setting");
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        //setSupportActionBar(toolbar);
    }

    private void initViewpager(){
        homeFragPager = new HomeFragPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.vp_home);
        viewPager.setAdapter(homeFragPager);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }
}
