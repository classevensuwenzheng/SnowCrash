package cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment5;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.bean.Video2;

public class Video2Adapter extends BaseQuickAdapter<Video2,BaseViewHolder> {
    public Video2Adapter(int layoutResId, @Nullable List<Video2> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video2 item) {
        helper.setText(R.id.tv_msg, item.getTitle());
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.iv_pic));
    }
}
