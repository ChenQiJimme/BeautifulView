package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/7/15.
 * @Package_name: BueatifulView
 */
public class PushRefreshView extends View {
    private Paint circlePaint;
    private Paint drectionPaint;
    private Paint arcPaint;

    private int RADIUS = 50;
    private int circleRadius;
    private int width, height;

    private int downHeight;
    /**/
    private int moveHeight;
    /*上一次移动的位置Y*/
    private int oldMoveHeight;
    /*移动还是抬起*/
    private int ISUP = -1;
    /*初始的Y轴*/
    private int ACTIONHEGHIT = -50;
    /*中心x,y*/
    private int centerX, centerY;
    /*圆弧开始位置*/
    private int arcStart = 0;
    /*圆弧停止位置*/
    private int arcStop = 0;
    /*一次的旋转角度*/
    private int degrees = 10;
    /*最大的旋转角度*/
    private int maxDegrees = 360;
    /*view刷新时间*/
    private long DELAYTIME = 10L;
    /*向下还是向上：0，向下，1，向上*/
    private int direction = 1;
    /*实时的需要绘制的圆弧大小*/
    private int radian = 0;
    /*圆弧最大的角度*/
    private float STOPMAXANGLE = 270;
    /*中间圆弧与移动距离的比例*/
    private float pressent;
    /*是否停止刷新*/
    private boolean stopRefresh;

    private RefreshStatusListener listener;

    public void setListener(RefreshStatusListener listener) {
        this.listener = listener;
    }

    public PushRefreshView(Context context) {
        super(context);
        init();
    }


    public PushRefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);

        drectionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drectionPaint.setColor(Color.RED);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(15f);
        arcPaint.setColor(getResources().getColor(R.color.halfColorAccent));
        arcPaint.setStyle(Paint.Style.STROKE);
        initView();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        centerX = width / 2;
        pressent = STOPMAXANGLE / (height / 5);
        centerY = moveHeight = ACTIONHEGHIT;
        circleRadius = RADIUS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (ISUP == 0) {
            upThing();
        } else if (ISUP == 1) {
            notUpThing();
        }
        canvas.drawCircle(centerX, centerY, circleRadius, circlePaint);
        RectF mArcRecF = new RectF();
        canvas.rotate(degrees, centerX, centerY);

        mArcRecF.left = centerX - (circleRadius / 2);
        mArcRecF.top = centerY - (circleRadius / 2);
        mArcRecF.right = centerX + (circleRadius / 2);
        mArcRecF.bottom = centerY + (circleRadius / 2);
        System.out.println("chenqi set " + " = " + mArcRecF.toString());
        canvas.drawArc(mArcRecF, arcStart, arcStop, false, arcPaint);
    }

    private void notUpThing() {
        if (direction > 0) {
            if (arcStop < STOPMAXANGLE / 3) {
                arcPaint.setColor(getResources().getColor(R.color.halfColorAccent));
            }
            if (arcStop <= STOPMAXANGLE) {
                if (arcStop >= STOPMAXANGLE / 3) {
                    arcPaint.setColor(getResources().getColor(R.color.colorAccent));
                }
                arcStop += radian;
            }
        } else {
            if (arcStop < STOPMAXANGLE / 3) {
                arcPaint.setColor(getResources().getColor(R.color.halfColorAccent));
            }
            if (arcStop > 0) {
                arcStop -= radian;
            }
        }
    }

    private void upThing() {
        if (130 <= centerY && centerY <= 150) {
            centerY = 130;
            if (degrees < maxDegrees) {
                if (arcStop < STOPMAXANGLE) {
                    arcStop += 5;
                }
                degrees += 5;
                postInvalidateDelayed(DELAYTIME);
            } else {
                circleRadius -= 5;
                if (circleRadius > 0) {
                    postInvalidateDelayed(DELAYTIME);
                } else if (circleRadius == 0) {
                    if (listener != null) {
                        listener.stop();
                    }
                }
            }
        } else if (centerY >= ACTIONHEGHIT) {
            centerY = centerY - 15;
            postInvalidateDelayed(DELAYTIME);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downHeight = (int) event.getY();
                initView();
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveHeight == ACTIONHEGHIT && downHeight - event.getY() > 0) {
                    break;
                }

                if (oldMoveHeight == 0) {
                    oldMoveHeight = downHeight;
                }

                int difference = (oldMoveHeight - (int) event.getY());

                if (difference > 0) {
                    direction = -1;
                } else {
                    direction = 1;
                }

                moveHeight = ACTIONHEGHIT + Math.abs((int) event.getY() - downHeight);

                if (moveHeight <= height / 5) {
                    centerY = moveHeight;
                    ISUP = 1;
                } else {
                    ISUP = 0;
                }
                radian = (int) (pressent * Math.abs(difference));

                oldMoveHeight = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (130 <= centerY && listener != null) {
                    listener.start(this);
                }
                ISUP = 0;
                invalidate();
                break;
        }

        return true;
    }

    public void stopRefresh(boolean stopRefresh) {
        this.stopRefresh = stopRefresh;
        invalidate();
    }

    private void initView() {
        circleRadius = RADIUS;
        degrees = 10;
        arcStart = 0;
        arcStop = 0;
        centerY = ACTIONHEGHIT;
        direction = 1;
        radian = 0;
        ISUP = -1;
    }

    public interface RefreshStatusListener {
        void start(PushRefreshView view);

        void stop();
    }
}
