package cn.edu.hebtu.software.snowcarsh2.activity.startActvity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import cn.edu.hebtu.software.snowcarsh2.MainActivity;
import cn.edu.hebtu.software.snowcarsh2.R;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉头部的标题
        setContentView(R.layout.activity_choose);
        Intent intent=getIntent();
        int type=intent.getIntExtra("type",0);
        if(type==1){
            handler2.sendEmptyMessageAtTime(0,  SystemClock.uptimeMillis()+2000);
        }else{
            handler.sendEmptyMessageAtTime(0,  SystemClock.uptimeMillis()+2000);
        }





        // entry = (ImageView) findViewById(R.id.entry);
    }
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };
    private Handler handler2=new Handler(){
        public void handleMessage(Message msg) {
            getHome2();
            super.handleMessage(msg);
        }
    };
    //设置跳转页面
    private void getHome() {
        Intent intent2 = new Intent();
        intent2.setClass(ChooseActivity.this,WelcomeActivity.class);
        startActivity(intent2);
        finish();
    };
    private void getHome2(){
        Intent intent2 = new Intent();
        intent2.setClass(ChooseActivity.this,MainActivity.class);
        startActivity(intent2);
        finish();
    }


}

