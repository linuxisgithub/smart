package javacommon.util.storage;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import javacommon.util.SpringContextUtil;
import org.springframework.util.Assert;

import java.util.Properties;

/**
 * 获取容器，此容器对象可以对云服务器上的文件进行CRUD操作<br><br>
 */
public class CloudStoreBlobContainer {

    /**
     * 验证字符串<br>
     */
    public static final String storageConnectionString = AzureConfig.getConnectionString();

    private CloudStoreBlobContainer() {
    }

    /**
     * 获取默认的容器
     *
     * @return
     * @throws StorageException
     * @throws Exception
     */
    public static CloudBlobContainer getDefaultContainer() throws Exception {
        Properties properties = SpringContextUtil.getBean("propertySetting");
        String container = properties.getProperty("azureContainer");
        return getContainer(container);
    }

    /**
     * 获取指定容器名
     *
     * @param containerName
     * @return
     * @throws StorageException
     * @throws Exception
     */
    public static CloudBlobContainer getContainer(String containerName) throws Exception {
        Assert.notNull(containerName, "containerName参数不能为空");
        //构造通行账户
        CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
        //获取
        CloudBlobClient serviceClient = account.createCloudBlobClient();
        CloudBlobContainer container = serviceClient.getContainerReference(containerName);
        container.createIfNotExists();
        return container;
    }
}
