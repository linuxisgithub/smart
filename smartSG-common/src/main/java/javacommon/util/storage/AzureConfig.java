package javacommon.util.storage;

import javacommon.util.SpringContextUtil;

import java.util.Properties;

/**
 * @author Ether_Leung<br>
 *         基本配置类
 */
public class AzureConfig {

    private AzureConfig() {
    }

    /**
     * 验证字符串
     *
     * @return
     */
    public static String getConnectionString() {
        Properties properties = SpringContextUtil.getBean("propertySetting");
        String account = properties.getProperty("azureAccount");
        String key = properties.getProperty("azureKey");
        return "DefaultEndpointsProtocol=http;"
                + "AccountName=" + account+ ";"
                + "AccountKey=" + key + ";"
                + "EndpointSuffix=core.chinacloudapi.cn";
    }

    /**
     * 文件下载前缀
     *
     * @return
     */
    public static String getAzureStoreUri() throws Exception {
        Properties properties = SpringContextUtil.getBean("propertySetting");
        String account = properties.getProperty("azureAccount");
        return "http://" + account + ".blob.core.chinacloudapi.cn/" + CloudStoreBlobContainer.getDefaultContainer().getName();
    }
}
