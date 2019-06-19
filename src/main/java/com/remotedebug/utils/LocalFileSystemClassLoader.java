package com.remotedebug.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/6/13 0013
 * creat_time: 10:19
 **/
@Slf4j
public class LocalFileSystemClassLoader extends ClassLoader {
    private String classPath;
    private String className;



    public LocalFileSystemClassLoader(String classPath, String className, ClassLoader parentWebappClassLoader) {
        super(parentWebappClassLoader);
        this.classPath = classPath;
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
        String path = classPath;

        try {
            FileInputStream inputStream = new FileInputStream(path);
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
