package com.chenqi.bueatifulview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/7/6.
 * @Package_name: CsjRobotApplication
 */
public class MyProgressBar extends View {
    private static long DELAY_TIME = 10L;
    private Paint paint;
    private Bitmap bitmap;
    private float width;
    private float height;
    private Context context;
    private BitmapShader mShader;

    public MyProgressBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        System.out.println("chenqi -----绘制");
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(R.mipmap.ic_launcher);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
//            mShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//            paint.setShader(mShader);

        } else
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    int de = 0;

    int direction = 1;

    int abse_de;

    int num = 2;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = (int) (width / 2 - bitmap.getWidth() / 2);
        int mHeight = (int) (height / 2 - bitmap.getHeight() / 2);

        int radiusW = (int) (width / 2);
        int radiusH = (int) (height / 2);

        int bitWidth = bitmap.getWidth();
        int bitHeight = bitmap.getHeight();

        for (int i = 0; i < 12; i++) {
            de = 30 * i;
            System.out.println("chenqi de = " + de);
            canvas.rotate(de, radiusW, radiusH);
            canvas.drawCircle(radiusW / 2, radiusH, 40, paint);
            canvas.drawCircle(radiusW, radiusH, 10, paint);
        }

//旋转画布 , 如果旋转的的度数大的话,视觉上看着是旋转快的


//        RectF rect = new RectF();
//        rect.set(0, 0, bitWidth, bitHeight);//图片位置
//        RectF dd = new RectF();
//        dd.set(mWidth, mHeight, bitWidth + mWidth, bitHeight + mHeight);//加体积,和边距
//        Matrix matrix = new Matrix();
//        matrix.setTranslate(mWidth, mHeight);//在整个view的某个地方。
//        if (de >= 360) {
//            num++;
//        } else if (de <= 0) {
//            num++;
//        }
//        direction = (int) Math.pow(-1, num);
//        matrix.preRotate(de, bitWidth / 2, bitHeight / 2);
//        canvas.drawBitmap(bitmap, matrix, paint);
//        postInvalidateDelayed(DELAY_TIME);
    }
}
