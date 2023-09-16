package com.example.autosmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.buttom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            if(item.getItemId()==R.id.nav_home){
                selectedFragment = new HomeFragment();
            } else if (item.getItemId()==R.id.nav_notification) {
                selectedFragment = new NotificationFragment();
            } else if (item.getItemId()==R.id.nav_request) {
                selectedFragment = new RequestFragment();
            } else if (item.getItemId()==R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,selectedFragment)
                    .commit();
            return true;
        }
    };
}