package com.sg.service;


import com.sg.dao.AnnexDao;
import com.sg.model.Annex;
import com.system.model.LoginUser;
import javacommon.base.BaseModel;
import javacommon.util.DateUtil;
import javacommon.util.Paginator;
import javacommon.util.UUID;
import javacommon.util.storage.BlobCRUD;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andy
 * @since 2016/10/14 0014.
 */
@Service
public class AnnexManager {

	@Inject
	private AnnexDao annexDao;

	@SuppressWarnings("unchecked")
	public void save(MultipartFile[] files, MultipartFile[] fileShows,
			BaseModel t) throws Exception {
		Class clazz = t.getClass();
		Method getUserId = clazz.getMethod("getUserId");
		Method getCreateTime = clazz.getMethod("getCreateTime");
		Long userId = (Long) getUserId.invoke(t);
		Integer erpBtype = t.getOaBtype();
		Long eid = t.getEid();

		String createTime = (String) getCreateTime.invoke(t);

		List<Annex> filesList = common(userId, erpBtype, eid, createTime,
				files, 1);
		List<Annex> fileShowsList = common(userId, erpBtype, eid, createTime,
				fileShows, 2);
		if (filesList != null && fileShowsList != null)
			filesList.addAll(fileShowsList);
		else if (filesList == null && fileShowsList != null) {
			filesList = fileShowsList;
		}

		if (filesList != null && filesList.size() > 0)
			annexDao.save(filesList);
	}

	public List<Map<String, Object>> fileUpload(MultipartFile[] files,
			MultipartFile[] fileShows, LoginUser loginUser) throws Exception {

		String createTime = DateUtil.getDate_TImeStr();

		List<Map<String, Object>> filesList = common(loginUser.getUser().getEid(),
				createTime, files, 1);

		List<Map<String, Object>> fileShowsList = common(loginUser.getUser().getEid(),
				createTime, fileShows, 2);

		if (filesList != null && fileShowsList != null)
			filesList.addAll(fileShowsList);
		else if (filesList == null && fileShowsList != null) {
			filesList = fileShowsList;
		}

		return filesList;

	}

	public List<Map<String, Object>> fileUpload(MultipartFile[] files,
			LoginUser loginUser) throws Exception {

		String createTime = DateUtil.getDate_TImeStr();

		List<Map<String, Object>> filesList = common(loginUser.getUser().getEid(),
				createTime, files);

		return filesList;
	}

	private List<Annex> common(Long userId, Integer erpBtype, Long eid,
			String createTime, MultipartFile[] files, int showType)
			throws Exception {
		List<Annex> annexList = null;
		if (files != null && files.length > 0) {
			String fileSrcName, fileType, fileName, path;
			Annex annex;
			annexList = new ArrayList<>();
			for (MultipartFile file : files) {
				annex = new Annex();
				annex.setUserId(userId);
				// annex.setCompanyId(companyId);
				fileSrcName = file.getOriginalFilename();
				fileType = StringUtils.substringAfterLast(fileSrcName, ".");
				fileName = UUID.getUUID() + "." + fileType;
				path = BlobCRUD.upload(fileName, file.getSize(), userId,
						fileSrcName, file.getInputStream());
				annex.setFileSize(file.getSize());
				annex.setSrcName(fileSrcName);
				annex.setName(fileName);
				annex.setPath(path);
				annex.setType(fileType);
				annex.setBid(eid);
				annex.setPid(eid);
				annex.setBtype(erpBtype);
				annex.setShowType(showType);
				annex.setCreateTime(createTime);
				annexList.add(annex);
			}
		}
		return annexList;
	}

	private Map<String, Object> common(Map<String, Object> map)
			throws NumberFormatException, Exception {
		InputStream sbs = (InputStream) map.get("inputStream");
		String size = (String) map.get("size");
		String type = (String) map.get("type");
		String name = (String) map.get("name");
		Long userId = (Long) map.get("userId");

		String fileName = UUID.getUUID() + type;
		String fileSrcName = name;

		String path = BlobCRUD.upload(fileName, Long.valueOf(size), userId,
				fileSrcName, sbs);

		System.out.println("上传成功后的图片地址============》" + path);
		map.put("fileName", fileName);
		map.put("path", path);
		return map;
	}

	private List<Map<String, Object>> common(Long userId, String createTime,
			MultipartFile[] files) throws Exception {
		List<Map<String, Object>> annexList = null;
		String img = "jpg_png_gif_jpeg_bmp";
		if (files != null && files.length > 0) {
			String fileSrcName, fileType, fileName, path;
			annexList = new ArrayList<>();
			for (MultipartFile file : files) {
				Map<String, Object> map = new HashMap<>();
				fileSrcName = file.getOriginalFilename();
				fileType = StringUtils.substringAfterLast(fileSrcName, ".");
				fileName = UUID.getUUID() + "." + fileType;
				path = BlobCRUD.upload(fileName, file.getSize(), userId,
						fileSrcName, file.getInputStream());

				map.put("userId", userId);
				map.put("fileSize", file.getSize());
				map.put("srcName", fileSrcName);
				map.put("name", fileName);
				map.put("path", path);
				map.put("type", fileType);
				if (img.indexOf(fileType) != -1
						|| img.toUpperCase().indexOf(fileType) != -1) {
					map.put("showType", 2);// 是图片
				} else {
					map.put("showType", 1);
				}
				map.put("createTime", createTime);
				annexList.add(map);
			}
		}
		return annexList;
	}

