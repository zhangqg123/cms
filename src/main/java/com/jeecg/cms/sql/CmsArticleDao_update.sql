UPDATE cms_article
SET 
	   <#if cmsArticle.title ?exists>
		   title = :cmsArticle.title,
		</#if>
	   <#if cmsArticle.imageHref ?exists>
		   image_href = :cmsArticle.imageHref,
		</#if>
	   <#if cmsArticle.summary ?exists>
		   summary = :cmsArticle.summary,
		</#if>
	   <#if cmsArticle.content ?exists>
		   content = :cmsArticle.content,
		</#if>
	   <#if cmsArticle.columnId ?exists>
		   column_id = :cmsArticle.columnId,
		</#if>
	   <#if cmsArticle.createName ?exists>
		   create_name = :cmsArticle.createName,
		</#if>
	   <#if cmsArticle.createBy ?exists>
		   create_by = :cmsArticle.createBy,
		</#if>
	    <#if cmsArticle.createDate ?exists>
		   create_date = :cmsArticle.createDate,
		</#if>
	   <#if cmsArticle.publish ?exists>
		   publish = :cmsArticle.publish,
		</#if>
	    <#if cmsArticle.publishDate ?exists>
		   PUBLISH_DATE = :cmsArticle.publishDate,
		</#if>
	   <#if cmsArticle.author ?exists>
		   AUTHOR = :cmsArticle.author,
		</#if>
	   <#if cmsArticle.label ?exists>
		   LABEL = :cmsArticle.label,
		</#if>
		<#if cmsArticle.serialNumber ?exists>
		   SERIAL_NUMBER = :cmsArticle.serialNumber,
		</#if>
		<#if cmsArticle.code ?exists>
		   code = :cmsArticle.code,
		</#if>
		<#if cmsArticle.isLink ?exists>
		   is_link = :cmsArticle.isLink,
		</#if>
	    <#if cmsArticle.link ?exists>
		   link = :cmsArticle.link,
		</#if>
		<#if cmsArticle.isShow ?exists>
		   is_show = :cmsArticle.isShow,
		</#if>
		<#if cmsArticle.deptId ?exists>
		   dept_id = :cmsArticle.deptId,
		</#if>
WHERE id = :cmsArticle.id