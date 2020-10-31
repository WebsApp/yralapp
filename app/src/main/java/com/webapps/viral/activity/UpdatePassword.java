package com.webapps.viral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.webapps.viral.R;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdatePassword extends AppCompatActivity {

    EditText et_password,et_confirm;
    Button register;
    String uid;
    APIService service;
    Retrofit retrofit;
    String pass,cpass;
    RequestBody requestBody;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        et_confirm=findViewById(R.id.password);
        et_password=findViewById(R.id.re_password);
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        register=findViewById(R.id.register);
        uid=getIntent().getExtras().getString("id");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cpass=et_confirm.getText().toString();
                pass=et_password.getText().toString();
                if (TextUtils.isEmpty(et_password.getText().toString())){

                    et_password.setError("Please Enter Password");
                    et_password.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(et_confirm.getText().toString())){

                    et_confirm.setError("Please Enter Confirm Password");
                    et_confirm.requestFocus();
                    return;
                }

                else if (!pass.equals(cpass)){

                    et_confirm.setError("Password Mismatch!");
                    et_confirm.requestFocus();
                    return;
                }
                else if(!isValidPassword(et_password.getText().toString()))
                {  et_password.setError("Minimum eight characters, at least one letter, one number and one special character:");
                return;
                }
                else if(!isValidPassword(et_confirm.getText().toString()))
                {  et_confirm.setError("Minimum eight characters, at least one letter, one number and one special character:");
               return;
                }
                else{
                    Register();
                }
            }
        });
    }
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
    public  void Register()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<RegisterModel> userCall = service.UpdatePassword(et_password.getText().toString(),uid);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                        Toast.makeText(UpdatePassword.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();
                        Intent i= new Intent(UpdatePassword.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
               // Toast.makeText(UpdatePassword.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
}
