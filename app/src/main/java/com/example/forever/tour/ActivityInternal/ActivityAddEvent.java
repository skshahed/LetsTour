package com.example.forever.tour.ActivityInternal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forever.tour.Activity.MainActivity;
import com.example.forever.tour.CRUDClass.Event;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityAddEvent extends AppCompatActivity {
    private String userName;

    // start for data update
    private int evId;
    private String budget;
    private String destination;
    private String fromDate;
    private String toDate;
    // end for data update

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    private TextView traDestinationET;
    private TextView estBudjetET;
    private Button fromDateBT;
    private Button toDateBT;
    private Button btnAddEventBT;
    private int year,month,day;
    private Calendar calendar;

    private Event event;
    private DatabaseSource databaseSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        userName = getIntent().getStringExtra("userName");



        calendar      = Calendar.getInstance(Locale.getDefault());
        year          = calendar.get(Calendar.YEAR);
        month         = calendar.get(Calendar.MONTH);
        day           = calendar.get(Calendar.DAY_OF_MONTH);

        traDestinationET= (TextView) findViewById(R.id.travelDestination);
        estBudjetET     = (TextView) findViewById(R.id.estimateBudjet);
        fromDateBT      = (Button) findViewById(R.id.fromDate);
        toDateBT        = (Button) findViewById(R.id.toDate);
        btnAddEventBT   = (Button) findViewById(R.id.btnAddEvent);

        // start for data update
        evId        = getIntent().getIntExtra("evId",0);
        userName    = getIntent().getStringExtra("userName");
        destination = getIntent().getStringExtra("destination");
        budget      = getIntent().getStringExtra("budget");
        fromDate    = getIntent().getStringExtra("fromDate");
        toDate      = getIntent().getStringExtra("toDate");

        //set for data update
        if(evId > 0){
            traDestinationET.setText(destination);
            estBudjetET.setText(budget);
            fromDateBT.setText(fromDate);
            toDateBT.setText(toDate);
            btnAddEventBT.setText("Update");
        }
        // end for data update



        databaseSource = new DatabaseSource(this);

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



    public void showFromDate(View view) {
       /* DatePickerDialog dpd    =   new DatePickerDialog(this,dateListner,year,month,day);
        dpd.show();*/
        DatePickerDialog dpd    =   new DatePickerDialog(this,dateListner,year,month,day);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();
    }
    private DatePickerDialog.OnDateSetListener dateListner  =   new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Toast.makeText(MainActivity.this, "year:"+year, Toast.LENGTH_SHORT).show();

            //start for date compare
                String fromDateEdit = fromDateBT.getText().toString();

                Calendar c = Calendar.getInstance();
                System.out.println("Current time => "+c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c.getTime());
                String currrentDate = (formattedDate);
                int dateCom = currrentDate.compareTo(fromDateEdit);

            if(evId > 0){
               if(dateCom > 0){
                    fromDateBT.requestFocus();
                    fromDateBT.setError("Sorry! This field is not writable ");
               }
           }else{
               fromDateBT.setText(dayOfMonth+"-"+(month+1)+"-"+year);
           }
            //end for date compare
        }
    };

    public void showToDate(View view) {
        /*DatePickerDialog dpd2    =   new DatePickerDialog(this,dateListner2,year,month,day);
        dpd2.show();*/
        DatePickerDialog dpd2    =   new DatePickerDialog(this,dateListner2,year,month,day);
        dpd2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd2.show();
    }
    private DatePickerDialog.OnDateSetListener dateListner2  =   new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Toast.makeText(MainActivity.this, "year:"+year, Toast.LENGTH_SHORT).show();
            toDateBT.setText(dayOfMonth+"-"+(month+1)+"-"+year);
        }
    };

    public void addEvent(View view) {
        String usernameId = String.valueOf(userName);
        String destination =   traDestinationET.getText().toString();
        String budjet      =   estBudjetET.getText().toString();
        String fdate       =   fromDateBT.getText().toString();
        String tdate       =   toDateBT.getText().toString();

        int result = fdate.compareTo(tdate);


        if(destination.isEmpty()){
            traDestinationET.requestFocus();
            traDestinationET.setError("This field must not be Empty !");
        }
        else if(budjet.isEmpty()){
            estBudjetET.requestFocus();
            estBudjetET.setError("This field must not be Empty !");
        }

        else if(fdate.isEmpty()){
            fromDateBT.requestFocus();
            fromDateBT.setError("This field must not be Empty !");
        }

        else if(tdate.isEmpty()){

            toDateBT.requestFocus();
            toDateBT.setError("This field must not be Empty !");
            //int result = fdate.compareTo(tdate);

            // SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
        }
        else if(result>0){
            toDateBT.requestFocus();
            toDateBT.setError("Ending date must not be backdated  than start Date");
        }else{
            // it condition for update
            if(evId > 0){
                this.event =   new Event(destination,budjet,fdate,tdate);
                boolean status  = databaseSource.updateEvent(this.event,evId);
                if(status){
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,EventListActivity.class)
                            .putExtra("userName",userName));
                }else{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
                // end update
            } else{
                this.event =   new Event(usernameId,destination,budjet,fdate,tdate);
                boolean status  =   databaseSource.addEventInfo(this.event,usernameId);
                if(status){
                    Toast.makeText(this, "Event added sucessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,EventListActivity.class)
                            .putExtra("userName",usernameId));
                }else{
                    Toast.makeText(this, "Could not save", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
