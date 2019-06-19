package com.remotedebug.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * desc:
 * @author: caokunliang
 * creat_date: 2019/6/18 0018
 * creat_time: 15:44
 **/
@Slf4j
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtils.applicationContext = applicationContext;
	    log.info("applicationContext has been autowired:{}",applicationContext);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据bean id获得bean实例.
	 *
	 * @param id String
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String id) {
		return (T) applicationContext.getBean(id);
	}

    /**
     * 根据类型获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

	/**
	 * 根据bean名称和类型获取指定的bean
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getBean(String name, Class requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 判断是否存在指定名称的bean
	 * 
	 * @param name
	 * @return
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断某个bean是否是单例模式
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * 判断bean的类型
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	@SuppressWarnings("rawtypes")
	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

    @SuppressWarnings("rawtypes")
    public   static <T> Map<String, T> getBeansByType(Class<T> t) {
        Map<String, T> beans = applicationContext.getBeansOfType(t);

        return beans;
    }
}
