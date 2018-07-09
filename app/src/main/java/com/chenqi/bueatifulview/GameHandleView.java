package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/6/20.
 * @Package_name: BueatifulView
 */
public class GameHandleView extends View {
    public enum Position {
        TOP,
        RIGHT,
        BOTTOM,
        LEFT,
        RIGHT_TOP,
        LEFT_TOP,
        RIGHT_BOTTOM,
        LEFT_BOTTOM,
    }

    private Paint circlePaint;
    private Paint insideCirclePaint;
    private Paint centerCirclePaint;

    private int radius = 100;
    private int center_radius = 60;
    private int inside_radius = 40;

    private int width;
    private int height;
    private int circle_centerX;
    private int circle_centerY;

    private int inside_circle_centerX;
    private int inside_circle_centerY;

    private float down_x;
    private float down_y;

    private int POSITIVE_DIFFERENCE = 20;

    private Bitmap bitmap;

    private Context context;

    private addMoveListener listener;

    public void addMoveListener(@NonNull addMoveListener listener) {
        this.listener = listener;
    }

    public GameHandleView(Context context) {
        super(context);
        this.context = context;
        init();
    }


    public GameHandleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public GameHandleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        circlePaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        circlePaint.setFilterBitmap(true);
        circlePaint.setARGB(30, 0, 0, 0);


        centerCirclePaint = new Paint();
        centerCirclePaint.setAntiAlias(true);
        centerCirclePaint.setDither(true);
        centerCirclePaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        centerCirclePaint.setFilterBitmap(true);
        centerCirclePaint.setARGB(35, 0, 0, 0);

        insideCirclePaint = new Paint();
        insideCirclePaint.setAntiAlias(true);
        insideCirclePaint.setDither(true);
        insideCirclePaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        insideCirclePaint.setFilterBitmap(true);
        insideCirclePaint.setARGB(40, 0, 0, 0);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(R.mipmap.ic_launcher);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        initPosition();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("chenqi " + bitmap.getWidth() + "----" + bitmap.getHeight());
        int left = circle_centerX - bitmap.getWidth() / 2;
        int top = circle_centerY - bitmap.getHeight() / 2;

        int x = bitmap.getWidth();
        int y = bitmap.getHeight();

        for (int i = 0; i < width / x; i++) {
            for (int j = 0; j < height / y; j++) {
                Rect rect = new Rect();
                rect.set(0, 0, x, y);
//                Rect rect2 = new Rect();
//                rect2.set(0, 0, width, 130);
                RectF rectF = new RectF();
                rectF.set(x * i, y * j, x + x * i, y + y * j);
                canvas.drawBitmap(bitmap, rect, rectF, new Paint());
            }
        }

        canvas.drawCircle(circle_centerX, circle_centerY, radius, circlePaint);
        canvas.drawCircle(inside_circle_centerX, inside_circle_centerY, center_radius, centerCirclePaint);
        canvas.drawCircle(inside_circle_centerX, inside_circle_centerY, inside_radius, insideCirclePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_x = event.getX();
                down_y = event.getY();
                if (checkOutBoundary((int) down_x, (int) down_y)) {
                    circle_centerX = (int) down_x;
                    circle_centerY = (int) down_y;
                    inside_circle_centerX = (int) down_x;
                    inside_circle_centerY = (int) down_y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (checkOutSideCircle((int) event.getX(), (int) event.getY())) {
                    inside_circle_centerX = (int) event.getX();
                    inside_circle_centerY = (int) event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                initPosition();
                break;
        }
        invalidate();
        return true;
    }


    /**
     * 简单思路 通过三角函数处理
     *
     * @param stopX
     * @param stopY
     * @return
     */
    private boolean checkOutSideCircle(int stopX, int stopY) {
        //点击位置x坐标与圆心的x坐标的距离
        float distanceX = circle_centerX - stopX;
        //点击位置y坐标与圆心的y坐标的距离
        float distanceY = circle_centerY - stopY;

        checkPosition(distanceX, distanceY);

        float distanceZ = (float) Math.sqrt(Math.pow(Math.abs(distanceX), 2) + Math.pow(Math.abs(distanceY), 2));

        if (distanceZ >= radius) {
            int x = stopX - circle_centerX;
            int y = stopY - circle_centerY;
            double radian = Math.atan2(y, x);//弧度
            double cosA = Math.cos(radian);
            double sinA = Math.sin(radian);
            inside_circle_centerX = (int) (radius * cosA) + circle_centerX;
            inside_circle_centerY = (int) (radius * sinA) + circle_centerY;
            return false;
        } else return true;
    }

    private void checkPosition(float x, float y) {
        if (listener == null) {
            throw new NullPointerException("listener is null 在" + new Throwable().getStackTrace()[1].getLineNumber() + "行");
        }
        if (y < 0 && (-POSITIVE_DIFFERENCE <= x && POSITIVE_DIFFERENCE >= x)) {
            listener.position(Position.BOTTOM);
        } else if (y > 0 && (-POSITIVE_DIFFERENCE <= x && POSITIVE_DIFFERENCE >= x)) {
            listener.position(Position.TOP);
        } else if (x < 0 && (-POSITIVE_DIFFERENCE <= y && POSITIVE_DIFFERENCE >= y)) {
            listener.position(Position.RIGHT);
        } else if (x > 0 && (-POSITIVE_DIFFERENCE <= y && POSITIVE_DIFFERENCE >= y)) {
            listener.position(Position.LEFT);
        } else if (y < 0 && x < 0) {
            listener.position(Position.RIGHT_BOTTOM);
        } else if (y < 0 && x > 0) {
            listener.position(Position.LEFT_BOTTOM);
        } else if (y > 0 && x < 0) {
            listener.position(Position.RIGHT_TOP);
        } else if (y > 0 && x > 0) {
            listener.position(Position.LEFT_TOP);
        }
    }

    private boolean checkOutBoundary(int stopX, int stopY) {
        int ra = radius + center_radius;
        if (height - stopY >= ra && (width - stopX) >= ra && stopX >= ra && stopY >= ra) {
            return true;
        } else return false;
    }

    private void initPosition() {
        circle_centerX = width / 2;
        circle_centerY = height / 2;
        inside_circle_centerX = width / 2;
        inside_circle_centerY = height / 2;
    }

    public interface addMoveListener {
        void position(Position position);
    }
}
