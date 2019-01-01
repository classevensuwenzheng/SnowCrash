package cn.edu.hebtu.software.snowcarsh2;



import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import cn.edu.hebtu.software.snowcarsh2.activity.login.LoginActivity;
import cn.edu.hebtu.software.snowcarsh2.bean.IndexHorizontal;
import cn.edu.hebtu.software.snowcarsh2.fragment.fragment1;
import cn.edu.hebtu.software.snowcarsh2.fragment.fragment2;
import cn.edu.hebtu.software.snowcarsh2.fragment.fragment3;
import cn.edu.hebtu.software.snowcarsh2.fragment.fragment4;
import cn.edu.hebtu.software.snowcarsh2.fragment.fragment5;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Map<String,View> tabspecViews=new HashMap<>();
    private Map<String,ImageView> imageViewmap=new HashMap<>();
    private Map<String,TextView>  textViewMap=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        NavigationView navView=(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        //登陆模块嵌入

        View headerLayout = navView.inflateHeaderView(R.layout.nav_header);
        CircleImageView circleImageView=(CircleImageView)headerLayout.findViewById(R.id.icon_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        TextView userView=headerLayout.findViewById(R.id.name);
        if(username!=null&&!username.equals("")){

            userView.setText(username);
        }






        //这是峰哥线--------------------------------------------------








        FragmentTabHost fragmentTabHost=findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabhost);

        TabHost.TabSpec tabSpec1=fragmentTabHost.newTabSpec("tab1")
                .setIndicator(getTabSpecView("推荐",R.drawable.tj2,"tab1"));
        //添加选项卡
        fragmentTabHost.addTab(tabSpec1, fragment1.class,null);

        TabHost.TabSpec tabSpec2=fragmentTabHost.newTabSpec("tab2")
                .setIndicator(getTabSpecView("GTalk",R.drawable.gtalk,"tab2"));
        //添加选项卡
        fragmentTabHost.addTab(tabSpec2, fragment2.class,null);

        TabHost.TabSpec tabSpec3=fragmentTabHost.newTabSpec("tab3")
                .setIndicator(getTabSpecView("搜索",R.drawable.ss,"tab3"));
        //添加选项卡
        fragmentTabHost.addTab(tabSpec3, fragment3.class,null);

        TabHost.TabSpec tabSpec4=fragmentTabHost.newTabSpec("tab4")
                .setIndicator(getTabSpecView("吉考斯",R.drawable.jks,"tab4"));
        //添加选项卡
        fragmentTabHost.addTab(tabSpec4, fragment4.class,null);

        TabHost.TabSpec tabSpec5=fragmentTabHost.newTabSpec("tab5")
                .setIndicator(getTabSpecView("我",R.drawable.w,"tab5"));
        //添加选项卡
        fragmentTabHost.addTab(tabSpec5, fragment5.class,null);

        //设置默认选中
        fragmentTabHost.setCurrentTab(0);
        //设置第二个选项卡背景颜色
//        tabspecViews.get("tab2").setBackgroundColor(getResources()
//                .getColor(android.R.color.darker_gray));
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                    if(tabId.equals("tab1")){
                        ImageView imageView1=imageViewmap.get("tab1");
                        imageView1.setImageResource(R.drawable.tj2);
                        ImageView imageView2=imageViewmap.get("tab2");
                        imageView2.setImageResource(R.drawable.gtalk);
                        ImageView imageView3=imageViewmap.get("tab3");
                        imageView3.setImageResource(R.drawable.ss);
                        ImageView imageView4=imageViewmap.get("tab4");
                        imageView4.setImageResource(R.drawable.jks);
                        ImageView imageView5=imageViewmap.get("tab5");
                        imageView5.setImageResource(R.drawable.w);

                        TextView textView1=textViewMap.get("tab1");
                        textView1.setTextColor(getResources().getColor(R.color.colorPrimary));
                        TextView textView2=textViewMap.get("tab2");
                        textView2.setTextColor(getResources().getColor(R.color.text));
                        TextView textView3=textViewMap.get("tab3");
                        textView3.setTextColor(getResources().getColor(R.color.text));
                        TextView textView4=textViewMap.get("tab4");
                        textView4.setTextColor(getResources().getColor(R.color.text));
                        TextView textView5=textViewMap.get("tab5");
                        textView5.setTextColor(getResources().getColor(R.color.text));


                    }else if(tabId.equals("tab2")){
                        ImageView imageView1=imageViewmap.get("tab1");
                        imageView1.setImageResource(R.drawable.tj);
                        ImageView imageView2=imageViewmap.get("tab2");
                        imageView2.setImageResource(R.drawable.gtalk2);
                        ImageView imageView3=imageViewmap.get("tab3");
                        imageView3.setImageResource(R.drawable.ss);
                        ImageView imageView4=imageViewmap.get("tab4");
                        imageView4.setImageResource(R.drawable.jks);
                        ImageView imageView5=imageViewmap.get("tab5");
                        imageView5.setImageResource(R.drawable.w);



                        TextView textView1=textViewMap.get("tab1");
                        textView1.setTextColor(getResources().getColor(R.color.text));
                        TextView textView2=textViewMap.get("tab2");
                        textView2.setTextColor(getResources().getColor(R.color.colorPrimary));
                        TextView textView3=textViewMap.get("tab3");
                        textView3.setTextColor(getResources().getColor(R.color.text));
                        TextView textView4=textViewMap.get("tab4");
                        textView4.setTextColor(getResources().getColor(R.color.text));
                        TextView textView5=textViewMap.get("tab5");
                        textView5.setTextColor(getResources().getColor(R.color.text));

                    }else if(tabId.equals("tab3")){
                        ImageView imageView1=imageViewmap.get("tab1");
                        imageView1.setImageResource(R.drawable.tj);
                        ImageView imageView2=imageViewmap.get("tab2");
                        imageView2.setImageResource(R.drawable.gtalk);
                        ImageView imageView3=imageViewmap.get("tab3");
                        imageView3.setImageResource(R.drawable.ss2);
                        ImageView imageView4=imageViewmap.get("tab4");
                        imageView4.setImageResource(R.drawable.jks);
                        ImageView imageView5=imageViewmap.get("tab5");
                        imageView5.setImageResource(R.drawable.w);

                        TextView textView1=textViewMap.get("tab1");
                        textView1.setTextColor(getResources().getColor(R.color.text));
                        TextView textView2=textViewMap.get("tab2");
                        textView2.setTextColor(getResources().getColor(R.color.text));
                        TextView textView3=textViewMap.get("tab3");
                        textView3.setTextColor(getResources().getColor(R.color.colorPrimary));
                        TextView textView4=textViewMap.get("tab4");
                        textView4.setTextColor(getResources().getColor(R.color.text));
                        TextView textView5=textViewMap.get("tab5");
                        textView5.setTextColor(getResources().getColor(R.color.text));

                    }else if(tabId.equals("tab4")){
                        ImageView imageView1=imageViewmap.get("tab1");
                        imageView1.setImageResource(R.drawable.tj);
                        ImageView imageView2=imageViewmap.get("tab2");
                        imageView2.setImageResource(R.drawable.gtalk);
                        ImageView imageView3=imageViewmap.get("tab3");
                        imageView3.setImageResource(R.drawable.ss);
                        ImageView imageView4=imageViewmap.get("tab4");
                        imageView4.setImageResource(R.drawable.jks2);
                        ImageView imageView5=imageViewmap.get("tab5");
                        imageView5.setImageResource(R.drawable.w);

                        TextView textView1=textViewMap.get("tab1");
                        textView1.setTextColor(getResources().getColor(R.color.text));
                        TextView textView2=textViewMap.get("tab2");
                        textView2.setTextColor(getResources().getColor(R.color.text));
                        TextView textView3=textViewMap.get("tab3");
                        textView3.setTextColor(getResources().getColor(R.color.text));
                        TextView textView4=textViewMap.get("tab4");
                        textView4.setTextColor(getResources().getColor(R.color.colorPrimary));
                        TextView textView5=textViewMap.get("tab5");
                        textView5.setTextColor(getResources().getColor(R.color.text));

                    }else{
                        ImageView imageView1=imageViewmap.get("tab1");
                        imageView1.setImageResource(R.drawable.tj);
                        ImageView imageView2=imageViewmap.get("tab2");
                        imageView2.setImageResource(R.drawable.gtalk);
                        ImageView imageView3=imageViewmap.get("tab3");
                        imageView3.setImageResource(R.drawable.ss);
                        ImageView imageView4=imageViewmap.get("tab4");
                        imageView4.setImageResource(R.drawable.jks);
                        ImageView imageView5=imageViewmap.get("tab5");
                        imageView5.setImageResource(R.drawable.w2);

                        TextView textView1=textViewMap.get("tab1");
                        textView1.setTextColor(getResources().getColor(R.color.text));
                        TextView textView2=textViewMap.get("tab2");
                        textView2.setTextColor(getResources().getColor(R.color.text));
                        TextView textView3=textViewMap.get("tab3");
                        textView3.setTextColor(getResources().getColor(R.color.text));
                        TextView textView4=textViewMap.get("tab4");
                        textView4.setTextColor(getResources().getColor(R.color.text));
                        TextView textView5=textViewMap.get("tab5");
                        textView5.setTextColor(getResources().getColor(R.color.colorPrimary));

                    }

            }
        });
//        Toast.makeText(MainActivity.this,
//                "您还未登陆，请点绿色头像进行登陆",
//                Toast.LENGTH_LONG).show();
    }
    private View getTabSpecView(String name, int imageId,String tag){
        //LayoutInflater layoutInflater=LayoutInflater.from(this);
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.tabspec_layout,null);
        ImageView imageView=view.findViewById(R.id.image);
        imageView.setImageResource(imageId);
        TextView textView=view.findViewById(R.id.textview);
        textView.setText(name);
        if(tag.equals("tab1")){
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        tabspecViews.put(tag,view);
        imageViewmap.put(tag,imageView);
        textViewMap.put(tag,textView);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(this,"You click Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You click Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this,"You click Settingd",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return  true;
    }





}
