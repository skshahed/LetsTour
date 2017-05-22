package com.example.forever.tour.ActivityInternal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forever.tour.CRUDClass.Moment;
import com.example.forever.tour.Camera.Utility;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityAddMoment extends AppCompatActivity {
    private String userName;
    private TextView showdTimeTV, showImagepathTV;

    private String imagePath;
    private EditText momentDetailsET;
    private int eventId,momentId;

    // start camera related
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect,momentBtn;
    private ImageView ivImage;
    private String userChoosenTask,momentDetails;
    private EditText imagePathET;
    // end camera related

    private DatabaseSource databaseSource;
    private Moment moment;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moment);
        userName = getIntent().getStringExtra("userName");


        eventId           = getIntent().getIntExtra("evId",0);
        momentId          = getIntent().getIntExtra("momentId",0);
        momentDetails     = getIntent().getStringExtra("momentDetails");
        imagePath         = getIntent().getStringExtra("momentImagePath");

        momentBtn   = (Button) findViewById(R.id.addMomentBtn);
        //start related camera
        btnSelect 	= (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage 	= (ImageView) findViewById(R.id.ivImage);
        //end related cmera

        momentDetailsET = (EditText) findViewById(R.id.momentDetails);
        showdTimeTV = (TextView) findViewById(R.id.momentShowdTime);
        showImagepathTV = (TextView) findViewById(R.id.showImagepath);
        //momentDetailsET.setText(userName);
        if (momentId > 0){
            File imgFile = new  File(imagePath);

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivImage.setImageBitmap(myBitmap);
                showImagepathTV.setText(imagePath);
            }
            //showImagepathET.setText(imagePath);
            momentDetailsET.setText(momentDetails);
            momentBtn.setText("Update");
        }


        databaseSource = new DatabaseSource(this);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddMoment.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(ActivityAddMoment.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*ivImage.setImageBitmap(thumbnail);
        imagePathET.setText(destination.toString());
*/

        Bitmap bm = BitmapFactory.decodeFile(destination.getAbsolutePath());
        ivImage.setImageBitmap(bm);
        showImagepathTV.setText(destination.getAbsolutePath());
        //imagePath = destination.getAbsolutePath();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bm1 = BitmapFactory.decodeFile(destination.getAbsolutePath());
        ivImage.setImageBitmap(bm1);
        // String selectedImageUri = data.getData().getPath();
        showImagepathTV.setText(destination.getAbsolutePath());
        /*File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        imagePath = destination.getAbsolutePath();
        ivImage.setImageBitmap(bm);*/
/*
        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        String fpath = String.valueOf(destination.getAbsolutePath());
        imagePathET.setText(fpath);*/
    }
/*

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
                startActivity(new Intent(this,ActivityInternal.class));
                break;

            case R.id.inAddEvent:
                startActivity(new Intent(this,ActivityAddEvent.class));
                break;



            case R.id.inViewTravelEvent:
                Toast.makeText(this, "View Event", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,EventListActivity.class));
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
*/

    public void saveMoment(View view) {
        String evid = String.valueOf(eventId);
        String dTime     =   showdTimeTV.getText().toString();
        // String dTime     =   "12-12-12";
        imagePath    =   showImagepathTV.getText().toString();
        String momentDetail =   momentDetailsET.getText().toString();

       /* if(imagePath.isEmpty()){
            showImagepathET.setError("This field must not be Empty !");
        }*/
        if(momentDetail.isEmpty()){
            momentDetailsET.setError("This field must not be Empty !");
        }else{
            if(momentId > 0){
                moment =   new Moment(imagePath,momentDetail);
                boolean status  =   databaseSource.editMoment(moment,momentId);
                if(status){
                    Toast.makeText(this, "Moment updated sucessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,ActivityViewMoment.class)
                            .putExtra("userName", userName)
                            .putExtra("evId",eventId));
                }else{
                    Toast.makeText(this, "Could not save", Toast.LENGTH_SHORT).show();
                }

            } else {
                moment = new Moment(evid, dTime, imagePath, momentDetail);
                boolean status = databaseSource.addMomentInfo(moment);
                if (status) {
                    Toast.makeText(this, "Moment added sucessfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, ActivityViewMoment.class)
                            .putExtra("userName", userName)
                            .putExtra("evId",eventId));
                } else {
                    Toast.makeText(this, "Could not save", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
