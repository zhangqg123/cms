select * from cms_article ca where 
concat(',',ca.column_id,',') regexp concat(',(',replace(:cmsArticle.columnIds,',','|'),'),')