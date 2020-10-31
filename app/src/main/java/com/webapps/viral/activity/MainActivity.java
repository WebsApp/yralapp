package com.webapps.viral.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.irfaan008.irbottomnavigation.SpaceItem;
import com.irfaan008.irbottomnavigation.SpaceNavigationView;
import com.irfaan008.irbottomnavigation.SpaceOnClickListener;
import com.webapps.viral.R;
import com.webapps.viral.adapter.ViewPagerAdapter;
import com.webapps.viral.chat.ui.LoginActivity;
import com.webapps.viral.fragement.HomeFragment;
import com.webapps.viral.fragement.HomeMainFragment;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    SpaceNavigationView bottom_navigation;

    private final int REQUEST_CODE_PERMISSION = 55;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.initWithSaveInstanceState(savedInstanceState);
        bottom_navigation.changeCenterButtonIcon(R.drawable.ic_add_circle_black);
        bottom_navigation.addSpaceItem(new SpaceItem("", R.drawable.home));
        bottom_navigation.addSpaceItem(new SpaceItem("", R.drawable.ic_feed));
        bottom_navigation.addSpaceItem(new SpaceItem("", R.drawable.chat));
        bottom_navigation.addSpaceItem(new SpaceItem("", R.drawable.user));
        bottom_navigation.setCentreButtonColor(getResources().getColor(R.color.basecolor));
        //sharedPreferences =getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        try {
            if(checkStoragePermissions())
            {

            }
            else { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
            }}
            HomeMainFragment homeMainFragment = new HomeMainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_main, homeMainFragment, "Home").commitAllowingStateLoss();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Exception", Toast.LENGTH_SHORT).show();
        }
        bottom_navigation.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                try {
                    //     AddNonFragment addPostFragment = new AddNonFragment();
                    //   getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_main, addPostFragment, "AddPost").commitAllowingStateLoss();
                    Intent i = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(i);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,"Exception", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                bottomNavigation(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                bottomNavigation(itemIndex);
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION && checkStoragePermissions()) {

        }
    }
    private boolean checkStoragePermissions() {
        return
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }
    private void bottomNavigation(int itemIndex) {


        switch (itemIndex) {
            case 0:
                 backStackRemove();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
               startActivity(i);
                break;
              //  getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_main, new HomeFragment(),"Home").commitAllowingStateLoss();
              //  search_title("Home");

            case 1:
                Intent i2 = new Intent(MainActivity.this, TrendingActivity.class);
                startActivity(i2);
                break;
            case 2:
                Intent il = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(il);
                break;
            case 3:
                Intent i4 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i4);
                break;
        }

    }
    public void invisible_bottomNavigation() {
        bottom_navigation.changeCurrentItem(-1);
    }

    public void backStackRemove() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
    }


}
