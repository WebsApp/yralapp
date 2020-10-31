package com.webapps.viral.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.readmoreoption.ReadMoreOption;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.PostAdapter;
import com.webapps.viral.fragement.PostPhotoFragment;
import com.webapps.viral.fragement.PostStoryFragment;
import com.webapps.viral.fragement.PostVideoFragment;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.LoginModel;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.HeightWrappingViewPager;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfileActivity extends AppCompatActivity {

    ImageView camera,setting;
   TabLayout tabLayout;
    ArrayList<PostModel>   category;
    RecyclerView rv_category,rv_food;
   PostAdapter  postAdapter;
    ProgressDialog progressDialog;
    RequestBody requestBody;
    Retrofit retrofit;
    APIService service;
    SharedPref sharedPref;
    String user_id;
    Button follow;
    TextView back;
    private final int REQUEST_CODE_PERMISSION = 55;
    File mediaFile;
    private static final int CAM_REQUEST = 1313;
    private int GALLERY = 1, CAMERA = 2,GALLERYImage = 3;
    CircleImageView civ_profile;
    ImageView iv_cover;
    TextView tv_name,tv_desc,tv_email,tv_about,tv_post,tv_following,tv_follower,tv_like;
    Integer[] image ={R.drawable.ic_add_circle_black,R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
    MultipartBody.Part body;
    private int[] tabIcons = {
            R.drawable.ic_image_black_24dp,
            R.drawable.ic_play,
            R.drawable.ic_face
    };
    String src=null,name=null,csrc=null;
    HeightWrappingViewPager viewPager;
    ReadMoreOption readMoreOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setting=findViewById(R.id.setting);
        camera=findViewById(R.id.camera);
        follow=findViewById(R.id.follow);
        follow.setVisibility(View.GONE);
        sharedPref= new SharedPref(this);
        tv_desc=findViewById(R.id.tv_desc);
        tv_name=findViewById(R.id.tv_name);
        tv_email=findViewById(R.id.tv_email);
        civ_profile=findViewById(R.id.iv_profile);
        iv_cover=findViewById(R.id.iv_cover);
        tv_about=findViewById(R.id.tv_about);
       back=findViewById(R.id.back);
        tv_follower=findViewById(R.id.tv_follower);
        tv_following=findViewById(R.id.tv_following);
        tv_like=findViewById(R.id.tv_like);
        tv_post=findViewById(R.id.tv_post);
        tabLayout=findViewById(R.id.tab);
       tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
       // ReadMoreOption readMoreOption = new ReadMoreOption.Builder(this).build();

        // OR using options to customize

        readMoreOption = new ReadMoreOption.Builder(this)
                .textLength(3, ReadMoreOption.TYPE_LINE) // OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("more")
                .lessLabel("less")
                .moreLabelColor(Color.RED)
                .lessLabelColor(Color.MAGENTA)
                .labelUnderLine(true)
                .expandAnimation(true)
                .build();
         back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tv_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Src",""+src);
                Intent i = new Intent(ProfileActivity.this, AllFollowerActivity.class);
                i.putExtra("id", "" + sharedPref.getID());
                startActivity(i);
            }
        });
        tv_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Src",""+src);
                Intent i = new Intent(ProfileActivity.this, AllFollowingActivity.class);
                i.putExtra("id", "" + sharedPref.getID());
                startActivity(i);
            }
        });
        civ_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name!=null&src!=null) {
                    Log.e("Src",""+src);
                    Intent i = new Intent(ProfileActivity.this, ProfiledetailsActivity.class);
                    i.putExtra("name", "" + name);
                    i.putExtra("src", "" + src);
                    startActivity(i);
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ProfileActivity.this, SettingActivity.class);
                startActivity(i2);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pickMedia();
            }
        });
        iv_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name!=null&csrc!=null) {
                    Log.e("Src",""+csrc);
                    Intent i = new Intent(ProfileActivity.this, ProfiledetailsActivity.class);
                    i.putExtra("name", "" + name);
                    i.putExtra("src", "" + csrc);
                    startActivity(i);
                }
            }
        });
        category = new ArrayList<>();
        rv_food=findViewById(R.id.recyclerview);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(false);
        rv_food.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        user_id=sharedPref.getID();
        Login();
       // GetPost();
        viewPager = findViewById(R.id.viewpager);
        viewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PostPhotoFragment(0), "Photos");
        adapter.addFrag(new PostVideoFragment(0), "Videos");
        adapter.addFrag(new PostStoryFragment(0), "Stories");
        viewPager.setAdapter(adapter);
    }
    public Bitmap addGradient(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);

        canvas.drawBitmap(originalBitmap, 0, 0, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, 0, height, 0xFFF0D252, 0xFFF07305, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0, 0, width, height, paint);

        return updatedBitmap;
    }
    public  void Login()
    {
        Call<LoginModel> userCall = service.GetProfile(user_id,user_id);
        userCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                      //  Toast.makeText(ProfileActivity.this,""+response.body().getMsg(),Toast.LENGTH_LONG).show();
                       tv_name.setText(response.body().getData().get(0).getUserName());
                       // tv_desc.setText(response.body().getData().get(0).getUserBiography());
                        tv_email.setText(response.body().getData().get(0).getUserEmail());
                        tv_about.setText("About "+response.body().getData().get(0).getUserName());
                        tv_follower.setText(""+response.body().getFollowers());
                        tv_following.setText(""+response.body().getFollow());
                        tv_like.setText(""+response.body().getLikes());
                        tv_post.setText(""+response.body().getPost());

                        sharedPref.setPic(""+response.body().getData().get(0).getUserPicture());
                        Picasso.get().load(getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserPicture()).error(R.drawable.c1).into(civ_profile);
                        Picasso.get().load(getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserCover()).error(R.drawable.cover).into(iv_cover);
                        src=getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserPicture();
                        name=response.body().getData().get(0).getUserName();
                        csrc=getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserCover();

                        if(response.body().getData().get(0).getUserBiography()!=null) {
                            readMoreOption.addReadMoreTo(tv_desc, response.body().getData().get(0).getUserBiography());
                        }
                        else {
                            tv_desc.setVisibility(View.GONE);
                        }
                    }
                    else {
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
               // Toast.makeText(ProfileActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public void pickMedia() {
        if (checkStoragePermissions()) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileActivity.this);
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

            Uri tempUri = getImageUri(ProfileActivity.this, photo);
            //select.setVisibility(View.GONE);
            // CALL THIS METHOD TO GET THE ACTUAL PATH
            mediaFile = new File(getRealPathFromURI(tempUri));

            UpdateUser();
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

                   UpdateUser();

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
      //  mCurrentPhotoPath = image.getAbsolutePath();
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
                ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    public  void UpdateUser()
    {
        progressDialog.show();
        if(mediaFile!=null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile);
            body = MultipartBody.Part.createFormData("files", mediaFile.getName(), requestFile);
        }
        else {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");
            body = MultipartBody.Part.createFormData("files", "", requestFile);
        }

        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);

        Call<RegisterModel> userCall = service.CoverUpdate(userid,body);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {

                    // Log.d("Email", "" + response.body().getStatus());
                    // if (Integer.parseInt(response.body().getStatus())==1) {

                    Toast.makeText(ProfileActivity.this,"Cover Image Update Successfully",Toast.LENGTH_LONG).show();

                    Intent intent=new Intent(ProfileActivity.this,ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
///////