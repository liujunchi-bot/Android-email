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

import entity.MyBlackList;
import util.BlackList;
import util.DeleteBlackList;

/*1.修改系统输出√
2.修改UI输出√
3.如果不麻烦，修改注释√
4.线程里的使用这个修改√
5.记录一下哪些界面跳转未传值（可能一个界面多个跳转）√
*/
public class BlackListActivity extends AppCompatActivity {    //修改黑名单，增加，删除

    private Button btnSubmit;
    private Button btnDelete;
    private String blackList;
    private MyBlackList myBlackList= new MyBlackList();
    private int flag =1 ;//1执行加，0执行删除

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what==0){
                Toast.makeText(BlackListActivity.this,"服务器连接失败，请检查网络或者服务器是否开启",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
//                Toast.makeText(BlackListActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                Toast.makeText(BlackListActivity.this,"操作黑名单成功",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
//                Toast.makeText(BlackListActivity.this,"加入黑名单失败",Toast.LENGTH_SHORT).show();
                Toast.makeText(BlackListActivity.this,"操作黑名单失败",Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);

        btnSubmit=findViewById(R.id.btn_black_list);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag=1;


                //从上个界面传入的
                String username = getIntent().getStringExtra("username");
                //获取输入
                blackList=((EditText)findViewById(R.id.black_list)).getText().toString();
                myBlackList.setAccount(username);
                myBlackList.setBlacklistAccount(blackList);
//                //控制台输出验证
//                System.out.println("nickname:"+nickname);


//                //显示
//                ((EditText)findViewById(R.id.nickname)).setText("显示");

                //验证格式
                if(isNull(blackList)){
//                    Toast.makeText(getApplicationContext(), "昵称为空", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "黑名单为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(flag==0){
                        flag=1;
                    }
                    //启动加入黑名单线程
                    new Thread(new BlackListActivity.MyThread()).start();

                }

            }
        });

        btnDelete=findViewById(R.id.btn_delete_black_list);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag=0;

                //从上个界面传入的
                String username = getIntent().getStringExtra("username");
                //获取输入
                blackList=((EditText)findViewById(R.id.black_list)).getText().toString();
                myBlackList.setAccount(username);
                myBlackList.setBlacklistAccount(blackList);
//                //控制台输出验证
//                System.out.println("nickname:"+nickname);


//                //显示
//                ((EditText)findViewById(R.id.nickname)).setText("显示");

                //验证格式
                if(isNull(blackList)){
//                    Toast.makeText(getApplicationContext(), "昵称为空", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "黑名单为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(flag==1){
                        flag=0;
                    }
                    //启动加入黑名单线程
                    new Thread(new BlackListActivity.MyThread()).start();

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
            String result;
            if(flag==1){
                System.out.println("黑名单增加开始了");
                result = BlackList.blackList(myBlackList);
            }
            else {
                System.out.println("黑名单删除开始了");
                result = DeleteBlackList.deleteBlackList(myBlackList);
            }

            String [] blackUser = blackList.split(";");

            int len = Math.min(result.length(),blackUser.length);
            for(int i=0;i<len;i++){
                if('1'==result.charAt(i)){
                    //加入黑名单成功，跳转到登录界面
//                    System.out.println(blackUser[i]+"发送成功");
                    if(flag==1){
                        System.out.println(blackUser[i]+"加入黑名单成功");
                    }
                    else {
                        System.out.println(blackUser[i]+"删除黑名单成功");
                    }
                    Intent intent=new Intent(BlackListActivity.this, JumpActivity.class);
                    String username = getIntent().getStringExtra("username");
                    String nickname = getIntent().getStringExtra("nickname");
                    intent.putExtra("username",username);
                    intent.putExtra("nickname",nickname);
                    startActivity(intent);
                    mHandler.sendEmptyMessage(1);
                }
                else if('2'==result.charAt(i)){
                    //已经在黑名单内
//                    System.out.println(blackUser[i]+"发送失败");
                    if(flag==1){
                        System.out.println(blackUser[i]+"加入黑名单失败");
                    }
                    else {
                        System.out.println(blackUser[i]+"删除黑名单失败");
                    }
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
                    mHandler.sendEmptyMessage(2);
                }
                else if('0'==result.charAt(i)){
                    //连接服务器失败
                    System.out.println("连接服务器失败");
//                Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    mHandler.sendEmptyMessage(0);
                }
            }


        }
    }


}
