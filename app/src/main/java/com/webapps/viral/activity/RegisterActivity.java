package com.webapps.viral.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.goodiebag.pinview.Pinview;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;
import com.webapps.viral.R;
import com.webapps.viral.adapter.ImageSilder;
import com.webapps.viral.model.BannerModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    LinearLayout login;
    Button signup;
    ViewPager viewPager;
    ArrayList<BannerModel.Login> logins;
    APIService service;
    Retrofit retrofit;
    ProgressDialog progressDialog;
    EditText et_name,et_email,et_phone,et_password,et_cpassword;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private static final String TAG = "MainActivity";
    private EditText email;
    private String password;
    private ProgressBar progressBar;
    //FaceBook
    RequestQueue queue;
    private static final int RC_SIGN_IN = 234;
    TwitterLoginButton twitterLoginButton;
    //Tag for the logs optional
    SharedPref sharedPref;
    Pinview email_otp;
    LinearLayout id1,otp_l;
    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    String  user_email,user_fullname,otp;
    Button otpsubmit;
    TextView resend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register);
        sharedPref = new SharedPref(this);
        TwitterAuthConfig mTwitterAuthConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(mTwitterAuthConfig)
                .build();
        Twitter.initialize(twitterConfig);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }*/
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
      //  LoginButton loginButton = findViewById(R.id.login_button);

        //Setting the permission that we need to read
        /*loginButton.setReadPermissions("public_profile","email");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Sign in completed
                Log.e("Su   ", "onSuccess: logged in successfully");

                //handling the token for Firebase Auth
                handleFacebookAccessToken(loginResult.getAccessToken());

                //Getting the user information
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        Log.i(TAG, "onCompleted: response: " + response.toString());
                        try {
                            String email = object.getString("email");
                            String name = object.getString("name");
                          //  OTPVerify(name,"0",email);
                            Log.i(TAG, "onCompleted: Email: " + email);
                            Log.i(TAG, "onCompleted: Name: " + name);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "onCompleted: JSON exception");
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
*/
        signup=findViewById(R.id.register);
        login=findViewById(R.id.login);
        email_otp=findViewById(R.id.email_otp);
        viewPager=findViewById(R.id.viewpager);
        viewPager.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        resend=findViewById(R.id.resend);
        otp_l=findViewById(R.id.otp_l);
        id1=findViewById(R.id.id1);
        otpsubmit=findViewById(R.id.otpsubmit);
        et_email=findViewById(R.id.et_email);
        et_name=findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        et_cpassword=findViewById(R.id.et_cpassword);

        et_phone=findViewById(R.id.et_phone);
        GetBanner();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 10)
                {
PhoneCheck(""+s);
                }
            }
        });
        et_email.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(isValidEmail(s))
                {
EmailCheck(""+s);
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int random = new Random().nextInt(999999) + 100000;

                otp=""+random;
                Log.e("OTP",""+otp);
                String url="http://sms.webappssoft.com/app/smsapi/index.php?key=25E15B5643C006&campaign=9864&routeid=7&type=text&contacts="+et_phone.getText().toString()+"&senderid=VIRALL&msg=Viral OTP is "+otp+" \n Please don't share with others";
                queue = Volley.newRequestQueue(RegisterActivity.this);
                StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     //    Toast.makeText(RegisterActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                    }
                });
                queue.add(request);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password=et_password.getText().toString();
               if(et_name.getText().length()==0)
               {
                   et_name.setError("Cann't Empty");

               }/*else if(et_email.getText().length()==0){
                //   et_email.setError("Cann't Empty");
               }
               else if(!isValidEmail(et_email.getText())){
                  // et_email.setError("Email Id not valid");
               }*/
               else if(et_phone.getText().length()==0){
                   et_phone.setError("Cann't Empty");
               }
               else if(et_password.getText().length()==0){
                   et_password.setError("Cann't Empty");
               }
               else if(et_cpassword.getText().length()==0){
                   et_cpassword.setError("Cann't Empty");
               }
               else if(et_password.getText().toString().length()<8)
               {  et_password.setError("8 character required");
               }
               else if(et_cpassword.getText().toString().length()<8)
               { et_cpassword.setError("8 character required");
               }
               else if(!password.equals(et_cpassword.getText().toString())){
                   et_cpassword.setError("Password Mismatch");
               }
               else if(!isValidPassword(et_password.getText().toString()))
               {  et_password.setError("Minimum eight characters, at least one letter, one number and one special character:");
               }
               else if(!isValidPassword(et_cpassword.getText().toString()))
               {  et_cpassword.setError("Minimum eight characters, at least one letter, one number and one special character:");
               }
               else
                {
otp_l.setVisibility(View.VISIBLE);
id1.setVisibility(View.GONE);
                    final int random = new Random().nextInt(999999) + 100000;
                    otp=""+random;
                    Log.e("OTP",""+otp);
                    String url="http://sms.webappssoft.com/app/smsapi/index.php?key=25E15B5643C006&campaign=9864&routeid=7&type=text&contacts="+et_phone.getText().toString()+"&senderid=VIRALL&msg=Viral OTP is "+otp+" \n Please don't share with others";
                    queue = Volley.newRequestQueue(RegisterActivity.this);
                    StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(RegisterActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.toString());
                        }
                    });
                    queue.add(request);

                    String url2="https://webappssoft.com/theutredns/API/api.php?send_email=&email="+et_email.getText().toString()+"&otp"+otp+"";
                    queue = Volley.newRequestQueue(RegisterActivity.this);
                    StringRequest request2 = new StringRequest(Request.Method.GET, url2, new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(RegisterActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.toString());
                        }
                    });
                    queue.add(request2);
                    // Register();
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

                }
                else{
                    sharedPref.setStatus("1");
                  Register();
                }}
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Then we will get the GoogleSignInClient object from GoogleSignIn class
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

       ImageView btn_facebook = findViewById(R.id.fb);
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                    LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("public_profile", "email"));
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("public_profile", "email"));
                }
            }
        });

        twitterLoginButton=findViewById(R.id.twitter_login_button);
        ImageView tw= findViewById(R.id.twitter);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    twitterLoginButton.performClick();

                }catch (Exception ex){}
                    }
        });
        try {
            twitterLoginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                   // Toast.makeText(RegisterActivity.this, "Signed in to twitter successful", Toast.LENGTH_LONG).show();
                    //signInToFirebaseWithTwitterSession(result.data);

                    TwitterSession t=result.data;
                   //  TwitterApiClient t= new TwitterApiClient(t);
                    TwitterSession sess = TwitterCore.getInstance().getSessionManager().getActiveSession();
                   t.getUserName();
                    TwitterApiClient apiClient = new TwitterApiClient(sess);
                    final Call<com.twitter.sdk.android.core.models.User> getUserCall = apiClient
                            .getAccountService()
                            .verifyCredentials(true, false,true);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                com.twitter.sdk.android.core.models.User user = getUserCall.execute().body();
                                String realname = user.name;
                                long realid = user.id;
                                Log.e("Email",""+user.email);
                                Log.e("name",""+user.name);
                                Log.e("ID",""+user.id);
                                Log.e("username",""+t.getUserName());
                               user_email=user.email;
                               user_fullname=user.name;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TWRegister(t.getUserName(),user_fullname,user_email,""+t.getId());
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();


                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(RegisterActivity.this, "Login failed. No internet or No Twitter app found on your phone", Toast.LENGTH_LONG).show();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            });
        }catch (Exception ex){}
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        AccessToken accessToken = AccessToken
                                .getCurrentAccessToken();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject object, GraphResponse response) {
                                        try {
                                            Log.i("output", ""+object.toString());

                                            String fullName,id,firstName,lastName,gender,profilePicUrl,birthday,emailAddress;
                                            id = object.optString("id").toString();
                                            fullName = object.optString("name").toString();
                                            String array[] = fullName.split(" ");
                                            firstName = array[0].toString();
                                            lastName = array[1].toString();
                                            fullName=firstName+"_"+lastName;
                                            // gender = object.optString("gender").toString();
                                            profilePicUrl = "https://graph.facebook.com/" + object.getString("id").toString()
                                                    + "/picture?type=large&return_ssl_resources=1";
                                            //birthday = object.optString("birthday");
                                            try {
                                                emailAddress = object.getString("email").toString();
                                                FBRegister(fullName,firstName,lastName,emailAddress,id);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                emailAddress = "";
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                    @Override
                    public void onCancel() {
                        AccessToken.setCurrentAccessToken(null);
                    }
                    @Override
                    public void onError(FacebookException exception) {
                        AccessToken.setCurrentAccessToken(null);
                        Toast.makeText(RegisterActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        // ShowMsg(exception.getMessage());
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
    private void signInToFirebaseWithTwitterSession(TwitterSession session){
        AuthCredential credential = TwitterAuthProvider.getCredential(session.getAuthToken().token,
                session.getAuthToken().secret);

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegisterActivity.this, "Signed in firebase twitter successful", Toast.LENGTH_LONG).show();
                        if (!task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Auth firebase twitter failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
      }

    public  void GetBanner()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<BannerModel> userCall = service.BANNER_MODEL();
        userCall.enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                        login=null;
                        logins= response.body().getLogin();
                        viewPager.setVisibility(View.VISIBLE);
                        viewPager.setAdapter(new ImageSilder(RegisterActivity.this, logins,response.body().getLogo().get(0).getImage()));
                        Timer timer = new Timer();
                        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void Register()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<RegisterModel> userCall = service.Register(et_name.getText().toString(),et_email.getText().toString(),et_phone.getText().toString(),et_password.getText().toString());
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                        sharedPref.setID(""+response.body().getId());
                        sharedPref.setStatus("1");

                        Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this,UserinfoActivity.class);
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
                //  Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void PhoneCheck(String s)
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

       // progressDialog.show();

        Call<RegisterModel> userCall = service.PhoneCheck(s);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
               // progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {


                    }  else {
                        et_phone.setError(response.body().getMessage());
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //progressDialog.dismiss();
                //hidepDialog();
                //  Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void EmailCheck(String s)
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

       /// progressDialog.show();

        Call<RegisterModel> userCall = service.EmailCheck(s);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
          //      progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                    }
                    else {
                        et_email.setError(response.body().getMessage());
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
            //    progressDialog.dismiss();
                //hidepDialog();
              //  Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void FBRegister(String name,String fname,String lname,String email,String id)
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<RegisterModel> userCall = service.FBRegister(name,fname,lname,email,id);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                        sharedPref.setID(""+response.body().getId());
                        sharedPref.setStatus("1");
                        Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this,UserinfoActivity.class);
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
                //Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void TWRegister(String name,String fname,String email,String id)
    {
        String array[] = fname.split(" ");
        String   firstName = array[0].toString();
        String  lastName = array[1].toString();

        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        progressDialog.show();
        Call<RegisterModel> userCall = service.TWRegister(name,firstName,lastName,email,id);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().getStatus()==1) {
                        sharedPref.setID(""+response.body().getId());
                        sharedPref.setStatus("1");
                        Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this,UserinfoActivity.class);
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
                //Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void GoogleRegister(String name,String phone,String email,String id)
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<RegisterModel> userCall = service.GoogleRegister(name,phone,email,id);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                        sharedPref.setID(""+response.body().getId());
                        sharedPref.setStatus("1");
                        Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this,UserinfoActivity.class);
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
                //Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();
                Log.d("onFailure", t.toString());
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Log.i(TAG, "onStart: Someone logged in <3");
        } else {
            Log.i(TAG, "onStart: No one logged in :/");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);

            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }

        super.onActivityResult(requestCode, resultCode, data);

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void onFacebookLogInClicked( View view ){
        LoginManager
                .getInstance()
                .logInWithReadPermissions(
                        this,
                        Arrays.asList("public_profile", "user_friends", "email")
                );
    }
   /* private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            Log.i(TAG, "onComplete: login completed with user: " + user.getDisplayName());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
*/
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Now using firebase we are signing in the user here
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                           GoogleRegister(user.getDisplayName(),user.getPhoneNumber(),user.getEmail(),user.getUid());
                           // Toast.makeText(RegisterActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    //this method is called on click
    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            RegisterActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < logins.size() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
