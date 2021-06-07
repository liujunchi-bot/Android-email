package com.example.wemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.MailHelp;
import entity.User;
import util.ReadMail;

public class ReadMailActivity extends AppCompatActivity {//发件箱

    private ListView listview;
    private User user=new User();
    private String mailListString=null;
    private int flag=0;//是否加载完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {//发件箱
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listview = (ListView)findViewById(R.id.mylistview);

//        List<Map<String,Object>> mailList = new ArrayList<Map<String,Object>>();
//        mailList = putData();

        flag = 0;
        //从上个界面传入的
        String username = getIntent().getStringExtra("username");
        user.setAccount(username);

        final List<Map<String,Object>> mailList = putData();

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,mailList,R.layout.list_item,
                new String[]{"to","time","content"},new int[]{R.id.from,R.id.time,R.id.content});
        listview.setAdapter(simpleAdapter);

        //设置监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), "第"+position+"个项", Toast.LENGTH_SHORT).show();
//                System.out.println(mailList.get(position).get("from"));

                Intent intent = new Intent(ReadMailActivity.this, CheckMailActivity.class);

                Map<String,Object> map = new HashMap<String,Object>();
                map = mailList.get(position);
                intent.putExtra("from",map.get("to").toString());
//                intent.putExtra("time",map.get("time").toString());
                intent.putExtra("content",map.get("content").toString());
                intent.putExtra("subject",map.get("subject").toString());
                startActivity(intent);

            }
        });

    }

    public List<Map<String,Object>> putData(){


        if(isNull(user.getAccount())){
//            Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "发件箱为空", Toast.LENGTH_SHORT).show();
        }
        else {
            //连接服务器，读取发件箱邮件
            new Thread(new ReadMailActivity.MyThread()).start();

        }

        while(flag==0){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(flag);
        }
        System.out.println("发件箱完成获取");

        List<MailHelp> mailHelpList = new ArrayList<MailHelp>();
        mailHelpList = JSONObject.parseArray(mailListString, MailHelp.class);


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

        System.out.println(mailHelpList.size());
        for (int i = 0; i < mailHelpList.size(); i++) {
            System.out.println(mailHelpList.get(i).getMailId());
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("from",mailHelpList.get(i).getSenderAccount() );
            map1.put("to",mailHelpList.get(i).getReceiverAccount() );
            map1.put("time", mailHelpList.get(i).getDateTime());
            map1.put("subject", mailHelpList.get(i).getSubject());
            map1.put("content", mailHelpList.get(i).getContent());
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
            System.out.println("发件箱获取开始了");

            mailListString = ReadMail.readMail(user);

            flag = 1;

        }
    }
}
