package javacommon.util;

import javacommon.util.storage.AzureConfig;
import javacommon.util.storage.BlobCRUD;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chris
 * @since 2016年4月25日
 * @description
 */
public class FileHandleUtils {

	/**
	 * MultipartFile类型文件上传处理
	 *
	 * @param multipartFileList
	 * @param userId
	 *            用户ID
	 * @param companyId
	 *            公司ID
	 * @return Map<String,Object> name（原始文件名）path(保存路径)
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static Map<String, Object> MultipartFileUpload(
			List<MultipartFile> multipartFileList, Long userId, Long companyId)
			throws Exception {
		Map<String, Object> result = new HashMap<>();
		// 上传文件处理
		if (multipartFileList != null && multipartFileList.size() > 0) {
			for (MultipartFile file : multipartFileList) {
				// 原始文件名处理特殊字符
				String originalFileName = file.getOriginalFilename();
				originalFileName = originalFileName.replaceAll("\\/", "");
				originalFileName = originalFileName.replaceAll("%", "");
				originalFileName = originalFileName.replaceAll("\\{", "");
				originalFileName = originalFileName.replaceAll("\\}", "");
				originalFileName = originalFileName.replaceAll("|", "");
				originalFileName = originalFileName.replaceAll(",", "");
				String fileName = java.net.URLDecoder.decode(originalFileName,
						"UTF-8");
				int index = fileName.lastIndexOf(".");
				// 文件名后缀
				String suffix = fileName.substring(index+1);
				// UUID文件名（保存到数据库的文件名）
				String uuidFileName = UUID.getUUID()  +"."+ suffix;
				// 文件大小
				long fileSize = file.getSize();
				InputStream in = file.getInputStream();
				FileOutputStream out = null;
				File tempFile = null;
				// 图片大于1M压缩
				/*if ((suffix.equals("bmp") || suffix.equals("jpg")
						|| suffix.equals("jpeg") || suffix.equals("png") || suffix
							.equals("gif")) && fileSize > 1024 * 1024) {
					tempFile = File.createTempFile("temp", "." + suffix);
					tempFile.deleteOnExit();
					String tempFilePath = tempFile.getAbsolutePath();
					out = new FileOutputStream(tempFilePath);
					PhotoCompressionUtil.compress(in, out, 1000, 1000, true);
					in = new FileInputStream(tempFilePath);
				}*/
				// 上传文件
				String path = null;
				if(!StringUtils.isEmpty(uuidFileName) && fileSize > 0 && userId != null && in != null){
					path = BlobCRUD.upload(uuidFileName, fileSize, userId, fileName, in);
				}

				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (tempFile != null)
					tempFile.delete();
				result.put("name", uuidFileName);
				result.put("path", path);
				result.put("srcName", fileName);
				result.put("fileSize", fileSize);
				result.put("suffix", suffix);
			}
		}
		return result;
	}

	/**
	 * 文件上传处理
	 * @param file
	 * @param userId 用户ID
	 * @param companyId 公司ID
	 * @return Map<String,Object> name（原始文件名）path(保存路径)
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static Map<String,Object> FileUpload(File file,Long userId,Long companyId) throws Exception{
		Map<String,Object> result = new HashMap<>();
		//上传文件处理
		if(file != null){
				//原始文件名处理特殊字符
				String originalFileName = java.net.URLDecoder.decode(file.getName(), "UTF-8");
				originalFileName = originalFileName.replaceAll("\\/","");
				originalFileName = originalFileName.replaceAll("%", "");
				originalFileName = originalFileName.replaceAll("\\{","");
				originalFileName = originalFileName.replaceAll("\\}","");
				originalFileName = originalFileName.replaceAll("|","");
				originalFileName = originalFileName.replaceAll(",","");
				String fileName = java.net.URLDecoder.decode(originalFileName, "UTF-8");
				int index =  fileName.lastIndexOf(".");
				//文件名后缀
				String suffix = StringUtils.substring(fileName, index + 1);
				String uuid = UUID.getUUID();
				//UUID文件名（保存到数据库的文件名）
				String uuidFileName = uuid + "." + suffix;
				//文件大小
				long fileSize = file.getTotalSpace();
				InputStream in =  new FileInputStream(file);
				FileOutputStream out = null;
				File tempFile = null;
				//图片大于1M压缩
				/*if((suffix.equals("bmp") || suffix.equals("jpg") || suffix.equals("jpeg") || suffix.equals("png") || suffix.equals("gif")) && fileSize > 1024*1024){
					tempFile = File.createTempFile("temp", "."+suffix);
					tempFile.deleteOnExit();
					String tempFilePath = tempFile.getAbsolutePath();
					out = new FileOutputStream(tempFilePath);
					PhotoCompressionUtil.compress(in, out, 1000, 1000, true);
					in = new FileInputStream(tempFilePath);
				}*/
				//上传文件
				BlobCRUD.upload(uuidFileName, fileSize, userId,fileName, in);
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if(tempFile != null)tempFile.delete();
				String stroeUri= AzureConfig.getAzureStoreUri();
				String path=stroeUri+"/sudablob/"+uuidFileName;
				result.put("name", fileName);
				result.put("path", path);				
		}
		return result;		
	}
	
	
	
}
