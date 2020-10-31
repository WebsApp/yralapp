package com.webapps.viral.fragement;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webapps.viral.R;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.MyPostAdapter;
import com.webapps.viral.adapter.PhotoAdapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.PhotoModel;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PostStoryFragment extends Fragment {

    public  PostStoryFragment(int id)
    {
        this.id=id;
    }
    int id;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_fragment, container, false);
        //DashBoard.toolbar.setTitle("Home");
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();

        sharedPref= new SharedPref(getActivity());
user_id=sharedPref.getID();
        category = new ArrayList<>();
        rv_food=view.findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(false);
        rv_food.setLayoutManager(new GridLayoutManager(getActivity(),1));
       error=view.findViewById(R.id.error);
        imageView=view.findViewById(R.id.imageview);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        // progressDialog.show();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        if(id==0)
        {
            user_id=sharedPref.getID();
            GetAllPost();
        }else {
            user_id=""+id;
            GetUSer();
        }
        return view;
    }
    public  void GetUSer()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<PostModel>> userCall = service.GetMyPost(user_id,user_id);
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
                        postAdapter = new MyPostAdapter(getActivity(), category);
                        rv_food.setAdapter(postAdapter);
                    }else {
                        rv_food.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                   }catch (Exception ex)
                {
                    rv_food.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void GetAllPost()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<PostModel>> userCall = service.GetMyAllPost(user_id,user_id);
        userCall.enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                progressDialog.dismiss();
                try {
                    category.clear();
                    category=response.body();
                    if(category.size()>0) {
                        postAdapter = new MyPostAdapter(getActivity(), category);
                        rv_food.setAdapter(postAdapter);
                    }else {
                        rv_food.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }catch (Exception ex)
                {
                    rv_food.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                //Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                error.setVisibility(View.VISIBLE);
                rv_food.setVisibility(View.GONE);
                Log.d("onFailure", t.toString());
            }
        });

    }
}
