package javacommon.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Chris
 * @since 2016年5月7日
 * @description  javaBean处理工具类
 */
public class BeanUtil {

	private static Logger log = LoggerFactory.getLogger(BeanUtil.class);
	
    /**
     * Bean --> Map javaBean转Map 
     * @param bean 
     * @return
     */
    public static Map<String, Object> transBeanToMap(Object bean) {  
  
        if(bean == null){return null;}  
        
        Map<String, Object> map = new HashMap<String, Object>(); 
        
        try {  
        	
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors(); 
            for (PropertyDescriptor property : propertyDescriptors) {  
            	try {
            		String key = property.getName();  
            		
            		// 过滤class属性  
            		if (!key.equals("class")) {  
            			// 得到property对应的getter方法  
            			Method getter = property.getReadMethod();  
            			if(getter != null) {
            				Object value = getter.invoke(bean);  
            				
            				map.put(key, value);  
            			}
            		}  
					
				} catch (Exception e) {
					 log.error("Bean --> Map Error", e);  
				}
  
            }  
        } catch (Exception e) {  
            log.error("Bean --> Map Error", e);  
        }  
  
        return map;  
  
    }  
    
    public static Map<String, Object> transBeanToMap2(Object bean) {  
    	  
        if(bean == null){return null;}  
        
        Map<String, Object> map = new HashMap<String, Object>(); 
        
        try {  
        	
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors(); 
            for (PropertyDescriptor property : propertyDescriptors) {  
            	try {
            		String key = property.getName();  
            		
            		// 过滤class属性  
            		if (!key.equals("class")) {  
            			// 得到property对应的getter方法  
            			Method getter = property.getReadMethod();  
            			if(getter != null) {
            				Object value = getter.invoke(bean);  
            				if(value != null) {
            					map.put(key, value);  
            				}
            			}
            		}  
					
				} catch (Exception e) {
					 log.error("Bean --> Map Error", e);  
				}
  
            }  
        } catch (Exception e) {  
            log.error("Bean --> Map Error", e);  
        }  
  
        return map;  
  
    }  
}
