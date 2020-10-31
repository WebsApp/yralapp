package com.webapps.viral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.webapps.viral.R;
import com.webapps.viral.adapter.ImageSilder;
import com.webapps.viral.chat.ui.MainActivity1;
import com.webapps.viral.data.SharedPreferenceHelper;
import com.webapps.viral.data.StaticConfig;
import com.webapps.viral.model.BannerModel;
import com.webapps.viral.model.LoginModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.model.User;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;



import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

public class LoginActivity extends AppCompatActivity {

    LinearLayout signup,forget;
    Button login;
    ViewPager viewPager;
    ArrayList<BannerModel.Login> logins;
    APIService service;
    Retrofit retrofit;
    ProgressDialog progressDialog;
    EditText email,password;
    ImageView iv_phone;
    SharedPref sharedPref;
    private AccessToken accessToken;
    ImageView btn_facebook;
    CallbackManager callbackManager;
    private FirebaseAuth mFirebaseAuth;
    private static final String TAG = "MainActivity";
    private AuthUtils authUtils;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private boolean firstTimeAccess;
    //FaceBook
    private static final int RC_SIGN_IN = 234;

    //Tag for the logs optional

    //creating a GoogleSignInClient object
    GoogleSignInClient mGoogleSignInClient;
    TwitterLoginButton twitterLoginButton;
    String  user_email,user_fullname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);
        forget=findViewById(R.id.forget);
       // logins = (ArrayList<LoginSer>) getIntent().getSerializableExtra("login");
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        sharedPref = new SharedPref(this);
        iv_phone=findViewById(R.id.iv_phone);
        viewPager=findViewById(R.id.viewpager);
        viewPager.setVisibility(View.INVISIBLE);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        TwitterAuthConfig mTwitterAuthConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(mTwitterAuthConfig)
                .build();
        Twitter.initialize(twitterConfig);
        twitterLoginButton=findViewById(R.id.twitter_login_button);
        ImageView tw= findViewById(R.id.twitter);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twitterLoginButton.performClick();
            }
        });
        GetBanner();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,FogotPasswordActivity.class);
                startActivity(i);
            }
        });
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,OTPLoginActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean digitsOnly = TextUtils.isDigitsOnly(email.getText());


               if (!digitsOnly) {
                    if(email.getText().toString().length()==0)
                    {
                        email.setError("Cann't Empty");
                    }
                 else if (!isValidEmail(email.getText().toString())) {
                     email.setError("Please Enter Valid Email");
                     email.requestFocus();
                 }
                    else if(password.getText().toString().length()==0)
                    {  password.setError("Cann't Empty");
                    }
                    else if(!isValidPassword(password.getText().toString()))
                    {  password.setError("Minimum eight characters, at least one letter, one number and one special character:");
                    }
                    else{
                        GetLogin(email.getText().toString(),password.getText().toString());
                    }
               }
               else if(digitsOnly){
                   if(email.getText().toString().length()==0)
                   {
                       email.setError("Cann't Empty");
                   }
                   else if (email.getText().toString().length() < 10) {
                            email.setError("Please Enter Valid Phone");
                            email.requestFocus();
                        }
                     else if(password.getText().toString().length()==0)
                     {  password.setError("Cann't Empty");
                     }
                     else if(!isValidPassword(password.getText().toString()))
                     {  password.setError("Minimum eight characters, at least one letter, one number and one special character:");
                     }
                     else{
                         GetLogin(email.getText().toString(),password.getText().toString());
                     }
                 }

            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager=CallbackManager.Factory.create();
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        btn_facebook = findViewById(R.id.fb);
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null) {
                      LoginManager.getInstance().logOut();
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
                }
            }
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
                                        TWLogin(t.getUserName(),user_fullname,user_email,""+t.getId());
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
                    Toast.makeText(LoginActivity.this, "Login failed. No internet or No Twitter app found on your phone", Toast.LENGTH_LONG).show();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                }
            });
        }catch (Exception ex){}

        LoginManager.getInstance().registerCallback(callbackManager,
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
                                           // gender = object.optString("gender").toString();
                                            profilePicUrl = "https://graph.facebook.com/" + object.getString("id").toString()
                                                    + "/picture?type=large&return_ssl_resources=1";
                                            //birthday = object.optString("birthday");
                                            try {
                                                emailAddress ="";/// object.getString("email").toString();


                                                    FBLogin(fullName, firstName,lastName,emailAddress, id);

                                            } catch (Exception e) {
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
                         Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
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
    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        authUtils = new AuthUtils();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    StaticConfig.UID = user.getUid();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (firstTimeAccess) {
                        //startActivity(new Intent(com.webapps.viral.chat.ui.LoginActivity.this, MainActivity1.class));
                        //com.webapps.viral.chat.ui.LoginActivity.this.finish();
                    }
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                firstTimeAccess = false;
            }
        };
      //  waitingDialog = new LovelyProgressDialog(this).setCancelable(false);
    }
    private void signIn() {
        //getting the google signin intent
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public  void TWLogin(String name,String fname,String email,String id)
    {
        String array[] = fname.split(" ");
        String   firstName = array[0].toString();
        String  lastName = array[1].toString();

        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        progressDialog.show();
        Call<LoginModel> userCall = service.TWLogin(name,firstName,lastName,email,id);
        userCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().getStatus()==1) {
                        sharedPref.setID(response.body().getData().get(0).getUserId());
                        sharedPref.setStatus("1");
                        sharedPref.setPic(""+response.body().getData().get(0).getUserPicture());

                        //Toast.makeText(LoginActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                //Toast.makeText(RegisterActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void signInToFirebaseWithTwitterSession(TwitterSession session){
        AuthCredential credential = TwitterAuthProvider.getCredential(session.getAuthToken().token,
                session.getAuthToken().secret);

        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(LoginActivity.this, "Signed in firebase twitter successful", Toast.LENGTH_LONG).show();
                        if (!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Auth firebase twitter failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
       AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
       mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            GoogleLogin(user.getDisplayName(),user.getPhoneNumber(),user.getEmail(),user.getUid());
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);

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
    public  void FBLogin(String name,String fname,String lname,String email,String id)
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        progressDialog.show();
        Call<LoginModel> userCall = service.FBLogin(name,fname,lname,email,id);
        userCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                try {
                    if (response.body().getStatus()==1) {
                        sharedPref.setID(response.body().getData().get(0).getUserId());
                        sharedPref.setStatus("1");
                        sharedPref.setPic(""+response.body().getData().get(0).getUserPicture());

                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
               // Toast.makeText(LoginActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });
    }
    public  void GoogleLogin(String name,String phone,String email,String id)
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        progressDialog.show();
        Call<LoginModel> userCall = service.GoogleLogin(name,phone,email,id);
        userCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                        sharedPref.setID(response.body().getData().get(0).getUserId());
                        sharedPref.setStatus("1");
                        sharedPref.setPic(""+response.body().getData().get(0).getUserPicture());
                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                // Toast.makeText(LoginActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void GetLogin(String email,String password)
    {
        progressDialog.show();
        Call<LoginModel> userCall = service.userLogin(email,password);
        userCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                try {

                    if (response.body().getStatus()==1) {
                       sharedPref.setID(response.body().getData().get(0).getUserId());
                        sharedPref.setStatus("1");
                         sharedPref.setPic(""+response.body().getData().get(0).getUserPicture());
                        Intent i = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Email Id or password wrong",Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
              //  Toast.makeText(LoginActivity.this,"Server Error",Toast.LENGTH_LONG).show();
                Toast.makeText(LoginActivity.this,"Email Id or password wrong",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void GetBanner()
    {

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
                        viewPager.setAdapter(new ImageSilder(LoginActivity.this, logins,response.body().getLogo().get(0).getImage()));
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
                Toast.makeText(LoginActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            LoginActivity.this.runOnUiThread(new Runnable() {
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

    class AuthUtils {
        /**
         * Action register
         *
         * @param email
         * @param password
         */
        void createUser(String email, String password) {
           /* waitingDialog.setIcon(R.drawable.ic_add_friend)
                    .setTitle("Registering....")
                    .setTopColorRes(R.color.colorPrimary)
                    .show();*/
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                       //     waitingDialog.dismiss();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                new LovelyInfoDialog(LoginActivity.this) {
                                    @Override
                                    public LovelyInfoDialog setConfirmButtonText(String text) {
                                        findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dismiss();
                                            }
                                        });
                                        return super.setConfirmButtonText(text);
                                    }
                                }
                                        .setTopColorRes(R.color.colorAccent)
                                        .setIcon(R.drawable.ic_add_friend)
                                        .setTitle("Register false")
                                        .setMessage("Email exist or weak password!")
                                        .setConfirmButtonText("ok")
                                        .setCancelable(false)
                                        .show();
                            } else {
                                initNewUserInfo();
                               // Toast.makeText(LoginActivity.this, "Register and Login success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, MainActivity1.class));
                                LoginActivity.this.finish();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                          //  waitingDialog.dismiss();
                        }
                    })
            ;
        }
        /**
         * Action Login
         *
         * @param email
         * @param password
         */
        void signIn(String email, String password) {
           /* waitingDialog.setIcon(R.drawable.ic_person_low)
                    .setTitle("Login....")
                    .setTopColorRes(R.color.colorPrimary)
                    .show();*/
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                         //   waitingDialog.dismiss();
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                new LovelyInfoDialog(LoginActivity.this) {
                                    @Override
                                    public LovelyInfoDialog setConfirmButtonText(String text) {
                                        findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dismiss();
                                            }
                                        });
                                        return super.setConfirmButtonText(text);
                                    }
                                }
                                        .setTopColorRes(R.color.colorAccent)
                                        .setIcon(R.drawable.ic_person_low)
                                        .setTitle("Login false")
                                        .setMessage("Email not exist or wrong password!")
                                        .setCancelable(false)
                                        .setConfirmButtonText("Ok")
                                        .show();
                            } else {
                                saveUserInfo();
                              //  startActivity(new Intent(LoginActivity.this, MainActivity1.class));
                              //LoginActivity.this.finish();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                           // waitingDialog.dismiss();
                        }
                    });
        }
        /**
         * Action reset password
         *
         * @param email
         */
        void resetPassword(final String email) {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            new LovelyInfoDialog(LoginActivity.this) {
                                @Override
                                public LovelyInfoDialog setConfirmButtonText(String text) {
                                    findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dismiss();
                                        }
                                    });
                                    return super.setConfirmButtonText(text);
                                }
                            }
                                    .setTopColorRes(R.color.colorPrimary)
                                    .setIcon(R.drawable.ic_pass_reset)
                                    .setTitle("Password Recovery")
                                    .setMessage("Sent email to " + email)
                                    .setConfirmButtonText("Ok")
                                    .show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new LovelyInfoDialog(LoginActivity.this) {
                                @Override
                                public LovelyInfoDialog setConfirmButtonText(String text) {
                                    findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dismiss();
                                        }
                                    });
                                    return super.setConfirmButtonText(text);
                                }
                            }
                                    .setTopColorRes(R.color.colorAccent)
                                    .setIcon(R.drawable.ic_pass_reset)
                                    .setTitle("False")
                                    .setMessage("False to sent email to " + email)
                                    .setConfirmButtonText("Ok")
                                    .show();
                        }
                    });
        }

        void saveUserInfo() {
            FirebaseDatabase.getInstance().getReference().child("user/" + StaticConfig.UID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  //  waitingDialog.dismiss();
                    HashMap hashUser = (HashMap) dataSnapshot.getValue();
                    User userInfo = new User();
                    userInfo.name = (String) hashUser.get("name");
                    userInfo.email = (String) hashUser.get("email");
                    userInfo.avata = (String) hashUser.get("avata");
                    SharedPreferenceHelper.getInstance(LoginActivity.this).saveUserInfo(userInfo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        void initNewUserInfo() {
            User newUser = new User();
            newUser.email = user.getEmail();
            newUser.name = user.getEmail().substring(0, user.getEmail().indexOf("@"));
            newUser.avata = StaticConfig.STR_DEFAULT_BASE64;
            FirebaseDatabase.getInstance().getReference().child("user/" + user.getUid()).setValue(newUser);
        }
    }
}
