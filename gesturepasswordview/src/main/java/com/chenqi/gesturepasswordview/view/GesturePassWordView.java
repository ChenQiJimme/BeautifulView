package com.chenqi.gesturepasswordview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.chenqi.gesturepasswordview.R;
import com.chenqi.gesturepasswordview.res.Circle;
import com.chenqi.gesturepasswordview.util.GsonUtil;
import com.chenqi.gesturepasswordview.util.SharePrefaceUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/3/20.
 */

public class GesturePassWordView extends View {
    /*正常模式*/
    private byte NORMAL_MODE = 0x01;
    /*按下模式*/
    private byte PRESSED_MODE = 0x02;
    /*错误模式*/
    private byte ERROR_MODE = 0x03;

    private List<Circle> list;
    /*View的长度*/
    private float mWidth;
    /*View的宽度*/
    private float mHeight;
    /*错误圆的画笔*/
    private Paint errorCirclePaint;
    /*手指按下圆的画笔*/
    private Paint pressedCirclePaint;
    /*正常圆的画笔*/
    private Paint normalCirclePaint;
    /*正常画直线的画笔*/
    private Paint pressedLinePaint;
    /*画密码错误的画笔*/
    private Paint errorLinePaint;
    /*外部圆画笔默认的颜色*/
    private Paint outSideCirclePaint;
    /*外部圆画笔错误时外部的颜色*/
    private Paint outSideErrorCirclePaint;
    /*外部点击时的颜色*/
    private Paint outSidePressedCirclePaint;

    /*圆线画笔错误的颜色*/
    private int errorCircleColor;
    /*圆画笔险种的颜色*/
    private int pressedCircleColor;
    /*圆画笔默认的颜色*/
    private int normalCircleColor;
    /*直线画笔默认的颜色*/
    private int linePressedColor;
    /*直线画笔错误的颜色*/
    private int lineErrorColor;
    /*外部直线笔错误的颜色*/
    private int outSideErrorCircleColor;
    /*外部直线笔默认和选中的颜色*/
    private int outSidePressedCircleColor;
    /*外部直线正常颜色*/
    private int outSideNormalCircleColor;

    /*是否需要外部的圆和内部的圆一个颜色*/
    private boolean isAlikeInsideCirleErrorColor;

    /*是否需要内部的圆和外部的圆一个颜色*/
    private boolean isAlikeOutsideCirleErrorColor;

    /*是否需要外部圆*/
    private boolean isNotNeedOutSide;

    /*外圆笔粗细*/
    private float outSideStrokeWidth;

    /*线的粗细*/
    private float lineStrokeWidth;

    /**/
    private float strokeWidth;

    private List<Integer> pwdList;

    /*圆半径*/
    private int radius;
    /*圆直径*/
    private int bigRadius;
    /*间隔*/
    private int space;
//    /*利用bitmap画*/
//    private Bitmap bitmap;
//    private Bitmap bitmap2;

    /*直线的坐标记录*/
    private float stopX = 0;
    private float stopY = 0;
    private float startX = 0;
    private float startY = 0;
    private List<float[]> linesList;

    /*密码错误*/
    private boolean pwdPass = true;

    /*事件监听*/
    private PwdListener listener;

    /*重新绘制*/
    private int times = 0;

    /*设置密码时需要验证最大次数*/
    public int MAXCHECKTIMES;

    /*绘制最大密码个数*/
    public int MAXPWDSIZE;

    /*缓存的密码*/
    private List<Integer> initPwd = new ArrayList<>();


    public void setListener(PwdListener listener) {
        this.listener = listener;
    }

    public GesturePassWordView(Context context) {
        super(context);
    }

