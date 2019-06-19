// java
package com.remotedebug.controller;

import com.remotedebug.service.IRedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc:
 * 测试接口，模拟从redis中获取缓存。当然，实际场景下，看缓存可以直接用工具的，这里就是举个栗子
 * @author : caokunliang
 * creat_date: 2019/6/18 0018
 * creat_time: 10:13
 **/
@RestController
public class TestController {

    @Autowired
    private IRedisCacheService iRedisCacheService;

    /**
     * 缓存获取接口
     * @param cacheKey
     */
    @RequestMapping("getCache.do")
    public String getCache(@RequestParam String  cacheKey){
        String value = iRedisCacheService.getCache(cacheKey);
        System.out.println(value);

        return value;
    }
}
