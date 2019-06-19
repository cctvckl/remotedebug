package com.remotedebug.utils;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/6/18 0018
 * creat_time: 15:10
 **/
public class MyReflectionUtils {

    public static void invokeMethodOfClass(Class clazz,String methodName) throws Exception {
        Object o = clazz.newInstance();
        clazz.getMethod(methodName).invoke(o);
    }

}
