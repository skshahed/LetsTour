package com.example.forever.tour.ActivityInternal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.forever.tour.Activity.MainActivity;
import com.example.forever.tour.R;
import com.example.forever.tour.ViewPagerAdapter.ViewPageAdapter;

public class ApiServiceActivity extends AppCompatActivity {

    private ViewPageAdapter mPagerAdapter;
    private ViewPager viewPager;
    TabLayout tabLayout;
    //private Geocoder geocoder;
    private String userName;

    //private String currentLat = null, currentLng = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_service);

        userName = getIntent().getStringExtra("userName");

/*
        personFullNameET    = (EditText) findViewById(R.id.personFullName);
        prsonUsernameET     = (EditText) findViewById(R.id.personUsername);
        passwordET          = (EditText) findViewById(R.id.password);
        personPhoneNumberET = (EditText) findViewById(R.id.personPhoneNumber);*/

        /*FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft  =   fm.beginTransaction();
        FragmentNearby fragmentNearbyn  = new FragmentNearby();
        ft.add(R.id.fragmentContainerExternal,fragmentNearbyn);
        ft.addToBackStack(null);
        ft.commit();
*/

        viewPager = (ViewPager) findViewById(R.id.inContainer);
        mPagerAdapter =new ViewPageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.inTabs);
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
        inflater.inflate(R.menu.internal_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.inMenuHome:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,ActivityInternal.class)
                        .putExtra("userName",userName));
                break;

            case R.id.apiServices:
                startActivity(new Intent(this,ApiServiceActivity.class)
                        .putExtra("userName",userName));
                break;

            case R.id.inAddEvent:
                startActivity(new Intent(this,ActivityAddEvent.class)
                        .putExtra("userName",userName));
                break;



            case R.id.inViewTravelEvent:
                Toast.makeText(this, "View Event", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,EventListActivity.class)
                        .putExtra("userName",userName));
                break;


            case R.id.inLogOut:
                Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
                Intent mainActivity=new Intent(this,MainActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainActivity);
                this.finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }
}
