package com.webapps.viral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.goodiebag.pinview.Pinview;
import com.google.android.material.snackbar.Snackbar;
import com.webapps.viral.R;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class FogotPasswordActivity extends AppCompatActivity {

    LinearLayout id1,id2;
    Button submit,otpsubmit;
    EditText et_email;
    Pinview email_otp;
    ProgressDialog progressDialog;
    RequestBody requestBody;
    Retrofit retrofit;
    APIService service;
    String otp,id;
    TextView resend;
    RelativeLayout root;
    LinearLayout back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);
        submit=findViewById(R.id.submit);
        otpsubmit=findViewById(R.id.otpsubmit);
        email_otp=findViewById(R.id.email_otp);
        back=findViewById(R.id.back);
        id1= findViewById(R.id.id1);
        id2=findViewById(R.id.id2);
        id2.setVisibility(View.GONE);
        resend=findViewById(R.id.resend);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        et_email=findViewById(R.id.edit_phone);
        root=findViewById(R.id.root);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean digitsOnly = TextUtils.isDigitsOnly(et_email.getText());


                if (!digitsOnly) {
                    if (TextUtils.isEmpty(et_email.getText().toString())){

                        et_email.setError("Please Enter Phone No or Email");
                        et_email.requestFocus();
                        return;
                    }
                    else if (!isValidEmail(et_email.getText().toString())) {
                        et_email.setError("Please Enter Valid Email");
                        et_email.requestFocus();
                        return;
                    }
                    else{
                        Login();
                    }
                }
                else if(digitsOnly){
                    if (TextUtils.isEmpty(et_email.getText().toString())){
                        et_email.setError("Please Enter Phone No or Email");
                        et_email.requestFocus();
                        return;
                    }
                    if (et_email.getText().toString().length() < 10) {
                        et_email.setError("Please Enter Valid Phone");
                        et_email.requestFocus();
                        return;
                    }
                    else{
                        Login();
                    }
                }



            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        otpsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_otp.getValue().toString().length()==0)
                {
                Snackbar snackbar=   Snackbar.make(root,"Cann't Empty",Snackbar.LENGTH_LONG);
                snackbar.show();
                  //  email_otp.setError("Cann't Empty");
                }
                else if(!otp.equals(email_otp.getValue().toString()))
                {
                   // email_otp.setError("OTP Mismatch");
                }
                else{
                    Intent intent=new Intent(FogotPasswordActivity.this,UpdatePassword.class);
                    intent.putExtra("id",""+id);
                    startActivity(intent);
                    finish();
                }}
        });
    }

    public  void Login()
    {
        progressDialog.show();
        Call<RegisterModel> userCall = service.ForgetPassword(et_email.getText().toString());
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                       // Toast.makeText(FogotPasswordActivity.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();

                        Snackbar snackbar=   Snackbar.make(root,""+response.body().getMessage(),Snackbar.LENGTH_LONG);
                        snackbar.show();id1.setVisibility(View.GONE);
                        id2.setVisibility(View.VISIBLE);
                        otp=""+response.body().getOtp();
                        id=""+response.body().getId();
                        Log.e("otp",""+otp+" "+id);
                    }
                    else {
                        Toast.makeText(FogotPasswordActivity.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
              //  Toast.makeText(FogotPasswordActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
