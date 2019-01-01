package cn.edu.hebtu.software.snowcarsh2.radio.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.edu.hebtu.software.snowcarsh2.R;


public class ViewPagerAdapter extends PagerAdapter {
//    private int[] mDate;
    private String[]mDate;
    private Context mContext;


    public ViewPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ViewPagerAdapter(Context mContext, String[] mDate) {
        this.mDate = mDate;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDate.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = View.inflate(container.getContext(), R.layout.viewpager_item, null);
        ImageView imageView = view.findViewById(R.id.iv_icon);
//       imageView.setImageResource(mDate[3]);
        Glide.with(imageView.getContext()).load(mDate[position]).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "当前条目：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);//添加到父控件
        return view;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view ==o;// 过滤和缓存的作用
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);//从viewpager中移除掉
    }
}
