package javacommon.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName SpringContextUtil
 * @Description spring 获取bean工具类
 * @author gqjiang
 * @date 2014年10月20日下午4:35:53
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext context = null;
	/*private static SpringContextUtil springContext = null;

	public synchronized static SpringContextUtil init() {
		if (springContext == null) {
			springContext = new SpringContextUtil();
		}
		return springContext;
	}*/

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public synchronized static <T> T getBean(String beanName) {
		return (T)context.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> clz) {
		return context.getBean(clz);
	}

}
