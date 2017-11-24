package tv.live.bx.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class AutoHeightGridView extends GridView {
    public AutoHeightGridView(Context context) {
        super(context);
    }

    public AutoHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHeightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getLayoutParams() != null && getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
            // 使GridView支持wrap_content的高度
            // see http://www.jayway.com/2012/10/04/how-to-make-the-height-of-a-gridview-wrap-its-content/
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}