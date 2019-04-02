package com.jeecg.cms.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.page.SystemTools;
import org.jeecgframework.p3.core.util.oConvertUtils;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.cms.dao.CmsArticleDao;
import com.jeecg.cms.dao.CmsMenuDao;
import com.jeecg.cms.entity.CmsArticle;
import com.jeecg.cms.entity.CmsMenu;
import com.jeecg.lhs.entity.LhSDeptEntity;
import com.jeecg.lhs.service.LhSDeptService;

 /**
 * 描述：</b>CmsArticleController<br>
 * @author p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/p3/cms/cmsArticle")
public class CmsArticleController extends BaseController{
  @Autowired
  private CmsArticleDao cmsArticleDao;
  
  @Autowired
  private CmsMenuDao cmsMenuDao;
  @Autowired
  private LhSDeptService lhSDeptService;
  
	/**
	  * 列表页面
	  * @return
	  */
	@RequestMapping(params = "list",method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute CmsArticle query,HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
			try {
			 	LOG.info(request, " back list");
			 	//分页数据
				String rolecodes=(String) request.getSession().getAttribute("rolecodes");
				String userName=(String) request.getSession().getAttribute("loginUserName");
				if(rolecodes.contains("exam")){
					query.setCreateBy(userName);
				}
				MiniDaoPage<CmsArticle> list =  cmsArticleDao.getAll(query,pageNo,pageSize);
				VelocityContext velocityContext = new VelocityContext();
				velocityContext.put("cmsArticle",query);
				velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
				//获取栏目数据
				MiniDaoPage<CmsMenu> menuList = cmsMenuDao.getAll();
				velocityContext.put("menuList",SystemTools.convertPaginatedList(menuList));

				if(oConvertUtils.isNotEmpty(query.getColumnId())) {
					velocityContext.put("columnId", query.getColumnId());
				}

				String viewName = "cms/cmsArticle-list.vm";
				ViewVelocity.view(request,response,viewName,velocityContext);
			} catch (Exception e) {
			e.printStackTrace();
			}
}

	 /**
	  * 详情
	  * @return
	  */
	@RequestMapping(params="toDetail",method = RequestMethod.GET)
	public void cmsArticleDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
			VelocityContext velocityContext = new VelocityContext();
			String viewName = "cms/cmsArticle-detail.vm";
			CmsArticle cmsArticle = cmsArticleDao.get(id);
			velocityContext.put("cmsArticle",cmsArticle);
			ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		LhSDeptEntity lhSDept=new LhSDeptEntity();
		MiniDaoPage<LhSDeptEntity> list = lhSDeptService.getAll(lhSDept, 1, 200); 
		List<LhSDeptEntity> lhSDeptList = list.getResults();
		VelocityContext velocityContext = new VelocityContext();
		String sessionid = request.getSession().getId();
		velocityContext.put("sessionid", sessionid);
		velocityContext.put("deptList",lhSDeptList);
		String viewName = "cms/cmsArticle-add.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping(params = "doAdd",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doAdd(HttpServletRequest request,@ModelAttribute CmsArticle cmsArticle){
		AjaxJson j = new AjaxJson();
		try {
			String userName=(String) request.getSession().getAttribute("loginUserName");		
			cmsArticle.setCreateBy(userName);
		    String randomSeed = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		    cmsArticle.setId(randomSeed);

		    cmsArticle.setCode(getRandomCode());

		    cmsArticle.setPublishDate(new Date());

		    if(oConvertUtils.isEmpty(cmsArticle.getSummary())) {
		    	cmsArticle.setSummary("");
		    }
		    if(oConvertUtils.isEmpty(cmsArticle.getImageHref())) {
		    	cmsArticle.setImageHref("lesson.jpg");
		    }
		    
			cmsArticleDao.insert(cmsArticle);
			j.setMsg("保存成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("保存失败");
		}
		return j;
	}

	/**
	 * 跳转到编辑页面
	 * @return
	 */
	@RequestMapping(params="toEdit",method = RequestMethod.GET)
	public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		LhSDeptEntity lhSDept=new LhSDeptEntity();
		MiniDaoPage<LhSDeptEntity> list = lhSDeptService.getAll(lhSDept, 1, 200); 
		List<LhSDeptEntity> lhSDeptList = list.getResults();
		VelocityContext velocityContext = new VelocityContext();
		CmsArticle cmsArticle = cmsArticleDao.get(id);
		velocityContext.put("cmsArticle",cmsArticle);
		String sessionid = request.getSession().getId();
		velocityContext.put("sessionid", sessionid);
		velocityContext.put("deptList",lhSDeptList);
		String viewName = "cms/cmsArticle-edit.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(params = "doEdit",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doEdit(HttpServletRequest request,@ModelAttribute CmsArticle cmsArticle){
		AjaxJson j = new AjaxJson();
		try {
			if(cmsArticle.getCreateBy()==null||cmsArticle.getCreateBy()==""){
				String userName=(String) request.getSession().getAttribute("loginUserName");
				cmsArticle.setCreateBy(userName);
			}
			cmsArticleDao.update(cmsArticle);
			j.setMsg("编辑成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("编辑失败");
		}
		return j;
	}


	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(params="doDelete",method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id){
			AjaxJson j = new AjaxJson();
			try {
			    CmsArticle cmsArticle = new CmsArticle();
				cmsArticle.setId(id);
				cmsArticleDao.delete(cmsArticle);
				j.setMsg("删除成功");
			} catch (Exception e) {
			    log.info(e.getMessage());
				j.setSuccess(false);
				j.setMsg("删除失败");
			}
			return j;
	}

	/**
	 * 随机生成文章编码
	 * @return
	 */
	public static String getRandomCode() {
		Random rd = new Random();
		int code = rd.nextInt(999 - 100 + 1) + 100;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date()) + code;
	}

	/**
	 * 更新发布、取消发布操作
	 * @param articleId 文章ID
	 */
	@RequestMapping(params="doUpdatePublish",method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public AjaxJson doUpdatePublish(@RequestParam(required = true, value = "articleId" ) String articleId, HttpServletResponse response,HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			String publish = request.getParameter("publish");
			if(oConvertUtils.isNotEmpty(publish)) {
				CmsArticle cmsArticle = new CmsArticle();
				cmsArticle.setId(articleId);
				cmsArticle.setPublish(publish);
				cmsArticleDao.update(cmsArticle);
			}
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
		}
		return j;
	}

	
}

