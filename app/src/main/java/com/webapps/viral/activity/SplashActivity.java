package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.webapps.viral.MultiSelectActivity;
import com.webapps.viral.R;
import com.webapps.viral.model.BannerModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {
    private static int splashtimeout = 5000;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    public static final String mypreference = "mypref";
    ProgressDialog progressDialog;
    SharedPref sharedPreferences;
    APIService service;
    Retrofit retrofit;
    ImageView imageView;
    ArrayList<BannerModel.Login> login;
    ArrayList<BannerModel.Login> logins;

    ProgressBar progressBar;
    ArrayList<BannerModel.Register> registers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.progress_bar);
        progressDialog = new ProgressDialog(SplashActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
        registers= new ArrayList<>();
        logins= new ArrayList<>();
        imageView= findViewById(R.id.image);
       GetBanner();
        dojob();

        sharedPreferences = new SharedPref(this);

    }
    public  void GetBanner()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

       // progressDialog.show();

        Call<BannerModel> userCall = service.BANNER_MODEL();
        userCall.enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                         logins= response.body().getLogin();

                        Log.e("Email", "" +response.body().getLogo().get(0).getImage());
                        // recipientLists= response.body().getMessage();
                      /*  Toast.makeText(SplashActivity.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();
                        Intent i= new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();*/
                        if (Integer.parseInt(response.body().getLogo().get(0).getType())==0) {

                            Picasso.get().load(response.body().getLogo().get(0).getImage()).into(imageView);

                        }
                    else{
                            Glide.with(SplashActivity.this)
                                    .load(response.body().getLogo().get(0).getImage())
                                    .into(imageView);
                        }
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(SplashActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public void dojob() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                int s =Integer.parseInt( sharedPreferences.getStatus());
                if (s == 0) {
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, splashtimeout);
    }


}