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

import com.webapps.viral.MultiSelectActivity;
import com.webapps.viral.R;
import com.webapps.viral.RecyclerItemClickListener;
import com.webapps.viral.SampleModel;
import com.webapps.viral.adapter.Adapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.LanguageModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LanguageActivity extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<LanguageModel.Datum> arrayList,selectedlist;
Adapter adapter;
Integer image[]={R.drawable.hindi,R.drawable.bhojpuri,R.drawable.bengali,R.drawable.tamil};
String name[]={"Hindi","Bhojpuri","Bengali","Tamil"};
Retrofit retrofit;
APIService service;
Button login;
String output;

    boolean isMultiSelect = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_language);
         recyclerView=findViewById(R.id.recyclerview);
        login=findViewById(R.id.login);
        arrayList= new ArrayList<>();
        selectedlist= new ArrayList<>();
       // output=new ArrayList<>();
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
GetLang();
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
            Intent i = new Intent(LanguageActivity.this,UserinfoActivity.class);
            i.putExtra("lan",""+output);
            startActivity(i);
            }
    }
});
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(LanguageActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
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
            if (selectedlist.contains(arrayList.get(position)))
                selectedlist.remove(arrayList.get(position));
            else
                selectedlist.add(arrayList.get(position));
            adapter.notifyDataSetChanged();
    }

    public  void GetLang()
    {

        Call<LanguageModel> userCall = service.get_language();
        userCall.enqueue(new Callback<LanguageModel>() {
            @Override
            public void onResponse(Call<LanguageModel> call, Response<LanguageModel> response) {
                //  progressDialog.dismiss();
                try {

                    arrayList=response.body().getData();
                    adapter = new Adapter(LanguageActivity.this, arrayList);
                    recyclerView.setAdapter(adapter);
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LanguageModel> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


    }
}
