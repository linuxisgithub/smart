package com.sg.controller;

import com.sg.service.AnnexManager;
import com.system.model.LoginUser;
import com.system.model.SysCompany;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.base.BaseModel;
import javacommon.util.HttpStatusCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 卢子敬
 *
 */
@Controller
@RequestMapping("/annex")
public class AnnexController extends BaseBsController<BaseModel> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Inject
	private AnnexManager annexManager;

/*	@Inject
	private ExtraStotageManager stotageManager;*/

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam(value = "files") MultipartFile[] files, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUser loginUser = getLoginUser(request);
		/*double size = annexManager.countCompanyFileSize(loginUser.getCompany().getEid());*/
		/*double extraSize = stotageManager.findExtraStotageSize(loginUser.getCompany().getEid());*/
		SysCompany comapny = loginUser.getCompany();
		/*if (size >= comapny.maxFileSizeNum() + extraSize) {
			gzipCompress.failWithMsg(HttpStatusCode.FAIL, "您的存储空间已经超出额度！", response);
			return;
		}*/
		List<Map<String, Object>> result = null;
		if (files.length != 0) {
			// 返回上传后返回的对象
			result = annexManager.fileUpload(files, loginUser);
		}
		gzipCompress.data(result, response);
	}

	@RequestMapping(value = "/removeUpload", method = RequestMethod.POST)
	public void removeUpload(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			boolean result = annexManager.deleteFromBlob(name);
			gzipCompress.data(result, response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "remove file fail.", response);
		}
	}

	/**
	 * 删除附件
	 *
	 * @param request
	 * @param response
	 * @param
	 */
	@RequestMapping(value = "/deleteFiles", method = RequestMethod.POST)
	public void deleteFiles(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("names") String names) {
		try {
			if (StringUtils.isNotBlank(names)) {
				String[] fileSrcNameArray = names.split(",");
				List<String> fileSrcNameList = Arrays.asList(fileSrcNameArray);
				annexManager.deleteFromBlob(fileSrcNameList);
			}
			gzipCompress.success(response);

		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "fileUpload.error", response);
			logger.error("deleteFile error", e);
		}
	}

	@RequestMapping("/list")
	public String getUpdateFiles(Long bid, String btype, Model model, HttpServletRequest request) {
		List<Map<String, Object>> annexList = annexManager.getUpdateFiles(bid, btype);
		model.addAttribute("annexList", annexList);
		return "/common/showFiles_other";
	}

	@RequestMapping("/listMarket")
	public String getUpdateFilesMarket(Long bid, String btype, Model model, HttpServletRequest request) {
		List<Map<String, Object>> annexList = annexManager.getUpdateFilesMarket(bid, btype);
		model.addAttribute("annexList", annexList);
		return "/common/showFiles_other";
	}

	@Override
	protected BaseManager<BaseModel> getManager() {
		return null;
	}

	@RequestMapping(value = "/add/count", method = RequestMethod.POST)
	public void addDownCount(Long eid, HttpServletRequest request, HttpServletResponse response) {
		try {
			annexManager.addDownCount(eid);
			gzipCompress.success(response);
		} catch (Exception e) {
			gzipCompress.success(response);
			logger.error("addDownCount error", e);
		}
	}

	/**
	 * 逻辑删除，（update状态）
	 * 
	 * @param eid
	 * @param response
	 */
	@Override
	public void delete(@PathVariable Long eid, HttpServletRequest request, HttpServletResponse response) {

		try {
			LoginUser loginUser = getLoginUser(request);
			Integer userType = loginUser.getUser().getUserType();
			int right = 0;
			if (userType > 1) {
				right = 1;
			} else {
				int num = rightManager.getSpecialRight(loginUser, 10903L);
				if (num > 0) {
					right = 1;
				}
			}
			if (right > 0) {
				annexManager.isDelete(loginUser.getCompany().getEid(), eid);
				gzipCompress.success(response);
			} else {
				gzipCompress.fail(HttpStatusCode.ILLEGAL_ACTION, "delete.illegal", response);
			}

		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "server.error", response);
			logger.error("findById error", e);
		}

	}
}
