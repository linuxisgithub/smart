package javacommon.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sg.model.Annex;
import com.sg.service.AnnexManager;
import javacommon.util.Paginator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 基类业务员层
 * 
 * @description
 * @author zengwt 2017-6-26
 * @version 1.0
 * @param <T>
 */
public abstract class BaseManager<T extends BaseModel> {

	public final static String MODEL_KEY = "model";
	public final static String CC_ALL_KEY = "ccModel";
	public final static String CC_THREE_KEY = "ccThree";
	public final static String MODELS_KEY = "models";

	@Inject
	protected AnnexManager annexManager;

	@Inject
	private RabbitTemplate erpTemplate;

	protected abstract BaseMybatis3Dao<T> getDao();

	public T get(Long id) {
		return getDao().get(id);
	}
	public List<T> findByBid(Long bid) {
		return getDao().findByBid(bid);
	}

	public T getWithCc(Long id) {
		return getDao().getWithCc(id);
	}

	public T getWithFile(Long eid) {
		return getDao().getWithFile(eid);
	}

	public T getWithFileAndCc(Long eid) {
		return getDao().getWithFileAndCc(eid);
	}

	public Map<String, Object> getWithCcMap(Long eid) {
		return getDao().getWithCcMap(eid);
	}

	public Map<String, Object> getWithFileMap(Long eid) {
		return getDao().getWithFileMap(eid);
	}

	public Map<String, Object> getWithFileAndCcMap(Long eid) {
		return getDao().getWithFileAndCcMap(eid);
	}

	@Transactional
	public void save(T t) {

		// 保存业务主表
		getDao().save(t);

		// 保存附件
		String annex = t.getAnnex();
		if (StringUtils.isNotBlank(annex)) {
			if (annex != null && !"[]".equals(annex) && annex.length() > 0) {
				List<Annex> annexs = JSONArray.parseArray(annex, Annex.class);
				Map<String, Object> annexMap = new HashMap<>();
				if (annexs != null && annexs.size() > 0) {
					annexMap.put("bid", t.getEid());
					annexMap.put("pid", t.getEid());
					annexMap.put("btype", t.getOaBtype());
					annexMap.put("annexList", annexs.toArray());
					annexManager.saveBatch(annexMap);
				}
			}
		}


	}

	@Transactional
	public void update(T t) {
		String annexId = t.getRomoveAnnexId();
		if (StringUtils.isNotBlank(annexId)) {

			String[] eidArry = annexId.split(",");
			List<String> annexs = annexManager.findNameByEids(eidArry);
			// 删除附件
			annexManager.deleteFromBlob(annexs);
			annexManager.deleteByEids(eidArry);
		}

		// 添加附件
		String annex = t.getAnnex();
		if (annex != null && !"[]".equals(annex) && annex.length() > 0) {
			List<Annex> annexsList = JSONArray.parseArray(annex, Annex.class);
			Map<String, Object> annexMap = new HashMap<>();
			if (annexsList != null && annexsList.size() > 0) {
				annexMap.put("bid", t.getEid());
				annexMap.put("pid", t.getEid());
				annexMap.put("btype", t.getOaBtype());
				annexMap.put("annexList", annexsList.toArray());
				annexManager.saveBatch(annexMap);
			}
		}

		// 修改业务表
		getDao().update(t);
	}

	@Transactional
	public void delete(long id) {
		getDao().delete(id);
	}

	@Transactional
	public void isDelete(long conpanyId, long id) {
		getDao().isDelete(conpanyId, id);
	}

	public List<T> findAll() {
		return getDao().findAll();
	}

	public List<T> findList(Map<String, Object> param) {
		return getDao().findList(param);
	}

	public void findPage(Paginator<T> paginator) {
		getDao().findPage(paginator);
	}

	public void findByPageNumAndSize(Paginator<T> paginator) {
		getDao().findPage(paginator, "findByPageNumAndSize");
	}
	
	public void findPage(Paginator<T> paginator, String sql) {
		getDao().findPage(paginator, sql);
	}
	
	public void findPageMap(Paginator<Map<String, Object>> paginator, String sql) {
		getDao().findPageMap(paginator, sql);
	}

	public long getTotalCount(Map<String, Object> param) {
		return getDao().getTotalCount(param);
	}

	public long findAllCount(Long userId) {
		return getDao().findAllCount(userId);
	}

	public void saveInitInsert(List<T> list) {
		getDao().saveInitInsert(list);
	}

	public void setCcToModel(Model model, List<Map<String, Object>> ccList) {
		setCcToModel(model, ccList, "userName");
	}

	public void setCcToModel(Model model, List<Map<String, Object>> ccList,
			String nameKey) {
		if (ccList.size() != 0) {
			StringBuilder sb = new StringBuilder();
			String ccThree = "";
			int x = 1;
			for (Map<String, Object> map : ccList) {
				String name = (String) map.get(nameKey);
				sb.append(name + "、");
				if (x <= 3) {
					ccThree += name + "、";
					x++;
				}
			}
			String ccStr = sb.toString();
			ccThree = ccThree.substring(0, ccThree.length() - 1);
			if (ccThree.length() >= 12) {
				ccThree = ccThree.substring(0, 10) + "...";
			}
			model.addAttribute(CC_ALL_KEY,
					ccStr.substring(0, ccStr.length() - 1));
			model.addAttribute(CC_THREE_KEY, ccThree);
		}
	}
	
}
