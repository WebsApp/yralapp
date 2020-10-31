package com.webapps.viral.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.webapps.viral.fragement.EnterTainmentFragment;
import com.webapps.viral.fragement.HomeFragment;
import com.webapps.viral.fragement.PhotoFragment;
import com.webapps.viral.fragement.TopFragment;
import com.webapps.viral.fragement.VideoFragment;


public class SearchViewPagerAdapter extends FragmentPagerAdapter {

    String  s;
    public SearchViewPagerAdapter(FragmentManager fm,String query) {
        super(fm);
        this.s=query;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new TopFragment(s);
        }
        else if (position == 1)
        {
            fragment = new TopFragment(s);
        }
        else if (position == 2)
        {
            fragment = new PhotoFragment(s);
        }
        else if (position == 3)
        {
            fragment = new VideoFragment(s);
        }
        /*else if (position == 4)
        {
            fragment = new TopFragment(s);
        }*/
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Top";
        }
        else if (position == 1)
        {
            title = "People";
        }
        else if (position == 2)
        {
            title = "Photo";
        } else if (position == 3)
        {
            title = "Videos";
        }
      /*  else if (position == 4)
        {
            title = "Tag";
        }*/
        return title;
    }
}
