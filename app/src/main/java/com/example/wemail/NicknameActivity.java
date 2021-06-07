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
import util.Nickname;
/*1.修改系统输出√
2.修改UI输出√
3.如果不麻烦，修改注释√
4.线程里的使用这个修改√
5.记录一下哪些界面跳转未传值（可能一个界面多个跳转）√
*/
public class NicknameActivity extends AppCompatActivity {

    private Button btnSubmit;
    private User user = new User();
    Handler mHandler = new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what==0){
                Toast.makeText(NicknameActivity.this,"服务器连接失败，请检查网络或者服务器是否开启",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(NicknameActivity.this,"昵称设置成功",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
                Toast.makeText(NicknameActivity.this,"有重复昵称，设置失败",Toast.LENGTH_SHORT).show();
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nikename);
        btnSubmit=findViewById(R.id.btn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //从上个界面传入的
                String username = getIntent().getStringExtra("username");
                //获取输入
                String nickname=((EditText)findViewById(R.id.nickname)).getText().toString();

//                //控制台输出验证
//                System.out.println("nickname:"+nickname);


//                //显示
//                ((EditText)findViewById(R.id.nickname)).setText("显示");

                //验证格式
                if(isNull(nickname)){
                    Toast.makeText(getApplicationContext(), "昵称为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    //连接服务器，开启昵称设置线程
                    user.setAccount(username);
                    user.setNickname(nickname);
                    new Thread(new NicknameActivity.MyThread()).start();

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

            String result = Nickname.nickName(user);

            if("1".equals(result)){
                //昵称设置成功，跳转到登录界面
//                System.out.println("发送成功");
                System.out.println("昵称设置成功");
                Intent intent=new Intent(NicknameActivity.this, JumpActivity.class);
                String username = getIntent().getStringExtra("username");
                //String nickname = getIntent().getStringExtra("nickname");
                intent.putExtra("username",username);
                intent.putExtra("nickname",user.getNickname());
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
