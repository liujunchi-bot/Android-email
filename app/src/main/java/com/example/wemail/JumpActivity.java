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
        ((TextView)findViewById(R.id.text)).setText("????????????" + nickname);


    }


    @Override
    public void onClick(View v) {
        //????????????????????????
        if (isFastUtils.isFastClick()) {
            switch (v.getId()) {
                case R.id.btn_send_mail:
                    //??????????????????
                    sendMail();
                    break;
                case R.id.btn_send_mail2:
                    //????????????????????????
                    sendInnerMail();
                    break;
                case R.id.btn_nickname:
                    //????????????????????????
                    nickname();
                    break;
                case R.id.btn_parameters:
                    //???????????????????????????
                    changeParameters();
                    break;
                case R.id.btn_read_mail:
                    //????????????????????????
                    readMail();
                    break;
                case R.id.btn_read_inner_mail:
                    //?????????????????????
                    readInnerMail();
                    break;
                case R.id.btn_get_black_list:
                    //???????????????????????????
                    getBlackList();
                    break;
                case R.id.btn_black_list:
                    //???????????????????????????
                    blackList();
                    break;
                case R.id.btn_friend:
                    //?????????????????????
                    friendList();
                    break;
                case R.id.btn_set_friend:
                    //???????????????????????????
                    setFriend();
                    break;

            }
        }
    }

    private void sendMail() {
        //????????????????????????
        Intent intent = new Intent(JumpActivity.this,SendMailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void sendInnerMail() {
        //??????????????????????????????
        Intent intent = new Intent(JumpActivity.this,SendMail2Activity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void nickname() {
        //????????????????????????
        Intent intent = new Intent(JumpActivity.this,NicknameActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void changeParameters() {
        //????????????????????????
        if("2".equals(role)){
            Intent intent = new Intent(JumpActivity.this,ManageActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtra("username",username);
            intent.putExtra("nickname",nickname);
            intent.putExtra("role",role);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "???????????????????????????", Toast.LENGTH_SHORT).show();
        }
    }

    private void readMail() {
        //????????????????????????
        Intent intent = new Intent(JumpActivity.this,ReadMailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void readInnerMail() {
        //?????????????????????
        Intent intent = new Intent(JumpActivity.this,ReadInnerMailActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void blackList() {
        //???????????????????????????
        Intent intent = new Intent(JumpActivity.this,BlackListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void friendList() {
        //?????????????????????
        Intent intent = new Intent(JumpActivity.this,SendMailToFriend.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void setFriend() {
        //???????????????????????????
        Intent intent = new Intent(JumpActivity.this,AddFriendActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

    private void getBlackList() {
        //???????????????????????????
        Intent intent = new Intent(JumpActivity.this,GetBlackListActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("username",username);
        intent.putExtra("nickname",nickname);
        startActivity(intent);
    }

}
