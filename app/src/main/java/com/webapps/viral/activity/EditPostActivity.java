package com.webapps.viral.activity;
import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.adapter.ListViewAdapter;
import com.webapps.viral.model.AddPost;
import com.webapps.viral.model.CategoryModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.ProgressRequestBody;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
public class EditPostActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks{
    TextView back;
    Button schedule,button_photos,buttonsubmit,buttonvideo,feeling;
    ArrayList<File> arrayList;
    private static final int CAM_REQUEST = 1313;
    private final int REQUEST_CODE_PERMISSION = 55;
    String mCurrentPhotoPath;
    File mediaFile,videofile;
   List<File> imagefile;
    FFmpeg fFmpeg;
   // ImageView imageView,imageView2,imageView3,imageView4,imageView5,imageView6,
     ImageView   userimage;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    APIService service;
    MultipartBody.Part body,body2;
    List<MultipartBody.Part> imagebody;
    EditText et_text;
    SharedPref sharedPref;
    String uid,privacy;
    Switch aSwitch,commentSwitch;
    String type="";
    private VideoView videoView;
    private static final String VIDEO_DIRECTORY = "/viral";
    private int GALLERY = 1, CAMERA = 2,GALLERYImage = 3;
    Spinner spinner;
    String  category,sfeeling="0",comment="0";
    String[] s;
    ListView myNames;
    ListViewAdapter adapter;

    ProgressBar progressBar;
    TextView progress;

    TextView tv_name;
    CircleImageView image;
    String [] name={"Scared","Happy","Sad","Angry","Excited","Worried","Surprised","Silly","Frusted"};
    int[] img={R.drawable.feel1,R.drawable.feel2,R.drawable.feel3,R.drawable.feel4,R.drawable.feel5,R.drawable.feel6,R.drawable.feel7,R.drawable.feel8,R.drawable.feel9};
    final Calendar myCalendar = Calendar.getInstance();
    String t,post_id;
    int imageno=0;
    HorizontalScrollView scrollView;
    LinearLayout  lnrImages;
    private ArrayList<String> imagesPathList;
    private Bitmap yourbitmap;
    private Bitmap resized;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        back=findViewById(R.id.back);
        button_photos=findViewById(R.id.button_photos);
        arrayList= new ArrayList<>();
        imagebody= new ArrayList<>();
        imagefile=new ArrayList<>();
        privacy="Private";
        userimage=findViewById(R.id.image);
        scrollView=findViewById(R.id.scrollview);
        spinner=findViewById(R.id.spinner);
        lnrImages=findViewById(R.id.lnrImages);
        progressBar=findViewById(R.id.progress_bar);
        progress=findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        commentSwitch=findViewById(R.id.switch1);
        buttonsubmit = findViewById(R.id.button);
        buttonvideo=findViewById(R.id.buttonvideo);
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        et_text=findViewById(R.id.et_text);
        videoView=findViewById(R.id.vv);
        sharedPref=new SharedPref(this);
        uid=sharedPref.getID();
        tv_name=findViewById(R.id.tv_name);
        tv_name.setText(sharedPref.getYourName());

