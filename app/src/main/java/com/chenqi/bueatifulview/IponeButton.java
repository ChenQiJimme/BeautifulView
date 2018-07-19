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
    /**
     * 内部小圆圈的画笔
     */
    private Paint circlePaint;
    /**
     * 外部边框的画笔
     */
    private Paint outSidePaint;
    /**
     * 覆盖在外部边框的内的画笔
     */
    private Paint onePaint;
    /**
     * 获取到位置坐标
     */
    private int bodyCenterX, bodyCenterY;
    /**
     * 是否已经按下
     */
    private boolean isDown = false;
    /**
     * 是确认还是取消
     */
    private int times = 0;
    /**
     * 是否已经抬起
     */
    private boolean isUp;
    /**
     * 按下的次数
     */
    private int downtimes;
    /**
     * 按下的时候内部的圆圈变长的长度（左边）
     */
    private int inSideLeftChangeLength = 0;
    /**
     * 按下的时候内部的圆圈变长的长度（右边）
     */
    private int inSideRightChangeLength = 0;
    /**
     * 屏幕的宽度和高度
     */
    private int width, height;
    /**
     * 移动的距离
     */
    private int moveDistance;
    /**
     * 外部坐标的半径（画笔的粗细）
     */
    private int outSideWidth = 50;
    /**
     * 内部圆形坐标的半径（画笔的粗细）
     */
    private int circleWidth = 30;
    /**
     * 覆盖在外部背景内的距离
     */
    private int disappearingDistance = 0;

    /**
     * 修改第二张图的画笔的粗细的变化长度
     */
    private int onePaintStrokeWidth;

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
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(circleWidth);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int outSideStartX = outSideWidth / 2;
        int outSideStopX = width - outSideWidth / 2;

        if (isUp) {
            if (inSideLeftChangeLength > 0) {
                inSideLeftChangeLength = inSideLeftChangeLength - 5;
            }
            if (inSideRightChangeLength > 0) {
                inSideRightChangeLength = inSideRightChangeLength - 5;
            }
            if (times % 2 != 0) {
                if (moveDistance < (outSideStopX - outSideStartX)) {
                    moveDistance = moveDistance + 10;
                } else {
                    moveDistance = (outSideStopX - outSideStartX);
                    outSidePaint.setColor(Color.GREEN);
                    isUp = false;
                }
                if (onePaintStrokeWidth < outSideWidth) {
                    onePaintStrokeWidth = onePaintStrokeWidth + 10;
                }
                if (disappearingDistance < bodyCenterY) {
                    disappearingDistance = disappearingDistance + 10;
                }
                //绿色出来
                postInvalidateDelayed(10L);
            } else {
                if (moveDistance > 0) {
                    moveDistance = moveDistance - 10;
                } else {
                    moveDistance = 0;
                    isUp = false;
                    outSidePaint.setColor(getResources().getColor(R.color.gray_D1D1D1));
                }
                if (onePaintStrokeWidth > 0) {
                    onePaintStrokeWidth = onePaintStrokeWidth - 10;
                }
                if (disappearingDistance > 0) {
                    disappearingDistance = disappearingDistance - 10;
                }
                postInvalidateDelayed(10L);
            }
        }


        if (isDown) {
            if (downtimes % 2 != 0) {
                if (onePaintStrokeWidth < outSideWidth) {
                    onePaintStrokeWidth = onePaintStrokeWidth + 10;
                }
                if (disappearingDistance < bodyCenterY) {
                    disappearingDistance = disappearingDistance + 10;
                }
                inSideRightChangeLength = 0;
                if (inSideLeftChangeLength < 30) {
                    inSideLeftChangeLength = inSideLeftChangeLength + 5;
                }
            } else {
                inSideLeftChangeLength = 0;
                if (inSideRightChangeLength < 30) {
                    inSideRightChangeLength = inSideRightChangeLength + 5;
                }
            }
            postInvalidateDelayed(10L);
        }

        canvas.drawLine(outSideStartX, bodyCenterY, outSideStopX, bodyCenterY, outSidePaint);


        if (outSideWidth - onePaintStrokeWidth != 0) {
            onePaint.setStrokeWidth(outSideWidth - onePaintStrokeWidth);
            canvas.drawLine(outSideStartX + disappearingDistance, bodyCenterY, outSideStopX - disappearingDistance, bodyCenterY, onePaint);
        }

        int circleStartX = (outSideWidth / 2) + moveDistance - inSideRightChangeLength;
        int circleStopX = (outSideWidth / 2) + 1 + moveDistance + inSideLeftChangeLength;
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
