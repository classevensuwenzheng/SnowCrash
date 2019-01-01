package cn.edu.hebtu.software.snowcarsh2.adapter.fragment2;





import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.edu.hebtu.software.snowcarsh2.R;
import cn.edu.hebtu.software.snowcarsh2.bean.say;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeAdapter extends BaseQuickAdapter<say,BaseViewHolder> {

    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, say item) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.date, item.getDate());
        helper.setText(R.id.content,item.getContent());
        helper.setText(R.id.title,item.getTitle());

//        helper.setImageResource(R.id.icon_image, item.getIcon());
//        helper.setImageResource(R.id.img1, item.getA1());
//        helper.setImageResource(R.id.img2, item.getA2());
//        helper.setImageResource(R.id.img3, item.getA3());



        // 加载网络图片
        //Glide.with(mContext).load(item.getTitle()).into((ImageView) helper.getView(R.id.img));

        Glide.with(mContext).load(item.getIcon()).into((CircleImageView) helper.getView(R.id.icon_image));

        Glide.with(mContext).load(item.getImg()).into((ImageView) helper.getView(R.id.img));





    }

}
