package com.example.forever.tour.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forever.tour.ActivityInternal.ActivityAddMoment;
import com.example.forever.tour.ActivityInternal.ActivityViewMoment;
import com.example.forever.tour.CRUDClass.Moment;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Forever on 5/8/2017.
 */

public class MomentAdapter extends ArrayAdapter<Moment> {
    private Context context;
    private ArrayList<Moment> moment;
    private DatabaseSource databaseSource;
    public MomentAdapter(@NonNull Context context, ArrayList<Moment> moment) {
        super(context, R.layout.row_layout_moment, moment);
        this.context    =   context;
        this.moment = moment;
    }

    class ViewHolder{
        TextView momentDateTimeTV;
        TextView momentDetalisTV;
        ImageView momentPhotoIV;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        databaseSource = new DatabaseSource(getContext());
        final MomentAdapter.ViewHolder holder;
        if(convertView==null) {
            holder          =   new MomentAdapter.ViewHolder();

            convertView     = inflater.inflate(R.layout.row_layout_moment, parent, false);
            holder.momentDateTimeTV    = (TextView) convertView.findViewById(R.id.momentDateTimeTV);
            holder.momentDetalisTV     = (TextView) convertView.findViewById(R.id.momentDetalisTV);
            holder.momentPhotoIV      = (ImageView) convertView.findViewById(R.id.momentPhoto);

            convertView.setTag(holder);
        }else{
            holder = (MomentAdapter.ViewHolder) convertView.getTag();
        }

        final String userName = (moment.get(position).getUsername());
        holder.momentDateTimeTV.setText(moment.get(position).getmDateTime());
       // holder.momentDateTimeTV.setText(userName);
        holder.momentDetalisTV.setText(moment.get(position).getMomentDetails());

        final int momentId= moment.get(position).getMomentId();
        final int eventID = Integer.parseInt(moment.get(position).getEventId());
        //final String userName = (moment.get(position).getUsername());
        final String imagePathName = moment.get(position).getMomentPhotopath();
        //Toast.makeText(context, imagePathName, Toast.LENGTH_SHORT).show();
        File file = new File(imagePathName);
        if(file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imagePathName);
            holder.momentPhotoIV.setImageBitmap(myBitmap);
        }

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final Dialog dialog = new Dialog(getContext(),R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                dialog.setContentView(R.layout.update_delete_popup);

                Button deleteBtn = (Button) dialog.findViewById(R.id.delete);
                Button updateBtn = (Button) dialog.findViewById(R.id.update);
                Button cancelBtn = (Button) dialog.findViewById(R.id.back);

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle("Delete Moment");
                        alert.setMessage("Are you sure to delete this moment ?");
                        alert.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean status = databaseSource.deleteMoment(momentId);
                                if(status){
                                    Toast.makeText(context, "Moment Deleted", Toast.LENGTH_SHORT).show();
                                    getContext().startActivity(new Intent(getContext(),ActivityViewMoment.class)
                                            .putExtra("evId",eventID)
                                            .putExtra("userName",moment.get(position).getUsername()));
                                    //getContext().startActivity(new Intent(this,ActivityEventDetails.class));
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
                        dialog.dismiss();
                        String momentDetails = moment.get(position).getMomentDetails();
                        getContext().startActivity(new Intent(getContext(),ActivityAddMoment.class)
                                .putExtra("evId",eventID)
                                .putExtra("momentId",momentId)
                                .putExtra("userName",moment.get(position).getUsername())
                                .putExtra("momentDetails",momentDetails)
                                .putExtra("momentImagePath",imagePathName));

                    }
                });



                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });


                dialog.show();


                return false;
            }
        });


        //final View finalConvertView = convertView;

        return convertView;
    }

}

