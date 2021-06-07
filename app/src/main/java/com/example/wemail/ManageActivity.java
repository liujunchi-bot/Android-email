package com.example.wemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entity.User;
import util.ChangeRole;

public class ManageActivity extends AppCompatActivity {

    private Button btnSubmit;
    private User user = new User();

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what==0){
                Toast.makeText(ManageActivity.this,"服务器连接失败，请检查网络或者服务器是否开启",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(ManageActivity.this,"权限设置成功",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
                Toast.makeText(ManageActivity.this,"权限设置失败",Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
        btnSubmit=findViewById(R.id.btn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //从上个界面传入的
                String username = getIntent().getStringExtra("username");
                //获取输入
                String username2=((EditText)findViewById(R.id.id)).getText().toString();
                String role=((EditText)findViewById(R.id.role)).getText().toString();

//                //控制台输出验证
//                System.out.println("nickname:"+nickname);


//                //显示
//                ((EditText)findViewById(R.id.nickname)).setText("显示");

                //验证格式
                if(isNull(username2)){
                    Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(role)){
                    Toast.makeText(getApplicationContext(), "权限为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    //连接服务器，开启昵称设置线程
                    user.setAccount(username2);
                    user.setRole(Long.parseLong(role));
                    new Thread(new ManageActivity.MyThread()).start();

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

            System.out.println("username="+user.getAccount());
            System.out.println("role="+user.getAccount());

            String result = ChangeRole.changeRole(user);

            if("1".equals(result)){
                //昵称设置成功，跳转到登录界面
//                System.out.println("发送成功");
                System.out.println("权限设置成功");
                Intent intent=new Intent(ManageActivity.this, JumpActivity.class);
                String username = getIntent().getStringExtra("username");
                String nickname = getIntent().getStringExtra("nickname");
                String role = getIntent().getStringExtra("role");

                intent.putExtra("username",username);
                intent.putExtra("nickname",nickname);
                intent.putExtra("role",role);
                startActivity(intent);
                mHandler.sendEmptyMessage(1);
            }
            else if("2".equals(result)){
                //已存在昵称
//                System.out.println("发送失败");
                System.out.println("昵称设置失败");
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
                mHandler.sendEmptyMessage(2);
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
