package com.webapps.viral.fragement;

import android.app.ProgressDialog;
import android.media.Image;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webapps.viral.R;
import com.webapps.viral.activity.PostdetailsActivity;
import com.webapps.viral.activity.SearchActivity;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.CommentAdapter;
import com.webapps.viral.adapter.PostAdapter;
import com.webapps.viral.adapter.TopAdapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.CommentModel;
import com.webapps.viral.model.TopUserModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.ListenFromActivity;
import com.webapps.viral.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class TopFragment extends Fragment  {

     TopAdapter foodAdapter;
     static FloatingActionButton floating_home;
     CategoryAdapter categoryAdapter;
     RecyclerView rv_category,rv_food,rv_missed,rv_donation;
     ArrayList<TopUserModel>   category;

    ProgressDialog progressDialog;
    APIService service;
     Retrofit retrofit;
    Integer[] image ={R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
   // Integer[] categor ={R.drawable.ic_t_shirt,R.drawable.ic_food_,R.drawable.ic_breakfast,R.drawable.ic_fish};
    TextView txt_allfood;
    EditText search;
    String query;
    ImageView error;
      public TopFragment(String s)
       {
           query=s;
       }

       ShimmerFrameLayout shimmerFrameLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_fragment, container, false);
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();

        //DashBoard.toolbar.setTitle("Home");
       error=view.findViewById(R.id.error);
          category = new ArrayList<>();
        rv_food=view.findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(true);
        rv_food.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
       // progressDialog.show();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        GetUSer();
        Log.e("Query",""+query);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
    public  void beginSearch(String s)
    {
        Log.e("Str",""+s);
        if (s.equalsIgnoreCase("")) {
         GetUSer();
        }
        else {
            foodAdapter.getFilter().filter(s);
        }
    }
    public  void GetUSer()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<TopUserModel>> userCall = service.GetUser();
        userCall.enqueue(new Callback<ArrayList<TopUserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TopUserModel>> call, Response<ArrayList<TopUserModel>> response) {
                progressDialog.dismiss();
                try {
                    category.clear();
                    category.addAll(response.body());
                    if(category.size()>0) {
                        // commentAdapter.notifyDataSetChanged();
                        foodAdapter = new TopAdapter(getActivity(),category);
                        rv_food.setAdapter(foodAdapter);
                        //commentAdapter.notifyDataSetChanged();
                    }
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<TopUserModel>> call, Throwable t) {
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
