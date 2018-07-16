package com.chenqi.bueatifulview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/7/16.
 * @Package_name: BueatifulView
 */
@SuppressLint("AppCompatCustomView")
public class ShadowView extends LinearLayout {
    private int width, height;
    private Paint paint;

    public ShadowView(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @SuppressLint("NewApi")
    public ShadowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint.setColor(getResources().getColor(R.color.blue_0097e5));
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setShadowLayer(5, 2.5f, 5, getResources().getColor(R.color.blue_0097e5));
        RectF rect = new RectF();
        rect.top = 0 + 40;
        rect.left = 0 + 40;
        rect.bottom = height - 40;
        rect.right = width - 40;
        canvas.drawRoundRect(rect,5,5, paint);
    }
}
