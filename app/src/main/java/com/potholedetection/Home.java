package com.potholedetection;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private ActionBar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment f;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    f = new HomeFragment();
                    loadFragment(f);
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_complain:
                    toolbar.setTitle("Complain");
                    f = new Complain();
                    loadFragment(f);
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notification:
                    toolbar.setTitle("Notifiacation");
                    f = new NotificationFragment();
                    loadFragment(f);
                    return true;
                case R.id.navigation_user:
                    toolbar.setTitle("User Profile");
                    f = new UserFragment();
                    loadFragment(f);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Core.getInstance().init(this);

        toolbar.setTitle("Home");
        Fragment f = new HomeFragment();
        loadFragment(f);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
