package com.webapps.viral.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.devs.readmoreoption.ReadMoreOption;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.webapps.viral.R;
import com.webapps.viral.adapter.PostAdapter;
import com.webapps.viral.adapter.UserAdapter;
import com.webapps.viral.fragement.PostPhotoFragment;
import com.webapps.viral.fragement.PostStoryFragment;
import com.webapps.viral.fragement.PostVideoFragment;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.LoginModel;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.model.RegisterModel;
import com.webapps.viral.model.UserModel;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.HeightWrappingViewPager;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserDetailsActivity extends AppCompatActivity {

    ImageView camera,setting;
    ArrayList<ChildModel> foodlist,mislist;
    ArrayList<UserModel> userModelArrayList;
    RecyclerView rv_category,rv_food;
    UserAdapter userAdapter;
    ProgressDialog progressDialog;
    RequestBody requestBody;
    Retrofit retrofit;
    APIService service;
    SharedPref sharedPref;
    String user_id,id;
    CircleImageView civ_profile;
    ImageView iv_cover;
    Button follow;
    TextView back, tv_name,tv_desc,tv_email,tv_about,tv_post,tv_following,tv_follower,tv_like;
    Integer[] image ={R.drawable.ic_add_circle_black,R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
    TabLayout tabLayout;
    ReadMoreOption readMoreOption;
    private int[] tabIcons = {
            R.drawable.ic_image_black_24dp,
            R.drawable.ic_play,
            R.drawable.ic_face
    };
    String src=null,name=null,csrc=null;
    HeightWrappingViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setting=findViewById(R.id.setting);
        camera=findViewById(R.id.camera);
        sharedPref= new SharedPref(this);
        follow=findViewById(R.id.follow);
        tv_desc=findViewById(R.id.tv_desc);
        tv_name=findViewById(R.id.tv_name);
        tv_email=findViewById(R.id.tv_email);
        civ_profile=findViewById(R.id.iv_profile);
        iv_cover=findViewById(R.id.iv_cover);
        tv_about=findViewById(R.id.tv_about);
        tv_follower=findViewById(R.id.tv_follower);
        tv_following=findViewById(R.id.tv_following);
        tv_like=findViewById(R.id.tv_like);
        tv_post=findViewById(R.id.tv_post);
      back=findViewById(R.id.back);
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              onBackPressed();
          }
      });
      iv_cover.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(name!=null&csrc!=null) {
                  Intent i = new Intent(UserDetailsActivity.this, ProfiledetailsActivity.class);
                  i.putExtra("name", "" + name);
                  i.putExtra("src", "" + csrc);
                  startActivity(i);
              }
          }
      });
      civ_profile.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             if(name!=null&src!=null) {
                 Intent i = new Intent(UserDetailsActivity.this, ProfiledetailsActivity.class);
                 i.putExtra("name", "" + name);
                 i.putExtra("src", "" + src);
                 startActivity(i);
             }
          }
      });
        tv_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Src",""+id);
                Intent i = new Intent(UserDetailsActivity.this, AllFollowerActivity.class);
                i.putExtra("id", "" + id);

                startActivity(i);
            }
        });
        tv_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Src",""+id);
                Intent i = new Intent(UserDetailsActivity.this, AllFollowingActivity.class);
                i.putExtra("id", "" + id);
                startActivity(i);
            }
        });
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow();
            }
        });
        setting.setVisibility(View.GONE);
        camera.setVisibility(View.GONE);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(UserDetailsActivity.this, SettingActivity.class);
                startActivity(i2);
            }
        });
        tabLayout=findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("Photos").setIcon(R.drawable.ic_image_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setText("Videos").setIcon(R.drawable.ic_play));
        tabLayout.addTab(tabLayout.newTab().setText("Stories").setIcon(R.drawable.ic_face));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        userModelArrayList = new ArrayList<>();
        rv_food=findViewById(R.id.recyclerview);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(false);
        rv_food.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        readMoreOption = new ReadMoreOption.Builder(this)
                .textLength(3, ReadMoreOption.TYPE_LINE) // OR
                //.textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel("more")
                .lessLabel("less")
                .moreLabelColor(Color.RED)
                .lessLabelColor(Color.MAGENTA)
                .labelUnderLine(true)
                .expandAnimation(true)
                .build();
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
        id=getIntent().getExtras().getString("id");
        user_id=sharedPref.getID();
        if(user_id.equalsIgnoreCase(id))
        {
            follow.setVisibility(View.GONE);
        }else {
            follow.setVisibility(View.VISIBLE);
        }
        GetPost();
        Login();
    viewPager = findViewById(R.id.viewpager);
    viewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    setupViewPager(viewPager);
    tabLayout.setupWithViewPager(viewPager);
    setupTabIcons();
}
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PostPhotoFragment(Integer.parseInt(id)), "Photos");
        adapter.addFrag(new PostVideoFragment(Integer.parseInt(id)), "Videos");
        adapter.addFrag(new PostStoryFragment(Integer.parseInt(id)), "Stories");
        viewPager.setAdapter(adapter);
    }

    public  void GetPost()
    {

        Call<ArrayList<UserModel>> userCall = service.Get_Sug_User();
        userCall.enqueue(new Callback<ArrayList<UserModel>>() {
            @Override
            public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                //  progressDialog.dismiss();
                try {

                    userModelArrayList=response.body();
                    Log.e("Leng","A"+userModelArrayList.size());
                    userAdapter = new UserAdapter(UserDetailsActivity.this,userModelArrayList);

                    rv_food.setAdapter(userAdapter);   // Toast.makeText(AddActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();


                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
    public  void Login()
    {
        Call<LoginModel> userCall = service.GetProfile(id,user_id);
        userCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                progressDialog.dismiss();
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                      //  Toast.makeText(UserDetailsActivity.this,""+response.body().getMsg(),Toast.LENGTH_LONG).show();
                       tv_name.setText(response.body().getData().get(0).getUserName());
                      //  tv_desc.setText(response.body().getData().get(0).getUserBiography());
                        tv_email.setText(response.body().getData().get(0).getUserEmail());
                        tv_about.setText("About "+response.body().getData().get(0).getUserName());
                        tv_follower.setText(""+response.body().getFollowers());
                        tv_following.setText(""+response.body().getFollow());
                        tv_like.setText(""+response.body().getLikes());
                        tv_post.setText(""+response.body().getPost());
                       if(response.body().getFstatus()==1)
                       {
                           follow.setText("Followed");
                           follow.setBackgroundColor(Color.WHITE);
                           follow.setTextColor(Color.BLACK);
                       }
                       else {
                           follow.setText("Follow");
                           follow.setBackgroundColor(Color.BLACK);
                           follow.setTextColor(Color.WHITE);
                       }

                        Picasso.get().load(getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserPicture()).into(civ_profile);
                        Picasso.get().load(getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserCover()).into(iv_cover);

                        src=getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserPicture();
                     name=response.body().getData().get(0).getUserName();
                        csrc=getString(R.string.base_url)+"utredns_admires/content/uploads"+response.body().getData().get(0).getUserCover();

                        if(response.body().getData().get(0).getUserBiography()!=null) {
                            readMoreOption.addReadMoreTo(tv_desc, response.body().getData().get(0).getUserBiography());
                        }
                        else {
                            tv_desc.setVisibility(View.GONE);
                        }
                        //readMoreOption.addReadMoreTo(tv_desc, response.body().getData().get(0).getUserBiography());

                    }
                    else {
                       // Toast.makeText(UserDetailsActivity.this,""+response.body().getMsg(),Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
                //Toast.makeText(UserDetailsActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    public  void Follow()
    {
       progressDialog.show();
        Call<RegisterModel> userCall = service.AddFollow(id,user_id);
        userCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                try {
                    Log.d("Email", "" + response.body().getStatus());
                    if (response.body().getStatus()==1) {
                        // recipientLists= response.body().getMessage();
                    Login();
                        Toast.makeText(UserDetailsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(UserDetailsActivity.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                progressDialog.dismiss();
                //hidepDialog();
              //  Toast.makeText(UserDetailsActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                Log.d("onFailure", t.toString());
            }
        });

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
