package com.example.wemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import entity.MyFriendList;
import util.DeleteFriend;
import util.SetFriend;

public class AddFriendActivity extends AppCompatActivity {

    private Button btnSubmit;
    private Button btnDelete;
    private String friendList;
    private MyFriendList myFriendList= new MyFriendList();
    private int flag =1 ;//1执行加，0执行删除

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        btnSubmit=findViewById(R.id.btn_set_friend);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;//加入

                //从上个界面传入的
                String username = getIntent().getStringExtra("username");
                //获取输入
                friendList=((EditText)findViewById(R.id.friend_list)).getText().toString();
                myFriendList.setAccount(username);
                myFriendList.setFriendAccount(friendList);
//                //控制台输出验证
//                System.out.println("nickname:"+nickname);


//                //显示
//                ((EditText)findViewById(R.id.nickname)).setText("显示");

                //验证格式
                if(isNull(friendList)){
                    Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(flag==0){
                        flag=1;
                    }
                    //连接服务器，把邮件发送

                    new Thread(new AddFriendActivity.MyThread()).start();

                }

            }
        });

        btnDelete=findViewById(R.id.btn_delete_friend);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag=0;//删除


                //从上个界面传入的
                String username = getIntent().getStringExtra("username");
                //获取输入
                friendList=((EditText)findViewById(R.id.friend_list)).getText().toString();
                myFriendList.setAccount(username);
                myFriendList.setFriendAccount(friendList);
//                //控制台输出验证
//                System.out.println("nickname:"+nickname);


//                //显示
//                ((EditText)findViewById(R.id.nickname)).setText("显示");

                //验证格式
                if(isNull(friendList)){
                    Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(flag==1){
                        flag=0;
                    }
                    //连接服务器，把邮件发送

                    new Thread(new AddFriendActivity.MyThread()).start();

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
                System.out.println("好友增加开始了");
                result = SetFriend.setFriend(myFriendList);
            }
            else {
                System.out.println("好友删除开始了");
                result = DeleteFriend.deleteFriend(myFriendList);
            }

            String [] friends = friendList.split(";");

            int len = Math.min(result.length(),friends.length);
            for(int i=0;i<len;i++){
                if('1'==result.charAt(i)){
                    //注册成功，跳转到登录界面
                    //System.out.println(friends[i]+"发送成功");

                    if(flag==1){
                        System.out.println(friends[i]+"添加成功");
                    }
                    else {
                        System.out.println(friends[i]+"删除成功");
                    }
                    Intent intent=new Intent(AddFriendActivity.this, JumpActivity.class);
                    String username = getIntent().getStringExtra("username");
                    String nickname = getIntent().getStringExtra("nickname");
                    intent.putExtra("username",username);
                    intent.putExtra("nickname",nickname);
                    startActivity(intent);
                }
                else if('2'==result.charAt(i)){
                    //已被注册
                    System.out.println(friends[i]+"操作失败");
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
                    //mHandler.sendEmptyMessage(2);
                }
                else if('0'==result.charAt(i)){
                    //连接服务器失败
                    System.out.println("连接服务器失败");
//                Toast.makeText(getApplicationContext(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

}
