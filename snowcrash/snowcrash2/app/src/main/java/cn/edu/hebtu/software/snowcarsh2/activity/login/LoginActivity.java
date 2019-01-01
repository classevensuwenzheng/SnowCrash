package cn.edu.hebtu.software.snowcarsh2.activity.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.hebtu.software.snowcarsh2.MainActivity;
import cn.edu.hebtu.software.snowcarsh2.R;

public class LoginActivity extends AppCompatActivity {
    //用户信息
    private EditText usernameET;
    private EditText passwordET;

    //登陆
    private Button loginBtn;
    //关闭
    private Button closeBtn;
    //注册
    private Button registBtn;
    //遇到其他问题(忘记密码,忘记登录名,联系客服,关闭)
    private Button queBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //绑定
        findView();

    }
    //绑定控件
    private void findView(){
        usernameET=findViewById(R.id.et_username);
        passwordET=findViewById(R.id.et_password);
        loginBtn=findViewById(R.id.btn_login);
        queBtn=findViewById(R.id.btn_que);
        closeBtn = findViewById(R.id.btn_close);
        registBtn = findViewById(R.id.btn_register);


        //按钮绑定事件
        usernameET.setOnKeyListener(new getFocus());
        passwordET.setOnKeyListener(new getFocus());
        queBtn.setOnClickListener(new setOnClickListener());
        closeBtn.setOnClickListener(new setOnClickListener());
        registBtn.setOnClickListener(new setOnClickListener());
        loginBtn.setOnClickListener(new setOnClickListener());
    }


    //触发按钮函数
    public class setOnClickListener implements  View.OnClickListener{
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_que:
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(LoginActivity.this);
                    final String[] arrayOfString = { "忘记密码", "忘记用户名",  "咨询客服" };
                    localBuilder.setTitle("您遇到了什么问题").setIcon(R.mipmap.ic_launcher).setItems(arrayOfString, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                        {
                            /**
                             * 操作
                             * */
                            Toast.makeText(LoginActivity.this, "你选择了： " + arrayOfString[paramAnonymousInt], Toast.LENGTH_SHORT).show();
                            /**
                             * 列表对话框不加这句，点击选择后也后不会消失
                             * */
                            paramAnonymousDialogInterface.dismiss();
                        }
                    }).create().show();
                    break;
                case R.id.btn_register:
                    register();
                    break;
                case R.id.btn_login:
                    //boolean返回值验证用户名,密码是否正确

                    checkUP();

                    break;
                case R.id.btn_close:
//                    Intent intent = new Intent(MainActivity.this,TabhostActivity.class);
//                    startActivity(intent);
//                    finish();
            }
        }
    }

    private void register(){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivityForResult(intent,1);
    }
    //和数据库交互检查用户名密码是否匹配(String name ,String password)
    private boolean checkUP(){
        if(usernameET.getText().toString().equals("")|| passwordET.getText().toString().equals("")){
            Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }else{

            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("username",usernameET.getText().toString());
            startActivity(intent);

        }


        return false;
    }


    //对用户名,密码输入框的判断
    public class  getFocus implements  View.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            String username1 = usernameET.getText().toString();
            String password1 = passwordET.getText().toString();
            switch (v.getId()){
                case R.id.et_username:
                    if(keyCode==67){
                        if ("".equals(username1)|| "".equals( password1)) {
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        }else{
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorTomato));
                        }
                    }else{
                        if ("".equals(username1)|| "".equals( password1)) {
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        }else {
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorTomato));
                        }
                    }
                    break;
                case R.id.et_password:
                    if(keyCode==67){
                        if ("".equals(username1)||"".equals( password1)) {
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        }else{
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorTomato));
                        }
                    }else{
                        if ("".equals(username1)|| "".equals( password1)) {
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorGray));
                        }else {
                            loginBtn.setBackgroundColor(getResources().getColor(R.color.colorTomato));
                        }
                    }
                    break;
            }
            return false;
        }
    }
}

