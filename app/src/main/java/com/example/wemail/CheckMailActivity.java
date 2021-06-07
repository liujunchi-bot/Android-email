package com.example.wemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckMailActivity extends AppCompatActivity {

    private Button btnResponse;
    private Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_mail);

        final String from=getIntent().getStringExtra("from");
        final String subject=getIntent().getStringExtra("subject");
        final String content=getIntent().getStringExtra("content");

        ((EditText)findViewById(R.id.from)).setText(from);
        ((EditText)findViewById(R.id.subject)).setText(subject);
        ((EditText)findViewById(R.id.content)).setText(content);

        btnResponse=findViewById(R.id.btn_response);
        btnResponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转到发送内部邮件界面
                Intent intent = new Intent(CheckMailActivity.this,SendMail2Activity.class);
                Bundle bundle = new Bundle();
                String username = getIntent().getStringExtra("username");
                intent.putExtra("username",username);
                intent.putExtra("to",from);
                intent.putExtra("subject",subject);

                startActivity(intent);
            }
        });

        btnQuit=findViewById(R.id.btn_quit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转到发送内部邮件界面
                CheckMailActivity.this.finish();
            }
        });



    }
}
