package com.jeecg.cms.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.common.utils.StringUtil;
import org.jeecgframework.p3.core.page.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.cms.dao.CmsMenuDao;
import com.jeecg.cms.entity.CmsMenu;
import com.jeecg.cms.util.SimpleTreeIdBuild;
import com.jeecg.lhs.entity.User;
import com.jeecg.lhs.service.LhSAccountService;

 /**
 * 描述：</b>CmsMenuController<br>
 * @author p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/p3/cms/cmsMenu")
public class CmsMenuController extends BaseController{
	@Autowired
	private CmsMenuDao cmsMenuDao;
	@Autowired
	private LhSAccountService lhSAccountService;
  
	/**
	  * 列表页面
	  * @return
	  */
	@RequestMapping(params = "list",method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute CmsMenu query,HttpServletRequest request,HttpServletResponse response,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
		try {
		 	LOG.info(request, " back list");
		 	//分页数据
			String rolecodes=(String) request.getSession().getAttribute("rolecodes");
			String userName=(String) request.getSession().getAttribute("loginUserName");
			String xcxId=(String) request.getSession().getAttribute("departAddress");
			if(!rolecodes.contains("admin") &&xcxId!=null){
				query.setAppOwner(xcxId);
			}
			if(rolecodes.contains("exam")){
				query.setCreateBy(userName);
			}
			MiniDaoPage<CmsMenu> list =  cmsMenuDao.getAll(query,pageNo,pageSize);
			VelocityContext velocityContext = new VelocityContext();
			velocityContext.put("cmsMenu",query);
			velocityContext.put("pageInfos",SystemTools.convertPaginatedList(list));
			String viewName = "cms/cmsMenu-list.vm";
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
	public void cmsMenuDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "cms/cmsMenu-detail.vm";
		CmsMenu cmsMenu = cmsMenuDao.get(id);
		velocityContext.put("cmsMenu",cmsMenu);
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(params = "toAdd",method ={RequestMethod.GET, RequestMethod.POST})
	public void toAddDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String rolecodes=(String) request.getSession().getAttribute("rolecodes");
		String xcxId=(String) request.getSession().getAttribute("departAddress");
		List<User> users = getUsers(request);
		VelocityContext velocityContext = new VelocityContext();
		String sessionid = request.getSession().getId();
		velocityContext.put("sessionid", sessionid);
		if(!rolecodes.contains("admin") && xcxId!=null){
			velocityContext.put("xcxId", xcxId);
		}
		velocityContext.put("users", users);
		String viewName = "cms/cmsMenu-add.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
	}
	
	private List<User> getUsers(HttpServletRequest request) {
		String rolecodes = (String) request.getSession().getAttribute("rolecodes");
		List<User> users = null;
		if(rolecodes.contains("admin")||rolecodes.contains("longshi")){
			users = lhSAccountService.getUsers("lsrole"); 
		}else{
			if(rolecodes.contains("zwzx")){
				String userId=(String) request.getSession().getAttribute("loginUserId");
				User user=lhSAccountService.getUserById(userId);
				users=new ArrayList<User>();
				users.add(user);
			}
		}
		return users;
	}

	/**
	 * 保存信息
	 * @return
	 */
	@RequestMapping(params = "doAdd",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doAdd(HttpServletRequest request,@ModelAttribute CmsMenu cmsMenu){
		AjaxJson j = new AjaxJson();
		try {
		    if (StringUtil.isEmpty(cmsMenu.getParentCode())) {//无上级
		    	cmsMenu.setId(new SimpleTreeIdBuild().getId(this.cmsMenuDao,null));
		    	cmsMenu.setParentCode(null);
			}else{//有上级
				cmsMenu.setId(new SimpleTreeIdBuild().getId(this.cmsMenuDao,cmsMenu.getParentCode()));
			}
			String userName=(String) request.getSession().getAttribute("loginUserName");
			cmsMenu.setCreateBy(userName);
			cmsMenuDao.insert(cmsMenu);
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
		String rolecodes=(String) request.getSession().getAttribute("rolecodes");
		String xcxId=(String) request.getSession().getAttribute("departAddress");
		List<User> users = getUsers(request);
		VelocityContext velocityContext = new VelocityContext();
		CmsMenu cmsMenu = cmsMenuDao.get(id);
		velocityContext.put("cmsMenu",cmsMenu);
		String sessionid = request.getSession().getId();
		velocityContext.put("sessionid", sessionid);
		if(!rolecodes.contains("admin") && xcxId!=null){
			velocityContext.put("xcxId", xcxId);
		}
		velocityContext.put("users", users);
		String viewName = "cms/cmsMenu-edit.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
	}

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(params = "doEdit",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson doEdit(HttpServletRequest request,@ModelAttribute CmsMenu cmsMenu){
		AjaxJson j = new AjaxJson();
		try {
			if(cmsMenu.getCreateBy()==null||cmsMenu.getCreateBy()==""){
				String userName=(String) request.getSession().getAttribute("loginUserName");
				cmsMenu.setCreateBy(userName);
			}
			cmsMenuDao.update(cmsMenu);
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
		    CmsMenu cmsMenu = new CmsMenu();
			cmsMenu.setId(id);
			cmsMenuDao.delete(cmsMenu);
			j.setMsg("删除成功");
		} catch (Exception e) {
		    log.info(e.getMessage());
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
	}
	@RequestMapping(params = "tree")
	@ResponseBody
	public List<CmsMenu> tree() {
		MiniDaoPage<CmsMenu> list = cmsMenuDao.getTree();
		List<CmsMenu> cmsMenuList = list.getResults();
		return cmsMenuList;
	}
}

