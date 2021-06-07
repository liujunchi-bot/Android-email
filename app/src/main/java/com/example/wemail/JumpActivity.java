package com.example.wemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import util.isFastUtils;

public class JumpActivity extends AppCompatActivity implements View.OnClickListener{
    private String username;
    private String nickname;
    private String role;

    private Button btn_send_mail;
    private Button btn_send_mail2;
    private Button btn_nickname;
    private Button btn_read_mail;
    private Button btn_read_inner_mail;
    private Button btn_black_list;
    private Button btn_friend;
    private Button btn_set_friend;
    private Button btn_get_black_list;
    private Button btn_parameters;


    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        bundle = savedInstanceState;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);


        btn_send_mail = findViewById(R.id.btn_send_mail);
        btn_send_mail.setOnClickListener(this);
        btn_send_mail2 = findViewById(R.id.btn_send_mail2);
        btn_send_mail2.setOnClickListener(this);
        btn_nickname = findViewById(R.id.btn_nickname);
        btn_nickname.setOnClickListener(this);
        btn_parameters = findViewById(R.id.btn_parameters);
        btn_parameters.setOnClickListener(this);
        btn_read_mail = findViewById(R.id.btn_read_mail);
        btn_read_mail.setOnClickListener(this);
        btn_read_inner_mail = findViewById(R.id.btn_read_inner_mail);
        btn_read_inner_mail.setOnClickListener(this);
        btn_black_list = findViewById(R.id.btn_black_list);
        btn_black_list.setOnClickListener(this);
        btn_friend = findViewById(R.id.btn_friend);
        btn_friend.setOnClickListener(this);
        btn_set_friend = findViewById(R.id.btn_set_friend);
        btn_set_friend.setOnClickListener(this);
        btn_get_black_list = findViewById(R.id.btn_get_black_list);
        btn_get_black_list.setOnClickListener(this);

        username = getIntent().getStringExtra("username");
        role = getIntent().getStringExtra("role");
        nickname = getIntent().getStringExtra("nickname");
        System.out.println("username="+username);
        System.out.println("role="+role);
        System.out.println("nickname="+nickname);
        ((TextView)findViewById(R.id.text)).setText("欢迎您，" + nickname);


    }


    @Override
    public void onClick(View v) {
        //多个按钮进行分类
        if (isFastUtils.isFastClick()) {
            switch (v.getId()) {
                case R.id.btn_send_mail:
                    //发送代理邮箱
                    sendMail();
                    break;
                case R.id.btn_send_mail2:
                    //到达内部发送界面
                    sendInnerMail();
                    break;
                case R.id.btn_nickname:
                    //到达修改昵称界面
                    nickname();
                    break;
                case R.id.btn_parameters:
                    //到达修改通讯录界面
                    changeParameters();
                    break;
                case R.id.btn_read_mail:
                    //到达已发邮箱界面
                    readMail();
                    break;
                case R.id.btn_read_inner_mail:
                    //到达收件箱界面
                    readInnerMail();
                    break;
                case R.id.btn_get_black_list:
                    //到达获取黑名单界面
                    getBlackList();
                    break;
                case R.id.btn_black_list:
                    //到达修改黑名单界面
                    blackList();
                    break;
                case R.id.btn_friend:
                    //到达通讯录界面
                    friendList();
                    break;
                case R.id.btn_set_friend:
                    //到达修改通讯录界面
                    setFriend();
                    break;

            }
        }
    }

    private void sendMail() {
        //转到发送代理界面
        Intent intent = new Intent(JumpActivity.this,SendMailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void sendInnerMail() {
        //转到发送内部邮件界面
        Intent intent = new Intent(JumpActivity.this,SendMail2Activity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void nickname() {
        //转到修改昵称界面
        Intent intent = new Intent(JumpActivity.this,NicknameActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void changeParameters() {
        //转到修改昵称界面
        if("2".equals(role)){
            Intent intent = new Intent(JumpActivity.this,ManageActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtra("username",username);
            intent.putExtra("nickname",nickname);
            intent.putExtra("role",role);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "抱歉，你的权限不够", Toast.LENGTH_SHORT).show();
        }
    }

    private void readMail() {
        //到达已发邮箱界面
        Intent intent = new Intent(JumpActivity.this,ReadMailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void readInnerMail() {
        //到达收件箱界面
        Intent intent = new Intent(JumpActivity.this,ReadInnerMailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void blackList() {
        //到达加入黑名单界面
        Intent intent = new Intent(JumpActivity.this,BlackListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void friendList() {
        //到达通讯录界面
        Intent intent = new Intent(JumpActivity.this,SendMailToFriend.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void setFriend() {
        //到达设置通讯录界面
        Intent intent = new Intent(JumpActivity.this,AddFriendActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void getBlackList() {
        //到达设置通讯录界面
        Intent intent = new Intent(JumpActivity.this,GetBlackListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

}
