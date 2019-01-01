package cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment2;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.bean.IndexHorizontal;


public class RecyclerAdapter2 extends BaseQuickAdapter<IndexHorizontal,BaseViewHolder> {
    public RecyclerAdapter2(int layoutResId, @Nullable List<IndexHorizontal> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexHorizontal item) {
        //helper.setText(R.id.iv_portrait, item.getImgUrl());
        helper.setText(R.id.tv_nickname,item.getTitle());
        helper.setText(R.id.tv_motto,item.getIntroduce());
        //helper.setImageResource(R.id.radio_image, item.getImgPic());
        Glide.with(mContext).load(item.getImgUrl()).into((ImageView) helper.getView(R.id.iv_portrait));

    }
}
