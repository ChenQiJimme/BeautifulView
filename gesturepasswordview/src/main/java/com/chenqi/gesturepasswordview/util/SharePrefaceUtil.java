package com.chenqi.gesturepasswordview.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/3/14.
 */

public class SharePrefaceUtil {
    private static String SHARENAME = "Pwd_data";
    private static String PWDKEY = "gesture_pwd";

    public static SharedPreferences baseShare(Context context) {
        return context.getSharedPreferences(SHARENAME, Activity.MODE_PRIVATE);
    }

    /**
     * 保存相应的对象到本地
     *
     * @param object
     * @return
     */
    public static boolean putStringByClass(Context context, Object object) {
        if (object == null) {
            return false;
        }
        SharedPreferences.Editor editor = baseShare(context).edit();
        editor.putString(PWDKEY, GsonUtil.beanToString(object));
        //提交数据
        return editor.commit();
    }

    /**
     * 获取本地的string
     *
     * @return
     */
    public static String getString(Context context) {
        return baseShare(context).getString(PWDKEY, null);
    }

    /**
     * 删除密码
     *
     * @return
     */
    public static boolean removeString(Context context) {
        return baseShare(context).edit().remove(PWDKEY).commit();
    }
}
