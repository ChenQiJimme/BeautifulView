package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/7/18.
 * @Package_name: BueatifulView
 */
public class IponeButton extends View {
    private Paint circlePaint;
    private Paint outSidePaint;
    private Paint onePaint;
    private int bodyCenterX, bodyCenterY;
    private int centerX = 50;
    private int centerY = 50;
    private boolean isDown = false;
    private int times = 0;
    private boolean isUp;
    private int downtimes;
    private int inSideLength = 0;
    private int inSideLength2 = 0;
    private int width, height;
    private int moveX;
    private int outSideWidth = 50;
    private int circleWidth = 30;
    private int length = 0;

    public IponeButton(Context context) {
        super(context);
        init();
    }

    public IponeButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 设置画笔遮罩滤镜  ,传入度数和样式
        circlePaint.setColor(Color.WHITE);
        circlePaint.setShadowLayer(5, 0, 0, Color.BLACK);
        circlePaint.setStrokeWidth(circleWidth);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
//        // 设置画笔遮罩滤镜 ,传入度数和样式
//        circlePaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));
//        circlePaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));

        outSidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outSidePaint.setColor(getResources().getColor(R.color.gray_D1D1D1));
        outSidePaint.setStrokeCap(Paint.Cap.ROUND);
        outSidePaint.setStrokeWidth(outSideWidth);


        onePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        onePaint.setColor(getResources().getColor(R.color.blue_0097e5));
        onePaint.setStrokeCap(Paint.Cap.ROUND);
        onePaint.setStrokeWidth(outSideWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        bodyCenterX = width / 2;
        bodyCenterY = height / 2;
    }

    int grayX;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int outSideStartX = outSideWidth / 2;
        int outSideStopX = width - outSideWidth / 2;

        if (isUp) {
            if (inSideLength > 0) {
                inSideLength = inSideLength - 5;
            }
            if (inSideLength2 > 0) {
                inSideLength2 = inSideLength2 - 5;
            }
            if (times % 2 != 0) {
                if (moveX < (outSideStopX - outSideStartX)) {
                    moveX = moveX + 10;
                } else {
                    moveX = (outSideStopX - outSideStartX);
                    outSidePaint.setColor(Color.GREEN);
                    isUp = false;
                }
                if (grayX < outSideWidth) {
                    grayX = grayX + 10;
                }
                if (length < bodyCenterY) {
                    length = length + 10;
                }
                //绿色出来
                postInvalidateDelayed(10L);
            } else {
                if (moveX > 0) {
                    moveX = moveX - 10;
                } else {
                    moveX = 0;
                    isUp = false;
                    outSidePaint.setColor(getResources().getColor(R.color.gray_D1D1D1));
                }
                if (grayX > 0) {
                    grayX = grayX - 10;
                }
                if (length > 0) {
                    length = length - 10;
                }
                postInvalidateDelayed(10L);
            }
        }


        if (isDown) {
            if (downtimes % 2 != 0) {
                if (grayX < outSideWidth) {
                    grayX = grayX + 10;
                }
                if (length < bodyCenterY) {
                    length = length + 10;
                }
                inSideLength2 = 0;
                if (inSideLength < 30) {
                    inSideLength = inSideLength + 5;
                }
            } else {
                inSideLength = 0;
                if (inSideLength2 < 30) {
                    inSideLength2 = inSideLength2 + 5;
                }
            }
            postInvalidateDelayed(10L);
        }

        canvas.drawLine(outSideStartX, bodyCenterY, outSideStopX, bodyCenterY, outSidePaint);


        if (outSideWidth - grayX != 0) {
            onePaint.setStrokeWidth(outSideWidth - grayX);
            System.out.println("chenqi grayX" + grayX);
            canvas.drawLine(outSideStartX + length, bodyCenterY, outSideStopX - length, bodyCenterY, onePaint);
        }

        int circleStartX = (outSideWidth / 2) + moveX - inSideLength2;
        int circleStopX = (outSideWidth / 2) + 1 + moveX + inSideLength;
        int circleStopY = bodyCenterY;
        int circleStartY = bodyCenterY;
        canvas.drawLine(circleStartX, circleStartY, circleStopX, circleStopY, circlePaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                downtimes++;
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                isUp = true;
                times++;
                break;
        }
        invalidate();
        return true;
    }
}
