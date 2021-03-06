package com.jeecg.cms.dao;

import java.util.List;

import org.jeecgframework.minidao.annotation.Param;
import org.jeecgframework.minidao.annotation.ResultType;
import org.jeecgframework.minidao.annotation.Sql;
import org.jeecgframework.minidao.pojo.MiniDaoPage;
import org.springframework.stereotype.Repository;

import com.jeecg.cms.entity.CmsMenu;
import com.jeecg.lhs.entity.LhSAccountEntity;
/**
 * 描述：</b>CmsMenuDao<br>
 * @author：p3.jeecg
 * @since：2016年06月13日 15时00分30秒 星期一 
 * @version:1.0
 */
@Repository
public interface CmsMenuDao{

    /**
	 * 查询返回Java对象
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM cms_menu WHERE ID = :id")
	CmsMenu get(@Param("id") String id);
	
	/**
	 * 修改数据
	 * @param cmsMenu
	 * @return
	 */
	int update(@Param("cmsMenu") CmsMenu cmsMenu);
	
	/**
	 * 插入数据
	 * @param act
	 */
	void insert(@Param("cmsMenu") CmsMenu cmsMenu);
	
	/**
	 * 通用分页方法，支持（oracle、mysql、SqlServer、postgresql）
	 * @param cmsMenu
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResultType(CmsMenu.class)
	public MiniDaoPage<CmsMenu> getAll(@Param("cmsMenu") CmsMenu cmsMenu,@Param("page")  int page,@Param("rows") int rows);
	
	@ResultType(CmsMenu.class)
	public MiniDaoPage<CmsMenu> getAll();
	
	@ResultType(CmsMenu.class)
	public MiniDaoPage<CmsMenu> getFirstMenu();
	
	@Sql("DELETE from cms_menu WHERE ID = :cmsMenu.id")
	public void delete(@Param("cmsMenu") CmsMenu cmsMenu);
	
	@ResultType(CmsMenu.class)
	public MiniDaoPage<CmsMenu> getTree();

	@ResultType(String.class)
	public String getMaxLocalCode(@Param("localCodeLength") String localCodeLength,@Param("parentCode") String parentCode);

	/**
	 * 根据id获取子节点
	 * @param id
	 * @return
	 */
	@Sql("SELECT * FROM cms_menu AS cm where cm.PARENT_CODE = :id ORDER BY if(isnull(cm.SERIAL_NUMBER),1,0),cm.SERIAL_NUMBER;")
	public List<CmsMenu> getChildNode(@Param("id")String id);

	@Sql("SELECT * FROM cms_menu AS cm where cm.CREATE_BY = :userId and (cm.PARENT_CODE = '' OR cm.PARENT_CODE IS NULL) order by if(isnull(cm.SERIAL_NUMBER),1,0),cm.SERIAL_NUMBER ")
	public List<CmsMenu> getFirstMenuByUser(@Param("userId") String userId);
	
	@Sql("SELECT * FROM lh_s_account WHERE app_id = :appId")
	LhSAccountEntity getByAppId(@Param("appId") String appId);
	
	@Sql("SELECT * FROM cms_menu AS cm where cm.APP_OWNER = :xcxId and (cm.PARENT_CODE = '' OR cm.PARENT_CODE IS NULL) order by if(isnull(cm.SERIAL_NUMBER),1,0),cm.SERIAL_NUMBER ")
	List<CmsMenu> getFirstMenuByAppOwner(@Param("xcxId") String xcxId);


	@Sql("select * from cms_menu WHERE owner = :userId")
	CmsMenu getMenuByOwner(@Param("userId") String userId);
}

