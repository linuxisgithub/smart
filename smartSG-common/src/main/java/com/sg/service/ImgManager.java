package com.sg.service;

import com.sg.dao.ImgDao;
import com.sg.model.Img;
import com.system.model.LoginUser;
import javacommon.base.BaseManager;
import javacommon.base.BaseMybatis3Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 超享
 * @date 2018-12-25 13:49:07
 *
 */
@Service
public class ImgManager extends BaseManager<Img> {

	@Inject
	private ImgDao imgDao;
	@Inject
	private AnnexManager annexManager;
	@Override
	protected BaseMybatis3Dao<Img> getDao() {
		return imgDao;
	}

	@Transactional
	public Img doUpLoad(MultipartFile[] files, LoginUser loginUser,Long bid,Integer dtype) throws Exception {
		List<Map<String, Object>> result = annexManager.fileUpload(files, loginUser);
		Map<String, Object> map = result.get(0);
		Img img = new Img();
		img.setUserId(Long.parseLong(map.get("userId").toString()));
		img.setPath(map.get("path").toString());
		img.setSrcName(map.get("srcName").toString());
		img.setType(map.get("type").toString());
		img.setBid(bid);
		img.setName(map.get("name").toString());
		img.setFileSize(Integer.parseInt(map.get("fileSize").toString()));
		img.setDtype(dtype);
		imgDao.save(img);
		return img;
	}

	@Transactional
	public boolean doDel(Long eid,String name){
		boolean result = annexManager.deleteFromBlob(name);
		if (result){
			List<Img> sgImgs = imgDao.findByName(name);// 逻辑删除所有引用该图片数据 解决复制应用的图片
			for (Img sgImg:sgImgs) {
				sgImg.setIsDel(1);
				imgDao.update(sgImg);
			}
		}
		return result;
	}

	/**
	 * @param bid
	 * @param dtype  1=食阁装修图片，2=摊位装修图片，3=菜品图片
	 * @return
	 */
	public List<Img> findByBidAndDtype(long bid,Integer dtype) {
		return imgDao.findByBidAndDtype(bid,dtype);
	}

}