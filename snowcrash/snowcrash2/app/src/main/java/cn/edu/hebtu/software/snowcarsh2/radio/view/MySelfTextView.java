package cn.edu.hebtu.software.snowcarsh2.radio.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


public class MySelfTextView extends android.support.v7.widget.AppCompatTextView {
    public MySelfTextView(Context context) {
        super(context);
    }

    public MySelfTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MySelfTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean isFocused() {
        return true;
    }
}
