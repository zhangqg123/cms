		<#if (cmsArticle.title)?? && cmsArticle.title ?length gt 0>
		    /* 标题 */
			and ca.title like CONCAT('%', :cmsArticle.title ,'%') 
		</#if>
		<#if (cmsArticle.imageHref)?? && cmsArticle.imageHref ?length gt 0>
		    /* 图片地址 */
			and ca.image_href like CONCAT('%', :cmsArticle.imageHref ,'%') 
		</#if>
		<#if (cmsArticle.summary)?? && cmsArticle.summary ?length gt 0>
		    /* summary */
			and ca.summary like CONCAT('%', :cmsArticle.summary ,'%') 
		</#if>
		<#if (cmsArticle.content)?? && cmsArticle.content ?length gt 0>
		    /* 内容 */
			and ca.content like CONCAT('%', :cmsArticle.content ,'%') 
		</#if>
		<#if (cmsArticle.columnId)?? && cmsArticle.columnId ?length gt 0>
		    /* 栏目id */
			and ca.column_id like CONCAT('%', :cmsArticle.columnId ,'%') 
		</#if>
		<#if (cmsArticle.createName)?? && cmsArticle.createName ?length gt 0>
		    /* 创建人 */
			and ca.create_name like CONCAT('%', :cmsArticle.createName ,'%') 
		</#if>
		<#if (cmsArticle.createBy)?? && cmsArticle.createBy ?length gt 0>
		    /* 创建人id */
			and ca.create_by like CONCAT('%', :cmsArticle.createBy ,'%') 
		</#if>
	    <#if (cmsArticle.createDate)??>
		    /* 创建日期 */
			and ca.create_date = :cmsArticle.createDate
		</#if>
		<#if (cmsArticle.publish)?? && cmsArticle.publish ?length gt 0>
		    /* publish */
			and ca.publish like CONCAT('%', :cmsArticle.publish ,'%') 
		</#if>
	    <#if (cmsArticle.publishDate)??>
		    /* PUBLISH_DATE */
			and ca.PUBLISH_DATE = :cmsArticle.publishDate
		</#if>
		<#if (cmsArticle.author)?? && cmsArticle.author ?length gt 0>
		    /* AUTHOR */
			and ca.AUTHOR like CONCAT('%', :cmsArticle.author ,'%') 
		</#if>
		<#if (cmsArticle.label)?? && cmsArticle.label ?length gt 0>
		    /* LABEL */
			and ca.LABEL like CONCAT('%', :cmsArticle.label ,'%') 
		</#if>
		<#if (cmsArticle.code)?? && cmsArticle.code ?length gt 0>
		    /* 文章编码 */
			and ca.code like CONCAT('%', :cmsArticle.code ,'%') 
		</#if>
		<#if ( cmsArticle.isLink )?? && cmsArticle.isLink ?length gt 0>
		    /* 是否链接，0没有链接，1链接 */
			and ca.is_link = :cmsArticle.isLink
		</#if>
		<#if ( cmsArticle.link )?? && cmsArticle.link ?length gt 0>
		    /* 链接地址 */
			and ca.link = :cmsArticle.link
		</#if>
		<#if ( cmsArticle.isShow )?? && cmsArticle.isShow ?length gt 0>
		    /* 是否在明细页面显示*/
			and ca.is_show = :cmsArticle.isShow
		</#if>