package com.webapps.viral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.webapps.viral.R;

public class ListViewAdapter extends BaseAdapter {
        Context context;
        String studentList[];
        int image[];
        LayoutInflater inflter;

        public ListViewAdapter(Context applicationContext, String[] studentList, int[] image) {
            this.context = context;
            this.studentList = studentList;
            this.image = image;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return studentList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.list_item, null);
            TextView name = view.findViewById(R.id.textViewName);
            ImageView imageView = view.findViewById(R.id.imageView);
            name.setText(studentList[i]);
            imageView.setImageResource(image[i]);
            return view;
        }

}
