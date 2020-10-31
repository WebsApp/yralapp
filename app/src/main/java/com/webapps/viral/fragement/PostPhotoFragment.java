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
import com.webapps.viral.activity.ProfileActivity;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.MyPostAdapter;
import com.webapps.viral.adapter.PhotoAdapter;
import com.webapps.viral.adapter.PostAdapter;
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


public class PostPhotoFragment extends Fragment {

    PhotoAdapter postAdapter;
    RecyclerView rv_category,rv_food,rv_missed,rv_donation;
    ArrayList<PhotoModel>   category;
    ProgressDialog progressDialog;
    Retrofit retrofit;
    APIService service;
    SharedPref sharedPref;
    String  user_id;
    ImageView imageView;
    public  PostPhotoFragment(int id)
    {
        this.id=id;
    }
    int id;
    ShimmerFrameLayout shimmerFrameLayout;
    ImageView error;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_fragment, container, false);
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();

        //DashBoard.toolbar.setTitle("Home");
        sharedPref=new SharedPref(getActivity());
        user_id=sharedPref.getID();
        category = new ArrayList<>();
        rv_food=view.findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(false);
        rv_food.setLayoutManager(new GridLayoutManager(getActivity(),2));
       error=view.findViewById(R.id.error);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        imageView=view.findViewById(R.id.imageview);
        if(id==0)
        {
            user_id=sharedPref.getID();
        }else {
            user_id=""+id;
        }
        GetPost();
        return view;
    }
    public  void GetPost()
    {

        Call<ArrayList<PhotoModel>> userCall = service.GetPhoto_user(user_id);
        userCall.enqueue(new Callback<ArrayList<PhotoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotoModel>> call, Response<ArrayList<PhotoModel>> response) {
                //  progressDialog.dismiss();
                try {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();

                    category=response.body();
                    if(category.size()>0) {
                        postAdapter = new PhotoAdapter(getActivity(), category);
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
            public void onFailure(Call<ArrayList<PhotoModel>> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                error.setVisibility(View.VISIBLE);
                rv_food.setVisibility(View.GONE);
                Log.d("onFailure", t.toString());
            }
        });
    }
}
