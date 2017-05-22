package com.example.forever.tour.ActivityInternal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forever.tour.Activity.MainActivity;
import com.example.forever.tour.Adapter.UserAdapter;
import com.example.forever.tour.CRUDClass.User;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.util.ArrayList;

public class ActivityInternal extends AppCompatActivity {
    private String userName;
    private ListView mListView;
    private UserAdapter userAdapter;
    private ArrayList<User> users;
    private DatabaseSource databaseSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal);

        userName = getIntent().getStringExtra("userName");

        TextView emptyText = (TextView) findViewById(R.id.empty);
        mListView = (ListView) findViewById(R.id.userList);
        mListView.setEmptyView(emptyText);
        databaseSource = new DatabaseSource(this);
        users = databaseSource.getSingleUsers(userName);

        userAdapter = new UserAdapter(this, users);
        mListView.setAdapter(userAdapter);

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
