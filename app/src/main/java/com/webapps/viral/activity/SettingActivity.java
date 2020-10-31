package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.webapps.viral.R;
import com.webapps.viral.adapter.NotificationAdapter;
import com.webapps.viral.adapter.SettingAdapter;
import com.webapps.viral.model.ChildModel;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ChildModel> childModels;
   SettingAdapter settingAdapter;
   TextView back;
    Integer[] image ={R.drawable.ic_mode_edit_black_24dp,R.drawable.account_e, R.drawable.contacs_e,R.drawable.graph,R.drawable.lightbulb,R.drawable.ic_privacy,R.drawable.email_e,R.drawable.trash,R.drawable.ic_bookmark_border_black_24dp,R.drawable.ic_browser,R.drawable.support,R.drawable.ic_power_settings_new_black_24dp};
    String[] name ={"Edit Profile","Account setting", "Change Phone No","Know My Analytics","Ad with us","Privacy","Change email","Delete Account","Saved Post","About This App","Raise your queries","Logout"};
    String[] desc ={"update your personal details","Account setting", "","Know my analytics","Ad with us","","","","","","","Logout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        childModels= new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        for (int i = 0; i < image.length; i++) {
            ChildModel objItem = new ChildModel();
            objItem.setImage(image[i]);
            objItem.setName(name[i]);
            objItem.setDescription(desc[i]);
          //objItem.setCategoryImageUrl(objJson2cat.getString(Constant.SUBCATEGORY_IMAGE));
            childModels.add(objItem);
        }
        settingAdapter = new SettingAdapter(this,childModels);

        recyclerView.setAdapter(settingAdapter);

    }
}
