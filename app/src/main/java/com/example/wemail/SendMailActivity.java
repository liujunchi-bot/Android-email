package com.example.wemail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.Mail;
import util.SendMail;

public class SendMailActivity extends AppCompatActivity {

    private Button btnSubmit;
    private Mail mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);
        btnSubmit=findViewById(R.id.btn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入
                String from=((EditText)findViewById(R.id.from)).getText().toString();
                String password=((EditText)findViewById(R.id.password)).getText().toString();
                String to=((EditText)findViewById(R.id.to)).getText().toString();
                String subject=((EditText)findViewById(R.id.subject)).getText().toString();
                String content=((EditText)findViewById(R.id.content)).getText().toString();

//                //控制台输出验证
//                System.out.println("from:"+from);
//                System.out.println("password:"+password);
//                System.out.println("to:"+to);
//                System.out.println("subject:"+subject);
//                System.out.println("content:"+content);
//
//                //显示
//                ((EditText)findViewById(R.id.from)).setText("显示");
//                ((EditText)findViewById(R.id.password)).setText("显示");
//                ((EditText)findViewById(R.id.to)).setText("显示");
//                ((EditText)findViewById(R.id.subject)).setText("显示");
//                ((EditText)findViewById(R.id.content)).setText("显示");

//                //直接跳转
//                Intent intent=new Intent(SendMailActivity.this, SendMail2Activity.class);
//                startActivity(intent);

                //从上个界面传入的
                String username = getIntent().getStringExtra("username");

                //可转为TimeStamp，存入数据库的时间格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                mail = new Mail();
                mail.setSenderAccount(from+"=="+password);

                mail.setReceiverAccount(to);
                mail.setDateTime(Timestamp.valueOf(sdf.format(new Date())));
                mail.setSubject(subject);
                mail.setContent(content);




//                //控制台输出验证
//
//                System.out.println("to:"+to);
//                System.out.println("subject:"+subject);
//                System.out.println("content:"+content);
//
//                //显示
//                ((EditText)findViewById(R.id.to)).setText("显示");
//                ((EditText)findViewById(R.id.subject)).setText("显示");
//                ((EditText)findViewById(R.id.content)).setText("显示");
                //验证格式
                if(isNull(to)){
                    Toast.makeText(getApplicationContext(), "收件人为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(subject)){
                    Toast.makeText(getApplicationContext(), "主题为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(content)){
                    Toast.makeText(getApplicationContext(), "内容为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(from)){
                    Toast.makeText(getApplicationContext(), "发送邮箱为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(password)){
                    Toast.makeText(getApplicationContext(), "授权码为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    //连接服务器，把邮件发送
                    new Thread(new SendMailActivity.MyThread()).start();

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
            //Mail mail = new Mail(nickname,username,password);
            String result = SendMail.sendMail(mail);

            if("1".equals(result)){
                //注册成功，跳转到登录界面
                System.out.println("发送成功");
                Intent intent=new Intent(SendMailActivity.this, JumpActivity.class);
                String username = getIntent().getStringExtra("username");
                String nickname = getIntent().getStringExtra("nickname");
                intent.putExtra("username",username);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
            }
            else if("2".equals(result)){
                //已被注册
                System.out.println("发送失败");
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
