package javacommon.util.encrypt;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * spring加载数据源properties文件时解密里面有加密的属性
 * @ClassName: EncryptProperty 
 * @Description: TODO
 * @author duwufeng
 * @date 2015年9月7日 下午4:14:25
 */
public class EncryptProperty extends PropertyPlaceholderConfigurer {

	/** properties文件中需要解密的属性key **/
	private String[] encryptPropNames = { "redis_pwd", "rabbit_mq_username", "rabbit_mq_pwd"};
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			String decryptValue = AESSecurity.doSecurity(propertyValue, AESSecurity.DECRYPT_MODE);
            return decryptValue;
        } else {
            return propertyValue;
        }
	}
	/**
     * 判断是否是加密的属性
     * 
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName) {
    	boolean result = false;
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName)) {
            	result = true;
            	break;
            }
        }
        return result;
    }
    public static void main(String[] args) {
	}
}
