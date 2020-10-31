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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webapps.viral.R;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.PhotoAdapter;
import com.webapps.viral.adapter.TopAdapter;
import com.webapps.viral.adapter.VideoAdapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.PhotoModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class VideoFragment extends Fragment {

     VideoAdapter foodAdapter;
    static FloatingActionButton floating_home;
    CategoryAdapter categoryAdapter;
    RecyclerView rv_category,rv_food,rv_missed,rv_donation;
    ArrayList<ChildModel> foodlist,mislist;
    ArrayList<PhotoModel>   category;
Retrofit retrofit;
APIService service;
ProgressDialog progressDialog;
    Integer[] image ={R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
   // Integer[] categor ={R.drawable.ic_t_shirt,R.drawable.ic_food_,R.drawable.ic_breakfast,R.drawable.ic_fish};
    TextView txt_allfood;
    EditText search;
String query;
ImageView error;
ShimmerFrameLayout shimmerFrameLayout;
    public  VideoFragment(String s)
    {
        query=s;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.top_fragment, container, false);

        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();

        //DashBoard.toolbar.setTitle("Home");
error=view.findViewById(R.id.error);
         foodlist = new ArrayList<>();
        category = new ArrayList<>();
        rv_food=view.findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(true);
        rv_food.setLayoutManager(new GridLayoutManager(getActivity(),2));

         /*  for (int i = 0; i < categor.length; i++) {
            ChildModel objItem = new ChildModel();
            objItem.setImage(categor[i]);
            objItem.setName("Title "+i);
            //objItem.setCategoryImageUrl(objJson2cat.getString(Constant.SUBCATEGORY_IMAGE));
            category.add(objItem);
        }*/

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        // progressDialog.show();
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        GetUSer();
        return view;
    }
    public  void beginSearch(String s)
    {
        Log.e("Str",""+s);
        if (s.equalsIgnoreCase("")) {
          GetUSer();
        }
        else {
            GetUSer(s);
          //  foodAdapter.getFilter().filter(s);
        }
    }
    public  void GetUSer()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<PhotoModel>> userCall = service.GetVideo();
        userCall.enqueue(new Callback<ArrayList<PhotoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotoModel>> call, Response<ArrayList<PhotoModel>> response) {
                progressDialog.dismiss();
                try {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();

                    category.clear();

                    category.addAll(response.body());
                    if(category.size()>0) {
                        // commentAdapter.notifyDataSetChanged();
                        foodAdapter = new VideoAdapter(getActivity(),category);

                        rv_food.setAdapter(foodAdapter);
                        //commentAdapter.notifyDataSetChanged();
                    }}catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PhotoModel>> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
              //  Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                error.setVisibility(View.VISIBLE);
                rv_food.setVisibility(View.GONE);
                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void GetUSer(String  s)
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        progressDialog.show();

        Call<ArrayList<PhotoModel>> userCall = service.GetVideo(s);
        userCall.enqueue(new Callback<ArrayList<PhotoModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotoModel>> call, Response<ArrayList<PhotoModel>> response) {
                progressDialog.dismiss();
                try {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();

                    category.clear();

                    category.addAll(response.body());
                    if(category.size()>0) {
                        // commentAdapter.notifyDataSetChanged();
                        foodAdapter = new VideoAdapter(getActivity(),category);

                        rv_food.setAdapter(foodAdapter);
                        //commentAdapter.notifyDataSetChanged();
                    }}catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PhotoModel>> call, Throwable t) {
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
