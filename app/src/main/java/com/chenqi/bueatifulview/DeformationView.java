package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/5/7.
 * @Package_name: BueatifulView
 */
public class DeformationView extends View {
    private Paint paint;
    private Context context;

    private Scroller scroller;
    private int ANGLE = 0;

    public DeformationView(Context context) {
        super(context);
        this.context = context;
        init();
        scroller = new Scroller(context);
    }

    public DeformationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        scroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            ((View) getParent()).scrollTo(scroller.getCurrX(),scroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initView();
    }

    float radius = 48f;



//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        Paint.FontMetrics fm = paint.getFontMetrics();
//        String text = "?";
//        //文本的宽度
////        float textWidth = paint.measureText(text);
//
//        RectF rectF = new RectF();
////        canvas.drawCircle(100, 350, 50 - (moX / 6), paint);
////        if (moX < 95f) {
////            rectF.set(50, 300 + (moX / 6), 150 + (moX / 2), 400 - (moX / 6));
////        } else if (moX > 95f) {
//            rectF.set(50, 300, 150, 400);
////        }
//        canvas.drawOval(rectF, paint);
//
//
////        rectF.set(50 + 130, 300, 150 + 130, 400);
////
////        canvas.drawOval(rectF, paint);
////
////        float smallRadius = (moX / 4);
////
////        System.out.println("chenqi moX = " + moX);
////        if (moX < 95f) {
////            rectF.set(50 + moX, 300 + (moX / 2), 150 + (moX / 2), 400 - (moX / 2));
////        } else if (moX > 95f) {
////            moX = 95f;
////            rectF.set(50 + 130, 300, 150 + 130, 400);
////            smallRadius = 0;
////        }
////
////        canvas.drawCircle(150, 350, smallRadius, paint);
////        canvas.drawOval(rectF, paint);
//
////        canvas.drawCircle(100, 400, 0.0f + (moY / 2), paint);
////        Path path = new Path();
////        path.moveTo(100, 320);//设置Path的起点
////        path.cubicTo(150, 320, 150 - moY, 400, 100, 400);  //设置路径点和终点
////        path.moveTo(100, 320);//设置Path的起点
////        path.cubicTo(50, 320, 50 + moY, 400, 100, 400);  //设置路径点和终点
////        canvas.drawPath(path, paint);
//
////        float textCenterVerticalBaselineY = getMeasuredHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
////
////        float baseR = radius - (Math.abs(moY) / 2);
//////        System.out.println("chenqi circle draw = " + baseR);
////
////        paint.setTextSize(100f - Math.abs(moY));
////
////        canvas.drawCircle((float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2), baseR, paint);
////
////        canvas.drawText(text, (float) (getMeasuredWidth() / 2), textCenterVerticalBaselineY, paint);
////
////        RectF rectf_head = new RectF(10, 10, 100, 100);//确定外切矩形范围
////
////        rectf_head.offset(100, 20);//使rectf_head所确定的矩形向右偏移100像素，向下偏移20像素
////
////        float s = 180 - moY;
//////        System.out.println("chenqi onDraw moY = " + moY + " s = " + s);
////
////        canvas.drawArc(rectf_head, 0, s, true, paint);//绘制圆弧，含圆心
//
//    }


    private float downX;
    private float downY;

    private float moY;
    private float moX;

    List<Float> list = new ArrayList<>();


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean touch = true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                list.add(0.0f);
                moY = 0;
                moX = 0;

                smoothScrollTo(-400,0);


//                start();
                break;
            case MotionEvent.ACTION_MOVE:
                moX = event.getX() - downX;
                moY = event.getY() - downY;
                ((View)getParent()).scrollBy(-(int)moX,-(int)moX);

//                offsetLeftAndRight((int) moX);
//                layout((int)(getLeft() + moX), (int)(getTop() + moY),
//                        (int)(getRight() + moX), (int)(getBottom() + moY));
//                if ((event.getX() - downX > 0) && moX != event.getX() - downX) {
//                    moX = event.getX() - downX;
//                    moY = event.getY() - downY;
//                    System.out.println("chenqi move" + moY);
//                    invalidate();
//                    list.add(moY);
//                }
                ANGLE ++;
                break;
            case MotionEvent.ACTION_UP:

                View parent = (View) getParent();
                scroller.startScroll(
                        parent.getScrollX(),
                        parent.getScrollY(),
                        -parent.getScrollX(),
                        -parent.getScrollY());

                ANGLE = 0;

//                int scrollX = getScrollX();
//                int delta = (int) moX - scrollX;
//                //1000秒内滑向destX
//                scroller.startScroll(scrollX, 0, delta, 0, 2000);
//                invalidate();
//                ((View)getParent()).scrollBy(-(int) moX,-(int) moY);
//                moY = 0;
//                invalidate();
//                if (thread != null) {
//                    thread.isOver = false;
//                    thread = null;
//                    touch = false;
//                }
//
//                if (thread == null) {
//                    touch = true;
//                    thread = new YCThread();
//                    thread.start();
//                }
                break;
        }
        return true;
    }

    public void smoothScrollTo(int destX,int destY){
        int scrollX=getScrollX();
        int delta=destX-scrollX;
        //1000秒内滑向destX
        scroller.startScroll(scrollX,0,delta,0,2000);
        invalidate();
    }

    private YCThread thread = null;

    private void start() {
        stop();
        if (thread == null) {
            thread = new YCThread();
            thread.start();
        }
    }

    private void stop() {
        if (thread != null) {
            thread.isOver = false;
            thread = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public class YCThread extends Thread {
        public volatile boolean isOver = true;

        @Override
        public void run() {
            moY = 100 / 10;
            for (int i = 0; i < 10; i++) {
                System.out.println("chenqi xunhuan = " + i);
                moY = moY * (i + 1);
                delay();
            }
//            System.out.println("chenqi else =  " + Math.abs(radius - list.get(list.size() - 1)) / 10);
//            //检查是否有分水岭
//            if (!checkWaterShed()) {
//                for (int i = list.size() - 1; i >= 0; i--) {
//                    if (isOver && i < list.size()) {
//                        moY = list.get(i);
//                        delay();
//                    }
//                }
//            } else {
//                moY = Math.abs(radius - list.get(list.size() - 1)) / 10;
//                System.out.println("chenqi else =  " + moY);
//                for (int i = 0; i < 10; i++) {
//                    delay();
//                }
//            }
//            list.clear();
        }
    }

    public void delay() {
        try {
            Thread.sleep(100);
            invalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取最终恢复的大小
     *
     * @return
     */
    private boolean checkWaterShed() {
        if (list.size() == 0) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("chenqi  list 有多少值" + list.get(i));
        }

        float valueS = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            System.out.println("chenqi  check = " + list.get(i) + " value =" + valueS);
            if (valueS < list.get(i)) {
                valueS = list.get(i);
            } else if (valueS > list.get(i)) {
                return true;
            }
        }
        return false;
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAlpha(100);
        paint.setColor(Color.BLUE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(100f);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    private void initView() {

    }
}