        try {
            post_id = getIntent().getExtras().getString("id", "");
            type = "" + getIntent().getExtras().get("type");
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            Picasso.get().load(getString(R.string.base_url)+"utredns_admires/content/uploads"+sharedPref.getPic()).error(R.drawable.c1).into(userimage);

        }catch (Exception ex){}
        GetCategory();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(i>0)
               {
                category=s[i];
              }else{
                   category="Feed";
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        aSwitch=findViewById(R.id.switch2);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(aSwitch.isChecked())
                {
                     privacy="Public";

                }     else {
                    privacy="Private";

                }
            }
        });
        commentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(commentSwitch.isChecked())
                {
                    comment="0";
                }else {
                    comment="1";
                }
            }
        });
        buttonvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="video";
                scrollView.setVisibility(View.GONE);
                showPictureDialog();
            }
        });
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagefile==null&videofile==null)
                {
                    type="post";
                }
                if(type.equalsIgnoreCase("photos"))
                {
                    if(et_text.getText().length()<5000) {
                        AddPhoto();
                    }
                    else{
                        et_text.setError("Text must be less than 5000 letters");
                        Toast.makeText(EditPostActivity.this,"Text must be less than 5000 letters",Toast.LENGTH_LONG).show();

                    }
                }
            else  if(type.equalsIgnoreCase("videos"))
                {
                    if(et_text.getText().length()<5000) {
                        AddVideo();
                     }
                else{
                    et_text.setError("Text must be less than 5000 letters");
                    Toast.makeText(EditPostActivity.this,"Text must be less than 5000 letters",Toast.LENGTH_LONG).show();
                }
                }
            else {
                    if(et_text.getText().length()<1000) {
                        AddPost();
                    }
                    else{
                        et_text.setError("Text must be less than 5000 letters");
                        Toast.makeText(EditPostActivity.this,"Text must be less than 5000 letters",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        progressDialog= new ProgressDialog(EditPostActivity.this);
        progressDialog.setTitle("Please Wait...");
        schedule=findViewById(R.id.schedule);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup1();
            }
        });
        button_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="photos";
                scrollView.setVisibility(View.VISIBLE);
                pickMedia();
            }
        });
        feeling = (Button) findViewById(R.id.feeling);
        feeling.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(EditPostActivity.this);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setTitle("Select Feeling");
                myNames=  dialog.findViewById(R.id.List);
                adapter = new ListViewAdapter(EditPostActivity.this, name,img);
                myNames.setAdapter(adapter);
                dialog.show();
             myNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                 @Override
                 public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                     sfeeling=""+(i+1);
                     Drawable im = getResources().getDrawable(img[i]);
                     im.setBounds(0, 0, 60, 60);
                     feeling.setCompoundDrawables(null,null,im,null);
                     dialog.dismiss();
                 }
             });
            }
        });
    }
    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select video from gallery",
                "Record video from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseVideoFromGallary();
                                break;
                            case 1:
                                takeVideoFromCamera();
                                break;
                        }
                    }
        });
        pictureDialog.show();
    }
    public void pickMedia() {
        if (checkStoragePermissions()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditPostActivity.this);
            alertDialog.setMessage("Get picture from");
            alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAM_REQUEST);
                }
            });
            alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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
            videoView.setVisibility(View.GONE);

            Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
            imageno++;
            Uri tempUri = getImageUri(EditPostActivity.this, photo);

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout
                    .LayoutParams(500,
                    400));
            //imageView.setImageBitmap(yourbitmap);
            imageView.setAdjustViewBounds(true);
            lnrImages.addView(imageView);
            Glide.with(EditPostActivity.this)
                    .load(tempUri)
                    .into(imageView);
            mediaFile = new File(getRealPathFromURI(tempUri));
            imagefile.add(mediaFile);
            int file_size = Integer.parseInt(String.valueOf(mediaFile.length()/1048576));
            Log.e("FIle Size",""+file_size);
            if(file_size<20) {

            }
            else {
                Toast.makeText(EditPostActivity.this,"File size is greater than 20MB",Toast.LENGTH_LONG).show();
                mediaFile=null;
            }
        }
        else if (requestCode == GALLERYImage&& resultCode == RESULT_OK) {
            videoView.setVisibility(View.GONE);
            imagesPathList = new ArrayList<String>();
           // String[] imagesPath = imageReturnedIntent.getStringExtra("data").split("\\|");
            ClipData mClipData = imageReturnedIntent.getClipData();
            try{
                lnrImages.removeAllViews();
            }catch (Throwable e){
                e.printStackTrace();
            }
            //imagefile= new File[mClipData.getItemCount()];
            for (int i=0;i<mClipData.getItemCount();i++){
              //  imagesPathList.add(mClipData.getItemAt(i).getUri().toString());
                //yourbitmap = BitmapFactory.decodeFile(mClipData.getItemAt(i).getUri().toString());
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout
                        .LayoutParams(500,
                        400));
                //imageView.setImageBitmap(yourbitmap);
                imageView.setAdjustViewBounds(true);
                lnrImages.addView(imageView);
                Glide.with(EditPostActivity.this)
                        .load(mClipData.getItemAt(i).getUri())
                        .into(imageView);
                try {
                    mediaFile = null;
                    try {
                        mediaFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d(TAG, "Error occurred while creating the file");
                    }
                    InputStream inputStream = getContentResolver().openInputStream(mClipData.getItemAt(i).getUri());
                    FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
                    // Copying
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                }catch (Exception e){}
               imagefile.add(mediaFile);
                Log.e("Image File Name",""+imagefile.get(i).getAbsolutePath());

            }
                  }
        if (resultCode == this.RESULT_CANCELED) {
            Log.d("what","cancle");
            return;
        }
        if (requestCode == GALLERY) {
            Log.d("what","gale");
            if (imageReturnedIntent != null) {
                Uri contentURI = imageReturnedIntent.getData();
                  videoView.setVisibility(View.VISIBLE);
                String selectedVideoPath = getPath(contentURI);
                Log.d("path",selectedVideoPath);
                saveVideoToInternalStorage(selectedVideoPath);
                int file_size = Integer.parseInt(String.valueOf(videofile.length()/1048576));
                Log.e("FIle Size",""+file_size);
                if(file_size<50) {
                    videoView.setVideoURI(contentURI);
                    videoView.requestFocus();
                   // videoView.start();
                }
            }

        } else if (requestCode == CAMERA) {
            videoView.setVisibility(View.VISIBLE);

            Uri contentURI = imageReturnedIntent.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr",recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);
            int file_size = Integer.parseInt(String.valueOf(videofile.length()/1048576));
            Log.e("FIle Size",""+file_size);
            if(file_size<50) {
                videoView.setVideoURI(contentURI);
                videoView.requestFocus();
              //  videoView.start();
            }
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
                ContextCompat.checkSelfPermission(EditPostActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(EditPostActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(EditPostActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void chooseVideoFromGallary() {
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void takeVideoFromCamera() {
        try {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
       }catch (Exception e){
        e.printStackTrace();
    }
    }
    public  void popup1()
    {
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditPostActivity.this);

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.schedule,null);
        builder.setView(customView);
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
        TextView time = customView.findViewById(R.id.time);
        TextView date =  customView.findViewById(R.id.date);
        TextView tv_date =  customView.findViewById(R.id.tv_date);

        Button submit =  customView.findViewById(R.id.btn_submit);
        Calendar calendar= Calendar.getInstance();
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        time.setText(""+currentTime);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date.setText(""+currentDate);
        tv_date.setText(""+currentDate+" "+currentTime);
         t=  new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date())+" "+currentTime;

        final AlertDialog  nameDialog = builder.create();
        nameDialog.show();
          closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                nameDialog.dismiss();
            }
        });
          time.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Calendar mcurrentTime = Calendar.getInstance();
                  int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                  int minute = mcurrentTime.get(Calendar.MINUTE);
                  int second = mcurrentTime.get(Calendar.SECOND);
                  TimePickerDialog mTimePicker;

                  mTimePicker = new TimePickerDialog(EditPostActivity.this, new TimePickerDialog.OnTimeSetListener() {
                      @Override
                      public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                          time.setText( selectedHour + ":" + selectedMinute+":00");
                          SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
                          // t=sdf2.format(date.getText())+" "+time.getText().toString();
                      }
                  }, hour, minute,true);//Yes 24 hour time
                  mTimePicker.setTitle("Select Time");
                  mTimePicker.show();
              }
          });
        DatePickerDialog.OnDateSetListener mydate = new DatePickerDialog.OnDateSetListener() {
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
                date.setText(sdf.format(myCalendar.getTime()));
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd hh:mm::ss", Locale.US);
                t=sdf2.format(myCalendar.getTime())+" "+time.getText().toString();
                //date1=sdf2.format(myCalendar.getTime());
            }

        };

        date.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  DatePickerDialog dialog =  new DatePickerDialog(EditPostActivity.this, mydate, myCalendar
                          .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                          myCalendar.get(Calendar.DAY_OF_MONTH));
                  dialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                  dialog.show();
              }
          });
          submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(mediaFile==null&videofile==null)
                  {
                      type="post";
                  }
                  if(type.equalsIgnoreCase("post"))
                  {
                      AddSPhoto(t);
                  }
                  else  if(type.equalsIgnoreCase("videos"))
                  {
                      AddSVideo(t);
                  }
                  else {
                      AddSPost(t);
                  }

                  nameDialog.dismiss();
              }
          });
    }
    public  void AddPhoto()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String cDate = format.format(c);
        progress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        progressDialog.dismiss();
        if(imagefile.size()!=0) {
            for (int i=0;i<imagefile.size();i++) {
                Log.e("URL",""+imagefile.get(i).getName());
             //   RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imagefile.get(i));
                ProgressRequestBody fileBody = new ProgressRequestBody(imagefile.get(i), this);

                body = MultipartBody.Part.createFormData("files[]", imagefile.get(i).getName(), fileBody);
                imagebody.add(body);
            }
        }
        else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body = MultipartBody.Part.createFormData("files[]", "", requestFile);
            imagebody.add(body);
        }
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody upic = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uname = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uverified = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
        RequestBody message = RequestBody.create(MediaType.parse("multipart/form-data"), et_text.getText().toString());
        RequestBody rcategory = RequestBody.create(MediaType.parse("multipart/form-data"),category);

        RequestBody location = RequestBody.create(MediaType.parse("multipart/form-data"),"Abc");
        RequestBody uprivacy = RequestBody.create(MediaType.parse("multipart/form-data"), privacy);
        RequestBody ptype = RequestBody.create(MediaType.parse("multipart/form-data"), "photos");
        RequestBody pdate = RequestBody.create(MediaType.parse("multipart/form-data"), cDate);
        RequestBody rcomment = RequestBody.create(MediaType.parse("multipart/form-data"), comment);
        RequestBody rfeeling = RequestBody.create(MediaType.parse("multipart/form-data"), sfeeling);


        RequestBody ccategory = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody ex = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody rid = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        Call<AddPost> userCall = service.AddPhoto(rid,ex,userid,upic,uname,uname,uname,uverified,rcategory,message,uprivacy,ptype,pdate,rcomment,rfeeling,location,ccategory,title,imagebody);
        userCall.enqueue(new Callback<AddPost>() {
            @Override
            public void onResponse(Call<AddPost> call, Response<AddPost> response) {
                progressDialog.dismiss();
                progress.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(100);
                try {

                   // Log.d("Email", "" + response.body().getStatus());
                   // if (Integer.parseInt(response.body().getStatus())==1) {
                        et_text.setText(null);
                        Toast.makeText(EditPostActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(EditPostActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<AddPost> call, Throwable t) {
                progressDialog.dismiss();
                progress.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(0);
                Toast.makeText(EditPostActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
    public  void AddPost()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String cDate = format.format(c);
        progressDialog.show();
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody upic = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uname = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uverified = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
        RequestBody message = RequestBody.create(MediaType.parse("multipart/form-data"), et_text.getText().toString());
        RequestBody rcategory = RequestBody.create(MediaType.parse("multipart/form-data"),category);

        RequestBody location = RequestBody.create(MediaType.parse("multipart/form-data"),"Abc");
        RequestBody uprivacy = RequestBody.create(MediaType.parse("multipart/form-data"), privacy);
        RequestBody ptype = RequestBody.create(MediaType.parse("multipart/form-data"), "post");
        RequestBody pdate = RequestBody.create(MediaType.parse("multipart/form-data"), cDate);
        RequestBody rcomment = RequestBody.create(MediaType.parse("multipart/form-data"), comment);
        RequestBody rfeeling = RequestBody.create(MediaType.parse("multipart/form-data"), sfeeling);

        RequestBody ccategory = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody ex = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        Call<AddPost> userCall = service.AddPost(ex,userid,upic,uname,uname,uname,uverified,rcategory,message,uprivacy,ptype,rcomment,rfeeling,pdate,ccategory,title,location);
        userCall.enqueue(new Callback<AddPost>() {
            @Override
            public void onResponse(Call<AddPost> call, Response<AddPost> response) {
                progressDialog.dismiss();
                try {

                    // Log.d("Email", "" + response.body().getStatus());
                    // if (Integer.parseInt(response.body().getStatus())==1) {
                    et_text.setText(null);

                    Toast.makeText(EditPostActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(EditPostActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<AddPost> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditPostActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
    public  void AddVideo()
    {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String cDate = format.format(c);
        progressDialog.dismiss();
        progress.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        if(videofile!=null) {

           // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), videofile);
            ProgressRequestBody fileBody = new ProgressRequestBody(videofile, this);

            body = MultipartBody.Part.createFormData("files", videofile.getName(), fileBody);
        }
        else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body = MultipartBody.Part.createFormData("files", "", requestFile);
        }

        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody upic = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uname = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uverified = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
        RequestBody message = RequestBody.create(MediaType.parse("multipart/form-data"), et_text.getText().toString());

        RequestBody rcategory = RequestBody.create(MediaType.parse("multipart/form-data"),category);
        RequestBody uprivacy = RequestBody.create(MediaType.parse("multipart/form-data"), privacy);
        RequestBody ptype = RequestBody.create(MediaType.parse("multipart/form-data"), "video");
        RequestBody pdate = RequestBody.create(MediaType.parse("multipart/form-data"), cDate);
        RequestBody rcomment = RequestBody.create(MediaType.parse("multipart/form-data"), comment);
        RequestBody rfeeling = RequestBody.create(MediaType.parse("multipart/form-data"), sfeeling);

        RequestBody locatiom = RequestBody.create(MediaType.parse("multipart/form-data"), "njsf");

        RequestBody ccategory = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody ex = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody rid = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        Call<AddPost> userCall = service.addVideo(rid,ex,userid,upic,uname,uname,uname,uverified,rcategory,message,uprivacy,ptype,pdate,rcomment,rfeeling,locatiom,ccategory,title,imagebody);
        userCall.enqueue(new Callback<AddPost>() {
            @Override
            public void onResponse(Call<AddPost> call, Response<AddPost> response) {
                progressDialog.dismiss();
                progress.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(100);
                try {

                    // Log.d("Email", "" + response.body().getStatus());
                    // if (Integer.parseInt(response.body().getStatus())==1) {
                    et_text.setText(null);

                    Toast.makeText(EditPostActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(EditPostActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<AddPost> call, Throwable t) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                Toast.makeText(EditPostActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
    public  void AddSPhoto(String time)
    {
        progressDialog.dismiss();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        progress.setVisibility(View.VISIBLE);
        if(imagefile.size()!=0) {
            for (int i=0;i<imagefile.size();i++) {
             //   RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imagefile.get(i));
                ProgressRequestBody fileBody = new ProgressRequestBody(imagefile.get(i), this);

                body = MultipartBody.Part.createFormData("files[]", imagefile.get(i).getName(), fileBody);
                imagebody.add(body);
            }
        }
        else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body = MultipartBody.Part.createFormData("files[]", "", requestFile);
            imagebody.add(body);
        }
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody upic = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uname = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uverified = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
        RequestBody message = RequestBody.create(MediaType.parse("multipart/form-data"), et_text.getText().toString());
        RequestBody rcategory = RequestBody.create(MediaType.parse("multipart/form-data"),category);

        RequestBody location = RequestBody.create(MediaType.parse("multipart/form-data"),"Abc");
        RequestBody uprivacy = RequestBody.create(MediaType.parse("multipart/form-data"), privacy);
        RequestBody ptype = RequestBody.create(MediaType.parse("multipart/form-data"), "photos");
        RequestBody pdate = RequestBody.create(MediaType.parse("multipart/form-data"), time);
        RequestBody rcomment = RequestBody.create(MediaType.parse("multipart/form-data"), comment);
        RequestBody rfeeling = RequestBody.create(MediaType.parse("multipart/form-data"), sfeeling);


        RequestBody ccategory = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody ex = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        RequestBody rid = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        Call<AddPost> userCall = service.AddPhoto(rid,ex,userid,upic,uname,uname,uname,uverified,rcategory,message,uprivacy,ptype,pdate,rcomment,rfeeling,location,ccategory,title,imagebody);
        userCall.enqueue(new Callback<AddPost>() {
            @Override
            public void onResponse(Call<AddPost> call, Response<AddPost> response) {
                progressDialog.dismiss();
                progress.setVisibility(View.GONE);
                progressBar.setProgress(100);
                progressBar.setVisibility(View.GONE);
                try {
                    et_text.setText(null);
                    Toast.makeText(EditPostActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(EditPostActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<AddPost> call, Throwable t) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                Toast.makeText(EditPostActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
    public  void AddSPost(String  time)
    {
         progressDialog.show();
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody upic = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uname = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uverified = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
        RequestBody message = RequestBody.create(MediaType.parse("multipart/form-data"), et_text.getText().toString());
        RequestBody rcategory = RequestBody.create(MediaType.parse("multipart/form-data"),category);

        RequestBody location = RequestBody.create(MediaType.parse("multipart/form-data"),"Abc");
        RequestBody uprivacy = RequestBody.create(MediaType.parse("multipart/form-data"), privacy);
        RequestBody ptype = RequestBody.create(MediaType.parse("multipart/form-data"), "post");
        RequestBody pdate = RequestBody.create(MediaType.parse("multipart/form-data"), time);
        RequestBody rcomment = RequestBody.create(MediaType.parse("multipart/form-data"), comment);

        RequestBody rfeeling = RequestBody.create(MediaType.parse("multipart/form-data"), sfeeling);


        RequestBody ccategory = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody ex = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        Call<AddPost> userCall = service.AddPost(ex,userid,upic,uname,uname,uname,uverified,rcategory,message,uprivacy,ptype,pdate,rcomment,rfeeling,ccategory,title,location);
        userCall.enqueue(new Callback<AddPost>() {
            @Override
            public void onResponse(Call<AddPost> call, Response<AddPost> response) {
                progressDialog.dismiss();
                try {
                    // Log.d("Email", "" + response.body().getStatus());
                    // if (Integer.parseInt(response.body().getStatus())==1) {
                    et_text.setText(null);
                    Toast.makeText(EditPostActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(EditPostActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<AddPost> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditPostActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
    public  void AddSVideo(String time)
    {
         System.out.println("Schedule time => " + time);
        progressDialog.dismiss();
        if(videofile!=null) {
          //  RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), videofile);
            ProgressRequestBody fileBody = new ProgressRequestBody(videofile, this);

            body = MultipartBody.Part.createFormData("files", videofile.getName(), fileBody);
        }
        else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body = MultipartBody.Part.createFormData("files", "", requestFile);
        }

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody upic = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uname = RequestBody.create(MediaType.parse("multipart/form-data"), sharedPref.getYourName());
        RequestBody uverified = RequestBody.create(MediaType.parse("multipart/form-data"),"1");
        RequestBody message = RequestBody.create(MediaType.parse("multipart/form-data"), et_text.getText().toString());

        RequestBody rcategory = RequestBody.create(MediaType.parse("multipart/form-data"),category);
        RequestBody uprivacy = RequestBody.create(MediaType.parse("multipart/form-data"), privacy);
        RequestBody ptype = RequestBody.create(MediaType.parse("multipart/form-data"), "video");
        RequestBody pdate = RequestBody.create(MediaType.parse("multipart/form-data"), time);
        RequestBody rcomment = RequestBody.create(MediaType.parse("multipart/form-data"), comment);
        RequestBody rfeeling = RequestBody.create(MediaType.parse("multipart/form-data"), sfeeling);

        RequestBody loc = RequestBody.create(MediaType.parse("multipart/form-data"), "hbh");

        RequestBody ccategory = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"),"");
        RequestBody ex = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        RequestBody rid = RequestBody.create(MediaType.parse("multipart/form-data"),"");

        Call<AddPost> userCall = service.addVideo(rid,ex,userid,upic,uname,uname,uname,uverified,rcategory,message,uprivacy,ptype,pdate,rcomment,rfeeling,loc,ccategory,title,imagebody);
        userCall.enqueue(new Callback<AddPost>() {
            @Override
            public void onResponse(Call<AddPost> call, Response<AddPost> response) {
                progressDialog.dismiss();
                progress.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                try {
                    // Log.d("Email", "" + response.body().getStatus());
                    // if (Integer.parseInt(response.body().getStatus())==1) {
                    et_text.setText(null);
                    Toast.makeText(EditPostActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(EditPostActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<AddPost> call, Throwable t) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                Toast.makeText(EditPostActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
    private void saveVideoToInternalStorage (String filePath) {
        File newfile;
        try {
            File currentFile = new File(filePath);
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + VIDEO_DIRECTORY);
            newfile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".mp4");
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }
            if(currentFile.exists()){
                int file_size = Integer.parseInt(String.valueOf(currentFile.length()/1048576));
                 Log.e("FIle Size",""+file_size);
                if(file_size<50) {
                    InputStream in = new FileInputStream(currentFile);
                    OutputStream out = new FileOutputStream(newfile);
                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                    Log.v("vii", "Video file saved successfully.");
                    videofile = currentFile;
                }
                else {
                    Toast.makeText(EditPostActivity.this,"File size is greater than 50MB",Toast.LENGTH_LONG).show();

                }

            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public  void GetCategory()
    {

        Call<CategoryModel> userCall = service.GetCategory();
        userCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getData().size());
                    s= new String[response.body().getData().size()+1];
                    if (response.body().getStatus()==1) {

                        s[0]="Select Category";
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            s[i+1]=response.body().getData().get(i).getTitle();
                        }
                            // recipientLists= response.body().getMessage();
                        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(EditPostActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                s);
                    spinner.setAdapter(arrayAdapter);
                    }
                    else {
                        Toast.makeText(EditPostActivity.this,""+response.body().getMsg(),Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(EditPostActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    @Override
    public void onProgressUpdate(int percentage) {
        progressBar.setProgress(percentage);
        progress.setText(""+percentage+"% file uploaded");
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        progressBar.setProgress(100);
        progress.setText("100% file uploaded");
    }
    //video Resize
    public  void  loadFFmpegLibrary() throws FFmpegNotSupportedException {
        if(fFmpeg==null)
        {
            fFmpeg= FFmpeg.getInstance(this);

            fFmpeg.loadBinary(new FFmpegLoadBinaryResponseHandler() {
                @Override
                public void onFailure() {

                    // Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess() {
                    //  Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFinish() {

                }
            });

        }

    }
}
