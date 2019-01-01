package cn.edu.hebtu.software.snowcarsh2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.activity.fragment3.fragment3Activity;
import cn.edu.hebtu.software.snowcarsh2.adapter.fragment3.BaseRecycleAdapter;
import cn.edu.hebtu.software.snowcarsh2.adapter.fragment3.SeachRecordAdapter;
import cn.edu.hebtu.software.snowcarsh2.db.fragment3.DbDao;


public class fragment3 extends Fragment {
    private String[] mStrs = {"主流孩子", "王者荣耀", "刺激战场", "破解"};
    private SearchView mSearchView;
    private ListView mListView;
    private RecyclerView mRecyclerView;
    private TextView mtv_deleteAll;
    private SeachRecordAdapter mAdapter;
    private DbDao mDbDao;
    private View view;
    private String search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment3,container,false);
        mSearchView = (SearchView) view.findViewById(R.id.searchView);


        //设置我们的SearchView
        mSearchView.setIconifiedByDefault(true);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
        mSearchView.onActionViewExpanded();// 写上此句后searchView初始是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
        mSearchView.requestFocus();//输入焦点
        mSearchView.setSubmitButtonEnabled(true);//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
        mSearchView.setFocusable(true);//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        mSearchView.setIconified(false);//输入框内icon不显示
        mSearchView.requestFocusFromTouch();//模拟焦点点击事件

        mSearchView.setFocusable(false);//禁止弹出输入法，在某些情况下有需要
        mSearchView.clearFocus();//禁止弹出输入法，在某些情况下有需要






        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mStrs));
        mListView.setTextFilterEnabled(true);
        initViews();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSearchView.setQuery(mStrs[position],true);
            }
        });

        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() != 0) {
                    boolean hasData = mDbDao.hasData(query.trim());
                    if (!hasData) {
                        mDbDao.insertData(query.trim());
                    } else {

                    }

                    //
                    mAdapter.updata(mDbDao.queryData(""));

                } else {

                }

                Intent intent = new Intent(getContext(),fragment3Activity.class);
                intent.putExtra("data",search);
                startActivity(intent);
                return false;
            }


            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    mListView.setFilterText(newText);
                } else {
                    mListView.clearTextFilter();
                }

                if(!newText.equals("")&&!newText.isEmpty())
                {
                    search = new String(newText.toString());

                }
                return false;
            }
        });
        return view ;
    }
    private void initViews() {
        mDbDao =new DbDao(getContext());
        mtv_deleteAll = (TextView) view.findViewById(R.id.tv_deleteAll);
        mtv_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbDao.deleteData();
                mAdapter.updata(mDbDao.queryData(""));
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter =new SeachRecordAdapter(mDbDao.queryData(""),getContext());
        mAdapter.setRvItemOnclickListener(new BaseRecycleAdapter.RvItemOnclickListener() {
            @Override
            public void RvItemOnclick(int position) {
                mDbDao.delete(mDbDao.queryData("").get(position));

                mAdapter.updata(mDbDao.queryData(""));
            }
        });
        mRecyclerView.setAdapter(mAdapter);

    }
}
