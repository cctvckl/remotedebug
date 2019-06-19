package com.remotedebug.controller;

import com.remotedebug.utils.LocalFileSystemClassLoader;
import com.remotedebug.utils.MyReflectionUtils;
import com.remotedebug.utils.UploadFileStreamClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * desc:
 * 原理：自定义类加载器，根据入参加载指定的调试类，调试类中需要引用webapp中的类，所以需要把webapp的类加载器作为parent传给自定义类加载器。
 * 这样就可以执行 调试类中的方法，调试类中可以访问 webapp中的类，所以通过 spring 容器的静态引用来获取spring中的bean，然后就可以执行很多业务方法了。
 * 比如获取系统的一些状态、执行service/dao bean中的方法并打印结果（如果方法是get类型的操作，则可以获取系统状态，或者模拟取redis/mysql库中的数据，如果
 * 为update类型的service 方法，则可以用来改变系统状态，在不用重启的情况下，进行一定程度的热修复。
 * @author : caokunliang
 * creat_date: 2018/10/19 0019
 * creat_time: 14:02
 **/
@Controller
@Slf4j
public class RemoteDebugController {


    /**
     * 远程debug，读取参数中的class文件的路径，然后加载，并执行其中的方法
     */
    @RequestMapping("/remoteDebug.do")
    @ResponseBody
    public String remoteDebug(@RequestParam String className ,@RequestParam String filePath, @RequestParam String methodName) throws Exception {
        /**
         * 获取当前类加载器，当前类肯定是放在webapp的web-inf下的classes，这个类所以是由 webappclassloader 加载的，所以这里获取的就是这个
         */
        ClassLoader webappClassloader = this.getClass().getClassLoader();
        log.info("webappClassloader:{}",webappClassloader);


        /**
         * 用自定义类加载器，加载参数中指定的filePath的class文件，并执行其方法
         */
        log.info("开始执行:{}中的方法:{}",className,methodName);
        LocalFileSystemClassLoader localFileSystemClassLoader = new LocalFileSystemClassLoader(filePath, className, webappClassloader);
        Class<?> myDebugClass = localFileSystemClassLoader.loadClass(className);
        MyReflectionUtils.invokeMethodOfClass(myDebugClass, methodName);

        log.info("结束执行:{}中的方法:{}",className,methodName);

        return "success";

    }


    /**
     * 远程debug，读取参数中的class文件的路径，然后加载，并执行其中的方法
     */
    @RequestMapping("/remoteDebugByUploadFile.do")
    @ResponseBody
    public String remoteDebugByUploadFile(@RequestParam String className, @RequestParam String methodName, MultipartFile file) throws Exception {
        if (className == null || file == null || methodName == null) {
            throw new RuntimeException("className,file,methodName must be set");
        }

        /**
         * 获取当前类加载器，当前类肯定是放在webapp的web-inf下的classes，这个类所以是由 webappclassloader 加载的，所以这里获取的就是这个
         */
        ClassLoader webappClassloader = this.getClass().getClassLoader();
        log.info("webappClassloader:{}",webappClassloader);

        /**
         * 用自定义类加载器，加载参数中指定的class文件，并执行其方法
         */
        log.info("开始执行:{}中的方法:{}",className,methodName);
        InputStream inputStream = file.getInputStream();
        UploadFileStreamClassLoader myClassLoader = new UploadFileStreamClassLoader(inputStream, className, webappClassloader);
        Class<?> myDebugClass = myClassLoader.loadClass(className);
        MyReflectionUtils.invokeMethodOfClass(myDebugClass, methodName);
        log.info("结束执行:{}中的方法:{}",className,methodName);


        return "success";

    }


    /**
     * 远程debug，读取参数中url指定的class文件的路径，然后加载，并执行其中的方法
     */
    @RequestMapping("/remoteDebugByURL.do")
    @ResponseBody
    public String remoteDebugByURL(@RequestParam String className,@RequestParam String url, @RequestParam String methodName) throws Exception {
        if (className == null || url == null || methodName == null) {
            throw new RuntimeException("className,url,methodName must be set");
        }

        /**
         * 获取当前类加载器，当前类肯定是放在webapp的web-inf下的classes，这个类所以是由 webappclassloader 加载的，所以这里获取的就是这个
         */
        ClassLoader webappClassloader = this.getClass().getClassLoader();
        log.info("webappClassloader:{}",webappClassloader);

        /**
         * 用自定义类加载器，加载参数中指定的class文件，并执行其方法
         */
        log.info("开始执行:{}中的方法:{}",className,methodName);
        UploadFileStreamClassLoader myClassLoader = new UploadFileStreamClassLoader(url, className, webappClassloader);
        Class<?> myDebugClass = myClassLoader.loadClass(className);
        MyReflectionUtils.invokeMethodOfClass(myDebugClass, methodName);
        log.info("结束执行:{}中的方法:{}",className,methodName);


        return "success";
    }
}
