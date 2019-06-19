package com.remotedebug.service.impl;

import com.remotedebug.service.IRedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/6/18 0018
 * creat_time: 10:17
 **/
@Service
@Slf4j
public class IRedisCacheServiceImpl implements IRedisCacheService {

    @Override
    public String getCache(String cacheKey) {
        String target = null;
        // ----------------------前面有复杂逻辑--------------------------
        String count = getCount(cacheKey);
        // ----------------------后面有复杂逻辑--------------------------
        if (Integer.parseInt(count) > 1){
            target = "abc";
        }else {
            // 一些业务逻辑，但是忘记给 target 赋值
            // .....
        }

        return target.trim();
    }

    @Override
    public String getCount(String cacheKey){
        // 假设是从redis 读取缓存,这里简单起见，假设value的值就是cacheKey
        return cacheKey;
    }
}
