package iaccounting.csie.com.iaccounting;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import iaccounting.csie.com.iaccounting.Fragments.Settings.MainSettingFragment;

public class SettingActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private MainSettingFragment mainSettingFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mainSettingFragment = new MainSettingFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.set_content, mainSettingFragment).commit();
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.tb_home);

        //setSupportActionBar(toolbar);
    }

}
