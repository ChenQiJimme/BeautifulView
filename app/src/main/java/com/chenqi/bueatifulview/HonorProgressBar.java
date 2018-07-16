package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/7/16.
 * @Package_name: BueatifulView
 */
public class HonorProgressBar extends View {
    private Paint paint;
    private int w, h;
    private int[] radius = new int[]{10, 8, 6, 4, 2};
    private int rotateRadius = 50;
    private int rotatePregress = 0;
    private int interval = 0;

    public HonorProgressBar(Context context) {
        super(context);
        init();
    }

    public HonorProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        setLayerType(View.LAYER_TYPE_SOFTWARE, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = getMeasuredWidth();
        h = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = w / 2;
        int centerY = h / 2;

        if (rotatePregress == 0 || rotatePregress % 360 != 0) {
            rotatePregress = rotatePregress + 20;
            System.out.println("chenqi fdsaf rotatePregress" + rotatePregress);
            interval = interval - 2;//扩大
        } else if (rotatePregress % 360 == 0) {
            interval = interval + 3;//缩小
            if (interval > 0) {
                interval = 0;
                rotatePregress = rotatePregress + 20;
            }
        }
        canvas.rotate(rotatePregress, centerX, centerY);

        for (int i = 0; i < radius.length; i++) {
            canvas.rotate(interval, centerX, centerY);
            canvas.drawCircle(centerX, centerY - rotateRadius, radius[i], paint);
        }

        postInvalidateDelayed(10L);
    }
}
