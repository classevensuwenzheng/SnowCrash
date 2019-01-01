package cn.edu.hebtu.software.snowcarsh2.radio.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.hebtu.software.snowcarsh2.R;


public class ViewPagerAdapter1 extends PagerAdapter {
    private Context mContext;
    private String[] mDate1;
    private String[] mDate2;

    public ViewPagerAdapter1(Context mContext, String[] mDate1, String[] mDate2) {
        this.mContext = mContext;
        this.mDate1 = mDate1;
        this.mDate2 = mDate2;
    }

    @Override
    public int getCount() {
        return mDate2.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
       View view=View.inflate(container.getContext(), R.layout.viewpage_text,null);
        TextView textView1=view.findViewById(R.id.jieshao_biaoti);
        textView1.setText(mDate1[position]);
        TextView textView2=view.findViewById(R.id.jieshao_xiangjie);
        textView2.setText(mDate2[position]);
        LinearLayout linearLayout=view.findViewById(R.id.jieshao);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "当前条目：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
