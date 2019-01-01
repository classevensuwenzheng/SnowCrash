package cn.edu.hebtu.software.snowcarsh2.activity.startActvity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class JudgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //判断是否首次运行
        SharedPreferences sp = this.getSharedPreferences("sharep", 0);
        int count = sp.getInt("start_count", 0);



        if(count == 0){
            SharedPreferences.Editor editor = sp.edit();
            //存入数据
            editor.putInt("start_count", ++count);
            //提交修改
            editor.commit();
            //    	Toast.makeText(this, "Guide", Toast.LENGTH_SHORT).show();
            Intent gIntent = new Intent();
            gIntent.setClass(JudgeActivity.this, ChooseActivity.class);
            gIntent.putExtra("type",0);
            JudgeActivity.this.startActivity(gIntent);
            JudgeActivity.this.finish();
            //mhandler.sendEmptyMessageDelayed(SWITCH_GUIDE, 100);
        }
        else
        {
            //  	Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(JudgeActivity.this,ChooseActivity.class);
            intent.putExtra("type",1);
            startActivity(intent);
            JudgeActivity.this.finish();

        }
    }
}
