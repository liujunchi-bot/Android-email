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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.Mail;
import util.SendMail2;

public class SendMail2Activity extends AppCompatActivity {

    private Button btnSubmit;
    private Button btn_quit;
    private Mail mail;

    Handler mHandler = new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what==0){
                Toast.makeText(SendMail2Activity.this,"服务器连接失败，请检查网络或者服务器是否开启",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==1){
                Toast.makeText(SendMail2Activity.this,"发送内部邮件成功",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==2){
                Toast.makeText(SendMail2Activity.this,"收件人不存在",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==3){
                Toast.makeText(SendMail2Activity.this,"系统的SMTP已被管理员禁用",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==4){
                Toast.makeText(SendMail2Activity.this,"收件人禁用了POP3",Toast.LENGTH_SHORT).show();
            }
            else if(msg.what==5){
                Toast.makeText(SendMail2Activity.this,"发件人禁用了SMTP",Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail2);

        //获取输入
        String to=getIntent().getStringExtra("to");

        ((EditText)findViewById(R.id.to)).setText(to);

        btnSubmit=findViewById(R.id.btn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入
                String to=((EditText)findViewById(R.id.to)).getText().toString();
                String subject=((EditText)findViewById(R.id.subject)).getText().toString();
                String content=((EditText)findViewById(R.id.content)).getText().toString();
                //从上个界面传入的
                String username = getIntent().getStringExtra("username");

                //可转为TimeStamp，存入数据库的时间格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                mail = new Mail();
                mail.setSenderAccount(username);
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
                    Toast.makeText(getApplicationContext(), "账号为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(subject)){
                    Toast.makeText(getApplicationContext(), "主题为空", Toast.LENGTH_SHORT).show();
                }
                else if(isNull(content)){
                    Toast.makeText(getApplicationContext(), "内容为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    //连接服务器，把邮件发送
                    new Thread(new SendMail2Activity.MyThread()).start();

                }

            }
        });
        btn_quit=findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMail2Activity.this.finish();

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
            String result = SendMail2.sendMail2(mail);

            if("1".equals(result)){
                //发送内部邮件成功，跳转到登录界面
                System.out.println("发送内部邮件成功");
                Intent intent=new Intent(SendMail2Activity.this, JumpActivity.class);
                String username = getIntent().getStringExtra("username");
                String nickname = getIntent().getStringExtra("nickname");
                intent.putExtra("username",username);
                intent.putExtra("nickname",nickname);
                startActivity(intent);
                mHandler.sendEmptyMessage(1);
            }
            else if("2".equals(result)){
                //收件人不存在
                System.out.println("收件人不存在");
                mHandler.sendEmptyMessage(2);
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
            }
            /*新增*/
            else if("3".equals(result)){
                //系统的SMTP已被管理员禁用
                System.out.println("系统的SMTP已被管理员禁用");
                mHandler.sendEmptyMessage(3);
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
            }
            else if("4".equals(result)){
                //收件人禁用了POP3
                System.out.println("收件人禁用了POP3");
                mHandler.sendEmptyMessage(4);
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
            }
            else if("5".equals(result)){
                //发件人禁用了SMTP
                System.out.println("发件人禁用了SMTP");
                mHandler.sendEmptyMessage(5);
//                Toast.makeText(getApplicationContext(), "该账号已被注册", Toast.LENGTH_SHORT).show();
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
