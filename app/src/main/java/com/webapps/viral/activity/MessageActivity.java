package com.webapps.viral.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.webapps.viral.R;
import com.webapps.viral.adapter.MessageAdapter;
import com.webapps.viral.adapter.SettingAdapter;
import com.webapps.viral.model.ChildModel;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ChildModel> childModels;
   MessageAdapter settingAdapter;
    Integer[] image ={R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
    String[] name ={"Alex Apes", "MAx Daniel","Will Amith","Dona Cora","MAz Telis","Rox ame","Fox Corex"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        childModels= new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        for (int i = 0; i < image.length; i++) {
            ChildModel objItem = new ChildModel();
            objItem.setImage(image[i]);
            objItem.setName(name[i]);
            objItem.setDescription(" Lorem ipsum hffnj fhhfnf");
          //objItem.setCategoryImageUrl(objJson2cat.getString(Constant.SUBCATEGORY_IMAGE));
            childModels.add(objItem);
        }
        settingAdapter = new MessageAdapter(this,childModels);

        recyclerView.setAdapter(settingAdapter);

    }
}
