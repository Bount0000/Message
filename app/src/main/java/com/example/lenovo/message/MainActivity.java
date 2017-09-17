package com.example.lenovo.message;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText ed_phone;
    private EditText ed_yzm;
    private TextView tv_send;
    private Button btn_zhuce;
    private EventHandler eventHandler;
    private int num=5;
    private Handler hd=new Handler();
   Runnable r=new Runnable() {
       @Override
       public void run() {
           num--;
          if(num==0)
          {  hd.removeCallbacks(this);
              num=5;
              tv_send.setEnabled(true);
              tv_send.setText("再次发送");
          }
          else
          {  tv_send.setEnabled(false);
             tv_send.setTextColor(Color.RED);
             tv_send.setText(num+"s");
              hd.postDelayed(this,1000);
          }
       }
   };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initView();
        //注册SMS
      // registerSMS();
    }

    private void registerSMS() {

        // 创建EventHandler对象
           eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(MainActivity.this, "服务器验证成功", Toast.LENGTH_SHORT).show();
                          }
                      });
                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }
    private void initView() {
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_yzm = (EditText) findViewById(R.id.ed_yzm);
        tv_send = (TextView) findViewById(R.id.tv_send);
        btn_zhuce = (Button) findViewById(R.id.btn_zhuce);

        btn_zhuce.setOnClickListener(this);
        tv_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
         switch (view.getId())
         {      //发送手机号
                case R.id.tv_send:
                   if(TextUtils.isEmpty(ed_phone.getText().toString()))
                   {
                       Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                        return;
                     }
                 hd.postDelayed(r,1000);
                 SMSSDK.getVerificationCode("86",ed_phone.getText().toString());
               break;
               case R.id.btn_zhuce:
                 verify();
                 SMSSDK.submitVerificationCode("86",ed_phone.getText().toString(),ed_yzm.getText().toString());
                 break;
         }
    }
    private void verify() {
        if(TextUtils.isEmpty(ed_phone.getText().toString()))
        {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(ed_yzm.getText().toString()))
        {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
