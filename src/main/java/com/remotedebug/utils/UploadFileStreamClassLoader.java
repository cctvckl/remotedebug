package com.remotedebug.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/6/13 0013
 * creat_time: 10:19
 **/
@Slf4j
public class UploadFileStreamClassLoader extends ClassLoader {
    /**
     * 要加载的class的类名
     */
    private String className;
    /**
     * 要加载的调试class的流，可以通过客户端文件上传，也可以通过传递url来获取
     */
    private InputStream inputStream;

    /**
     *
     * @param inputStream 要加载的class 的文件流
     * @param className 类名
     * @param parentWebappClassLoader 父类加载器
     */
    public UploadFileStreamClassLoader(InputStream inputStream, String className, ClassLoader parentWebappClassLoader) {
        super(parentWebappClassLoader);
        this.className = className;
        this.inputStream = inputStream;
    }


    /**
     *
     * @param url 要加载的类的class的url
     * @param className 类名
     * @param parentWebappClassLoader parent类加载器
     */
    public UploadFileStreamClassLoader(String url, String className, ClassLoader parentWebappClassLoader) {
        super(parentWebappClassLoader);
        URLConnection urlConnection = null;
        try {
            urlConnection = new URL(url).openConnection();
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            log.info("get inputstream of url connection failed.{}",e);
            throw new RuntimeException(e);
        }

        this.className = className;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = getData();
        try {
            String s = new String(data, "utf-8");
//            log.info("class content:{}",s);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return defineClass(className,data,0,data.length);
    }

    private byte[] getData(){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[2048];
            int num = 0;
            while ((num = inputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes, 0,num);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("read stream failed.{}",e);
            throw new RuntimeException(e);
        }
    }
}
