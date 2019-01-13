package javacommon.util.storage;

import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import javacommon.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * 微软云上传 、删除操作
 */
public class BlobCRUD {

	private static final Logger logger = LoggerFactory
			.getLogger(BlobCRUD.class);

	private BlobCRUD() {
		System.out.println("初始化");
	}

	public static String upload(String fileName, long fileSize, long userId,
			String srcName, InputStream in) throws Exception {
		String result;
		try {
			result = AzureConfig.getAzureStoreUri() + "/" + fileName;
			CloudBlobContainer container = CloudStoreBlobContainer
					.getDefaultContainer();
			CloudBlockBlob blob = container.getBlockBlobReference(fileName);
			HashMap<String, String> metadata = new HashMap<>();
			metadata.put("srcName", srcName);
			metadata.put("fileSize", String.valueOf(fileSize));
			metadata.put("userId", String.valueOf(userId));
			metadata.put("srcName", MD5Util.base64Encode(srcName));
			blob.setMetadata(metadata);
			blob.upload(in, fileSize);
		} catch (Exception e) {
			logger.error("upload error", e);
			throw new Exception(e);
		}
		return result;
	}

	public static boolean deleteBlob(String uuid, String containerName) {

		boolean flg = true;

		try {
			CloudBlobContainer container;
			if (StringUtils.isBlank(containerName)) {
				container = CloudStoreBlobContainer.getDefaultContainer();
			} else {
				container = CloudStoreBlobContainer.getContainer(containerName);
			}
			CloudBlockBlob blob = container.getBlockBlobReference(uuid);
			flg = blob.deleteIfExists();
		} catch (Exception e) {
			flg = false;
			logger.error("upload error", e);
		}
		return flg;
	}

	public static void downloadBlob(String containerName, String fileName,
			OutputStream os) {
		try {
			CloudBlobContainer container;
			if (StringUtils.isBlank(containerName)) {
				container = CloudStoreBlobContainer.getDefaultContainer();
			} else {
				container = CloudStoreBlobContainer.getContainer(containerName);
			}
			CloudBlockBlob blob = container.getBlockBlobReference(fileName);
			blob.download(os);
		} catch (Exception e) {
			logger.error("upload error", e);
		}
	}
}
