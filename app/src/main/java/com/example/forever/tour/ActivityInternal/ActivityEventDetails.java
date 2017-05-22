package com.example.forever.tour.ActivityInternal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forever.tour.Activity.MainActivity;
import com.example.forever.tour.CRUDClass.Expense;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;


public class ActivityEventDetails extends AppCompatActivity {
    private String userName;

    //private TextView showValTV;
    private TextView deDestinationTV;
    private TextView deFromDateTV;
    private TextView deToDateTV;
    private TextView deEstBudjetTV;
    private TextView totalCostExpenseTV;
    private TextView overExpenseTitleTV;
    private TextView overExpenseTV;
    private  int rowId;
    private  String recDestination;

    private Expense expense;

    private DatabaseSource databaseSource;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        userName = getIntent().getStringExtra("userName");

        recDestination  = getIntent().getStringExtra("destination");
        rowId           = getIntent().getIntExtra("id",0);
        String id = String.valueOf(rowId);

        //showValTV = (TextView) findViewById(R.id.showVal);
        deDestinationTV = (TextView) findViewById(R.id.deDestination);
        deFromDateTV = (TextView) findViewById(R.id.deFromDate);
        deToDateTV = (TextView) findViewById(R.id.deToDate);
        deEstBudjetTV = (TextView) findViewById(R.id.deEstBudjet);
        totalCostExpenseTV = (TextView) findViewById(R.id.totalCostExpense);
        overExpenseTitleTV = (TextView) findViewById(R.id.overExpenseTitle);
        overExpenseTV = (TextView) findViewById(R.id.overExpense);

        //showValTV.setText(id);
        deDestinationTV.setText(getIntent().getStringExtra("destination"));
        deFromDateTV.setText(getIntent().getStringExtra("fdate"));
        deToDateTV.setText(getIntent().getStringExtra("tdate"));
        String eBgt = getIntent().getStringExtra("bgt");
        deEstBudjetTV.setText("Tk. "+eBgt+".00");


        String toExp = getIntent().getStringExtra("totalExpense");
        if(toExp!=null){
            totalCostExpenseTV.setText("Tk. "+toExp+".00");

            if(Integer.parseInt(toExp) > Integer.parseInt(eBgt)){
                overExpenseTitleTV.setText("Over Expense: ");
                int overExp = Integer.parseInt(toExp)- Integer.parseInt(eBgt);
                overExpenseTV.setText("Tk. "+String.valueOf(overExp)+".00");
            }
        }else{
            totalCostExpenseTV.setText("Tk. 0.00");
        }

        //databaseSource.getAllExpense(id,userName);

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
                        .putExtra("userName",userName)
                        .putExtra("evId",rowId));
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
                        .putExtra("userName",userName)
                        .putExtra("evId",rowId));
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

    public void addExpense(View view) {
        // String evId = showValTV.getText().toString();
        startActivity(new Intent(ActivityEventDetails.this,ActivityAddExpense.class)
                .putExtra("userName",userName)
                .putExtra("evId",rowId));

    }

    public void addMoment(View view) {
        startActivity(new Intent(ActivityEventDetails.this,ActivityAddMoment.class)
                .putExtra("userName",userName)
                .putExtra("evId",rowId));
    }

    public void seeExpense(View view) {
        startActivity(new Intent(ActivityEventDetails.this,ActivityViewExpense.class)
                .putExtra("userName",userName)
                .putExtra("evId",rowId));
    }

    public void seeMoment(View view) {

        startActivity(new Intent(ActivityEventDetails.this,ActivityViewMoment.class)
                .putExtra("userName",userName)
                .putExtra("evId",rowId));
    }
}
