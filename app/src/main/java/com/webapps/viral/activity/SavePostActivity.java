package com.webapps.viral.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webapps.viral.R;
import com.webapps.viral.adapter.MyPostAdapter;
import com.webapps.viral.adapter.PhotoAdapter;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SavePostActivity extends AppCompatActivity {


    int id;
    TextView back;
     PhotoAdapter foodAdapter;
    static FloatingActionButton floating_home;
    MyPostAdapter postAdapter;
    RecyclerView rv_category,rv_food,rv_missed,rv_donation;
    ArrayList<PostModel>   category;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    APIService service;
    ImageView imageView;
    String user_id;
    SharedPref sharedPref;
    ImageView error;
    ShimmerFrameLayout shimmerFrameLayout;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_save_post); //DashBoard.toolbar.setTitle("Home");
        shimmerFrameLayout=findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();

        sharedPref= new SharedPref(this);
user_id=sharedPref.getID();
        back=findViewById(R.id.back);
        category = new ArrayList<>();
        rv_food=findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(false);
        rv_food.setLayoutManager(new GridLayoutManager(this,1));
       error=findViewById(R.id.error);
        imageView=findViewById(R.id.imageview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        // progressDialog.show();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        user_id=sharedPref.getID();
            GetUSer();
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

    }
    public  void GetUSer()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<PostModel>> userCall = service.GetSavePost(user_id);
        userCall.enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                progressDialog.dismiss();
                try {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();

                    category.clear();
                    category=response.body();
                    if(category.size()>0) {
                        postAdapter = new MyPostAdapter(SavePostActivity.this, category);
                        rv_food.setAdapter(postAdapter);
                    }else {
                        rv_food.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                   }catch (Exception ex)
                {
                    rv_food.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmerAnimation();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                progressDialog.dismiss();
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmerAnimation();

                //hidepDialog();
                Toast.makeText(SavePostActivity.this,"No Data Available",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }

}