    public GesturePassWordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GesturePassWordView);
        radius = a.getInteger(R.styleable.GesturePassWordView_radius, 50);
        space = a.getInteger(R.styleable.GesturePassWordView_distance, 100);
        bigRadius = a.getInteger(R.styleable.GesturePassWordView_bigRadius, radius * 2);
        normalCircleColor = a.getResourceId(R.styleable.GesturePassWordView_normalCircleColor, getResources().getColor(R.color.del_btn_disabled));
        pressedCircleColor = a.getResourceId(R.styleable.GesturePassWordView_pressedCircleColor, getResources().getColor(R.color.toolbar));
        errorCircleColor = a.getResourceId(R.styleable.GesturePassWordView_errorCircleColor, Color.RED);
        linePressedColor = a.getResourceId(R.styleable.GesturePassWordView_pressedLineCircleColor, getResources().getColor(R.color.toolbar));
        lineErrorColor = a.getResourceId(R.styleable.GesturePassWordView_errorLineCircleColor, Color.RED);
        outSideErrorCircleColor = a.getResourceId(R.styleable.GesturePassWordView_outSideErrorCircleColor, getResources().getColor(R.color.del_btn_disabled));
        outSidePressedCircleColor = a.getResourceId(R.styleable.GesturePassWordView_outSidePressedCircleColor, getResources().getColor(R.color.del_btn_disabled));
        isAlikeInsideCirleErrorColor = a.getBoolean(R.styleable.GesturePassWordView_isAlikeInsideCirleErrorColor, false);
        isAlikeOutsideCirleErrorColor = a.getBoolean(R.styleable.GesturePassWordView_isAlikeOutsideCirleErrorColor, false);
        outSideNormalCircleColor = a.getResourceId(R.styleable.GesturePassWordView_outSideNormalCircleColor, getResources().getColor(R.color.del_btn_disabled));
        isNotNeedOutSide = a.getBoolean(R.styleable.GesturePassWordView_isNotNeedOutSide, true);
        outSideStrokeWidth = a.getFloat(R.styleable.GesturePassWordView_outSideStrokeWidth, (float) 4.0);
        MAXCHECKTIMES = a.getInteger(R.styleable.GesturePassWordView_maxCheckTimes, 0);
        MAXPWDSIZE = a.getInteger(R.styleable.GesturePassWordView_maxPwdSize, 0);
        lineStrokeWidth = a.getInteger(R.styleable.GesturePassWordView_lineStrokeWidth, 5);
        a.recycle();
    }

    /**
     * 初始化
     */
    @SuppressLint("ResourceAsColor")
    private void initData() {
//        bitmap = BitmapFactory.decodeResource(CsjBotApplication.application.getResources(), R.mipmap.ic_launcher);
//        bitmap = BitmapFactory.decodeResource(CsjBotApplication.application.getResources(), R.mipmap.ic_launcher_round);

        errorCirclePaint = new Paint();
        pressedCirclePaint = new Paint();
        normalCirclePaint = new Paint();
        pressedLinePaint = new Paint();
        errorLinePaint = new Paint();
        outSideCirclePaint = new Paint();
        outSideErrorCirclePaint = new Paint();
        outSidePressedCirclePaint = new Paint();

        /*是否与外圆一个颜色*/
        if (isAlikeOutsideCirleErrorColor) {
            errorCirclePaint.setColor(outSideErrorCircleColor);
            pressedCirclePaint.setColor(outSidePressedCircleColor);
            normalCirclePaint.setColor(outSideNormalCircleColor);
        } else {
            errorCirclePaint.setColor(errorCircleColor);
            pressedCirclePaint.setColor(pressedCircleColor);
            normalCirclePaint.setColor(normalCircleColor);
        }
        errorCirclePaint.setDither(true);
        errorCirclePaint.setAntiAlias(true);
        errorCirclePaint.setStrokeWidth(20);


        pressedCirclePaint.setDither(true);
        pressedCirclePaint.setAntiAlias(true);
        pressedCirclePaint.setStrokeWidth(20);


        normalCirclePaint.setDither(true);
        normalCirclePaint.setAntiAlias(true);
        normalCirclePaint.setStrokeWidth(20);


        pressedLinePaint.setColor(linePressedColor);
        pressedLinePaint.setDither(true);
        pressedLinePaint.setAntiAlias(true);
        pressedLinePaint.setStrokeWidth(lineStrokeWidth);
        pressedLinePaint.setStrokeCap(Paint.Cap.ROUND);


        errorLinePaint.setColor(lineErrorColor);//red
        errorLinePaint.setDither(true);
        errorLinePaint.setAntiAlias(true);
        errorLinePaint.setStrokeWidth(lineStrokeWidth);
        errorLinePaint.setStrokeCap(Paint.Cap.ROUND);

        if (isAlikeInsideCirleErrorColor) {
            outSideCirclePaint.setColor(normalCircleColor);
            outSidePressedCirclePaint.setColor(pressedCircleColor);
            outSideErrorCirclePaint.setColor(errorCircleColor);
        } else {
            outSideCirclePaint.setColor(outSideNormalCircleColor);
            outSidePressedCirclePaint.setColor(outSidePressedCircleColor);
            outSideErrorCirclePaint.setColor(outSideErrorCircleColor);
        }

        outSideCirclePaint.setAntiAlias(true);                       //设置画笔为无锯齿
        outSideCirclePaint.setStrokeWidth(outSideStrokeWidth);              //线宽
        outSideCirclePaint.setStyle(Paint.Style.STROKE);
        outSideCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        outSidePressedCirclePaint.setAntiAlias(true);                       //设置画笔为无锯齿
        outSidePressedCirclePaint.setStrokeWidth(outSideStrokeWidth);              //线宽
        outSidePressedCirclePaint.setStyle(Paint.Style.STROKE);
        outSidePressedCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        outSideErrorCirclePaint.setAntiAlias(true);                       //设置画笔为无锯齿
        outSideErrorCirclePaint.setStrokeWidth(outSideStrokeWidth);              //线宽
        outSideErrorCirclePaint.setStyle(Paint.Style.STROKE);
        outSideErrorCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        pwdList = new ArrayList<>();
        linesList = new ArrayList<>();
        initView();
    }

    public GesturePassWordView setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public GesturePassWordView setBigRadius(int bigRadius) {
        this.bigRadius = bigRadius;
        return this;
    }

    public GesturePassWordView setSpace(int space) {
        this.space = space;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < list.size(); i++) {
            Paint paint;
            Paint outPaint;
            switch (list.get(i).getMode()) {
                case 0x01:
                    paint = normalCirclePaint;
                    outPaint = outSideCirclePaint;
                    //正常
                    break;
                case 0x02:
                    paint = pressedCirclePaint;
                    outPaint = outSidePressedCirclePaint;
                    //按下
                    break;
                case 0x03:
                    paint = errorCirclePaint;
                    outPaint = outSideErrorCirclePaint;
                    //错误
                    break;
                default:
                    paint = normalCirclePaint;
                    outPaint = outSideCirclePaint;
                    break;
            }
            canvas.drawCircle(list.get(i).getW(), list.get(i).getH(), radius, paint);
            if (isNotNeedOutSide) {
                canvas.drawCircle(list.get(i).getW(), list.get(i).getH(), bigRadius, outPaint);
            }
        }

        Paint linePaint;
        if (pwdPass) {
            linePaint = pressedLinePaint;
        } else {
            linePaint = errorLinePaint;
        }

        for (int j = 0; j < linesList.size(); j++) {
            float[] line = linesList.get(j);
            canvas.drawLine(line[0], line[1], line[2], line[3], linePaint);        //绘制直线
        }

        if (startX != 0 && startY != 0) {
            canvas.drawLine(startX, startY, stopX, stopY, pressedLinePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        stopX = event.getX();
        stopY = event.getY();
        pwdPass = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = 0;
                startY = 0;
                initView();
                if (!checkPoint()) {
                    initView();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                checkPoint();
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("chenqi up");
                startX = 0;
                startY = 0;
                stopX = 0;
                stopY = 0;
                if (pwdList.size() != 0) {
                    checkPwd();
                }
                break;
        }
        invalidate();
        return true;
    }

    /**
     * //     * 校验密码
     * //
     */
    private void checkPwd() {
        if (getPwd() == null) {
            System.out.println("times" + times);
            if (times == 0 && MAXCHECKTIMES > 0) {
                //第一次绘制
                if (pwdList.size() < MAXPWDSIZE) {
                    if (listener != null) {
                        listener.pwdBitNotEnough();
                    }
                    initView();
                    times = 0;
                } else {
                    initPwd.clear();
                    for (int i = 0; i < pwdList.size(); i++) {
                        initPwd.add(pwdList.get(i));
                    }
                    if (listener != null) {
                        listener.pwdSettingAgain();
                    }
                    initView();
                    times++;
                }
            } else if (0 < times && times < (MAXCHECKTIMES - 1)) {
                if (pwdList.toString().equals(initPwd.toString())) {
                    if (listener != null) {
                        listener.pwdSettingAgain();
                    }
                    initView();
                } else {
                    initPwd.clear();
                    times = 0;
                    if (listener != null) {
                        listener.pwdSettingError();
                    }
                    initView();
                }
                times++;
            } else {
                if (MAXCHECKTIMES == 0) {
                    initPwd.clear();
                    for (int i = 0; i < pwdList.size(); i++) {
                        initPwd.add(pwdList.get(i));
                    }
                }
                if (pwdList.toString().equals(initPwd.toString())) {
                    //最后一次绘制成功
                    savePwd(pwdList);
                    initPwd.clear();
                    times = 0;
                    if (listener != null) {
                        listener.pwdSettingSuccess();
                    }
                    initView();
                } else {
                    initPwd.clear();
                    times = 0;
                    if (listener != null) {
                        listener.pwdSettingError();
                    }
                    initView();
                }
            }
        } else if (getPwd() != null) {
            if (getPwd().toString().equals(pwdList.toString())) {
                pwdPass = true;
                initView();
                if (listener != null) {
                    listener.pwdCheckSuccess();
                }
            } else {
                errPoint();
                pwdPass = false;
                if (listener != null) {
                    if (listener.pwdCheckError()) {
                        initView();
                    }
                }
            }
        }
    }

    /**
     * 密码错误
     */
    private void errPoint() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMode() == PRESSED_MODE) {
                list.get(i).setMode(ERROR_MODE);
            }
        }
    }

    /**
     * 判断是否点上
     * 计算公式
     * m = x1-x2
     * n = y1-y2
     * d = √（m*m + n*n）
     */
    private boolean checkPoint() {
        float offsetX;
        float offsetY;
        float[] newLine;
        boolean isChecked = false;
        for (int i = 0; i < list.size(); i++) {
            Circle circle = list.get(i);
            offsetX = circle.getW();
            offsetY = circle.getH();

            //点击位置x坐标与圆心的x坐标的距离
            float distanceX = Math.abs(circle.getW() - stopX);
            //点击位置y坐标与圆心的y坐标的距离
            float distanceY = Math.abs(circle.getH() - stopY);
            //点击位置与圆心的直线距离
            float distanceZ = (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
            float distance;
            if (isNotNeedOutSide) {
                distance = bigRadius;
            } else {
                distance = radius;
            }
            if (distanceZ <= distance)
                if (circle.getMode() == NORMAL_MODE || circle.getMode() == ERROR_MODE) {
                    list.get(i).setMode(PRESSED_MODE);
                    pwdList.add(circle.getMark());
                    //如果移动到某个点了就记录画的线
                    if (startY != 0 && startX != 0) {
                        newLine = new float[4];
                        newLine[0] = startX;
                        newLine[1] = startY;
                        newLine[2] = offsetX;
                        newLine[3] = offsetY;
                        linesList.add(newLine);
                    }
                    startX = offsetX;
                    startY = offsetY;
                }
            isChecked = true;
        }
        return isChecked;
    }

    /**
     * 初始化九个点
     */
    public void initView() {
        /*初始化九个圆*/
        list = new ArrayList<>();
        linesList.clear();
        pwdList.clear();
        int size = 0;
        float base;
        if (isNotNeedOutSide) {
            base = bigRadius * 2 + space;
        } else {
            base = radius * 2 + space;
        }
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                float x = mWidth / 2 + base * (j - 2);
                float y = mHeight / 2 + base * (i - 2);
                Circle c = new Circle();
                c.setW(x);
                c.setH(y);
                c.setMode(NORMAL_MODE);
                c.setMark(size++);
                list.add(c);
            }
        }
    }

    /**
     * 保存密码
     */
    public void savePwd(List<Integer> list) {
        SharePrefaceUtil.putStringByClass(this.getContext(), list);
    }

    /**
     * 删除密码
     */
    public void removePwd() {
        SharePrefaceUtil.removeString(this.getContext());
    }

    /**
     * 获取密码
     */
    public List<Integer> getPwd() {
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        String s = SharePrefaceUtil.getString(this.getContext());
        return GsonUtil.stringToList(s, type);
    }

    public interface PwdListener {
        /*位数不够请重新画*/
        void pwdBitNotEnough();

        /*密码校验失败*/
        boolean pwdCheckError();

        /*密码校验成功*/
        void pwdCheckSuccess();

        /*密码设置成功*/
        void pwdSettingSuccess();

        /*麻烦重新输入一次*/
        void pwdSettingAgain();

        /*密码设置失败*/
        void pwdSettingError();

//        /*是否重置*/
//        boolean pwdReset();
    }
}
