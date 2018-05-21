package com.chenqi.gesturepasswordview.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author : chenqi.
 * @e_mail : 1650699704@163.com.
 * @create_time : 2018/3/14.
 */

public class GsonUtil {
    public static String beanToString(Object object) {
        if (object == null) {
            return null;
        }
        return new Gson().toJson(object);
    }

    public static <T> T stringToBean(String msg, Class<T> tClass) {
        if (msg == null) {
            return null;
        }
        return new Gson().fromJson(msg, tClass);
    }

    public static <T> T stringToList(String msg, Type type) {
        if (msg == null) {
            return null;
        }
        return new Gson().fromJson(msg, type);
    }

    public static String listToString(List<?> list) {
        if (list == null) {
            return null;
        }
        return new Gson().toJson(list);
    }
}
