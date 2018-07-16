package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/7/11.
 * @Package_name: BueatifulView
 */
public class RipplingView extends View {
    private Paint paint;
    private Paint insidePaint;
    private Paint gudPaint;
    private int width;
    private int height;
    private int isUp = -1;
    private boolean isLongOnClick = false;

    public RipplingView(Context context) {
        super(context);
        init(context);
    }

    public RipplingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setARGB(30, 5, 5, 5);
        paint.setAntiAlias(true);
        insidePaint = new Paint();
        insidePaint.setARGB(50, 5, 5, 5);
        insidePaint.setAntiAlias(true);
        gudPaint = new Paint();
        gudPaint.setAntiAlias(true);
        gudPaint.setColor(context.getResources().getColor(R.color.gray_c0));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(200, 200);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }


    int radius = 0;
    int insideRadius = 0;
    int centerX = 0;
    int centerY = 0;

    int size = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        rect.left = 0;
        rect.right = width;
        rect.top = 0;
        rect.bottom = height;

        canvas.drawRect(rect, gudPaint);


        // 构造一个matrix，x坐标缩放0.5
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1f);
        System.out.println("chenqi isup" + isLongOnClick + "radius= " + radius + " inside = " + insideRadius);
        if (isLongOnClick) {
            //长按
            one();
            two();

        } else {
            //没有长按
            one();
        }
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        canvas.drawCircle(width / 2, height / 2, insideRadius, insidePaint);
    }

    private void one() {
        if (isUp == 0) {
            if (radius < 200) {
                radius += 10;
                postInvalidateDelayed(10L);
            } else {
                radius = 200;
            }

        } else if (isUp == 1) {
            if (insideRadius == 0) {
                if (radius > 0) {
                    radius -= 10;
                    postInvalidateDelayed(1L);
                } else {
                    radius = 0;
                }
            }
        }
    }

    private void two() {
        if (isUp == 0) {
            if (insideRadius < 200) {
                insideRadius += 10;
                postInvalidateDelayed(10L);
            } else {
                insideRadius = 200;
            }

        } else if (isUp == 1) {
            if (insideRadius > 0) {
                insideRadius -= 20;
                postInvalidateDelayed(1L);
            } else {
                insideRadius = 0;
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isLongOnClick = false;
                isUp = 0;
                centerX = (int) event.getX();
                centerY = (int) event.getY();
                postDelayed(runnable, 500);
                break;
//            case MotionEvent.ACTION_MOVE:
//                centerX = (int) event.getX();
//                centerY = (int) event.getY();
//                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(runnable);
                isUp = 1;
                break;
        }
        invalidate();
        return true;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isLongOnClick = true;
        }
    };
}
