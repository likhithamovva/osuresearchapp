package edu.okstate.osuresearch.Contact;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by krishna on 7/22/2017.
 */

public class EqualWidthHeightTextView extends AppCompatTextView {

    public EqualWidthHeightTextView(Context context) {
        super(context);
    }

    public EqualWidthHeightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EqualWidthHeightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*
        DisplayMetrics metrics = new DisplayMetrics();
        getDisplay().getMetrics(metrics);
        int padding = 30;
        int r = (metrics.widthPixels/3)-padding;
        setMeasuredDimension(r, r);
        */

        int h = this.getMeasuredHeight();
        int w = this.getMeasuredWidth();
        int r = Math.max(w, h);

        setMeasuredDimension(r, r);
    }
}