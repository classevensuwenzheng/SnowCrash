package cn.edu.hebtu.software.snowcarsh2.adapter.childrenfragment3;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.bean.DataRead;

public class ReadAdapter extends BaseQuickAdapter<DataRead,BaseViewHolder> {
    public ReadAdapter(int layoutResId, @Nullable List<DataRead> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DataRead item) {
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.info, item.getInfo());
        helper.setText(R.id.date, item.getDate());

        //helper.setImageResource(R.id.radio_image, item.getImgPic());
        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.img));
    }


}

