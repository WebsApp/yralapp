package com.webapps.viral.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.webapps.viral.fragement.EnterTainmentFragment;
import com.webapps.viral.fragement.HomeFragment;
import com.webapps.viral.fragement.TrendingFragment;
import com.webapps.viral.model.CategoryModel;

import java.util.List;


public class VPTrendAdapter extends FragmentPagerAdapter {

    List<CategoryModel.Datum> arrayList;
    public VPTrendAdapter(FragmentManager fm, List<CategoryModel.Datum> arrayList) {
        super(fm);
      this.arrayList=arrayList;

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

            fragment = new TrendingFragment(arrayList.get(position).getTitle());

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

        return title;
    }
}
