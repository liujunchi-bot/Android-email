package com.example.wemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //setContentView(R.layout.activity_send_mail);

        //直接跳转
//        Intent intent=new Intent(MainActivity.this, SendMailActivity.class);
//        startActivity(intent);

        Intent intent=new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

        //带参数传递 的方法
        //Intent intent = new Intent(RegisterActivity.this, SetUpInfoActivity.class);
        //intent.putExtra("arg",arg) ;
        //startActivity(intent);
        //arg=getIntent().getStringExtra("arg");

    }
}
