package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/7/18.
 * @Package_name: BueatifulView
 */
public class ClockView extends View {
    /**
     * 表盘画笔
     */
    private Paint circlePaint;
    /**
     * 指针的画笔
     */
    private Paint linePaint;

    /**
     * 背景
     */
    private Paint backGroundPaint;

    /**
     * 刻度盘刻度数量
     */
    private int MAXSCALEVALUESIZE = 12;

    /**
     * 表盘半径
     */
    private int radius = 200;

    /**
     * 分针的长度
     */
    private int MINUTEHANDLENGTH = 120;

    /**
     * 秒针的长度
     */
    private int SECONDHANDLENGTH = 150;

    /**
     * 时针的长度
     */
    private int CLOCKWISELENGTH = 90;

    /**
     * 分针的粗细
     */
    private int MINUTEHANDTHICKNESS = 12;

    /**
     * 秒针的粗细
     */
    private int SECONDHANDTHICKNESS = 4;

    /**
     * 时针的粗细
     */
    private int CLOCKWISETHICKNESS = 25;

    private int width, height;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.BLUE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeCap(Paint.Cap.ROUND);


        backGroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backGroundPaint.setColor(getResources().getColor(R.color.blue_0097e5));
        backGroundPaint.setShadowLayer(10, 0, 0, getResources().getColor(R.color.blue_0097e5));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width / 2, height / 2, radius, backGroundPaint);
        canvas.drawCircle(width / 2, height / 2, radius - 20, backGroundPaint);
        drawClockNeedle(canvas);
        drawClockDial(canvas);
        circlePaint.setColor(Color.YELLOW);
        canvas.drawCircle(width / 2, height / 2, SECONDHANDTHICKNESS, circlePaint);
    }

    /**
     * 画刻度表
     *
     * @param canvas
     */
    private synchronized void drawClockNeedle(Canvas canvas) {
        int offsetX = width / 2;
        int offsetY = height / 2;
        for (int i = 1; i <= MAXSCALEVALUESIZE; i++) {
            circlePaint.setColor(Color.BLUE);
            canvas.rotate(360 / MAXSCALEVALUESIZE, offsetX, offsetY);
            canvas.drawCircle(offsetX, offsetY - radius + 20, 10, circlePaint);
        }
    }

    int ff = 0;

    int fff = 0;

    int ffff = 0;

    /**
     * 画指针
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void drawClockDial(Canvas canvas) {
        int offsetX = width / 2;
        int offsetY = height / 2;
        ff = 0;
        fff = 0;
        ffff = 0;

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        Object object = new Object();

        synchronized (object) {
            ffff = (hour % 12) * 30 + (minute / 60);
            canvas.rotate(ffff, offsetX, offsetY);
            linePaint.setColor(Color.WHITE);
            linePaint.setStrokeWidth(CLOCKWISETHICKNESS);
            canvas.drawLine(offsetX, offsetY + 15, offsetX, offsetY - CLOCKWISELENGTH, linePaint);


            fff = minute * 6 + (second / 60);
            System.out.println("chenqi fff " + fff);
            canvas.rotate(-ffff, offsetX, offsetY);
            canvas.rotate(fff, offsetX, offsetY);
            linePaint.setStrokeWidth(MINUTEHANDTHICKNESS);
            linePaint.setColor(Color.BLUE);
            canvas.drawLine(offsetX, offsetY + 15, offsetX, offsetY - SECONDHANDLENGTH, linePaint);


            ff = second * 6;
            canvas.rotate(-fff, offsetX, offsetY);
            canvas.rotate(ff, offsetX, offsetY);
            System.out.println("chenqi ff " + ff);
            linePaint.setStrokeWidth(SECONDHANDTHICKNESS);
            linePaint.setColor(Color.YELLOW);
            canvas.drawLine(offsetX, offsetY + 15, offsetX, offsetY - SECONDHANDLENGTH, linePaint);

            System.out.print("Calendar获取当前日期" + hour + ":" + minute + ":" + second);
        }
        postInvalidateDelayed(10L);
    }
}
