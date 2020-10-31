package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.webapps.viral.R;
import com.webapps.viral.model.OTPModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OTPLoginActivity extends AppCompatActivity {

    LinearLayout id1,id2;
    Button submit,otpsubmit;
    EditText et_email;
    Pinview email_otp;
    ProgressDialog progressDialog;
    RequestBody requestBody;
    Retrofit retrofit;
    APIService service;
    String otp,id;
    LinearLayout back;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otplogin);
        submit=findViewById(R.id.submit);
        sharedPref= new SharedPref(this);
        otpsubmit=findViewById(R.id.otpsubmit);
        email_otp=findViewById(R.id.email_otp);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        id1= findViewById(R.id.id1);
        id2=findViewById(R.id.id2);
        id2.setVisibility(View.GONE);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        et_email=findViewById(R.id.edit_phone);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_email.getText().toString())){

                    et_email.setError("Please Enter Phone No");
                    et_email.requestFocus();
                    return;
                }
                else if (et_email.getText().toString().length()<10){

                    et_email.setError("Please Enter Valid Phone");
                    et_email.requestFocus();
                    return;
                }
                else{
                    Login();
                }

            }
        });
        otpsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_otp.getValue().length()==0)
                {
                  //  email_otp.setError("Cann't Empty");
                }
                else if(!otp.equals(email_otp.getValue().toString()))
                {
                   // email_otp.setError("OTP Missmatch");
                    Toast.makeText(OTPLoginActivity.this,"OTP not matched",Toast.LENGTH_LONG).show();

                }
                else{
                    sharedPref.setStatus("1");
                    Intent intent=new Intent(OTPLoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }}
        });
    }

    public  void Login()
    {
        progressDialog.show();
        Call<OTPModel> userCall = service.get_otp_user(et_email.getText().toString());
        userCall.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                        Toast.makeText(OTPLoginActivity.this,"Otp send to your phone no",Toast.LENGTH_LONG).show();
                      //  Toast.makeText(OTPLoginActivity.this,""+response.body().getData().,Toast.LENGTH_LONG).show();

                        id1.setVisibility(View.GONE);
                        id2.setVisibility(View.VISIBLE);
                        otp=""+response.body().getOtp();
                        Log.e("otp",""+otp+" "+id);
                        sharedPref.setID(response.body().getData().getUserId());

                        sharedPref.setPic(""+response.body().getData().getUserPicture());

                    }
                    else {
                        Toast.makeText(OTPLoginActivity.this,"Phone Number not available",Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<OTPModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(OTPLoginActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
}
