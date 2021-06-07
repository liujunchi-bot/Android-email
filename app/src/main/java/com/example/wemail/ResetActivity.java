package com.example.wemail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import entity.User;
import util.Reset;

public class ResetActivity extends AppCompatActivity {

    private Button btnSubmit;
    private User user = new User();
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what==0){
                Toast.makeText(ResetActivity.this,"服务器连接失败，请检查网络或者服务器是否开启",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(ResetActivity.this,"成功修改密码",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
                Toast.makeText(ResetActivity.this,"旧密码或者昵称不正确",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==3){
                Toast.makeText(ResetActivity.this,"该用户不存在",Toast.LENGTH_SHORT).show();
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        btnSubmit=findViewById(R.id.btn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入
                String username=((EditText)findViewById(R.id.username)).getText().toString();
                String old_password=((EditText)findViewById(R.id.old_password)).getText().toString();
                String nickname=((EditText)findViewById(R.id.nickname)).getText().toString();
                String new_password=((EditText)findViewById(R.id.new_password)).getText().toString();
                String new_password2=((EditText)findViewById(R.id.new_password2)).getText().toString();

//                //控制台输出验证
//                System.out.println("username:"+username);
//                System.out.println("old_password:"+old_password);
//                System.out.println("nickname:"+nickname);
//                System.out.println("new_password:"+new_password);
//                System.out.println("new_password2:"+new_password2);
//
//                //显示
//                ((EditText)findViewById(R.id.username)).setText("显示");
//                ((EditText)findViewById(R.id.old_password)).setText("显示");
//                ((EditText)findViewById(R.id.nickname)).setText("显示");
//                ((EditText)findViewById(R.id.new_password)).setText("显示");
//                ((EditText)findViewById(R.id.new_password2)).setText("显示");
//
//                //跳转到登录界面
//                Intent intent=new Intent(ResetActivity.this, LoginActivity.class);
//                startActivity(intent);


                //验证格式
                if(isNull(username)){
                    Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(old_password)){
//                    Toast.makeText(getApplicationContext(), "确认密码为空", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "旧密码为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(new_password)){
                    Toast.makeText(getApplicationContext(), "新密码为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(new_password2)){
                    Toast.makeText(getApplicationContext(), "确认密码为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(nickname)){
                    Toast.makeText(getApplicationContext(), "昵称为空", Toast.LENGTH_SHORT).show();
                }
                else if(!new_password.equals(new_password)){
                    Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                }
                else {

                    user.setAccount(username);
                    user.setNickname(nickname);
                    user.setPassword(old_password+"=="+new_password);

                    //连接数据库，把新密码加入
                    new Thread(new ResetActivity.MyThread()).start();

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

            String result = Reset.reset(user);

            if("1".equals(result)){
                //修改密码成功，跳转至登录界面
//                System.out.println("发送成功");
                System.out.println("成功修改密码");
                Intent intent=new Intent(ResetActivity.this, LoginActivity.class);
                String username = getIntent().getStringExtra("username");
                //String nickname = getIntent().getStringExtra("nickname");
                intent.putExtra("username",username);
                intent.putExtra("nickname",user.getNickname());
                startActivity(intent);
                mHandler.sendEmptyMessage(1);
            }
            else if("2".equals(result)){
                //旧密码或者昵称不正确
//                System.out.println("发送失败");
                System.out.println("旧密码或者昵称不正确");
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(2);
            }
            /*新增*/
            else if("3".equals(result)){
                //该用户不存在
//                System.out.println("发送失败");
                System.out.println("该用户不存在");
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(3);
            }
            else if("0".equals(result)){
                //连接服务器失败
                System.out.println("连接服务器失败");
//                Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(0);
            }
        }
    }

}
