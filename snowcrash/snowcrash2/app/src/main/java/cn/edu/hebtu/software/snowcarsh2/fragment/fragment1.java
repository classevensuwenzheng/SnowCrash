package cn.edu.hebtu.software.snowcarsh2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.edu.hebtu.software.snowcarsh2.R;


public class fragment1 extends Fragment {
    private TabLayout tabLayout = null;

    private ViewPager viewPager;

    private Fragment[] mFragmentArrays = new Fragment[5];

    private String[] mTabTitles = new String[5];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        tabLayout = (TabLayout)view.findViewById(R.id.tablayout);
        viewPager = (ViewPager)view.findViewById(R.id.tab_viewpager);
        initView();
        return view;
    }
    private void initView() {
        mTabTitles[0] = "首页";
        mTabTitles[1] = "新闻";
        mTabTitles[2] = "文章";
        mTabTitles[3] = "电台";
        mTabTitles[4] = "视频";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mFragmentArrays[0] = ChildrenFragment1.newInstance();
        mFragmentArrays[1] = ChildrenFragment2.newInstance();
        mFragmentArrays[2] = ChildrenFragment3.newInstance();
        mFragmentArrays[3] = ChildrenFragment4.newInstance();
        mFragmentArrays[4] = ChildrenFragment5.newInstance();
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //将ViewPager和TabLayout绑定

        tabLayout.setupWithViewPager(viewPager);

    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }
}
