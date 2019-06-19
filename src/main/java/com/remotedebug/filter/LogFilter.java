package com.remotedebug.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * desc:
 *
 * @author: caokunliang
 * creat_date: 2018/1/4
 * creat_time: 17:33
 **/
public class LogFilter implements Filter, Ordered {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    private int order = Ordered.LOWEST_PRECEDENCE - 1;



    /**
     * 会话ID
     */
    private final static String SESSION_KEY = "sessionId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub





    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        try {
            // 设置请求的会话id
            String token = UUID.randomUUID().toString().replace("-", "");
            MDC.put(SESSION_KEY, token);

            chain.doFilter(request, response);
        }catch (Exception e){
            // 删除SessionId
            MDC.remove(SESSION_KEY);

            throw e;
        }

        // 删除SessionId
        MDC.remove(SESSION_KEY);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public int getOrder() {
        return order;
    }
}