	private List<Map<String, Object>> common(Long userId, String createTime,
			MultipartFile[] files, int showType) throws Exception {
		List<Map<String, Object>> annexList = null;
		if (files != null && files.length > 0) {
			String fileSrcName, fileType, fileName, path;
			annexList = new ArrayList<>();
			for (MultipartFile file : files) {
				Map<String, Object> map = new HashMap<>();
				map.put("userId", userId);
				fileSrcName = file.getOriginalFilename();
				fileType = StringUtils.substringAfterLast(fileSrcName, ".");
				fileName = UUID.getUUID() + "." + fileType;
				path = BlobCRUD.upload(fileName, file.getSize(), userId,
						fileSrcName, file.getInputStream());

				map.put("fileSize", file.getSize());
				map.put("srcName", fileSrcName);
				map.put("name", fileName);
				map.put("path", path);
				map.put("type", fileType);
				map.put("showType", showType);
				map.put("createTime", createTime);

				annexList.add(map);
			}
		}
		return annexList;
	}

	public void update(MultipartFile[] files, MultipartFile[] fileShows,
			BaseModel t) throws Exception {
		annexDao.delete(t.getEid(), t.getOaBtype());
		save(files, fileShows, t);
	}

	/**
	 * 获取附件
	 * 
	 * @param bid
	 * @param btype
	 * @return
	 */
	public List<Map<String, Object>> findAnnex(Long bid, Integer btype) {
		return annexDao.findAnnex(bid, btype);
	}

	/**
	 * 删除附件
	 * 
	 * @param bid
	 * @param btype
	 */
	public void delete(Long bid, Integer btype) {
		List<String> nameList = annexDao.findNameListByBid(bid, btype);
		deleteFromBlob(nameList);
		annexDao.delete(bid, btype);
	}

	/**
	 * 根据主表ID删除附件
	 * 
	 * @param pid
	 *            主表ID
	 * @param btypes
	 *            主/从类型数组
	 */
	public void deleteByPid(Long pid, int... btypes) {
		List<String> nameList = annexDao.findNameListByPid(pid, btypes);
		deleteFromBlob(nameList);
		annexDao.deleteByPid(pid, btypes);
	}

	public void deleteFromBlob(List<String> nameList) {
		if (nameList != null && nameList.size() > 0) {
			for (String name : nameList) {
				BlobCRUD.deleteBlob(name, null);
			}
		}
	}

	public boolean deleteFromBlob(String name) {
		return BlobCRUD.deleteBlob(name, null);
	}

	/**
	 * 我的文档数量
	 * 
	 * @param /pageNumber
	 * @param /request
	 * @param /response
	 */
	public List<Map<String, Object>> findFileCountByType(
			Map<String, Object> requestParam) {

		List<Map<String, Object>> list = annexDao
				.findFileCountByType(requestParam);
		// 总文件数
		Map<String, Object> count = annexDao.findFileCount(requestParam);
		list.add(count);
		return list;
	}

	/**
	 * 按文件类型查询附件
	 * 
	 * @param paginator
	 */
	public void findPageFileByType(Paginator<BaseModel> paginator) {
		annexDao.findPageFileByType(paginator);
	}

	public List<Map<String, Object>> fileUpload(LoginUser loginUser,
                                                MultipartFile[] file, MultipartFile[] fileShow) throws Exception {
		return fileUpload(file, fileShow, loginUser);
	}

	@Transactional
	public void saveBatch(Map<String, Object> map) {
		annexDao.saveBatch(map);
	}

	public List<String> findAllName() {
		return annexDao.findAllName();
	}

	public void deleteAll() {
		annexDao.deleteAll();
	}

	public List<Map<String, Object>> getUpdateFiles(Long bid, String btype) {
		Map<String, Object> param = new HashMap<>();
		param.put("bid", bid);
		param.put("btype", btype);
		return annexDao.getUpdateFiles(param);
	}
	public List<Map<String, Object>> getUpdateFilesMarket(Long bid, String btype) {
		Map<String, Object> param = new HashMap<>();
		param.put("bid", bid);
		param.put("btype", btype);
		return annexDao.getUpdateFilesMarket(param);
	}

	public void deleteFileList(Long eid, int value) {
		Map<String, Object> param = new HashMap<>();
		param.put("bid", eid);
		param.put("btype", value);
		List<String> nameList = annexDao.findNameList(param);

		if (nameList != null && nameList.size() > 0) {
			for (String name : nameList) {
				BlobCRUD.deleteBlob(name, null);
			}
		}

	}

	/**
	 * 上传头像
	 * 
	 * @param name
	 * @param type
	 * @param size
	 * @param userId
	 * @param Base
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> uplodImg(String name, String type, String size,
			Long userId, String Base) throws Exception {

		if (StringUtils.isNotBlank(name)) {
			int index = name.lastIndexOf("\\");
			name = name.substring(index + 1, name.lastIndexOf("."));
			System.out.println(name);
		}

		BASE64Decoder decoder = new BASE64Decoder();

		byte[] b = decoder.decodeBuffer(Base);

		InputStream sbs = new ByteArrayInputStream(b);

		Map<String, Object> map = new HashMap<>();
		map.put("inputStream", sbs);
		map.put("type", type);
		map.put("size", size);
		map.put("name", name);
		map.put("userId", userId);

		String createTime = DateUtil.getDate_TImeStr();

		map.put("createTime", createTime);
		return common(map);

	}

	public List<String> findNameByEids(String[] annexarry) {
		return annexDao.findNameByEids(annexarry);
	}

	public void deleteByEids(String[] eidArry) {
		annexDao.deleteByEids(eidArry);
	}
	
	/**
	 * 统计公司的附件总数大小
	 * @param companyId
	 * @return
	 */
	public double countCompanyFileSize(Long companyId) {
		return annexDao.countCompanyFileSize(companyId);
	}

	public void addDownCount(Long eid) {
		annexDao.addDownCount(eid);
	}

	public void isDelete(Long companyId, Long id) {
		annexDao.isDelete(companyId, id);
	}

}
