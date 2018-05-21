package com.chenqi.bueatifulview;

import android.graphics.Point;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/5/7.
 * @Package_name: BueatifulView
 */
public class PointC extends Point{
    public float radius;
    public boolean isShowText;

    public PointC() {
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
}
