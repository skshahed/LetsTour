package com.example.forever.tour.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forever.tour.ActivityInternal.ActivityAddEvent;
import com.example.forever.tour.ActivityInternal.ActivityEventDetails;
import com.example.forever.tour.ActivityInternal.EventListActivity;
import com.example.forever.tour.CRUDClass.Event;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.util.ArrayList;

/**
 * Created by Ashif Rahman on 5/6/2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    private Context context;
    private DatabaseSource databaseSource;
    private ArrayList<Event> events;
    public EventAdapter(@NonNull Context context, ArrayList<Event> events) {
        super(context, R.layout.row_layout_event, events);
        this.context    =   context;
        this.events = events;
    }

    class ViewHolder{
        TextView destinationTV;
        TextView fromDateTV;
        TextView toDateTV;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseSource = new DatabaseSource(getContext());


        final EventAdapter.ViewHolder holder;
        if(convertView==null) {
            holder          =   new EventAdapter.ViewHolder();

            convertView     = inflater.inflate(R.layout.row_layout_event, parent, false);
            holder.destinationTV= (TextView) convertView.findViewById(R.id.destination);
            holder.fromDateTV   = (TextView) convertView.findViewById(R.id.fromDate);
            holder.toDateTV     = (TextView) convertView.findViewById(R.id.toDate);

            convertView.setTag(holder);
        }else{
            holder = (EventAdapter.ViewHolder) convertView.getTag();
        }

        final int eventID = events.get(position).getEvid();

        final String userName = events.get(position).getUserName();


            holder.destinationTV.setText(events.get(position).getTraDestination());
            holder.fromDateTV.setText(events.get(position).getTraFromDate());
            holder.toDateTV.setText(events.get(position).getTraToDate());


        //final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                dialog.setContentView(R.layout.details_update_delete_popup);

                Button detailsBtn= (Button) dialog.findViewById(R.id.details);
                Button deleteBtn = (Button) dialog.findViewById(R.id.delete);

                Button updateBtn = (Button) dialog.findViewById(R.id.update);
                Button cancelBtn = (Button) dialog.findViewById(R.id.back);

                detailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getContext().startActivity(new Intent(getContext(),ActivityEventDetails.class)
                                .putExtra("id",eventID)
                                .putExtra("userName",userName)

                                .putExtra("destination",events.get(position).getTraDestination())
                                .putExtra("fdate",events.get(position).getTraFromDate())
                                .putExtra("tdate",events.get(position).getTraToDate())
                                .putExtra("bgt",events.get(position).getTraBudget())
                                .putExtra("totalExpense",events.get(position).getTotalExpAmt()));

                        notifyDataSetChanged();
                        dialog.dismiss();


                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Delete Event");
                        alert.setMessage("Are you sure to delete this Event ?");
                        alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean status = databaseSource.deleteEvent(eventID);
                                if(status){
                                    Toast.makeText(context, "Event Deleted", Toast.LENGTH_SHORT).show();
                                    getContext().startActivity(new Intent(getContext(),EventListActivity.class)
                                            .putExtra("evId",eventID)
                                            .putExtra("userName",userName));

                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(context, "Couldn't Delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        alert.setNegativeButton("Cancel",null);
                        alert.show();

                    }
                });

                  updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            String toDate = events.get(position).getTraToDate();

                            java.util.Calendar c = java.util.Calendar.getInstance();
                            System.out.println("Current time => "+c.getTime());
                            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(c.getTime());
                            String currrentDate = (formattedDate);
                            int dateCom = currrentDate.compareTo(toDate);

                            if (dateCom > 0) {
                                alert.setTitle("Time Out");
                                alert.setMessage("Sorry! You can't update at this moment .");
                                alert.setPositiveButton("Calcel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getContext().startActivity(new Intent(getContext(),EventListActivity.class)
                                                    .putExtra("userName",userName));

                                            notifyDataSetChanged();
                                            dialog.dismiss();
                                    }
                                });

                                alert.show();
                            } else {
                                getContext().startActivity(new Intent(getContext(), ActivityAddEvent.class)
                                        .putExtra("evId", events.get(position).getEvid())
                                        .putExtra("userName", userName)

                                        .putExtra("destination", events.get(position).getTraDestination())
                                        .putExtra("budget", events.get(position).getTraBudget())
                                        .putExtra("fromDate", events.get(position).getTraFromDate())
                                        .putExtra("toDate", events.get(position).getTraToDate()));

                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        }
                    });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                dialog.show();

            }
        });

        return convertView;
    }

}
