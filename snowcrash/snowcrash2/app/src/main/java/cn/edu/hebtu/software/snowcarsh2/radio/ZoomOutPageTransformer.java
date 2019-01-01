package cn.edu.hebtu.software.snowcarsh2.radio;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final String TAG = "ZoomOutPageTransformer";
    //控制缩放比例
    private static final float MAX_SCALE =1f;
    private static final float MIN_SCALE =0.8f;

    @Override
    public void transformPage(@NonNull View view, float v) {
        if(v<=1){
            float scaleFactor = MIN_SCALE +(1-Math.abs(v))*(MAX_SCALE-MIN_SCALE);
            view.setScaleX(scaleFactor);
            if (v>0){
                view.setTranslationX(-scaleFactor*2);
            }else if(v<0){
                view.setTranslationX(scaleFactor*2);
            }
            view.setScaleY(scaleFactor);
        }else {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        }
    }
}
