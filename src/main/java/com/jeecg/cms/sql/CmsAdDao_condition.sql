		<#if (cmsAd.createName)?? && cmsAd.createName ?length gt 0>
		    /* createName */
			and ca.create_name like CONCAT('%', :cmsAd.createName ,'%') 
		</#if>
		<#if (cmsAd.createBy)?? && cmsAd.createBy ?length gt 0>
		    /* createBy */
			and ca.create_by like CONCAT('%', :cmsAd.createBy ,'%') 
		</#if>
	    <#if (cmsAd.createDate)??>
		    /* createDate */
			and ca.create_date = :cmsAd.createDate
		</#if>
		<#if (cmsAd.title)?? && cmsAd.title ?length gt 0>
		    /* 标题 */
			and ca.title like CONCAT('%', :cmsAd.title ,'%') 
		</#if>
		<#if (cmsAd.imageHref)?? && cmsAd.imageHref ?length gt 0>
		    /* 图片地址 */
			and ca.image_href like CONCAT('%', :cmsAd.imageHref ,'%') 
		</#if>
		<#if ( cmsAd.isShow )?? && cmsAd.isShow ?length gt 0>
		    /* 是否显示，0不显示，1显示 */
			and ca.is_show = :cmsAd.isShow
		</#if>
		<#if ( cmsAd.resume )?? && cmsAd.resume ?length gt 0>
		    /* 简述 */
			and ca.resume = :cmsAd.resume
		</#if>
		<#if ( cmsAd.link )?? && cmsAd.link ?length gt 0>
		    /* 链接 */
			and ca.link = :cmsAd.link
		</#if>
		<#if ( cmsAd.sort )?? && cmsAd.sort ?length gt 0>
		    /* 排序 */
			and ca.sort = :cmsAd.sort
		</#if>
