package com.jeecg.cms.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.page.SystemTools;
import org.jeecgframework.p3.core.util.oConvertUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.jeecg.cms.dao.CmsAdDao;
import com.jeecg.cms.dao.CmsArticleDao;
import com.jeecg.cms.dao.CmsMenuDao;
import com.jeecg.cms.dao.CmsSiteDao;
import com.jeecg.cms.entity.CmsAd;
import com.jeecg.cms.entity.CmsArticle;
import com.jeecg.cms.entity.CmsMenu;
import com.jeecg.cms.entity.CmsSite;

/**
 * CMS API
 * 
 * @author zhangdaihao
 * 
 */
@Controller
@RequestMapping("/api/cms")
public class ApiCmsController extends BaseController {

	@Autowired
	private CmsMenuDao cmsMenuDao;
	@Autowired
	private CmsArticleDao cmsArticleDao;
	@Autowired
	private CmsAdDao cmsAdDao;
	@Autowired
	private CmsSiteDao cmsSiteDao;
	
	/**
	 * 返回栏目数据
	 * URL: http://localhost/jeecg-p3-web/api/cms/menu.do
	 * 
	 * @return
	 */
	@RequestMapping(value="/menu")
	public @ResponseBody String menu(@ModelAttribute CmsMenu query, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pid = query.getParentCode();
		MiniDaoPage<CmsMenu> list = cmsMenuDao.getFirstMenu();
		// 分页数据
		List<?> resut = list.getResults();
		return JSONArray.toJSONString(resut);
	}
	
	
	/**
	 * 返回文章数据
	 * URL: http://localhost/jeecg-p3-web/api/cms/articles.do?coulmnId=A01
	 * 
	 * @return
	 */
	@RequestMapping("/articles")
	public @ResponseBody AjaxJson articles(@ModelAttribute CmsMenu query, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "6") int pageSize) throws Exception {
		AjaxJson j = new AjaxJson();
		Map<String,Object> res = new HashMap<String,Object>();
		String coulmnId = request.getParameter("columnId");
		if(!oConvertUtils.isEmpty(coulmnId)){
			//分页数据
			MiniDaoPage<CmsArticle> list =  cmsArticleDao.getPagesAllMenu(coulmnId, pageNo, pageSize);
			List<CmsMenu> li = cmsMenuDao.getChildNode(coulmnId);

			int count = cmsArticleDao.getArticleCountByColumnId(coulmnId);
			int result_count = (int)(count % pageSize);
			res.put("count", result_count);

			res.put("list", list);
			res.put("li", li);
			j.setObj(res);
			j.setSuccess(true);
			System.out.println(JSONArray.toJSON(res));
			return j;//JSONArray.toJSONString(list.getResults()).toString();
		}else if(!oConvertUtils.isEmpty(query.getId())){
			//分页数据
			CmsArticle cms =  cmsArticleDao.get(query.getId());
			j.setObj(cms);
			return j;//JSONArray.toJSONString(list.getResults()).toString();
		}else{
			res.put("code", "-1");
			res.put("msg", "栏目ID不能为空");
			j.setObj(res);
			j.setSuccess(false);
			return j;
		}
	}

	/**
	 * 根据ID返回文章数据
	 * URL: http://localhost/jeecg-p3-web/api/cms/queryOneArticles.do?articlesId=4A15730AC99A408D8CEB4142C7831BC5
	 */
	@RequestMapping("/queryOneArticles")
	public @ResponseBody AjaxJson queryOneArticles(String articlesId) {
		AjaxJson j = new AjaxJson();
		try {
			if(oConvertUtils.isNotEmpty(articlesId)) {
				CmsArticle cmsArticle = cmsArticleDao.get(articlesId);
				j.setObj(cmsArticle);
				j.setSuccess(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
	}

	/**
	 * 小程序获取广告位接口API
	 * URL:http://localhost/jeecg-p3-web/api/cms/queryAllAdImages.do
	 */
	@RequestMapping("/queryAllAdImages")
	public @ResponseBody AjaxJson queryAllAdImages() {
		AjaxJson j = new AjaxJson();
		try {
			MiniDaoPage<CmsAd> ads = cmsAdDao.getAll();
			j.setObj(SystemTools.convertPaginatedList(ads));
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
	}

	/**
	 * 小程序获取站点信息接口
	 * URL:http://localhost/jeecg-p3-web/api/cms/querySiteInfo.do
	 */
	@RequestMapping("/querySiteInfo")
	public @ResponseBody AjaxJson querySiteInfo() {
		AjaxJson j = new AjaxJson();
		try {
			MiniDaoPage<CmsSite> list =  cmsSiteDao.getAll();
			j.setObj(SystemTools.convertPaginatedList(list));
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("获取站点信息失败！");
		}
		return j;
	}

}
