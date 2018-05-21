package com.chenqi.gesturepasswordview.res;

import java.io.Serializable;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/3/20.
 * @Package_name: RobotAmyApplication
 */

public class Circle implements Serializable {
    private static final long serialVersionUID = -5771547244414913769L;
    private float w;
    private float h;
    /*记录是否按下*/
    private byte mode;
    /*密码的记号*/
    private int mark;

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }


    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }
}
