package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.webapps.viral.R;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.CommentAdapter;
import com.webapps.viral.adapter.NotificationAdapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.CommentModel;
import com.webapps.viral.model.NotificationModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<NotificationModel> childModels;
    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    Integer[] image ={R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
    TextView back;
    APIService service;
    Retrofit retrofit;
    ProgressDialog progressDialog;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        childModels= new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
sharedPref=new SharedPref(this);
        progressDialog = new ProgressDialog(NotificationActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        GetComment();
    }
    public  void GetComment()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<NotificationModel>> userCall = service.Getnotification(sharedPref.getID());
        userCall.enqueue(new Callback<ArrayList<NotificationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<NotificationModel>> call, Response<ArrayList<NotificationModel>> response) {
                progressDialog.dismiss();
                try {
                    childModels.clear();

                    childModels.addAll(response.body());
                    if(childModels.size()>0) {
                        // commentAdapter.notifyDataSetChanged();
                        notificationAdapter = new NotificationAdapter(NotificationActivity.this,childModels);

                        recyclerView.setAdapter(notificationAdapter);
                        //commentAdapter.notifyDataSetChanged();
                    }}catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<NotificationModel>> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                //Toast.makeText(NotificationActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
}
