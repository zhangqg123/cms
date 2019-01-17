UPDATE cms_ad
SET 
	   <#if cmsAd.createName ?exists>
		   create_name = :cmsAd.createName,
		</#if>
	   <#if cmsAd.createBy ?exists>
		   create_by = :cmsAd.createBy,
		</#if>
	    <#if cmsAd.createDate ?exists>
		   create_date = :cmsAd.createDate,
		</#if>
	   <#if cmsAd.title ?exists>
		   title = :cmsAd.title,
		</#if>
	   <#if cmsAd.imageHref ?exists>
		   image_href = :cmsAd.imageHref,
		</#if>
		<#if cmsAd.isShow ?exists>
		   is_show = :cmsAd.isShow,
		</#if>
	   <#if cmsAd.resume ?exists>
		   resume = :cmsAd.resume,
		</#if>
	   <#if cmsAd.link ?exists>
		   link = :cmsAd.link,
		</#if>
		<#if cmsAd.sort ?exists>
		   sort = :cmsAd.sort,
		</#if>
WHERE id = :cmsAd.id