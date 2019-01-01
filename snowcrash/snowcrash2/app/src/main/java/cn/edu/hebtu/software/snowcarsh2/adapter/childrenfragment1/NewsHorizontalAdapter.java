package cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment1;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.bean.Home.NewsHorizontal;


public class NewsHorizontalAdapter extends BaseQuickAdapter<NewsHorizontal,BaseViewHolder> {
public NewsHorizontalAdapter(@LayoutRes int layoutResId, @Nullable List<NewsHorizontal> news) {
        super(layoutResId, news);
        }

@Override
protected void convert(BaseViewHolder helper, NewsHorizontal item) {
        helper.setText(R.id.index_horizontal_list_title,item.getNewsTitle());
        helper.setText(R.id.index_horizontal_list_desc,item.getDesc());
        Glide.with(mContext).load(item.getImgUrl()).into((ImageView) helper.getView(R.id.index_horizontal_list_image));
        }
}
