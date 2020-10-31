package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.webapps.viral.R;
import com.webapps.viral.RecyclerItemClickListener;
import com.webapps.viral.adapter.Adapter;
import com.webapps.viral.adapter.IAdapter;
import com.webapps.viral.adapter.InterestAdapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.InterestModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InterestActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<InterestModel.Datum> childModels,selectedlist;
    boolean isMultiSelect=true;
    Retrofit retrofit;
    APIService service;
    Button login;
    InterestAdapter adapter;
    Integer image[]={R.drawable.bike,R.drawable.gardening,R.drawable.hiking,R.drawable.tech};
    String name[]={"Bikes","Gardening","Hiking","Technology"};
    String output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_interest);
         recyclerView=findViewById(R.id.recyclerview);
        childModels= new ArrayList<>();
        recyclerView.setHasFixedSize(false);
        login=findViewById(R.id.login);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
GetInt();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent i = new Intent(LanguageActivity.this,MainActivity.class);
                //startActivity(i);
                if(selectedlist.size()>0)
                {
                    for(int i=0;i<selectedlist.size();i++) {
                        if(i==0)
                        {output="";
                            output=selectedlist.get(i).getTitle();
                        }
                        else {
                            output+=","+selectedlist.get(i).getTitle();
                        }
                    }
                    Intent i = new Intent(InterestActivity.this,UserinfoActivity.class);
                    i.putExtra("in",""+output);
                    startActivity(i);
                }
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(InterestActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    multi_select(position);
                else
                    Toast.makeText(getApplicationContext(), "Details Page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    isMultiSelect = true;
                }
                multi_select(position);
            }
        }));

    }
    public void multi_select(int position) {
        if (selectedlist.contains(childModels.get(position)))
            selectedlist.remove(childModels.get(position));
        else
            selectedlist.add(childModels.get(position));
        adapter.notifyDataSetChanged();
    }


    public  void GetInt()
    {

        Call<InterestModel> userCall = service.get_interest();
        userCall.enqueue(new Callback<InterestModel>() {
            @Override
            public void onResponse(Call<InterestModel> call, Response<InterestModel> response) {
                //  progressDialog.dismiss();
                try {

                    childModels=response.body().getData();
                    adapter = new InterestAdapter(InterestActivity.this, childModels);
                    recyclerView.setAdapter(adapter);

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<InterestModel> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
}
