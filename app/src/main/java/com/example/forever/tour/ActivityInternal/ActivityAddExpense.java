package com.example.forever.tour.ActivityInternal;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forever.tour.CRUDClass.Expense;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;


public class ActivityAddExpense extends AppCompatActivity {
    private String userName;

    // start for data update
    private int expId;
    private String expDetails;
    private String expAmt;
    private Button btnAddExpenseBT;
    // end for data update

    private TextView showdTimeTV;
    private EditText expDetailsTV;
    private  EditText expAmtTV;
    private int eventId;
    private String shoDetails;

    private DatabaseSource databaseSource;
    private Expense expense;
    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        userName = getIntent().getStringExtra("userName");
        eventId           = getIntent().getIntExtra("evId",0); // it can be use for update


        showdTimeTV = (TextView) findViewById(R.id.showExpCudTime);
        expDetailsTV = (EditText) findViewById(R.id.expDetails);
        expAmtTV     = (EditText) findViewById(R.id.expAmt);
        btnAddExpenseBT   = (Button) findViewById(R.id.btnAddExpense);

        // start for data update
        expId       = getIntent().getIntExtra("exId",0);
        expDetails  = getIntent().getStringExtra("expDetails");
        expAmt      = getIntent().getStringExtra("expAmt");

        //set for data update
        if(expId > 0){
            expDetailsTV.setText(expDetails);
            expAmtTV.setText(expAmt);
            btnAddExpenseBT.setText("Update");
        }
        // end for data update


        //String evid = String.valueOf(eventId);
        //expDetailsTV.setText(evid);

        databaseSource = new DatabaseSource(this);
        java.util.Calendar c = java.util.Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

       // java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time
        // Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();


        // Now we display formattedDate value in TextView
        //TextView txtView = new TextView(this);
        showdTimeTV.setText(formattedDate);
        /*txtView.setGravity(Gravity.CENTER);
        txtView.setTextSize(20);
        setContentView(txtView);*/



    }

    public void addExpense(View view) {
        String evid = String.valueOf(eventId);
        String dTime     =   showdTimeTV.getText().toString();
        String details  =   expDetailsTV.getText().toString();
        String amt      =   expAmtTV.getText().toString();

        if(details.isEmpty()){
            expDetailsTV.setError("This field must not be Empty !");
        }
        if(amt.isEmpty()){
            expAmtTV.setError("This field must not be Empty !");
        }else{
            // it condition for update
            if(expId > 0){
                this.expense =   new Expense(evid,details,amt);
                boolean status  = databaseSource.editExpense(this.expense,expId);
                if(status){
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,ActivityViewExpense.class)
                    .putExtra("evId",eventId)
                    .putExtra("userName",userName));
                }else{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            // end update
            }else{

                this.expense =   new Expense(evid,dTime,details,amt);
                boolean status  =   databaseSource.addExpenseInfo(this.expense);
                if(status){
                    Toast.makeText(this, "Expense added sucessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,ActivityViewExpense.class)
                            .putExtra("evId",eventId)
                            .putExtra("userName",userName));
                }else{
                    Toast.makeText(this, "Could not save", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
