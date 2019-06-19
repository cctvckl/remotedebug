package com.remotedebug.service;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/6/18 0018
 * creat_time: 10:14
 **/
public interface IRedisCacheService {
    String getCache(String cacheKey);

    String getCount(String cacheKey);
}
