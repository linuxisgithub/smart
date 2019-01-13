package com.sg.controller;

import com.sg.model.Img;
import com.sg.service.ImgManager;
import com.system.model.LoginUser;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.util.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author 超享
 * @date 2018-12-25 13:49:07
 *
 */
@Controller
@RequestMapping("/img")
public class ImgController extends BaseBsController<Img> {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	private ImgManager imgManager;

	@Override
	protected BaseManager<Img> getManager() {
		return imgManager;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam(value = "files") MultipartFile[] files, HttpServletRequest request,
					   HttpServletResponse response) throws Exception {
		LoginUser loginUser = getLoginUser(request);
		String bid = request.getParameter("bid");
		String dtype = request.getParameter("dtype");
		Img img = null;
		if (files.length != 0) {
			// 返回上传后返回的对象
			img = imgManager.doUpLoad(files, loginUser,Long.parseLong(bid),Integer.parseInt(dtype));
		}
		gzipCompress.data(img, response);
	}

	@RequestMapping(value = "/removeUpload", method = RequestMethod.POST)
	public void removeUpload(Long eid,String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			boolean result = imgManager.doDel(eid,name);
			gzipCompress.data(result, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "remove file fail.", response);
		}
	}

}