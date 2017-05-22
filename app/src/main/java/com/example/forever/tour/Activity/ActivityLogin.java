package com.example.forever.tour.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forever.tour.ActivityInternal.ActivityInternal;
import com.example.forever.tour.CRUDClass.User;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

public class ActivityLogin extends AppCompatActivity {

    private TextView userNameTV;
    private TextView userPasswordTV;

    private User user;
    private DatabaseSource databaseSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameTV      = (TextView) findViewById(R.id.username_input);
        userPasswordTV  = (TextView) findViewById(R.id.password);

        databaseSource = new DatabaseSource(this);
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
        //startActivity(new Intent(this,ActivityInternal.class));
        String userName    =   userNameTV.getText().toString();
        String userPassword=   userPasswordTV.getText().toString();
        if(userName.isEmpty()){
            userNameTV.setError("Username must not be Empty !");
        }else if(userPassword.isEmpty()){
            userPasswordTV.setError("Password must not be Empty !");
        }else{
            user      =   new User(userName,userPassword);
            boolean status  =   databaseSource.loginUser(user);
            //boolean status  =   true;
            if(status){
                Toast.makeText(this, "Sucessfully login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,ActivityInternal.class)
                        .putExtra("userName",userName));
            }else{
                Toast.makeText(this, "Username or password is not correct", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnSignUpLink(View view) {
        startActivity(new Intent(this,ActivitySignUP.class));
    }
}
