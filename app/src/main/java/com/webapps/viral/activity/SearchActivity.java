package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.webapps.viral.R;
import com.webapps.viral.adapter.SearchViewPagerAdapter;
import com.webapps.viral.adapter.TopAdapter;
import com.webapps.viral.adapter.ViewPagerAdapter;
import com.webapps.viral.fragement.PhotoFragment;
import com.webapps.viral.fragement.TopFragment;
import com.webapps.viral.fragement.VideoFragment;
import com.webapps.viral.model.TopUserModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.ListenFromActivity;
import com.webapps.viral.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    SearchViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    String querys="";
    SearchView.SearchAutoComplete searchAutoComplete;
    private ListenFromActivity activityListener1;
    androidx.appcompat.widget.SearchView searchView;
    TopFragment topFragment;
    PhotoFragment photoFragment;
    VideoFragment videoFragment;
    String data[],id[];
    Retrofit retrofit;
    APIService service;
    ImageView image_voice;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView=findViewById(R.id.search);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new SearchViewPagerAdapter(getSupportFragmentManager(),querys);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(R.id.search_src_text);

        image_voice=findViewById(R.id.image_search);
        image_voice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        GetUSer();
       searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent im = new Intent(SearchActivity.this, UserDetailsActivity.class);
               im.putExtra("id",id[i]);
               startActivity(im);
           }
       });

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String query) {
                // this is your adapter that will be filtered
                PagerAdapter pagerAdapter = (PagerAdapter) viewPager
                        .getAdapter();
                for (int i = 0; i < pagerAdapter.getCount(); i++) {

                    Fragment viewPagerFragment = (Fragment) viewPager
                            .getAdapter().instantiateItem(viewPager, i);
                    if (viewPagerFragment != null
                            && viewPagerFragment.isAdded()) {

                        if (viewPagerFragment instanceof TopFragment) {
                            topFragment = (TopFragment) viewPagerFragment;
                            if (topFragment != null) {
                                topFragment.beginSearch(query);
                            }
                        } else if (viewPagerFragment instanceof PhotoFragment) {
                            photoFragment = (PhotoFragment) viewPagerFragment;
                            if (photoFragment != null) {
                                photoFragment.beginSearch(query);
                            }
                        }
                        else if (viewPagerFragment instanceof VideoFragment) {
                            videoFragment = (VideoFragment) viewPagerFragment;
                            if (videoFragment != null) {
                                videoFragment.beginSearch(query);
                            }
                        }
                    }
                }

                return false;
            }
            public boolean onQueryTextSubmit(String query) {
                // Here u can get the value "query" which is entered in the
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.say_something));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Speech not Supported",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchAutoComplete.setText(result.get(0));
                }
                break;
            }

        }
    }
    public  void GetUSer()
    {
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);


        Call<ArrayList<TopUserModel>> userCall = service.GetUser();
        userCall.enqueue(new Callback<ArrayList<TopUserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TopUserModel>> call, Response<ArrayList<TopUserModel>> response) {
                 try {
                   data= new String[response.body().size()];
                     id= new String[response.body().size()];

                   for(int i=0;i<response.body().size();i++)
                   {
                       data[i]=response.body().get(i).getUserName();
                       id[i]=response.body().get(i).getId();

                   }
                     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_dropdown_item_1line, data);
                     searchAutoComplete.setAdapter(dataAdapter);
                 }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<TopUserModel>> call, Throwable t) {
                 //hidepDialog();
                Toast.makeText(SearchActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }

}
