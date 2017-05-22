package com.example.forever.tour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forever.tour.Activity.ActivitySignUP;
import com.example.forever.tour.CRUDClass.User;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.util.ArrayList;


/**
 * Created by Ashif Rahman on 4/5/2017.
 */

public class UserAdapter extends ArrayAdapter<User>{
    private Context context;
    private ArrayList<User> users;
    private DatabaseSource databaseSource;

    public UserAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, R.layout.row_layout, users);
        this.context    =   context;
        this.users = users;
    }

    class ViewHolder{
        ImageView shoowUserImageIV;
        TextView userFullNameTv;
        TextView userNameTV;
        TextView userPhoneTV;
        Button userChangeBtn;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater =   (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseSource = new DatabaseSource(getContext());

        final ViewHolder holder;
        if(convertView==null) {
            holder          =   new ViewHolder();

            convertView     = inflater.inflate(R.layout.row_layout, parent, false);

            holder.shoowUserImageIV = (ImageView) convertView.findViewById(R.id.shoowUserImage);
            holder.userFullNameTv   = (TextView) convertView.findViewById(R.id.fullName);
            holder.userNameTV  = (TextView) convertView.findViewById(R.id.userName);
            holder.userPhoneTV  = (TextView) convertView.findViewById(R.id.contactNumber);
            holder.userChangeBtn  = (Button) convertView.findViewById(R.id.changeUserBtn);


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String imagePathName = users.get(position).getImagePath();
        //Toast.makeText(context, imagePathName, Toast.LENGTH_SHORT).show();
//        File file = new File(imagePathName);
  //      if(file.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imagePathName);
            holder.shoowUserImageIV.setImageBitmap(myBitmap);
    //    }

        holder.userFullNameTv.setText(users.get(position).getFullName());
        holder.userNameTV.setText(users.get(position).getUserName());
        holder.userPhoneTV.setText(users.get(position).getContactNumber());


        holder.userChangeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int userId = users.get(position).getUserId();
                        /*String docSpecialist= doctorAdapters.get(position).getDocDetails();
                        String docApoint    = doctorAdapters.get(position).getDocApnmnt().toString();

                        String docPhone     = holder.phoneTV.getText().toString();
                        String docEmail     = holder.emailTV.getText().toString();
*/
                        parent.getContext().startActivity(new Intent(getContext(),ActivitySignUP.class)
                                .putExtra("userId",userId)
                                .putExtra("userName",users.get(position).getUserName())
                                .putExtra("password",users.get(position).getPassword())
                                .putExtra("fullName",users.get(position).getFullName())
                                .putExtra("contactNumber",users.get(position).getContactNumber())
                                .putExtra("userPhotoPath",users.get(position).getImagePath()));
                    }
                });

        return convertView;
    }
}
