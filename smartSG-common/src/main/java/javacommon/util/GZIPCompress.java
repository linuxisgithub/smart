package javacommon.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

public class GZIPCompress {

	private static Logger log = LoggerFactory.getLogger(GZIPCompress.class);

	@Inject
	protected MessageSource messageSource;

	public void data(Object data, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", HttpStatusCode.SUCCESS.value());
		result.put("data", data);
		writeToString(result, response);
	}

	public void data(Object data,Object otherData, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", HttpStatusCode.SUCCESS.value());
		result.put("data", data);
		result.put("otherData", otherData);
		writeToString(result, response);
	}

	public void success(HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", HttpStatusCode.SUCCESS.value());
		result.put("msg", messageSource.getMessage("success", null , Locale.getDefault()));
		writeToString(result, response);
	}
	public void fail(HttpServletResponse response, Object msg) {
		JSONObject result = new JSONObject();
		result.put("status", HttpStatusCode.FAIL.value());
		result.put("msg", msg);
		writeToString(result, response);
	}
	
	public void fail(HttpServletResponse response, Object msg,int status) {
		JSONObject result = new JSONObject();
		result.put("status", status);
		result.put("msg", msg);
		writeToString(result, response);
	}
	public void fail(HttpStatusCode httpStatusCode, String key, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", httpStatusCode.value());
		result.put("msg", messageSource.getMessage(key, null, Locale.getDefault()));
		writeToString(result, response);
	}
	public void failWithMsg(HttpStatusCode httpStatusCode, String msg, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status",  httpStatusCode.value());
		result.put("msg", msg);
		writeToString(result, response);
	}
	
	public void validator(String msg, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", HttpStatusCode.VALIDATOR_ERROR.value());
		result.put("msg", msg);
		writeToString(result, response);
	}

	public void pages(Paginator page, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", HttpStatusCode.SUCCESS.value());
		result.put("total", page.getTotal());
		result.put("page", page.getCurrentPage());
		result.put("data", page.getDataList());
		result.put("pageCount", page.getPages());
		if(page.getOtherData() != null && page.getOtherData().size() > 0) {
			result.put("otherData", page.getOtherData());
		}
		writeToString(result, response);
	}
	
	public void pagesMap(Paginator page, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		result.put("status", HttpStatusCode.SUCCESS.value());
		result.put("total", page.getTotal());
		result.put("page", page.getCurrentPage());
		result.put("data", page.getMapDataList());
		result.put("pageCount", page.getPages());
		if(page.getOtherData() != null && page.getOtherData().size() > 0) {
			result.put("otherData", page.getOtherData());
		}
		writeToString(result, response);
	}



	public void writeToString(JSONObject result, HttpServletResponse response) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
			String str = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue,
					SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
			System.out.println(str);
			/** 对应响应的文本进行GZIP压缩 */
			gzip.write(str.getBytes("utf-8"));
			gzip.finish();
			/** 告诉浏览器响应文本的编码 */
			response.setHeader("Content-Encoding", "GZIP");
			response.setContentType("application/json; charset=UTF-8");
			/** 输出 */
			response.getOutputStream().write(bos.toByteArray());
		} catch (Exception e) {
			log.error("GZIP compress error", e);
		}
	}

	public void writeToStringNoGzip(JSONObject result, HttpServletResponse response) {
		try {
			String str = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue,
					SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
			/** 告诉浏览器响应文本的编码 */
			response.setContentType("application/json; charset=UTF-8");
			/** 输出 */
			response.getWriter().println(str);
		} catch (Exception e) {
			log.error("writeToStringNoGzip compress error", e);
		}
	}
	
	public void writeToString(Object result, HttpServletResponse response) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
			 GZIPOutputStream gzip = new GZIPOutputStream(bos)) {
			String str = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue,
					SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
			System.out.println(str);
			/** 对应响应的文本进行GZIP压缩 */
			gzip.write(str.getBytes("utf-8"));
			gzip.finish();
			/** 告诉浏览器响应文本的编码 */
			response.setHeader("Content-Encoding", "GZIP");
			response.setContentType("application/json; charset=UTF-8");
			/** 输出 */
			response.getOutputStream().write(bos.toByteArray());
		} catch (Exception e) {
			log.error("GZIP compress error", e);
		}
	}
	
	public static GZIPCompress getInstance() {
		GZIPCompress gzipCompress = SpringContextUtil.getBean(GZIPCompress.class);
		return gzipCompress;
	}
}
