package com.webapps.viral.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.webapps.viral.R;

public class QueryActivity extends AppCompatActivity {

    TextView back;
    EditText concern,name,email,desc;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        back=findViewById(R.id.back);
        concern=findViewById(R.id.concern);
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        desc=findViewById(R.id.desc);
        login=findViewById(R.id.login);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (concern.getText().length()==0){
                    concern.setError("Cann't Empty");
                }
                else  if(name.getText().length()==0){
                    name.setError("Cann't Empty");
                }

                else  if(email.getText().length()==0){
                    email.setError("Cann't Empty");
                }

                else  if(desc.getText().length()==0){
                    desc.setError("Cann't Empty");
                }
                else {
                    name.setText(null);
                    concern.setText(null);
                    email.setText(null);
                    desc.setText(null);
                    Toast.makeText(QueryActivity.this,"Your Query Submitted",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
