package javacommon.util;

public class StringUtil {

	/**
     * 截取部门代码
     * @param deptCode
     * @return
     */
    public static String subCode(String deptCode) {
    	while(deptCode.endsWith("00")) {
    		deptCode = deptCode.substring(0, deptCode.length() - 2);
    	}
    	return deptCode;
    }
}
