package com.example.wemail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entity.User;
import util.*;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    JSONObject json = new JSONObject();
    private String result=null;

    private String url= "http://49.234.72.60:8080/wemail_master_war_exploded/AndroidLoginServlet";

    private EditText username;
    private EditText password;
    private Button login;
    private Button go_register;
    private Button find_pwd;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private Bundle bundle;

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what==0){
                Toast.makeText(LoginActivity.this,"请求非法",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
                Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==3){
                Toast.makeText(LoginActivity.this,"网络连接错误",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==4){
                Toast.makeText(LoginActivity.this,"用户名或密码输入错误",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==5){
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bundle = savedInstanceState;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        go_register = findViewById(R.id.goRegister);
        go_register.setOnClickListener(this);
        find_pwd = findViewById(R.id.forgetPassword);
        find_pwd.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23 &&
                //23以上的SDK版本
                ContextCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this,new String[]{ android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.RECORD_AUDIO,android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_LOCATION);
        }
        else{

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //gpsUtils = new GPSUtils(activity);// 安卓权限通过
            } else {
                // 权限拒绝
                if (!ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissions[0])) {
                    mHandler.sendEmptyMessage(0);
                } else {
                    // 用户此次选择了禁止权限
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        //多个按钮进行分类
        if (isFastUtils.isFastClick()) {
            switch (v.getId()) {
                case R.id.login:
                    login();
                    break;
                case R.id.goRegister:
                    //到达注册界面
                    goto_register();
                    break;
                case R.id.forgetPassword:
                    //到达找回密码界面
                    goto_findpwd();
                    break;
            }
        }
    }


    private void goto_findpwd() {
        //转到找回密码界面
        Intent intent = new Intent(LoginActivity.this,ResetActivity.class);
        Bundle bundle = new Bundle();
        startActivity(intent);
    }


    private void goto_register() {
        //转到注册界面
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        startActivity(intent);
    }


    private void login() {
        if (!isNetworkConnected(this)) {
            Toast.makeText(this,"网络连接失败",Toast.LENGTH_SHORT).show();
            return;
        }
        //调用服务器接口进行登录验证
        try {

            //方便调试，账号密码为root直接登录
            if(username.getText().equals("root")
                    &&password.getText().equals("root")) {
                mHandler.sendEmptyMessage(5);
                Intent intent = new Intent();

                intent.setClass(LoginActivity.this, JumpActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称

                Bundle bundle = new Bundle();
                bundle.putString("username","root");
                bundle.putInt("pos",1);
                intent.putExtras(bundle);
                startActivity(intent);
                LoginActivity.this.finish();
            }

            json.put("username",username.getText());
            json.put("password",password.getText());

            //空用户名
            if(username.getText().length()==0) {
                mHandler.sendEmptyMessage(1);
            }

            //空密码
            else if(password.getText().length()==0){
                mHandler.sendEmptyMessage(2);
            }

            else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                            //与服务器进行通信！！！

                            //result= HttpClientUtils.test(url,json);
                            User user = new User();
                            user.setAccount(username.getText().toString());
                            user.setPassword(password.getText().toString());
                            result = Login.login(user);
                            //result = "1";
                            System.out.println("/"+result+"/");

                            if(result==null) {
                                //网络错误
                                mHandler.sendEmptyMessage(3);
                            }
                            else{
                                // 失败
                                if (result.contains("0")){
                                    mHandler.sendEmptyMessage(4);
                                }
                                //成功
                                else if(result.contains("1")){
                                    System.out.println(result.substring(11));
                                    mHandler.sendEmptyMessage(5);
                                    Intent intent = new Intent();

                                    intent.setClass(LoginActivity.this, JumpActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称

                                    Bundle bundle = new Bundle();
                                    bundle.putString("username",username.getText().toString());
                                    bundle.putString("nickname",result.substring(11,result.indexOf(";role=")));
                                    bundle.putString("role",result.substring(result.indexOf(";role=")+6,result.length()));
                                    bundle.putInt("pos",1);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    //LoginActivity.this.finish();
                                }
                            }

                    }
                }).start();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager.getActiveNetworkInfo() != null) {
                //mNetworkInfo.isAvailable();
                return true;//有网
            }
        }
        return false;//没有网
    }
}
