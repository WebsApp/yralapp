package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.webapps.viral.R;
import com.webapps.viral.model.OTPModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePhoneActivity extends AppCompatActivity {

    TextView back;
    Button submit,get_otp;
    Pinview pinview;
    EditText email,newemail;
    Button confirm_otp;
    ProgressDialog progressDialog;
    RequestBody requestBody;
    Retrofit retrofit;
    APIService service;
    int otp;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        back=findViewById(R.id.back);
        sharedPref = new SharedPref(this);
        submit=findViewById(R.id.submit);
        get_otp=findViewById(R.id.login);
        email=findViewById(R.id.email);
        newemail=findViewById(R.id.newemail);
        confirm_otp=findViewById(R.id.confirm_otp);
        pinview=findViewById(R.id.pinview);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().length()==0)
                {
                    email.setError("Cann't Empty");
                }
                else {
                    Login();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newemail.getText().length()==0)
                {
                    newemail.setError("Cann't Empty");
                }
                else if(newemail.getText().length()<10)
                {
                    newemail.setError("Not valid Phone no");
                }
                else{

                    UpdateEmail();


                }
            }
        });
        confirm_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp==Integer.parseInt(pinview.getValue()))
                {
                    pinview.setVisibility(View.GONE);
                    newemail.setVisibility(View.VISIBLE);
                    confirm_otp.setVisibility(View.GONE);
                    get_otp.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    email.setVisibility(View.GONE);
                }
            }
        });
    }
    public  void UpdateEmail()
    {
        progressDialog.show();
        Call<OTPModel> userCall = service.PhoneUpdate(sharedPref.getID(),email.getText().toString(),newemail.getText().toString());
        userCall.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                        Toast.makeText(ChangePhoneActivity.this,"Phone no Changed",Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(ChangePhoneActivity.this,ProfileActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(ChangePhoneActivity.this,"Phone no is not available",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<OTPModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(ChangePhoneActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                Log.d("onFailure", t.toString());
            }
        });
    }

    public  void Login()
    {
        progressDialog.show();
        Call<OTPModel> userCall = service.phone_change_otp(sharedPref.getID(),email.getText().toString());
        userCall.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getOtp());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                        Toast.makeText(ChangePhoneActivity.this,"Otp send to your Phone No",Toast.LENGTH_LONG).show();
                        otp=response.body().getOtp();
                        //  Toast.makeText(OTPLoginActivity.this,""+response.body().getData().,Toast.LENGTH_LONG).show();
                        pinview.setVisibility(View.VISIBLE);
                        newemail.setVisibility(View.GONE);
                        confirm_otp.setVisibility(View.VISIBLE);
                        get_otp.setVisibility(View.GONE);
                        submit.setVisibility(View.GONE);
                        email.setVisibility(View.GONE);
                    }
                    else {
                        Toast.makeText(ChangePhoneActivity.this,"Phone No is not available",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<OTPModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(ChangePhoneActivity.this,"Something went wrong",Toast.LENGTH_LONG).show();
                Log.d("onFailure", t.toString());
            }
        });
    }
}
