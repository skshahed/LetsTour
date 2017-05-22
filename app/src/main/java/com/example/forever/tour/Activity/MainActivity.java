package com.example.forever.tour.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.forever.tour.ActivityInternal.ActivityInternal;
import com.example.forever.tour.R;
import com.example.forever.tour.ViewPagerAdapter.ViewPageAdapter;


public class MainActivity extends AppCompatActivity {


private ViewPageAdapter mPagerAdapter;
    private ViewPager viewPager;
    TabLayout tabLayout;
    //private Geocoder geocoder;
    private String userName;

    //private String currentLat = null, currentLng = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //userName = getIntent().getStringExtra("userName");


        viewPager = (ViewPager) findViewById(R.id.container);
        mPagerAdapter =new ViewPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(0).setIcon(R.drawable.nearby);
            tabLayout.getTabAt(1).setIcon(R.drawable.search_location);
            tabLayout.getTabAt(2).setIcon(R.drawable.forecast);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.external_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ExMenuHome:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                break;

            case R.id.ExMenuLogin:
                startActivity(new Intent(this,ActivityLogin.class));

                break;

            case R.id.ExMenuSignUp:
                startActivity(new Intent(this,ActivitySignUP.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loginControl(View view) {
        startActivity(new Intent(this,ActivityInternal.class));
    }

    public void btnSignUpLink(View view) {

    }
}
