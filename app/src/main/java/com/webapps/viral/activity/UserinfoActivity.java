package com.webapps.viral.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.webapps.viral.R;
import com.webapps.viral.RecyclerItemClickListener;
import com.webapps.viral.adapter.Adapter;
import com.webapps.viral.adapter.IAdapter;
import com.webapps.viral.adapter.InterestAdapter;
import com.webapps.viral.model.InterestModel;
import com.webapps.viral.model.LanguageModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.GpsTracker;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class UserinfoActivity extends AppCompatActivity {
    RecyclerView rv_city,rv_interest;
    //ArrayList<ChildModel> childModels;
    ArrayList<LanguageModel.Datum>     arrayList,lanselectlist;
   ArrayList<InterestModel.Datum> childModels;
    TextView tv_interest,tv_language;
    Adapter adapter;
    Button next;
    SharedPref sharedPref;
    String uid;
    Retrofit retrofit;
    APIService service;
    IAdapter interestAdapter;
    Integer image[]={R.drawable.bike,R.drawable.gardening,R.drawable.hiking,R.drawable.tech};
    String name[]={"Bikes","Gardening","Hiking","Technology"};
    Integer image1[]={R.drawable.hindi,R.drawable.bhojpuri,R.drawable.bengali,R.drawable.tamil};
    String name1[]={"Hindi","Bhojpuri","Bengali","Tamil"};
    Calendar myCalendar;
     EditText dob;
     String date1;
      EditText city,bio;
      String lan,in;
      boolean isMultiSelect=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        tv_interest=findViewById(R.id.tv_interest);
        tv_language=findViewById(R.id.tv_language);
        rv_city=findViewById(R.id.language);
        rv_interest=findViewById(R.id.interest);
        next= findViewById(R.id.next);
        arrayList= new ArrayList<>();
        lanselectlist= new ArrayList<>();
        //inselectlist= new ArrayList<>();
        dob=findViewById(R.id.dob);
        bio=findViewById(R.id.bio);
        city=findViewById(R.id.city);
        childModels= new ArrayList<>();
        rv_city.setHasFixedSize(false);
        rv_city.setNestedScrollingEnabled(false);
        sharedPref=new SharedPref(this);
       // LinearLayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rv_city.setLayoutManager(new GridLayoutManager(this,4));

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        rv_interest.setHasFixedSize(false);
        rv_interest.setNestedScrollingEnabled(false);
        rv_interest.setLayoutManager(new GridLayoutManager(this,4));

        try {
            in=getIntent().getExtras().getString("in","0");
            lan=getIntent().getExtras().getString("lan","0");

        }
        catch (Exception ex){}

        tv_language.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       /* Intent i =new Intent(UserinfoActivity.this,LanguageActivity.class);
        startActivity(i);*/
       showDialog();
    }
    });
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // updateLabel();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                dob.setText(sdf.format(myCalendar.getTime()));
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

                date1=sdf2.format(myCalendar.getTime());
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar=  Calendar.getInstance();
                myCalendar.add(Calendar.YEAR, -18);
                // DatePickerDialog datePicker = new DatePickerDialog(this, datePickerListener, y, m, d);
                // datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                DatePickerDialog dialog =  new DatePickerDialog(UserinfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                dialog.show();
                //setDateTimeField();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getTitle());
                        //  SpannableString redSpannable= new SpannableString(tagUserAdapter.getSelected().get(i).getUserName());
                        //    redSpannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, tagUserAdapter.getSelected().get(i).getUserName().length(), 0);
                        //      stringBuilder.append(redSpannable);
                        stringBuilder.append("\n");
                    }
                    lan=""+stringBuilder;
                }
                if (interestAdapter.getSelected().size() > 0) {
                    SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getTitle());
                        stringBuilder.append("\n");
                    }
                    in=""+stringBuilder;
                }
                UpdateUser();
            }
        });
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(UserinfoActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            getLocation();
        }
        tv_interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent i =new Intent(UserinfoActivity.this,InterestActivity.class);
                startActivity(i);*/
               showDialog2();
            }
        });
        sharedPref=new SharedPref(UserinfoActivity.this);
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);

        uid=sharedPref.getID();
        GetLang();
        GetInt();
       /* rv_interest.addOnItemTouchListener(new RecyclerItemClickListener(UserinfoActivity.this, rv_interest, new RecyclerItemClickListener.OnItemClickListener() {
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
        }));*/
       /* rv_city.addOnItemTouchListener(new RecyclerItemClickListener(UserinfoActivity.this, rv_city, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect)
                    Langmulti_select(position);
                else
                    Toast.makeText(getApplicationContext(), "Details Page", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect) {
                    isMultiSelect = true;
                }
                Langmulti_select(position);
            }
        }));
*/
    }
   /* public void multi_select(int position) {
        if (inselectlist.contains(childModels.get(position)))
            inselectlist.remove(childModels.get(position));
        else
            inselectlist.add(childModels.get(position));
        interestAdapter.notifyDataSetChanged();
    }*/

    public void Langmulti_select(int position) {
        if (lanselectlist.contains(arrayList.get(position)))
            lanselectlist.remove(arrayList.get(position));
        else
            lanselectlist.add(arrayList.get(position));
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(UserinfoActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                        getLocation();
                    }
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    public void getLocation(){
        GpsTracker gpsTracker = new GpsTracker(UserinfoActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
               try {
                   String city2 = addresses.get(0).getLocality();
                   String state = addresses.get(0).getAdminArea();
                   city.setText(city2+","+state);
               }catch (Exception e){}

                //loc=city+","+state;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{
            gpsTracker.showSettingsAlert();
        }
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(UserinfoActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            getLocation();
        }
        super.onPause();
    }

    public  void showDialog()
    {
        Dialog dialog = new Dialog(UserinfoActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.activity_language);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        RecyclerView  recyclerView=dialog.findViewById(R.id.recyclerview);
        Button login=dialog.findViewById(R.id.login);
        Button cancel=dialog.findViewById(R.id.cancel);

        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new Adapter(UserinfoActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getTitle());
                        //  SpannableString redSpannable= new SpannableString(tagUserAdapter.getSelected().get(i).getUserName());
                        //    redSpannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, tagUserAdapter.getSelected().get(i).getUserName().length(), 0);
                        //      stringBuilder.append(redSpannable);
                        stringBuilder.append("\n");
                    }
                    lan=""+stringBuilder;
                   }
                dialog.hide();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }
    public  void showDialog2()
    {
        Dialog dialog = new Dialog(UserinfoActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(R.layout.activity_interest);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        RecyclerView  recyclerView=dialog.findViewById(R.id.recyclerview);
        Button login=dialog.findViewById(R.id.login);
        Button cancel=dialog.findViewById(R.id.cancel);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        interestAdapter = new IAdapter(UserinfoActivity.this, childModels);
        recyclerView.setAdapter(interestAdapter);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interestAdapter.getSelected().size() > 0) {
                    SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getTitle());
                        stringBuilder.append("\n");
                    }
                      in=""+stringBuilder;
                       }
                dialog.hide();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
    }
    public  void UpdateUser()
    {
        Call<RegisterModel> userCall = service.UpdateUser(""+date1,""+bio.getText().toString(),""+city.getText().toString(),""+in,""+lan,""+uid);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                //  progressDialog.dismiss();
                try {
                    Intent i =new Intent(UserinfoActivity.this,MainActivity.class);
                    startActivity(i);
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });


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
                    adapter = new Adapter(UserinfoActivity.this, arrayList);
                    rv_city.setAdapter(adapter);
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
    public  void GetInt()
    {

        Call<InterestModel> userCall = service.get_interest();
        userCall.enqueue(new Callback<InterestModel>() {
            @Override
            public void onResponse(Call<InterestModel> call, Response<InterestModel> response) {
                //  progressDialog.dismiss();
                try {

                    childModels=response.body().getData();
                    interestAdapter= new IAdapter(UserinfoActivity.this, childModels);
                    rv_interest.setAdapter(interestAdapter);
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
