package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webapps.viral.R;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.FollowerAdapter;
import com.webapps.viral.adapter.TopAdapter;
import com.webapps.viral.model.Follower;
import com.webapps.viral.model.TopUserModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllFollowerActivity extends AppCompatActivity {


    FollowerAdapter foodAdapter;
    static FloatingActionButton floating_home;
    CategoryAdapter categoryAdapter;
    RecyclerView rv_category,rv_food,rv_missed,rv_donation;
    ArrayList<Follower>   category;
    ProgressDialog progressDialog;
    APIService service;
    Retrofit retrofit;
    TextView txt_allfood,back;
    EditText search;
    String id;
    ImageView error;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_follower);
        error=findViewById(R.id.error);
        back=findViewById(R.id.back);
        back.setText("Follower");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        category = new ArrayList<>();
        rv_food=findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(true);
        rv_food.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        id=getIntent().getExtras().getString("id");
        // progressDialog.show();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        sharedPref=new SharedPref(this);
        GetUSer();
    }
    public  void GetUSer()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<Follower>> userCall = service.GetFollower(id);
        userCall.enqueue(new Callback<ArrayList<Follower>>() {
            @Override
            public void onResponse(Call<ArrayList<Follower>> call, Response<ArrayList<Follower>> response) {
                progressDialog.dismiss();
                try {
                    category.clear();
                    category.addAll(response.body());
                    if(category.size()>0) {
                        // commentAdapter.notifyDataSetChanged();
                        foodAdapter = new FollowerAdapter(AllFollowerActivity.this,category);
                        rv_food.setAdapter(foodAdapter);
                        //commentAdapter.notifyDataSetChanged();
                    }

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Follower>> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                //  Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                error.setVisibility(View.VISIBLE);
                rv_food.setVisibility(View.GONE);
                Log.d("onFailure", t.toString());
            }
        });

    }
}