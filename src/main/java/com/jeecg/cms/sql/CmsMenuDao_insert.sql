INSERT  INTO
	cms_menu
      ( 
      ID                            
      ,CREATE_BY                      
      ,CREATE_DATE                    
      ,CREATE_NAME                    
      ,IMAGE_HREF                     
      ,IMAGE_NAME                     
      ,NAME                           
      ,TYPE                           
      ,PARENT_CODE                    
      ,HREF
      ,TEMPLATE_CODE
      ,SERIAL_NUMBER
      ,content
      ) 
values
      (
      :cmsMenu.id                            
      ,:cmsMenu.createBy                      
      ,:cmsMenu.createDate                    
      ,:cmsMenu.createName                    
      ,:cmsMenu.imageHref                     
      ,:cmsMenu.imageName                     
      ,:cmsMenu.name                          
      ,:cmsMenu.type                          
      ,:cmsMenu.parentCode                    
      ,:cmsMenu.href
      ,:cmsMenu.templateCode
      ,:cmsMenu.serialNumber
      ,:cmsMenu.content
      )