package com.webapps.viral.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.webapps.viral.fragement.ChallengeFragment;
import com.webapps.viral.fragement.EnterTainmentFragment;
import com.webapps.viral.fragement.FoundFragment;
import com.webapps.viral.fragement.HomeFragment;
import com.webapps.viral.fragement.HomeMainFragment;
import com.webapps.viral.fragement.LostFragment;
import com.webapps.viral.model.CategoryModel;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<CategoryModel.Datum> arrayList;
    public ViewPagerAdapter(FragmentManager fm, List<CategoryModel.Datum> arrayList) {
        super(fm);
      this.arrayList=arrayList;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new HomeFragment();
        }else if(position==arrayList.size()-3){
            fragment = new ChallengeFragment(arrayList.get(position).getTitle());

        }
        else if(position==arrayList.size()-2){
            fragment = new LostFragment(arrayList.get(position).getTitle());

        }
        else if(position==arrayList.size()-1){
            fragment = new FoundFragment(arrayList.get(position).getTitle());

        }
        else
        {
            fragment = new EnterTainmentFragment(arrayList.get(position).getTitle());
        }
       /* else if (position == 2)
        {
            fragment = new EnterTainmentFragment();
        }
        else if (position == 3)
        {
            fragment = new EnterTainmentFragment();
        }*/

        return fragment;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        title=arrayList.get(position).getTitle();
       /* if (position == 0)
        {
            title = "Home";
        }
        else if (position == 1)
        {
            title = "Entertainment";
        }
        else if (position == 2)
        {
            title = "Science";
        } else if (position == 3)
        {
            title = "Cultural";
        }*/
        return title;
    }
}
