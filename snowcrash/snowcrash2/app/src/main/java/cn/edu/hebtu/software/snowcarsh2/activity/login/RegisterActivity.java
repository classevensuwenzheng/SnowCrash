package cn.edu.hebtu.software.snowcarsh2.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import cn.edu.hebtu.software.snowcarsh2.MainActivity;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

import cn.edu.hebtu.software.snowcarsh2.R;

public class RegisterActivity extends AppCompatActivity {
    //获取电话输入框
    private EditText phoneET;

    //电话号码
    private String phone;

    //获取短信验证码按钮
    private Button getVerificationCodeBtu;

    //获取验证码
    private EditText codeET;

    //获取用户名
    private EditText anotherNameET;

    //获取密码
    private EditText pwdET;

    //注册按钮
    private Button registerBtu;

    //协议条款
    private TextView tv2;
    private TextView tv4;
    private TextView tv4_2;

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneET = findViewById(R.id.et_phone);
        getVerificationCodeBtu = findViewById(R.id.btn_getVerificationCode);
        codeET =findViewById(R.id.et_code);
        anotherNameET=findViewById(R.id.et_anotherName);
        pwdET=findViewById(R.id.et_pwd);
        registerBtu=findViewById(R.id.btn_register);
        linearLayout = findViewById(R.id.rel);

        getVerificationCodeBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneET.getText().toString().length()!=11){
                    //手机号格式不正确，弹出来不正确的消息
                    Log.e(TAG, "onClick: 手机号格式不正确" );
                }else{
                    //获得验证码
                    Log.e(TAG, "onClick: 获得验证码" );
                    SMSSDK.getVerificationCode("86", phoneET.getText().toString());
                    phone = phoneET.getText().toString();
                }
            }
        });
        registerBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SMSSDK.submitVerificationCode("86", phone, codeET.getText().toString());

            }
        });

        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
    }


    private void showPopupWindow(){
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        View view = getLayoutInflater().inflate(R.layout.activity_mes,null);
        view.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY,0,0);
    }

    //获得短信线程
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result" + event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
//                    textV.setText(data.toString());
                }

            } else {
//				((Throwable) data).printStackTrace();
//				Toast.makeText(MainActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
//					Toast.makeText(MainActivity.this, "123", Toast.LENGTH_SHORT).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private static final String TAG = "RegisterActivity";
}

