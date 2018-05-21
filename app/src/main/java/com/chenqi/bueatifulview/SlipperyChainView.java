package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/5/7.
 * @Package_name: BueatifulView
 * 可以滑动几个圆实现动态变化效果
 */
public class SlipperyChainView extends View {
    private Paint circlePaint;
    private Paint showPaint;
    //    private float maxRadius;
    private float radius = 10f;
    private float width;
    private float height;
    private float maxCircleSize = 0;
    private List<PointC> list;

    public SlipperyChainView(Context context) {
        super(context);
        init();
    }

    public SlipperyChainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setDither(true);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setAntiAlias(true);

        showPaint = new Paint();
        showPaint.setDither(true);
        showPaint.setStrokeCap(Paint.Cap.ROUND);
        showPaint.setTextAlign(Paint.Align.CENTER);
        showPaint.setAntiAlias(true);
        showPaint.setTextSize(30f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < list.size(); i++) {
            PointC pointC = list.get(i);
            canvas.drawCircle(pointC.x, pointC.y, pointC.radius, circlePaint);
            if (pointC.isShowText) {
                canvas.drawText(i + "", pointC.x, pointC.y - (pointC.radius * 2), showPaint);
            }
        }
    }

    private void initView() {
        maxCircleSize = 10;
        PointC point;
        list = new ArrayList<>();
        for (int i = 0; i < maxCircleSize; i++) {
            point = new PointC();
            point.set((int) (width / maxCircleSize * i + radius), (int) (height / 2));
            point.setRadius(radius);
            list.add(point);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int position;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                initView();
                position = checkDown(event.getX(), event.getY());
                zoomOrReCover(position, true);
                break;
            case MotionEvent.ACTION_DOWN:
                position = checkDown(event.getX(), event.getY());
                zoomOrReCover(position, true);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("chenqi up");
                initView();
                break;
        }
        invalidate();
        return true;
    }

    private int checkDown(float x, float y) {
        float xd;
        float yd;
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            PointC point = list.get(i);
            xd = point.x;
            yd = point.y;
            //点击位置x坐标与圆心的x坐标的距离
            float distanceX = Math.abs(xd - x);
            //点击位置y坐标与圆心的y坐标的距离
            float distanceY = Math.abs(yd - y);
            //点击位置与圆心的直线距离
            float distanceZ = (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
            if (distanceZ <= radius) {
                System.out.println("chenqi z = " + distanceZ + " r = " + radius);
                position = i;
            }
        }
        System.out.println("chenqi size = " + position);
        return position;
    }

    private void zoomOrReCover(int position, boolean a) {
        if (position != -1) {
            PointC point = list.get(position);
            if (a) {
                point.radius = point.radius * 2;
                point.isShowText = true;
            } else
                point.radius = point.radius / 2;
            list.set(position, point);
        }
    }
}
