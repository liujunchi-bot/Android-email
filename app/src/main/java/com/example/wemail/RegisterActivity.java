package com.example.wemail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import entity.User;
import util.Register;

public class RegisterActivity extends AppCompatActivity {

    private Button btnSubmit;
    private String username;
    private String password;
    private String nickname;
    private String password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btnSubmit=findViewById(R.id.btn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入
                username=((EditText)findViewById(R.id.username)).getText().toString();
                password=((EditText)findViewById(R.id.password)).getText().toString();
                nickname=((EditText)findViewById(R.id.nickname)).getText().toString();
                password2=((EditText)findViewById(R.id.password2)).getText().toString();

//                //控制台输出验证
//                System.out.println("username:"+username);
//                System.out.println("password:"+password);
//                System.out.println("nickname:"+nickname);
//                System.out.println("password2:"+password2);
//
//                //显示
//                ((EditText)findViewById(R.id.username)).setText("显示");
//                ((EditText)findViewById(R.id.password)).setText("显示");
//                ((EditText)findViewById(R.id.nickname)).setText("显示");
//                ((EditText)findViewById(R.id.password2)).setText("显示");

                //验证格式
                if(isNull(username)){
                    Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(password)){
                    Toast.makeText(getApplicationContext(), "密码为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(password2)){
                    Toast.makeText(getApplicationContext(), "确认密码为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(nickname)){
                    Toast.makeText(getApplicationContext(), "昵称为空", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(password2)){
                    Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                }
                else {
                    //连接数据库，把新注册用户加入
                    new Thread(new RegisterActivity.MyThread()).start();

                }

            }
        });

    }

    boolean isNull(String str){
        if(str == null ||str.length()==0)return true;
        return false;
    }

    public class MyThread implements Runnable{
        @Override
        public void run() {
            System.out.println("开始了");
            User user = new User(nickname,username,password);
            String result = Register.register(user);

            if("1".equals(result)){
                //注册成功，跳转到登录界面
                System.out.println("注册成功");
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            else if("2".equals(result)){
                //已被注册
                System.out.println("该账号已被注册");
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
            }
            else if("0".equals(result)){
                //连接服务器失败
                System.out.println("连接服务器失败");
//                Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
