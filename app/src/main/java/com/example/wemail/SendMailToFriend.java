package com.example.wemail;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Contact;
import entity.ContactHelp;
import entity.MailHelp;
import entity.User;
import util.GetFriend;
import util.ReadInnerMail;

public class SendMailToFriend extends AppCompatActivity {   // 查看通讯录

    private ListView listview;
    private User user=new User();
    private String friendListString=null;
    private int flag=0;//是否加载完成


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listview = (ListView)findViewById(R.id.mylistview);

        flag = 0;
        //从上个界面传入的
        String username = getIntent().getStringExtra("username");
        user.setAccount(username);

        final List<Map<String,Object>> friendList = putData();



        SimpleAdapter simpleAdapter = new SimpleAdapter(this,friendList,R.layout.friend_item,
                new String[]{"friend"},new int[]{R.id.friend});
        listview.setAdapter(simpleAdapter);

        //设置监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "第"+position+"个项", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SendMailToFriend.this, SendMail2Activity.class);

                Map<String,Object> map = new HashMap<String,Object>();
                map = friendList.get(position);
                intent.putExtra("to",map.get("friend").toString());
                intent.putExtra("username",user.getAccount());

                startActivity(intent);

            }
        });

    }

    public List<Map<String,Object>> putData(){


        if(isNull(user.getAccount())){
            Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
        }
        else {
            //连接服务器，把邮件发送

            new Thread(new SendMailToFriend.MyThread()).start();

        }

        while(flag==0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("flag="+flag);
        }
        System.out.println("收件箱完成获取");

        List<String> friendList = new ArrayList<String>();
        friendList = JSONObject.parseArray(friendListString, String.class);


        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

//        Map<String,Object> map1 = new HashMap<String,Object>();
//        map1.put("from", "test@wemail.com");
//        map1.put("time", "2020/04/21/14:30");
//        map1.put("title", "第一封邮件");
//
//        Map<String,Object> map2 = new HashMap<String,Object>();
//        map2.put("from", "李四");
//        map2.put("time", "2020/04/22");
//        map2.put("title", "第二封邮件");
//
//        Map<String,Object> map3 = new HashMap<String,Object>();
//        map3.put("from", "王五");
//        map3.put("time", "2020/04/23");
//        map3.put("title", "第三封邮件");
//
//        list.add(map1);
//        list.add(map2);
//        list.add(map3);

        System.out.println("size="+friendList.size());
        for (int i = 0; i < friendList.size(); i++) {
            System.out.println(friendList.get(i));
            Map<String,Object> map1 = new HashMap<String,Object>();
//            map1.put("from",friendList.get(i).getSenderAccount() );
//            map1.put("time", friendList.get(i).getDateTime());
//            map1.put("subject", friendList.get(i).getSubject());
//            map1.put("content", friendList.get(i).getContent());
            String str = new String(friendList.get(i));
            str = str.substring(18,str.length()-2);
            //map1.put("friend",friendList.get(i).getFriendAccount() );
            //map1.put("id",friendList.get(i).getConId() );
            map1.put("friend",str );
            list.add(map1);
        }


        return list;
    }

    boolean isNull(String str){
        if(str == null ||str.length()==0)return true;
        return false;
    }

    public class MyThread implements Runnable{
        @Override
        public void run() {
            System.out.println("通讯录获取开始了");

            friendListString = GetFriend.getFriend(user);

            flag = 1;

        }
    }


}
