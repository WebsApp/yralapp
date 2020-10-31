package com.webapps.viral.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerComponentHolder;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.features.imageloader.DefaultImageLoader;
import com.esafirm.imagepicker.model.Image;
import com.webapps.viral.R;
import com.webapps.viral.imagepicker.GrayscaleImageLoader;
import com.webapps.viral.model.AddPost;
import com.webapps.viral.model.BannerModel;
import com.webapps.viral.model.RegisterModel;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddStoryActivity extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks{

    private ArrayList<String> imagesPathList;
    List<File> imagefile;
    ImageView back,gallery,video,iv_image;
    private int GALLERY = 1, CAMERA = 2,GALLERYImage = 3,isvideo=0;
    int imageno=0;
    private static final int CAM_REQUEST = 1313;
    private final int REQUEST_CODE_PERMISSION = 55;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    APIService service;
    String mCurrentPhotoPath;
    File mediaFile,videofile;
    MultipartBody.Part body;
    String uid;
    SharedPref sharedPref;
    Button submit;
    ProgressBar progressBar;
    VideoView videoView;
    TextView progress;
    int type=0;
    HorizontalScrollView scrollView;
    LinearLayout  lnrImages;
    private ArrayList<Image> pickimages = new ArrayList<>();
    private static final String VIDEO_DIRECTORY = "/viral";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);
        back=findViewById(R.id.back);
        gallery=findViewById(R.id.gallery);
        submit=findViewById(R.id.submit);
        imagefile=new ArrayList<>();
        lnrImages=findViewById(R.id.lnrImages);
        progressBar=findViewById(R.id.progress_bar);
        progress=findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        videoView=findViewById(R.id.vv_video);
        video=findViewById(R.id.video);
        iv_image=findViewById(R.id.iv_image);
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        progressDialog= new ProgressDialog(AddStoryActivity.this);
        progressDialog.setTitle("Please Wait...");
          sharedPref= new SharedPref(this);
          uid=sharedPref.getID();
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkStoragePermissions()) {
                    type=1;
                    videoView.setVisibility(View.GONE);
                    iv_image.setVisibility(View.GONE);
                    AlertDialog.Builder pictureDialog = new AlertDialog.Builder(AddStoryActivity.this);
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
                                            getVideoPicker().start();
                                            break;
                                        case 1:
                                            takeVideoFromCamera();
                                            break;
                                    }
                                }
                            });
                    pictureDialog.show();

                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
                    }
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkStoragePermissions()) {
                    type=0;
                    videoView.setVisibility(View.GONE);
                    iv_image.setVisibility(View.GONE);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddStoryActivity.this);
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
                            /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            startActivityForResult(pickPhoto, GALLERYImage);*/
                            getImagePicker().start();
                        }
                    });
                    alertDialog.create().show();


                }
                else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
                    }
                }
            }
        });
         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 onBackPressed();
             }
         });
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(mediaFile!=null|| videofile!=null) {
                     AddPost();
                 }else {
                     Toast.makeText(AddStoryActivity.this,"Please Select File",Toast.LENGTH_LONG).show();

                 }
             }
         });
    }public  void AddPost()
    {
        progressBar.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
     progressBar.setProgress(0);
     progress.setText("0% file uploaded");
        if(type==0) {
            if (mediaFile != null) {
               // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile);
                ProgressRequestBody fileBody = new ProgressRequestBody(mediaFile, this);

                body = MultipartBody.Part.createFormData("files[]", mediaFile.getName(), fileBody);
            } else {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                body = MultipartBody.Part.createFormData("files[]", "", requestFile);
            }
        }
        else{
            if (videofile != null) {
               // RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), videofile);
                ProgressRequestBody fileBody = new ProgressRequestBody(videofile, this);

                body = MultipartBody.Part.createFormData("files", videofile.getName(), fileBody);
            } else {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
                body = MultipartBody.Part.createFormData("files", "", requestFile);
            }
        }
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody ty = RequestBody.create(MediaType.parse("multipart/form-data"), ""+type);

        Call<RegisterModel> userCall = service.AddStory(userid,ty,body);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                progressBar.setProgress(100);
                progressBar.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                try {
                    Intent intent=new Intent(AddStoryActivity.this,MainActivity.class);
                     startActivity(intent);
                    finish();
                    // Log.d("Email", "" + response.body().getStatus());
                    // if (Integer.parseInt(response.body().getStatus())==1) {

                    Toast.makeText(AddStoryActivity.this,"Story Posted",Toast.LENGTH_LONG).show();


                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                Toast.makeText(AddStoryActivity.this,"Network Error"+t.toString(),Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION && checkStoragePermissions()) {

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

    /*public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK)
        {

            Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
            iv_image.setImageBitmap(photo);
            Uri tempUri = getImageUri(AddStoryActivity.this, photo);
            //select.setVisibility(View.GONE);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            mediaFile = new File(getRealPathFromURI(tempUri));

        }
        else if (requestCode == GALLERYImage&&resultCode == RESULT_OK) {

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

                    iv_image.setImageBitmap(myBitmap);

                }
            } catch (Exception e) {
                Log.d(TAG, "onActivityResult: " + e.toString());
            }
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
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
                retriever.setDataSource(AddStoryActivity.this, contentURI);
                String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                long timeInMillisec = Long.parseLong(time );

                retriever.release();
                if(file_size<20) {
                    videoView.setVideoURI(contentURI);
                    videoView.requestFocus();
                   // videoView.start();
                }
                if(timeInMillisec>16000)
                {
                    Toast.makeText(AddStoryActivity.this,"Video Duration is more than 15 sec",Toast.LENGTH_LONG).show();
                }
            }

        } else if (requestCode == CAMERA)
        {
            videoView.setVisibility(View.VISIBLE);

            Uri contentURI = imageReturnedIntent.getData();
            String recordedVideoPath = getPath(contentURI);
            Log.d("frrr",recordedVideoPath);
            saveVideoToInternalStorage(recordedVideoPath);
            int file_size = Integer.parseInt(String.valueOf(videofile.length()/1048576));
            Log.e("FIle Size",""+file_size);
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
            retriever.setDataSource(AddStoryActivity.this, contentURI);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeInMillisec = Long.parseLong(time );

            retriever.release();
            if(file_size<20) {
                videoView.setVideoURI(contentURI);
                videoView.requestFocus();
                // videoView.start();
            }
            if(timeInMillisec>16000)
            {
                Toast.makeText(AddStoryActivity.this,"Video Duration is more than 15 sec",Toast.LENGTH_LONG).show();
            }
        }
    }*/
    private File saveVideoToInternalStorage (String filePath) {
        File newfile=null;
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
                    newfile = currentFile;
                }
                else {
                    Toast.makeText(AddStoryActivity.this,"File size is greater than 50MB",Toast.LENGTH_LONG).show();

                }

            }else{
                Log.v("vii", "Video saving failed. Source file missing.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 return  newfile;
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
    private void printVideo(List<com.esafirm.imagepicker.model.Image> images) {
        if (images == null) return;
        videoView.setVisibility(View.GONE);
        imagesPathList = new ArrayList<String>();
        try{
            lnrImages.removeAllViews();
        }catch (Throwable e){
            e.printStackTrace();
        }
        for (int i = 0, l = images.size(); i < l; i++) {
            VideoView imageView = new VideoView(this);
            imageView.setLayoutParams(new LinearLayout
                    .LayoutParams(500,
                    400));
            lnrImages.addView(imageView);
            imageView.setVideoURI(images.get(i).getUri());
            try {
                mediaFile = null;
                mediaFile = saveVideoToInternalStorage(images.get(i).getPath());
              /*  InputStream inputStream = getContentResolver().openInputStream(images.get(i).getUri());
                FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
                // Copying
                copyStream(inputStream, fileOutputStream);
                fileOutputStream.close();
                inputStream.close();*/
            }catch (Exception e){}
            imagefile.add(mediaFile);
            Log.e("Image File Name",""+imagefile.get(i).getAbsolutePath());
        }
    }

    private void printImages(List<com.esafirm.imagepicker.model.Image> images) {
        if (images == null) return;
        videoView.setVisibility(View.GONE);
        imagesPathList = new ArrayList<String>();
        try{
            lnrImages.removeAllViews();
        }catch (Throwable e){
            e.printStackTrace();
        }
        for (int i = 0, l = images.size(); i < l; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout
                    .LayoutParams(500,
                    400));
            //imageView.setImageBitmap(yourbitmap);
            imageView.setAdjustViewBounds(true);
            lnrImages.addView(imageView);
            Glide.with(AddStoryActivity.this)
                    .load(images.get(i).getUri())
                    .into(imageView);
            try {
                mediaFile = null;
                try {
                    mediaFile = createImageFile();
                } catch (IOException ex) {
                    Log.d(TAG, "Error occurred while creating the file");
                }
                InputStream inputStream = getContentResolver().openInputStream(images.get(i).getUri());
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

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (ImagePicker.shouldHandle(requestCode, resultCode, imageReturnedIntent)) {
            if(isvideo==0) {
                pickimages = (ArrayList<Image>) ImagePicker.getImages(imageReturnedIntent);
                printImages(pickimages);
            }
            else {
                pickimages = (ArrayList<Image>) ImagePicker.getImages(imageReturnedIntent);
                printVideo(pickimages);
            }
            return;
        }
        else if (requestCode == CAM_REQUEST && resultCode == RESULT_OK)
        {
            videoView.setVisibility(View.GONE);

            Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
            imageno++;
            Uri tempUri = getImageUri(AddStoryActivity.this, photo);

            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout
                    .LayoutParams(500,
                    400));
            //imageView.setImageBitmap(yourbitmap);
            imageView.setAdjustViewBounds(true);
            lnrImages.addView(imageView);
            Glide.with(AddStoryActivity.this).load(tempUri).into(imageView);
            mediaFile = new File(getRealPathFromURI(tempUri));
            imagefile.add(mediaFile);
            int file_size = Integer.parseInt(String.valueOf(mediaFile.length()/1048576));
            Log.e("FIle Size",""+file_size);
            if(file_size<20) {
            }
            else {
                Toast.makeText(AddStoryActivity.this,"File size is greater than 20MB",Toast.LENGTH_LONG).show();
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
                imageView.setLayoutParams(new LinearLayout.LayoutParams(500,   400));
                //imageView.setImageBitmap(yourbitmap);
                imageView.setAdjustViewBounds(true);
                lnrImages.addView(imageView);
                Glide.with(AddStoryActivity.this)
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
            // Uri selectedImage = imageReturnedIntent.getData();
            // imageView.setImageURI(selectedImage);
           /* try {
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
                   *//* if(imageno==0) {
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(myBitmap);
                    }
                    else  if(imageno==1)
                    {
                        imageView2.setVisibility(View.VISIBLE);
                        imageView2.setImageBitmap(myBitmap);
                    }
                    else  if(imageno==2)
                    {
                        imageView3.setVisibility(View.VISIBLE);
                        imageView3.setImageBitmap(myBitmap);
                    }else  if(imageno==3)
                    {
                        imageView4.setVisibility(View.VISIBLE);
                        imageView4.setImageBitmap(myBitmap);
                    }else  if(imageno==4)
                    {
                        imageView5.setVisibility(View.VISIBLE);
                        imageView5.setImageBitmap(myBitmap);
                    }
                    else  if(imageno==5)
                    {
                        imageView6.setVisibility(View.VISIBLE);
                        imageView6.setImageBitmap(myBitmap);
                    }*//*
                    int file_size = Integer.parseInt(String.valueOf(mediaFile.length()/1048576));
                    Log.e("FIle Size",""+file_size);
                    if(file_size<20) {

                    }
                    else {
                        Toast.makeText(AddActivity.this,"File size is greater than 20MB",Toast.LENGTH_LONG).show();
                        mediaFile=null;
                    }
                    imageno++;
                }
            } catch (Exception e) {
                Log.d(TAG, "onActivityResult: " + e.toString());
            }*/
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

    public void chooseVideoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takeVideoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, CAMERA);
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
                ContextCompat.checkSelfPermission(AddStoryActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(AddStoryActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(AddStoryActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
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
    private ImagePicker getVideoPicker() {
        isvideo=1;
        final boolean returnAfterCapture = false;//((Switch) findViewById(R.id.ef_switch_return_after_capture)).isChecked();
        final boolean isSingleMode =false;// ((Switch) findViewById(R.id.ef_switch_single)).isChecked();
        final boolean useCustomImageLoader = true;//((Switch) findViewById(R.id.ef_switch_imageloader)).isChecked();
        final boolean folderMode =true;// ((Switch) findViewById(R.id.ef_switch_folder_mode)).isChecked();
        final boolean includeVideo =false;// ((Switch) findViewById(R.id.ef_switch_include_video)).isChecked();
        final boolean onlyVideo = true;//((Switch) findViewById(R.id.ef_switch_only_video)).isChecked();
        final boolean isExclude = false;//((Switch) findViewById(R.id.ef_switch_include_exclude)).isChecked();
        ImagePicker imagePicker = ImagePicker.create(this)
                .language("in") // Set image picker language
                .theme(R.style.ImagePickerTheme)
                .returnMode(returnAfterCapture
                        ? ReturnMode.ALL
                        : ReturnMode.NONE) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                .folderMode(folderMode) // set folder mode (false by default)
                .includeVideo(includeVideo) // include video (false by default)
                .onlyVideo(onlyVideo) // include video (false by default)
                .toolbarArrowColor(Color.RED) // set toolbar arrow up color
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarDoneButtonText("DONE"); // done button text
        ImagePickerComponentHolder.getInstance()
                .setImageLoader(useCustomImageLoader
                        ? new GrayscaleImageLoader()
                        : new DefaultImageLoader());
        if (isSingleMode) {
            imagePicker.single();
        } else {
            imagePicker.multi(); // multi mode (default mode)
        }
        if (isExclude) {
            imagePicker.exclude(pickimages); // don't show anything on this selected images
        } else {
            imagePicker.origin(pickimages); // original selected images, used in multi mode
        }
        return imagePicker.limit(20) // max images can be selected (99 by default)
                .showCamera(false) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()); // can be full path
    }
    private ImagePicker getImagePicker()
    {
        isvideo=0;
        final boolean returnAfterCapture = false;//((Switch) findViewById(R.id.ef_switch_return_after_capture)).isChecked();
        final boolean isSingleMode =false;// ((Switch) findViewById(R.id.ef_switch_single)).isChecked();
        final boolean useCustomImageLoader = false;//((Switch) findViewById(R.id.ef_switch_imageloader)).isChecked();
        final boolean folderMode =true;// ((Switch) findViewById(R.id.ef_switch_folder_mode)).isChecked();
        final boolean includeVideo =false;// ((Switch) findViewById(R.id.ef_switch_include_video)).isChecked();
        final boolean onlyVideo = false;//((Switch) findViewById(R.id.ef_switch_only_video)).isChecked();
        final boolean isExclude = false;//((Switch) findViewById(R.id.ef_switch_include_exclude)).isChecked();

        ImagePicker imagePicker = ImagePicker.create(this)
                .language("in") // Set image picker language
                .theme(R.style.ImagePickerTheme)
                .returnMode(returnAfterCapture
                        ? ReturnMode.ALL
                        : ReturnMode.NONE) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
                .folderMode(folderMode) // set folder mode (false by default)
                .includeVideo(includeVideo) // include video (false by default)
                .onlyVideo(onlyVideo) // include video (false by default)
                .toolbarArrowColor(Color.RED) // set toolbar arrow up color
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarDoneButtonText("DONE"); // done button text

        ImagePickerComponentHolder.getInstance()
                .setImageLoader(useCustomImageLoader
                        ? new GrayscaleImageLoader()
                        : new DefaultImageLoader());

        if (isSingleMode) {
            imagePicker.single();
        } else {
            imagePicker.multi(); // multi mode (default mode)
        }

        if (isExclude) {
            imagePicker.exclude(pickimages); // don't show anything on this selected images
        } else {
            imagePicker.origin(pickimages); // original selected images, used in multi mode
        }

        return imagePicker.limit(20) // max images can be selected (99 by default)
                .showCamera(false) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()); // can be full path
    }
}
