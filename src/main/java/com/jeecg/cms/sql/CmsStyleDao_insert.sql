INSERT  INTO
	cms_style
      ( 
      ID                            
      ,CREATE_DATE                    
      ,CREATE_NAME                    
      ,REVIEW_IMG_URL                 
      ,TEMPLATE_NAME                  
      ,TEMPLATE_URL                   
      ,UPDATE_DATE                    
      ,UPDATE_NAME                    
      ) 
values
      (
      :cmsStyle.id                            
      ,:cmsStyle.createDate                    
      ,:cmsStyle.createName                    
      ,:cmsStyle.reviewImgUrl                  
      ,:cmsStyle.templateName                  
      ,:cmsStyle.templateUrl                   
      ,:cmsStyle.updateDate                    
      ,:cmsStyle.updateName                    
      )