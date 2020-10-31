package com.webapps.viral.fragement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.webapps.viral.R;
import com.webapps.viral.activity.AddStoryActivity;
import com.webapps.viral.activity.FogotPasswordActivity;
import com.webapps.viral.activity.NotificationActivity;
import com.webapps.viral.activity.SearchActivity;
import com.webapps.viral.activity.SplashActivity;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.PostAdapter;
import com.webapps.viral.adapter.ViewPagerAdapter;
import com.webapps.viral.model.CategoryModel;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.MovableFloatingButton;
import com.webapps.viral.utils.RetrofitClient;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeMainFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    ImageView search,noti;
    Retrofit retrofit;
    APIService service;
    ProgressDialog progressDialog;
    ShimmerFrameLayout shimmerFrameLayout;
    MovableFloatingButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_main_fragment, container, false);
        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewPager);
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();
        search=view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SearchActivity.class);
                startActivity(i);
            }
        });
        noti=view.findViewById(R.id.noti);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), NotificationActivity.class);
                startActivity(i);
            }
        });
        floatingActionButton= view.findViewById(R.id.addstory);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddStoryActivity.class);
                getActivity().startActivity(intent);
            }
        });
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait...");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        Login();
        return view;
    }
    public  void Login()
    {
        Call<CategoryModel> userCall = service.GetCategory();
        userCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmerAnimation();
                        // recipientLists= response.body().getMessage();
                        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),response.body().getData());
                        viewPager.setAdapter(viewPagerAdapter);
                        viewPager.setOffscreenPageLimit(5);
                        tabLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                tabLayout.setupWithViewPager(viewPager);
                            }
                        });
                    }
                    else {
                        Toast.makeText(getActivity(),""+response.body().getMsg(),Toast.LENGTH_LONG).show();
                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
               // Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                Log.d("onFailure", t.toString());
            }
        });
    }
}
