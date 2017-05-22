package com.example.forever.tour.Activity;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.forever.tour.ActivityInternal.ActivityAddEvent;
import com.example.forever.tour.ActivityInternal.ActivityInternal;
import com.example.forever.tour.ActivityInternal.ApiServiceActivity;
import com.example.forever.tour.ActivityInternal.EventListActivity;
import com.example.forever.tour.CRUDClass.User;
import com.example.forever.tour.Camera.Utility;
import com.example.forever.tour.DatabaseSource.DatabaseSource;
import com.example.forever.tour.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivitySignUP extends AppCompatActivity {
    // start for data update
    private int userId;
    private String userName;
    private String password;
    private String fullName;
    private String contactNumber;
    private String userPhotoPath;
    private Button btnSignUp;
    private TextView showImagepathTV;
    // end for data update

    private EditText imagePathET;
    private String imagePath;
    private EditText personFullNameET;
    private EditText prsonUsernameET;
    private EditText passwordET;
    private EditText personPhoneNumberET;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;

    private User user;
    private DatabaseSource databaseSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        showImagepathTV = (TextView) findViewById(R.id.showUserImagepath);
        //imagePathET = (EditText) findViewById(R.id.showImagepathUser);
        personFullNameET    = (EditText) findViewById(R.id.personFullName);
        prsonUsernameET     = (EditText) findViewById(R.id.personUsername);
        passwordET          = (EditText) findViewById(R.id.personPassword);
        personPhoneNumberET = (EditText) findViewById(R.id.personPhoneNumber);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);


        // start for data update
        userId        = getIntent().getIntExtra("userId",0);
        userName      = getIntent().getStringExtra("userName");
        password      = getIntent().getStringExtra("password");
        fullName      = getIntent().getStringExtra("fullName");
        contactNumber = getIntent().getStringExtra("contactNumber");
        userPhotoPath = getIntent().getStringExtra("userPhotoPath");



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


        //set for data update
        if(userId > 0){
            prsonUsernameET.setText(userName);
            passwordET.setText(password);
            personFullNameET.setText(fullName);
            personPhoneNumberET.setText(contactNumber);

            File imgFile = new  File(userPhotoPath);

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivImage.setImageBitmap(myBitmap);
                showImagepathTV.setText(userPhotoPath);
            }
           // prsonUsernameET.setEnabled(false);

            //File file = new File(userPhotoPath);
            //if(file.exists()){
               // Bitmap myBitmap = BitmapFactory.decodeFile(userPhotoPath);
                //ivImage.setImageBitmap(myBitmap);
            //}
            btnSignUp.setText("Update");
        }
        // end for data update

        databaseSource = new DatabaseSource(this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySignUP.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(ActivitySignUP.this);

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
        //ivImage.setImageBitmap(thumbnail);
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
    }


    public void btnSignUpProcess(View view) {
        //String imagePath    =   imagePathET.getText().toString();
        String name         =   personFullNameET.getText().toString();
        String user         =   prsonUsernameET.getText().toString();
        String pass         =   passwordET.getText().toString();
        String phone        =   personPhoneNumberET.getText().toString();
        userPhotoPath    =   showImagepathTV.getText().toString();

        if(name.isEmpty()){
            personFullNameET.setError("This field must not be Empty !");
        }
        if(user.isEmpty()){
            prsonUsernameET.setError("This field must not be Empty !");
        }

        if(pass.isEmpty()){
            passwordET.setError("This field must not be Empty !");
        }

        if(phone.isEmpty()){
            personPhoneNumberET.setError("This field must not be Empty !");
        }else{
            // it condition for update
            if(userId > 0){
                this.user =   new User(userPhotoPath,name,user,pass,phone);
                Integer status  = databaseSource.updateUserInfo(this.user,userId);

                if(status==00) {
                    Toast.makeText(this, "This user  name already exits", Toast.LENGTH_SHORT).show();
                }else if(status==1){
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this,ActivityInternal.class)
                    .putExtra("userName",user));
                }else{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
                // end update
            }
            else{
            //Log.d("TEST", "btnSignUpProcess: "+imagePath);
            this.user =   new User(userPhotoPath,name,user,pass,phone);
            Integer status  =   databaseSource.addSignUp(this.user);
                if(status==00) {
                    Toast.makeText(this, "This user  name already exits", Toast.LENGTH_SHORT).show();
                }else if(status==1){
                Toast.makeText(this, "User added sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,ActivityInternal.class)
                        .putExtra("userName",user));
            }else{
                Toast.makeText(this, "Could not save", Toast.LENGTH_SHORT).show();
            }
            }
        }
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(userId > 0) {

            inflater.inflate(R.menu.internal_menu, menu);

        }else{
            inflater.inflate(R.menu.external_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

                    switch (item.getItemId()) {
                    case R.id.ExMenuHome:
                        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        break;

                    case R.id.ExMenuLogin:
                        startActivity(new Intent(this, ActivityLogin.class));

                        break;

                    case R.id.ExMenuSignUp:
                        startActivity(new Intent(this, ActivitySignUP.class));
                        break;

                    ///// Start internal Menu

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
