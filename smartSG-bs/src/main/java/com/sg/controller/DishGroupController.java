package com.sg.controller;

import com.sg.model.DishGroup;
import com.sg.service.DishGroupManager;
import com.system.model.LoginUser;
import javacommon.base.BaseBsController;
import javacommon.base.BaseManager;
import javacommon.util.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author 超享
 * @date 2019-01-03 11:03:36
 *
 */
@Controller
@RequestMapping("/dishGroup")
public class DishGroupController extends BaseBsController<DishGroup> {

	@Inject
	private DishGroupManager dishGroupManager;
	@Override
	protected BaseManager<DishGroup> getManager() {
		return dishGroupManager;
	}

	@RequestMapping(value = "/{deptId}/data", method= RequestMethod.POST)
	public void findData(@PathVariable Long deptId, HttpServletRequest request, HttpServletResponse response) {
		LoginUser user = getLoginUser(request);
		List<DishGroup> dgList = dishGroupManager.findByBid(deptId);
		gzipCompress.data(dgList, response);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(String name,Long bid, HttpServletRequest request, HttpServletResponse response){
		try {
			long count = dishGroupManager.checkName(name,bid);
			if (count>0){
				gzipCompress.fail(response,"分组已存在！");
				return;
			}
			DishGroup dishGroup = new DishGroup();
			dishGroup.setDeptId(bid);
			dishGroup.setName(name);
			dishGroupManager.save(dishGroup);
			gzipCompress.data(dishGroup,response);
		}catch (Exception e){
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "save fail.", response);
		}
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public void remove(Long eid, HttpServletResponse response) throws Exception {
		try {
			DishGroup dishGroup = new DishGroup();
			dishGroup.setEid(eid);
			dishGroup.setIsDel(1);
			dishGroupManager.update(dishGroup);
			gzipCompress.success(response);
		} catch (Exception e) {
			gzipCompress.fail(HttpStatusCode.INTERNAL_SERVER_ERROR, "remove fail.", response);
		}
	}

}