package com.webapps.viral.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.model.AddPost;
import com.webapps.viral.model.LoginModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EditProfileActivity extends AppCompatActivity {
    RequestBody requestBody;
    String message,mCurrentPhotoPath,date1;
    Integer success;
    Button update;

    EditText bio,city,fname,lname,pincode;
    TextView dob,back;
    ImageView userimage,pickimage;
   SharedPref sharedPref;
   String  uid;
    MultipartBody.Part body;
    APIService service;
    Retrofit retrofit;
    private final int REQUEST_CODE_PERMISSION = 55;
    File mediaFile;
    private static final int CAM_REQUEST = 1313;
    private String pickerPath,states,gender="male";
    private int GALLERY = 1, CAMERA = 2,GALLERYImage = 3;
    RadioButton male,female,other;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    ProgressDialog progressDialog;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fname = findViewById(R.id.edit_fname);
        lname=findViewById(R.id.edit_lname);
        bio = findViewById(R.id.edit_bio);
        dob = findViewById(R.id.edit_dob);
        back=findViewById(R.id.back);
        sharedPref= new SharedPref(this);
        uid=sharedPref.getID();
        city = findViewById(R.id.edit_city);
          male = findViewById(R.id.male);


        female = findViewById(R.id.female);
        other = findViewById(R.id.other);
        userimage=findViewById(R.id.userImage);
        pickimage=findViewById(R.id.pickImage);
        pincode=findViewById(R.id.edit_pincode);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please Wait....");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        date1=dob.getText().toString();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(male.isChecked())
                {

                    gender="male";
                    male.setChecked(true);
                    female.setChecked(false);
                    other.setChecked(false);
                }
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(female.isChecked())
                {
                    gender="female";
                    male.setChecked(false);
                    female.setChecked(true);
                    other.setChecked(false);
                }
            }
        });
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(other.isChecked())
                {
                    gender="Other";
                    male.setChecked(false);
                    female.setChecked(false);
                    other.setChecked(true);
                }
            }
        });
        Login();
        update=findViewById(R.id.btnupdate);
         update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            UpdateUser();
            }
        });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // updateLabel();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dob.setText(sdf.format(myCalendar.getTime()));
                date1=sdf.format(myCalendar.getTime());
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar.add(Calendar.YEAR, -18);
               // DatePickerDialog datePicker = new DatePickerDialog(this, datePickerListener, y, m, d);
               // datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                DatePickerDialog dialog =  new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                dialog.show();
                //setDateTimeField();
            }
        });
pickimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        pickMedia();
    }
});

    }
    public void pickMedia() {
        if (checkStoragePermissions()) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfileActivity.this);
            alertDialog.setMessage("Get picture from");
            alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAM_REQUEST);
                }
            });
            alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();


                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, GALLERYImage);
                }
            });
            alertDialog.create().show();

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION && checkStoragePermissions()) {
            pickMedia();
        }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK)
        {

            Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
            userimage.setImageBitmap(photo);
            Uri tempUri = getImageUri(EditProfileActivity.this, photo);
            //select.setVisibility(View.GONE);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            mediaFile = new File(getRealPathFromURI(tempUri));

        }
        else if (requestCode == GALLERYImage&& resultCode == RESULT_OK) {

            // Uri selectedImage = imageReturnedIntent.getData();
            // imageView.setImageURI(selectedImage);
            try {
                // Creating file
                mediaFile = null;
                try {
                    mediaFile = createImageFile();
                } catch (IOException ex) {
                    Log.d(TAG, "Error occurred while creating the file");
                }
                InputStream inputStream = getContentResolver().openInputStream(imageReturnedIntent.getData());
                FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
                // Copying
                copyStream(inputStream, fileOutputStream);
                fileOutputStream.close();
                inputStream.close();
                if(mediaFile.exists()){

                    Bitmap myBitmap = BitmapFactory.decodeFile(mediaFile.getAbsolutePath());

                    userimage.setImageBitmap(myBitmap);

                }
            } catch (Exception e) {
                Log.d(TAG, "onActivityResult: " + e.toString());
            }
        }
        if (resultCode == this.RESULT_CANCELED) {
            Log.d("what","cancle");
            return;
        }

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public static void copyStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
    private boolean checkStoragePermissions() {
        return
                ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
public  void UpdateUser()
{
    progressDialog.show();
    if(mediaFile!=null) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile);
        body = MultipartBody.Part.createFormData("files", mediaFile.getName(), requestFile);
    }
    RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
    RequestBody uname = RequestBody.create(MediaType.parse("multipart/form-data"),fname.getText().toString() );
    RequestBody ulname = RequestBody.create(MediaType.parse("multipart/form-data"),lname.getText().toString());
    RequestBody ubio = RequestBody.create(MediaType.parse("multipart/form-data"), bio.getText().toString());
    RequestBody rcity = RequestBody.create(MediaType.parse("multipart/form-data"),city.getText().toString());
    RequestBody rdob = RequestBody.create(MediaType.parse("multipart/form-data"), date1);
    RequestBody rgender = RequestBody.create(MediaType.parse("multipart/form-data"), gender);
    Call<RegisterModel> userCall = service.EditProfile(userid,uname,ulname,rcity,ubio,rdob,rgender,body);
    userCall.enqueue(new Callback<RegisterModel>() {
        @Override
        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
            progressDialog.dismiss();
            try {
                // Log.d("Email", "" + response.body().getStatus());
                // if (Integer.parseInt(response.body().getStatus())==1) {
                Toast.makeText(EditProfileActivity.this,"User Update Successfully",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(EditProfileActivity.this,ProfileActivity.class);
                startActivity(intent);
                finish();
            }catch (Exception ex)
            {

            }
        }
        @Override
        public void onFailure(Call<RegisterModel> call, Throwable t) {
            progressDialog.dismiss();
            Toast.makeText(EditProfileActivity.this,"Network Error",Toast.LENGTH_LONG).show();
            //hidepDialog();
            Log.d("onFailure", t.toString());
        }
    });

 } public  void Login()
    {
        Call<LoginModel> userCall = service.GetProfile(uid,uid);
        userCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                        //  Toast.makeText(ProfileActivity.this,""+response.body().getMsg(),Toast.LENGTH_LONG).show();
                        if(response.body().getData().get(0).getUserFirstname()==null) {
                            fname.setText("");
                        }
                        else {
                            fname.setText(response.body().getData().get(0).getUserFirstname());
                        }
                        if(response.body().getData().get(0).getUserLastname()==null) {
                            lname.setText("");
                        }
                        else {
                            lname.setText(response.body().getData().get(0).getUserLastname());
                        }
                        if(response.body().getData().get(0).getUserCurrentCity()==null)
                        {
                            city.setText("");

                        }else {
                            city.setText("" + response.body().getData().get(0).getUserCurrentCity());

                        }
                        if(response.body().getData().get(0).getUserGender().equalsIgnoreCase("male")) {
                            gender="male";
                            male.setChecked(true);
                            female.setChecked(false);
                            other.setChecked(false);
                        }
                        else if(response.body().getData().get(0).getUserGender().equalsIgnoreCase("female")) {
                            gender="female";
                            male.setChecked(false);
                            female.setChecked(true);
                            other.setChecked(false);
                        }
                        else  if(response.body().getData().get(0).getUserGender().equalsIgnoreCase("other")){
                            gender="Other";
                            male.setChecked(false);
                            female.setChecked(false);
                            other.setChecked(true);
                        }
                       if(response.body().getData().get(0).getUserBirthdate()==null) {
                           dob.setText("");
                       }
                       else {
                           dob.setText(""+response.body().getData().get(0).getUserBirthdate());
                           SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
                           date1=""+response.body().getData().get(0).getUserBirthdate();
                           // dob.setText("" + response.body().getData().get(0).getUserBirthdate());
                       }
                        if(response.body().getData().get(0).getUserBiography()==null) {
                            bio.setText("");
                        }
                        else {
                            bio.setText("" + response.body().getData().get(0).getUserBiography());
                        }
                        Picasso.get().load(getString(R.string.base_url)+"utredns_admires/content/uploads/"+response.body().getData().get(0).getUserPicture()).error(R.drawable.c1).into(userimage);
                      //  Picasso.get().load(""+response.body().getData().get(0).getUserCover()).error(R.drawable.c3).into(iv_cover);
                           }
                    else {
                        Toast.makeText(EditProfileActivity.this,""+response.body().getMsg(),Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(EditProfileActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
}
