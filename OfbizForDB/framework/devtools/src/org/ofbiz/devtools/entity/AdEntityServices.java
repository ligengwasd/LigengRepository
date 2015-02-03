package org.ofbiz.devtools.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

public class AdEntityServices {
	
	  public static final String module = AdEntityServices.class.getName();
	    public static final String resource = "EntityUiLabels";
	    public static final String resourceError = "EntityErrorUiLabels";
	   
//	    public static Map<String, Object> deleteAdEntity(DispatchContext ctx, Map<String, Object> context) {
//	    	
//	    	Delegator delegator = ctx.getDelegator();
//	         
//	         String adEntityId = (String)context.get("adEntityId");
//
//	         // check to see if party object exists, if so make sure it is PERSON type party
//	         GenericValue adEntity = null;
//
//	         try {
//	        	 adEntity = delegator.findByPrimaryKey("AdEntity", UtilMisc.toMap("adEntityId", adEntityId));
//	        	 
////	        	 List<GenericValue> adRelationList = adEntity.getRelated("RELATION_RELENTITYAdRelation");
////	        	 for(GenericValue adRelation :  adRelationList){
////	        		 List<GenericValue> adKeymapList = adRelation.getRelated("KEYMAP_RELATIONAdKeymap");
////	        		 for(GenericValue adKeymap :  adKeymapList){
////	        			 adKeymap.remove();
////	        		 }
////	        		 adRelation.remove();
////	        	 }
//	        	 
//	        	 
//	        	 //删除实体对应的服务
//	        	 List<GenericValue> adServiceList = adEntity.getRelated("SERVICE_ENTITYAdService");
//	        	 for(GenericValue adService : adServiceList){
//	        		 
//	        		 List<GenericValue> adAttributeList = adEntity.getRelated("ATTRIBUTE_SERVICEAdAttribute");
//	        		 for(GenericValue adAttribute : adAttributeList){
//	        			 adAttribute.remove();
//	        		 }
//	        		 adService.remove();
//	        	 }
//	        	 
//	        	 //删除实体对应的关系
//	        	 List<GenericValue> adRelationList = adEntity.getRelated("RELATION_ENTITYAdRelation");
//	        	 for(GenericValue adRelation :  adRelationList){
//	        		 List<GenericValue> adKeymapList = adRelation.getRelated("KEYMAP_RELATIONAdKeymap");
//	        		 for(GenericValue adKeymap :  adKeymapList){
//	        			 adKeymap.remove();
//	        		 }
//	        		 adRelation.remove();
//	        	 }
//	        	 
//	        	 //删除实体的主键
//	        	 List<GenericValue> adPrimkeyList = adEntity.getRelated("PRIMKEY_ENTITYAdPrimkey");
//	        	 for(GenericValue adPrimkey : adPrimkeyList){
//	        		 adPrimkey.remove();
//	        	 }
//	        	 
//	        	 //删除实体对应的字段
//	        	 List<GenericValue> adFieldList = adEntity.getRelated("FIELD_ENTITYAdField");
//	        	 for(GenericValue adField : adFieldList){
//	        		 adField.remove();
//	        	 }
//	        	 //删除实体
//	        	 adEntity.remove();
//	        	 
//	         } catch (GenericEntityException e) {
//	             Debug.logWarning(e.getMessage(), module);
//	         }
//	         return ServiceUtil.returnSuccess();
//	         
//	    }
//	    
	    public static Map<String, Object> createServiceByEntity(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	
	    	Delegator delegator = ctx.getDelegator();
	    	LocalDispatcher dispatcher = ctx.getDispatcher();
	    	Locale locale = (Locale) context.get("locale");
	    	String adEntityId =  (String)context.get("adEntityId");
	    	
	    	GenericValue adEntity = null;
	    	
	    	try {
	    		adEntity = delegator.findOne("AdEntity",UtilMisc.toMap("adEntityId", adEntityId), false);
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	List<Map<String,String>>  serviceList =  new ArrayList<Map<String,String>>();
	    	
	    	HashMap<String,String> create = new HashMap<String,String>();
	    	create.put("name", "create"+adEntity.get("entityName"));
	    	create.put("invoke", "create"+adEntity.get("entityName"));
	    	
	    	HashMap<String,String> update = new HashMap<String,String>();
	    	update.put("name", "update"+adEntity.get("entityName"));
	    	update.put("invoke", "update"+adEntity.get("entityName"));
	    	
	    	HashMap<String,String> delete = new HashMap<String,String>();
	    	delete.put("name", "delete"+adEntity.get("entityName"));
	    	delete.put("invoke", "delete"+adEntity.get("entityName"));

	    	serviceList.add(create);
	    	serviceList.add(update);
	    	serviceList.add(delete);
	    		
    		try {
    			context.remove("adEntityId");
    			for(Map<String,String> serviceName : serviceList){
    				
    				EntityCondition whereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("serviceName", serviceName.get("name")));
    				EntityListIterator eli =  delegator.find("AdService", whereEntityCondition, null, null, null, null);
    				List<GenericValue> listIt = null;
    				if(eli!=null){
    	    			listIt = eli.getCompleteList();
    				}
    				if(listIt.size()==0){

    			    	String adServiceId = delegator.getNextSeqId("AdService");
    			    	
    			    	GenericValue adService = null;
    			    	
    			    	//<service name="updateAdField" engine="java" location="org.ofbiz.devtools.entity.AdFieldServices" invoke="updateAdField">
    			    	adService = delegator.makeValue("AdService", UtilMisc.toMap("adServiceId", adServiceId));
    			    	
    			    	adService.set("adModuleId", adEntity.get("adModuleId"));
    			    	adService.set("defaultEntity", adEntity.get("adEntityId"));
    			    	adService.set("defaultEntityName", adEntity.get("entityName"));
    			    	adService.set("location", adEntity.get("packageName")+"."+adEntity.get("entityName")+"Services");
    			    	adService.set("engine", "java");
    			    	adService.set("serviceName", serviceName.get("name"));
    			    	adService.set("auth", "Y");
    			    	adService.set("invoke", serviceName.get("invoke"));
    			    	
    			    	try {
    			    		delegator.create(adService);
    			    		context.put("adServiceId", adServiceId);
    			    		dispatcher.runSync("flushAttributes",context);
    			    	} catch (GenericEntityException e) {
    			    		e.printStackTrace();
    			    	} catch (GenericServiceException e) {
							e.printStackTrace();
						} catch(Exception e){
							e.printStackTrace();
						}
    				}else{
    					GenericValue adService = listIt.get(0);
    					adService.set("adModuleId", adEntity.get("adModuleId"));
    			    	adService.set("defaultEntity", adEntity.get("adEntityId"));
    			    	adService.set("defaultEntityName", adEntity.get("entityName"));
    			    	adService.set("location", adEntity.get("packageName")+"."+adEntity.get("entityName")+"Services");
    			    	adService.set("engine", "java");
    			    	adService.set("serviceName", serviceName.get("name"));
    			    	adService.set("auth", "Y");
    			    	adService.set("invoke", serviceName.get("invoke"));
    			    	try {
    			    		delegator.store(adService);
    			    		context.put("adServiceId", adService.get("adServiceId"));
    			    		dispatcher.runSync("flushAttributes",context);
    			    	} catch (GenericEntityException e) {
    			    		e.printStackTrace();
    			    	} catch (GenericServiceException e) {
							e.printStackTrace();
						} catch(Exception e){
							e.printStackTrace();
						}
    				}
    			}
			} catch (GenericEntityException e) {
				e.printStackTrace();
				Debug.logWarning(e.getMessage(), module);
				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
			}
	    	
	    	//result.put("adEntityId", adEntityId);
	    	//result.put("defaultEntity", adEntityId);
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    public static Map<String, Object> createFormByEntity(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	Locale locale = (Locale) context.get("locale");
	    	Delegator delegator = ctx.getDelegator();
	    	LocalDispatcher dispatcher = ctx.getDispatcher();
	    	
	    	String adEntityId =  (String)context.get("adEntityId");
	    	
	    	GenericValue adEntity = null;
	    	
	    	try {
	    		adEntity = delegator.findOne("AdEntity",UtilMisc.toMap("adEntityId", adEntityId), false);
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	List<Map<String,String>>  formList =  new ArrayList<Map<String,String>>();
	    	
	    	HashMap<String,String> find = new HashMap<String,String>();
	    	find.put("name", "find"+adEntity.get("entityName"));
	    	find.put("type", "single");
	    	
	    	HashMap<String,String> edit = new HashMap<String,String>();
	    	edit.put("name", "edit"+adEntity.get("entityName"));
	    	edit.put("type", "single");
	    	edit.put("defaultMapName", "show"+ adEntity.get("entityName"));
	    	
	    	
	    	formList.add(find);
	    	formList.add(edit);
	    	
	    	try {
	    		context.remove("adEntityId");
	    		for(Map<String,String> formName : formList){
	    			EntityCondition whereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("formName", formName.get("name")));
	    			EntityListIterator eli =  delegator.find("AdForm", whereEntityCondition, null, null, null, null);
	    			List<GenericValue> listIt = null;
	    			if(eli!=null){
	    				listIt = eli.getCompleteList();
	    			}
	    			if(listIt.size()==0){
	    				String adFormId = delegator.getNextSeqId("AdForm");
	    				GenericValue adForm = null;
	    				adForm = delegator.makeValue("AdForm", UtilMisc.toMap("adFormId", adFormId));
	    				adForm.set("adModuleId", adEntity.get("adModuleId"));
	    				adForm.set("formName", formName.get("name"));
	    				adForm.set("type", formName.get("type"));
	    				adForm.set("adEntityId", adEntityId);
	    				adForm.set("defaultMapName", formName.get("defaultMapName"));
	    				
	    				EntityCondition viwMapWhereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("viewName", formName.get("name")));
		    			EntityListIterator viewMapli =  delegator.find("AdViewmap", viwMapWhereEntityCondition, null, null, null, null);
		    			List<GenericValue> viewMapListIt = null;
		    			if(eli!=null){
		    				viewMapListIt = viewMapli.getCompleteList();
		    				adForm.set("adViewmapId", viewMapListIt.get(0).get("adViewmapId"));
		    			}
	    				try {
	    					delegator.create(adForm);
	    					context.put("adFormId", adFormId);
	    					dispatcher.runSync("flushFormfield",context);
	    					dispatcher.runSync("flushFormcommand",context);
	    				} catch (GenericEntityException e) {
	    					e.printStackTrace();
	    				} 
	    				catch (GenericServiceException e) {
	    					e.printStackTrace();
	    				} 
	    			}else{
	    				GenericValue adForm = listIt.get(0);
	    				adForm.set("adModuleId", adEntity.get("adModuleId"));
	    				adForm.set("formName", formName.get("name"));
	    				adForm.set("type", formName.get("type"));
	    				adForm.set("adEntityId", adEntityId);
	    				adForm.set("adEntityId", adEntityId);
	    				EntityCondition viwMapWhereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("viewName", formName.get("name")));
		    			EntityListIterator viewMapli =  delegator.find("AdViewmap", viwMapWhereEntityCondition, null, null, null, null);
		    			List<GenericValue> viewMapListIt = null;
		    			if(eli!=null){
		    				viewMapListIt = viewMapli.getCompleteList();
		    				adForm.set("adViewmapId", viewMapListIt.get(0).get("adViewmapId"));
		    			}
		    			
	    				try {
	    					delegator.store(adForm);
	    					context.put("adFormId", adForm.get("adFormId"));
	    					dispatcher.runSync("flushFormfield",context);
	    					dispatcher.runSync("flushFormcommand",context);
	    				} catch (GenericEntityException e) {
	    					e.printStackTrace();
	    				} 
	    				catch (GenericServiceException e) {
	    					e.printStackTrace();
	    				} 
	    			}
	    		}
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    
	    public static Map<String, Object> createGridByEntity(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	Locale locale = (Locale) context.get("locale");
	    	Delegator delegator = ctx.getDelegator();
	    	LocalDispatcher dispatcher = ctx.getDispatcher();
	    	
	    	String adEntityId =  (String)context.get("adEntityId");
	    	
	    	GenericValue adEntity = null;
	    	
	    	try {
	    		adEntity = delegator.findOne("AdEntity",UtilMisc.toMap("adEntityId", adEntityId), false);
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	

	    	
	    	List<Map<String,String>>  gridList =  new ArrayList<Map<String,String>>();
	    	
	    	HashMap<String,String> grid = new HashMap<String,String>();
	    	grid.put("name", "grid"+adEntity.get("entityName"));
	    	
	    	gridList.add(grid);
	    	
	    	try {
	    		context.remove("adEntityId");
	    		for(Map<String,String> gridName : gridList){
	    			EntityCondition whereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("gridName", gridName.get("name")));
	    			EntityListIterator eli =  delegator.find("AdGrid", whereEntityCondition, null, null, null, null);
	    			List<GenericValue> listIt = null;
	    			if(eli!=null){
	    				listIt = eli.getCompleteList();
	    			}
	    			if(listIt.size()==0){
	    				
	    				String adGridId = delegator.getNextSeqId("AdGrid");
	    				
	    				GenericValue adGrid = null;
	    				
	    				adGrid = delegator.makeValue("AdGrid", UtilMisc.toMap("adGridId", adGridId));
	    				
	    				
	    		    	String primkeyName = null;
	    		    	try {
	    		    		//adEntity = adGrid.getRelatedOne("GRID_ENTITYAdEntity",false);
	    		    		
	    		    		 Map<String,String> byAndFields = new HashMap<String,String>();
	    		    		 byAndFields.put("ispk", "Y");
	    		    		 primkeyName = (String) adEntity.getRelated("FIELD_ENTITYAdField", byAndFields, null, false).get(0).get("fieldName");
	    		    	} catch (GenericEntityException e) {
	    		    		e.printStackTrace();
	    		    	}
	    				
	    				
	    				
	    				adGrid.set("adModuleId", adEntity.get("adModuleId"));
	    				adGrid.set("gridName", gridName.get("name"));
	    				adGrid.set("adEntityId", adEntityId);
	    				adGrid.set("modelId", primkeyName);
	    				adGrid.set("updateCommand", "update"+adEntity.get("entityName"));
	    				adGrid.set("createCommand", "create"+adEntity.get("entityName"));
	    				adGrid.set("deleteCommand", "delete"+adEntity.get("entityName"));
	    				adGrid.set("lineNo", delegator.getNextLineNo("AdGrid"));
	    				
	    				EntityCondition viwMapWhereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("viewName", "find"+adEntity.get("entityName")));
	    				EntityListIterator viewMapli =  delegator.find("AdViewmap", viwMapWhereEntityCondition, null, null, null, null);
	    				List<GenericValue> viewMapListIt = null;
	    				if(eli!=null){
	    					viewMapListIt = viewMapli.getCompleteList();
	    					adGrid.set("adViewmapId", viewMapListIt.get(0).get("adViewmapId"));
	    				}
	    				
	    				try {
	    					delegator.create(adGrid);
	    					context.put("adGridId", adGridId);
	    					dispatcher.runSync("flushGridfield",context);
	    					dispatcher.runSync("flushGridcommand",context);
	    				} catch (GenericEntityException e) {
	    					e.printStackTrace();
	    				} 
	    				catch (GenericServiceException e) {
	    					e.printStackTrace();
	    				} 
	    			}else{
	    				GenericValue adGrid = listIt.get(0);
	    				
	    				String primkeyName = null;
	    		    	try {
	    		    		adEntity = adGrid.getRelatedOne("GRID_ENTITYAdEntity",false);
	    		    		
	    		    		 Map<String,String> byAndFields = new HashMap<String,String>();
	    		    		 byAndFields.put("ispk", "Y");
	    		    		 primkeyName = (String) adEntity.getRelated("FIELD_ENTITYAdField", byAndFields, null, false).get(0).get("fieldName");
	    		    	} catch (GenericEntityException e) {
	    		    		e.printStackTrace();
	    		    	}
	    				
	    				
	    				adGrid.set("adModuleId", adEntity.get("adModuleId"));
	    				adGrid.set("adEntityId", adEntityId);
	    				adGrid.set("modelId", primkeyName);
	    				adGrid.set("updateCommand", "update"+adEntity.get("entityName"));
	    				adGrid.set("createCommand", "create"+adEntity.get("entityName"));
	    				adGrid.set("deleteCommand", "delete"+adEntity.get("entityName"));
	    				
	    				EntityCondition viwMapWhereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("viewName", "find"+adEntity.get("entityName")));
	    				EntityListIterator viewMapli =  delegator.find("AdViewmap", viwMapWhereEntityCondition, null, null, null, null);
	    				List<GenericValue> viewMapListIt = null;
	    				if(eli!=null){
	    					viewMapListIt = viewMapli.getCompleteList();
	    					adGrid.set("adViewmapId", viewMapListIt.get(0).get("adViewmapId"));
	    				}
	    				
	    				try {
	    					delegator.store(adGrid);
	    					context.put("adGridId", adGrid.get("adGridId"));
	    					dispatcher.runSync("flushGridfield",context);
	    				} catch (GenericEntityException e) {
	    					e.printStackTrace();
	    				} 
	    				catch (GenericServiceException e) {
	    					e.printStackTrace();
	    				} 
	    			}
	    		}
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    
	    public static Map<String, Object> flushAttributes(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	
	    	Delegator delegator = ctx.getDelegator();
	    	Locale locale = (Locale) context.get("locale");
	    	String adServiceId = (String)context.get("adServiceId");
	    	
	    	List<GenericValue> adFieldList = null;
	    	GenericValue adEntity = null;
	    	List<GenericValue> adAttributeList = null;
	    	List<String> attributeNameList = new ArrayList<String>();
	    	GenericValue adService = null;
	    	try {
	    		
				adService = delegator.findOne("AdService",false, UtilMisc.toMap("adServiceId", adServiceId));
				adEntity = adService.getRelatedOne("SERVICE_ENTITYAdEntity",false);
				List<String> orderBy = new ArrayList<String>();
				orderBy.add("adFieldId");
				adFieldList = adEntity.getRelated("FIELD_ENTITYAdField", null, orderBy, false);
				adAttributeList = adService.getRelated("ATTRIBUTE_SERVICEAdAttribute",null,null,false);
				for(GenericValue adAttribute:adAttributeList){
					attributeNameList.add((String)adAttribute.get("attributeName"));
				}
			} catch (GenericEntityException e) {
				e.printStackTrace();
				Debug.logWarning(e.getMessage(), module);
				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
			}
	    	
	    	if(adService.get("serviceName").toString().startsWith("create")){
		    	for(GenericValue adField : adFieldList){
		    		//如果该字段已存在对应的参数，则不添加该参数
		    		if(attributeNameList.contains((String)adField.get("fieldName"))){
		    			continue;
		    		}
		    		//如果该字段不存在对应的参数，则添加该参数
			    	String adAttributeId = delegator.getNextSeqId("AdAttribute");
			    	GenericValue adAttribute = null;
			    	adAttribute = delegator.makeValue("AdAttribute", UtilMisc.toMap("adAttributeId", adAttributeId));
			    	adAttribute.set("adServiceId", adServiceId);
			    	adAttribute.set("adEntityId", adField.get("adEntityId"));
			    	adAttribute.set("adFieldId", adField.get("adFieldId"));
			    	adAttribute.set("attributeName", adField.get("fieldName"));
			    	adAttribute.set("type", adField.get("javaType"));
			    	adAttribute.set("optional", "Y");
			    	if("Y".equals(adField.get("ispk"))){
			    		adAttribute.set("modeIn", "OUT");
			    	}else{
			    		adAttribute.set("modeIn", "IN");
			    	}
			    	try {
			    		delegator.create(adAttribute);
			    	} catch (GenericEntityException e) {
			    		e.printStackTrace();
			    		Debug.logWarning(e.getMessage(), module);
			    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
			    	}
		    	}
	    	}else if(adService.get("serviceName").toString().startsWith("update")){
	    		for(GenericValue adField : adFieldList){
	    			//如果该字段已存在对应的参数，则不添加该参数
	    			if(attributeNameList.contains((String)adField.get("fieldName"))){
	    				continue;
	    			}
	    			//如果该字段不存在对应的参数，则添加该参数
	    			String adAttributeId = delegator.getNextSeqId("AdAttribute");
	    			GenericValue adAttribute = null;
	    			adAttribute = delegator.makeValue("AdAttribute", UtilMisc.toMap("adAttributeId", adAttributeId));
	    			adAttribute.set("adServiceId", adServiceId);
	    			adAttribute.set("adEntityId", adField.get("adEntityId"));
	    			adAttribute.set("adFieldId", adField.get("adFieldId"));
	    			adAttribute.set("attributeName", adField.get("fieldName"));
	    			adAttribute.set("type", adField.get("javaType"));
	    			adAttribute.set("optional", "Y");
	    			if("Y".equals(adField.get("ispk"))){
	    				adAttribute.set("modeIn", "IN");
	    			}else{
	    				adAttribute.set("modeIn", "IN");
	    			}
	    			try {
	    				delegator.create(adAttribute);
	    			} catch (GenericEntityException e) {
	    				e.printStackTrace();
	    				Debug.logWarning(e.getMessage(), module);
	    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    			}
	    		}
	    	}else if(adService.get("serviceName").toString().startsWith("delete")){
		    	for(GenericValue adField : adFieldList){
		    		//如果该字段已存在对应的参数，则不添加该参数
		    		if(attributeNameList.contains((String)adField.get("fieldName"))){
		    			continue;
		    		}
		    		//如果该字段不存在对应的参数，则添加该参数
			    	String adAttributeId = delegator.getNextSeqId("AdAttribute");
			    	GenericValue adAttribute = null;
			    	adAttribute = delegator.makeValue("AdAttribute", UtilMisc.toMap("adAttributeId", adAttributeId));
			    	if("Y".equals(adField.get("ispk"))){
			    		adAttribute.set("modeIn", "IN");
				    	adAttribute.set("adServiceId", adServiceId);
				    	adAttribute.set("adEntityId", adField.get("adEntityId"));
				    	adAttribute.set("adFieldId", adField.get("adFieldId"));
				    	adAttribute.set("attributeName", adField.get("fieldName"));
				    	adAttribute.set("type", adField.get("javaType"));
				    	adAttribute.set("optional", "Y");
				    	try {
				    		delegator.create(adAttribute);
				    	} catch (GenericEntityException e) {
				    		e.printStackTrace();
				    		Debug.logWarning(e.getMessage(), module);
				    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
				    	}
			    	}
		    	}
	    	}else{
		    	return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
	    	}
	    	
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    public static Map<String, Object> flushFormfield(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	
	    	Delegator delegator = ctx.getDelegator();
	    	Locale locale = (Locale) context.get("locale");
	    	String adFormId = (String)context.get("adFormId");
	    	List<GenericValue> adFieldList = null;
	    	GenericValue adEntity = null;
	    	List<GenericValue> adFormfieldList = null;
	    	List<String> formFieldNameList = new ArrayList<String>();
	    	GenericValue adForm = null;
	    	try {
	    		adForm = delegator.findOne("AdForm",false, UtilMisc.toMap("adFormId", adFormId));
	    		adEntity = adForm.getRelatedOne("FORM_ENTITYAdEntity",false);
	    		List<String> orderBy = new ArrayList<String>();
	    		orderBy.add("adFieldId");
	    		adFieldList = adEntity.getRelated("FIELD_ENTITYAdField", null, orderBy, false);
	    		adFormfieldList = adForm.getRelated("FORMFIELD_FORMAdFormfield",null,null,false);
	    		for(GenericValue adFormfield:adFormfieldList){
	    			formFieldNameList.add((String)adFormfield.get("formfieldName"));
	    		}
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "1 to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	if(adForm.get("formName").toString().startsWith("edit")){
	    		for(GenericValue adField : adFieldList){
	    			//如果该字段已存在对应的参数，则不添加该参数
	    			if(formFieldNameList.contains((String)adField.get("fieldName"))){
	    				continue;
	    			}
	    			
					try {
		    			//如果该字段不存在对应的参数，则添加该参数
		    			String adFormfieldId = delegator.getNextSeqId("AdFormfield");
		    			GenericValue adFormfield = null;
		    			adFormfield = delegator.makeValue("AdFormfield", UtilMisc.toMap("adFormfieldId", adFormfieldId));
		    			adFormfield.set("adFormId", adFormId);
		    			adFormfield.set("adFieldId", adField.get("adFieldId"));
		    			adFormfield.set("formfieldName", adField.get("fieldName"));
		    			adFormfield.set("title","${uiLabelMap."+ adField.get("fieldName")+"}");
		    			
		    			
		    			String dataType = (String)adField.get("javaType");
		    			String dataLength = Long.toString((Long) adField.get("dataLength"));
		    			String dataScale = Long.toString((Long) adField.get("dataScale"));
		    			if( !UtilValidate.isEmpty(adField.get("ispk"))  && adField.get("ispk").equals("Y")){
		    				adFormfield.set("inputtype", "hidden");
		    			}else if(dataType .equalsIgnoreCase("Blob")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("Timestamp")){
		    				adFormfield.set("inputtype", "dateTimePicker");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "60");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("DATETIME")){
		    				adFormfield.set("inputtype", "dateTimePicker");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "60");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("Date")){
		    				adFormfield.set("inputtype", "datePicker");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "60");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String") && dataLength .equalsIgnoreCase("1")){
		    				adFormfield.set("inputtype", "checkbox");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String") && dataLength.equalsIgnoreCase("10")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String") && dataLength.equalsIgnoreCase("20")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String") && dataLength.equalsIgnoreCase("60")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String") && dataLength.equalsIgnoreCase("100")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String") &&  dataLength.equalsIgnoreCase("250")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String") &&  dataLength.equalsIgnoreCase("255")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("String")){
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}else if(dataType.equalsIgnoreCase("BigDecimal") && dataLength.equalsIgnoreCase("18") && dataScale.equalsIgnoreCase("2")) {
		    				adFormfield.set("inputtype", "numericTextBox");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    				adFormfield.set("format", "#,###.00");
		    			}else if(dataType.equalsIgnoreCase("BigDecimal")&& dataLength.equalsIgnoreCase("18") && dataScale.equalsIgnoreCase("3")){
		    				adFormfield.set("inputtype", "numericTextBox");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    				adFormfield.set("format", "#,###.000");
		    			}else if(dataType.equalsIgnoreCase("BigDecimal")&& dataLength.equalsIgnoreCase("18") && dataScale.equalsIgnoreCase("6")){
		    				adFormfield.set("inputtype", "numericTextBox");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    				adFormfield.set("format", "#,###.000000");
		    			}else if(dataType.equalsIgnoreCase("Double")){
		    				adFormfield.set("inputtype", "numericTextBox");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    				//adFormfield.set("format", "#,###");
		    			}else if(dataType.equalsIgnoreCase("Long")&& dataLength.equalsIgnoreCase("20") && dataScale.equalsIgnoreCase("0")){
		    				adFormfield.set("inputtype", "numericTextBox");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    				adFormfield.set("format", "#,###");
		    			}else{
		    				adFormfield.set("inputtype", "text");
		    				adFormfield.set("position", "1");
		    				adFormfield.set("isactive", "Y");
		    				adFormfield.set("size", "40");
		    				adFormfield.set("maxlength", "60");
		    			}
		    			
		    			

						List<GenericValue> fiedtemp =  adField.getRelated("KEYMAP_FIELDAdKeymap",null,null,false);
						if(!UtilValidate.isEmpty(fiedtemp)){
							adFormfield.set("ddltype", "tableDir");
							GenericValue adRelation = fiedtemp.get(0).getRelatedOne("KEYMAP_RELATIONAdRelation",false);
							GenericValue relEntity =  delegator.findOne("AdEntity",false,UtilMisc.toMap("adEntityId", adRelation.get("relEntityId")));
							GenericValue valueField = relEntity.getRelated("FIELD_ENTITYAdField", UtilMisc.toMap("ispk", "Y"), null, false).get(0);
							GenericValue nameField  = relEntity.getRelated("FIELD_ENTITYAdField", UtilMisc.toMap("isidentifier", "Y"), null, false).get(0);
							//Object ligeng = relEntity.get("entityName");
							adFormfield.set("entity", relEntity.get("entityName"));
							adFormfield.set("namefield",nameField.get("fieldName") );
							adFormfield.set("valuefield",valueField.get("fieldName") );
						}else if ( !UtilValidate.isEmpty((String) adField.get("referencevalue"))  ){
							adFormfield.set("ddltype", "refList");
							adFormfield.set("referencevalue", adField.get("referencevalue"));
						}else{
							
						}
						delegator.create(adFormfield);
					} catch (GenericEntityException e) {
						e.printStackTrace();
						Debug.logWarning(e.getMessage(), module);
						return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "2 to be defined error", new Object[] { e.getMessage() }, locale));
					}catch (Exception e) {
						e.printStackTrace();
						Debug.logWarning(e.getMessage(), module);
						return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "3 to be defined error", new Object[] { e.getMessage() }, locale));
					}
	    		}
	    	}else if(adForm.get("formName").toString().startsWith("find")){
	    		for(GenericValue adField : adFieldList){
	    			//如果该字段已存在对应的参数，则不添加该参数
	    			if(formFieldNameList.contains((String)adField.get("fieldName"))){
	    				continue;
	    			}
	    			if((String)adField.get("isselectioncolumn")==null||!((String)adField.get("isselectioncolumn")).equals("Y")){
	    				continue;
	    			}
	    			//如果该字段不存在对应的参数，则添加该参数
	    			String adFormfieldId = delegator.getNextSeqId("AdFormfield");
	    			GenericValue adFormfield = null;
	    			adFormfield = delegator.makeValue("AdFormfield", UtilMisc.toMap("adFormfieldId", adFormfieldId));
	    			adFormfield.set("adFormId", adFormId);
	    			adFormfield.set("adFieldId", adField.get("adFieldId"));
	    			adFormfield.set("formfieldName", adField.get("fieldName"));
	    			adFormfield.set("title","${uiLabelMap."+ adField.get("fieldName")+"}");
	    			adFormfield.set("inputtype", "text");
	    			adFormfield.set("position", "1");
	    			adFormfield.set("isactive", "Y");
	    			adFormfield.set("size", "40");
	    			adFormfield.set("maxlength", "60");
	    			

					try {
						List<GenericValue> fiedtemp =  adField.getRelated("KEYMAP_FIELDAdKeymap",null,null,false);
						if(!UtilValidate.isEmpty(fiedtemp)){
							adFormfield.set("ddltype", "tableDir");
							GenericValue adRelation = fiedtemp.get(0).getRelatedOne("KEYMAP_RELATIONAdRelation",false);
							GenericValue relEntity =  delegator.findOne("AdEntity",false,UtilMisc.toMap("adEntityId", adRelation.get("relEntityId")));
							GenericValue valueField = relEntity.getRelated("FIELD_ENTITYAdField", UtilMisc.toMap("ispk", "Y"), null, false).get(0);
							GenericValue nameField  = relEntity.getRelated("FIELD_ENTITYAdField", UtilMisc.toMap("isidentifier", "Y"), null, false).get(0);
							adFormfield.set("entity", relEntity.get("entityName"));
							adFormfield.set("namefield", nameField.get("fieldName"));
							adFormfield.set("valuefield", valueField.get("fieldName"));
						}else if ( !UtilValidate.isEmpty((String) adField.get("referencevalue"))  ){
							adFormfield.set("ddltype", "refList");
							adFormfield.set("referencevalue", adField.get("referencevalue"));
						}else{
							
						}
					} catch (GenericEntityException e) {
						e.printStackTrace();
						Debug.logWarning(e.getMessage(), module);
						return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "4 to be defined error", new Object[] { e.getMessage() }, locale));
					}
	    			
	    			try {
	    				delegator.create(adFormfield);
	    			} catch (GenericEntityException e) {
	    				e.printStackTrace();
	    				Debug.logWarning(e.getMessage(), module);
	    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "5 to be defined error", new Object[] { e.getMessage() }, locale));
	    			}
	    		}
	    	} else{
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
	    	}
	    	
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    public static Map<String, Object> flushFormcommand(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	
	    	Delegator delegator = ctx.getDelegator();
	    	Locale locale = (Locale) context.get("locale");
	    	String adFormId = (String)context.get("adFormId");
	    	GenericValue adEntity = null;
	    	GenericValue adForm = null;
	    	try {
	    		adForm = delegator.findOne("AdForm",false, UtilMisc.toMap("adFormId", adFormId));
	    		adEntity = adForm.getRelatedOne("FORM_ENTITYAdEntity",false);
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	if(adForm.get("formName").toString().startsWith("edit")){
    			try {
    				GenericValue adFormcommand = null;
    				//根据adFormId和commandName获取唯一的AdFormcommand
    				EntityCondition conditionAdd = EntityCondition.makeCondition(UtilMisc.toMap("adFormId", adFormId,"commandName","add"));
    				List<GenericValue> tempAdd = delegator.findList("AdFormcommand", conditionAdd, null, null, null, false);
    				//获取主键名
    				String primkeyName = (String) adEntity.getRelated("FIELD_ENTITYAdField",UtilMisc.toMap("ispk", "Y"), null, false).get(0).get("fieldName");
    				
    				if (tempAdd.size()>0) {
						adFormcommand = tempAdd.get(0);
						adFormcommand.put("useWhen",primkeyName+"==null");
						adFormcommand.put("ajaxUrl",  "create"+adEntity.get("entityName"));
						adFormcommand.store();
					}else {
						String adFormcommandId = delegator.getNextSeqId("AdFormcommand");
	        			adFormcommand = delegator.makeValue("AdFormcommand", UtilMisc.toMap("adFormcommandId", adFormcommandId));
	        			adFormcommand.set("adFormId", adFormId);
	        			adFormcommand.set("commandName", "add");
	        			adFormcommand.set("commandValue", "add");
	        			adFormcommand.set("commandType", "button");
	        			adFormcommand.set("style", "k-textbox");
	        			adFormcommand.set("useWhen", primkeyName+"==null");
	        			adFormcommand.set("ajaxUrl", "create"+adEntity.get("entityName"));
						adFormcommand.create();
					}
		    		
				} catch (GenericEntityException e) {
					e.printStackTrace();
					Debug.logWarning(e.getMessage(), module);
					return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
				}
    			try {
    				GenericValue adFormcommand = null;
    				//获取主键名
    				String primkeyName = (String) adEntity.getRelated("FIELD_ENTITYAdField",UtilMisc.toMap("ispk", "Y"), null, false).get(0).get("fieldName");
    				//根据adFormId和commandName获取唯一的AdFormcommand
    				EntityCondition conditionUpdate = EntityCondition.makeCondition(UtilMisc.toMap("adFormId", adFormId,"commandName","update"));
    				List<GenericValue> tempUpdate = delegator.findList("AdFormcommand", conditionUpdate, null, null, null, false);
    				if (tempUpdate.size() > 0) {
						adFormcommand = tempUpdate.get(0);
						adFormcommand.put("useWhen", primkeyName+"!=null");
						adFormcommand.put("ajaxUrl", "update"+adEntity.get("entityName"));
						adFormcommand.store();
					}else {
						String adFormcommandId = delegator.getNextSeqId("AdFormcommand");
	    				adFormcommand = delegator.makeValue("AdFormcommand", UtilMisc.toMap("adFormcommandId", adFormcommandId));
	    				adFormcommand.set("adFormId", adFormId);
	    				adFormcommand.set("commandName", "update");
	    				adFormcommand.set("commandValue", "update");
	    				adFormcommand.set("commandType", "button");
	    				adFormcommand.set("style", "k-textbox");
	    				adFormcommand.set("useWhen", primkeyName+"!=null");
	    				adFormcommand.set("ajaxUrl", "update"+adEntity.get("entityName"));
	    				adFormcommand.create();
					}
    				
    			} catch (GenericEntityException e) {
    				e.printStackTrace();
    				Debug.logWarning(e.getMessage(), module);
    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
    			}
    			
	    	}else if(adForm.get("formName").toString().startsWith("find")){
    			try {
    				
    				GenericValue adFormcommand = null;
    				EntityCondition condition = EntityCondition.makeCondition("adFormId", adFormId);
    				List<GenericValue> tempList = delegator.findList("AdFormcommand", condition, null, null, null, false);
    				//查找是否已存在
    				if (tempList.size() > 0) {
    					adFormcommand =tempList.get(0);
    					adFormcommand.put("updategridname", "grid"+adEntity.get("entityName"));
    					adFormcommand.store();
    				}else {
						String adFormcommandId = delegator.getNextSeqId("AdFormcommand");
						adFormcommand = delegator.makeValue("AdFormcommand", UtilMisc.toMap("adFormcommandId", adFormcommandId));
	    				adFormcommand.set("adFormId", adFormId);
	    				adFormcommand.set("commandName", "refresh");
	    				adFormcommand.set("commandValue", "refresh");
	    				adFormcommand.set("commandType", "button");
	    				adFormcommand.set("style", "k-textbox");
	    				adFormcommand.set("updategridname", "grid"+adEntity.get("entityName"));
	    				adFormcommand.create();
					}
    				
				} catch (GenericEntityException e) {
					e.printStackTrace();
					Debug.logWarning(e.getMessage(), module);
					return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
				}
	    	} else{
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
	    	}
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    
	    public static Map<String, Object> flushGridfield(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	
	    	Delegator delegator = ctx.getDelegator();
	    	Locale locale = (Locale) context.get("locale");
	    	String adGridId = (String)context.get("adGridId");
	    	
	    	List<GenericValue> adFieldList = null;
	    	GenericValue adEntity = null;
	    	List<GenericValue> adGridfieldList = null;
	    	List<String> gridFieldNameList = new ArrayList<String>();
	    	GenericValue adGrid = null;
	    	try {
	    		adGrid = delegator.findOne("AdGrid",false, UtilMisc.toMap("adGridId", adGridId));
	    		adEntity = adGrid.getRelatedOne("GRID_ENTITYAdEntity",false);
	    		List<String> orderBy = new ArrayList<String>();
	    		orderBy.add("adFieldId");
	    		adFieldList = adEntity.getRelated("FIELD_ENTITYAdField", null, orderBy, false);
	    		adGridfieldList = adGrid.getRelated("GRIDFIELD_GRIDAdGridfield",null,null,false);
	    		                                     
	    		for(GenericValue adGridfield:adGridfieldList){
	    			gridFieldNameList.add((String)adGridfield.get("gridfieldName"));
	    		}
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	if(adGrid.get("gridName").toString().startsWith("grid")){
	    		for(GenericValue adField : adFieldList){
	    			//如果该字段已存在对应的参数，则不添加该参数
	    			if(gridFieldNameList.contains((String)adField.get("fieldName"))){
	    				continue;
	    			}
	    			//如果该字段不存在对应的参数，则添加该参数
	    			String adGridfieldId = delegator.getNextSeqId("AdGridfield");
	    			GenericValue adGridfield = null;
	    			adGridfield = delegator.makeValue("AdGridfield", UtilMisc.toMap("adGridfieldId", adGridfieldId));
	    			
	    			adGridfield.set("adGridId", adGridId);
	    			adGridfield.set("adFieldId", adField.get("adFieldId"));
	    			adGridfield.set("gridfieldName", adField.get("fieldName"));
	    			adGridfield.set("lineNo", adField.get("lineNo"));
	    			adGridfield.set("description", adField.get("description"));
	    			
					try {
						List<GenericValue> fiedtemp =  adField.getRelated("KEYMAP_FIELDAdKeymap",null,null,false);
						if(!UtilValidate.isEmpty(fiedtemp)){
							adGridfield.set("ddltype", "tableDir");
							GenericValue adRelation = fiedtemp.get(0).getRelatedOne("KEYMAP_RELATIONAdRelation",false);
							GenericValue relEntity =  delegator.findOne("AdEntity",false,UtilMisc.toMap("adEntityId", adRelation.get("relEntityId")));
							GenericValue valueField = relEntity.getRelated("FIELD_ENTITYAdField", UtilMisc.toMap("ispk", "Y"), null, false).get(0);
							GenericValue nameField  = relEntity.getRelated("FIELD_ENTITYAdField", UtilMisc.toMap("isidentifier", "Y"), null, false).get(0);
							adGridfield.set("entity", relEntity.get("entityName"));
							adGridfield.set("namefield", nameField.get("fieldName"));
							adGridfield.set("valuefield",valueField.get("fieldName"));
						}else if ( !UtilValidate.isEmpty((String) adField.get("referencevalue"))  ){
							adGridfield.set("ddltype", "refList");
							adGridfield.set("referencevalue", adField.get("referencevalue"));
						}else{
							
						}
					} catch (GenericEntityException e) {
						e.printStackTrace();
						Debug.logWarning(e.getMessage(), module);
						return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
					}
	    			
	    			if(((String)adField.get("type")).equals("BLOB")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("date-time")){
	    				adGridfield.set("gridfieldType", "date");
	    				adGridfield.set("format", "{0:yyyy-MM-dd}");
	    				adGridfield.set("width", "120");
	    			}else if(((String)adField.get("type")).equals("date")){
	    				adGridfield.set("gridfieldType", "date");
	    				adGridfield.set("format", "{0:yyyy-MM-dd}");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("time")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("indicator")){
	    				adGridfield.set("gridfieldType", "boolean");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("id-ne")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("id-long")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("name")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("id-vlong")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "100");
	    			}else if(((String)adField.get("type")).equals("long-varchar")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "100");
	    			}else if(((String)adField.get("type")).equals("very-long")){
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("currency-amount")){
	    				adGridfield.set("gridfieldType", "number");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("currency-precise")){
	    				adGridfield.set("gridfieldType", "number");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("fixed-point")){
	    				adGridfield.set("gridfieldType", "number");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("floating-point")){
	    				adGridfield.set("gridfieldType", "number");
	    				adGridfield.set("width", "60");
	    			}else if(((String)adField.get("type")).equals("numeric")){
	    				adGridfield.set("gridfieldType", "number");
	    				adGridfield.set("width", "60");
	    			}else {
	    				adGridfield.set("gridfieldType", "string");
	    				adGridfield.set("width", "60");
	    			}
	    			
	    			BigDecimal width = new BigDecimal(0);
	    			try{
		    			if(!UtilValidate.isEmpty(adField.get("dataLength"))){
		    				width = new BigDecimal((Long)adField.get("dataLength"));
		    			}
		    			if(!UtilValidate.isEmpty(adField.get("dataScale"))){
		    				width = width.add(new BigDecimal((Long)adField.get("dataScale")));
		    			}
	    			}catch(Exception e){
	    				e.printStackTrace();
	    				Debug.logWarning(e.getMessage(), module);
	    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    			}
    				
	    			if(width.intValue()==0){
    					adGridfield.set("width","60"  );
    				}else if(width.intValue()>1 && width.intValue()<=20){
    					adGridfield.set("width","50"  );
    				}else if(width.intValue()>21 && width.intValue()<=60){
    					adGridfield.set("width","60"  );
    				}else{
    					adGridfield.set("width","80"  );
    				}
    				
	    			adGridfield.set("lineNo",delegator.getNextLineNo("AdGridfield", adGridId));
	    			try {
	    				delegator.create(adGridfield);
	    			} catch (GenericEntityException e) {
	    				e.printStackTrace();
	    				Debug.logWarning(e.getMessage(), module);
	    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    			}
	    		}
	    	}else {
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { }, locale));
	    	}
	    	
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    public static Map<String, Object> flushGridcommand(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	Locale locale = (Locale) context.get("locale");
	    	Delegator delegator = ctx.getDelegator();
	    	
	    	String adGridId = (String)context.get("adGridId");
	    	
	    	GenericValue adEntity = null;
	    	GenericValue adGrid = null;
	    	String primkeyName = null;
	    	try {
	    		adGrid = delegator.findOne("AdGrid",false, UtilMisc.toMap("adGridId", adGridId));
	    		adEntity = adGrid.getRelatedOne("GRID_ENTITYAdEntity",false);
	    		
	    		 Map<String,String> byAndFields = new HashMap<String,String>();
	    		 byAndFields.put("ispk", "Y");
	    		 primkeyName = (String) adEntity.getRelated("FIELD_ENTITYAdField", byAndFields, null, false).get(0).get("fieldName");
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	if(adGrid.get("gridName").toString().startsWith("grid")){
	    		
    			GenericValue adGridcommand = null;
    			try {
    				String adGridcommandId = delegator.getNextSeqId("AdGridcommand");
    				adGridcommand = delegator.makeValue("AdGridcommand", UtilMisc.toMap("adGridcommandId", adGridcommandId));
    				adGridcommand.set("adGridId", adGridId);
    				adGridcommand.set("commandName", "edit");
    				adGridcommand.set("commandType", "standard");
    				adGridcommand.set("commandText", "update");
    				adGridcommand.set("commandTitle", "update");
    				adGridcommand.set("width", "200");
    				adGridcommand.set("lineNo",delegator.getNextLineNo("AdGridcommand", adGridId));
    				delegator.create(adGridcommand);
    			} catch (GenericEntityException e) {
    				e.printStackTrace();
    				Debug.logWarning(e.getMessage(), module);
    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
    			}
    			
    			try {
    				adGridcommand = null;
    				String adGridcommandId = delegator.getNextSeqId("AdGridcommand");
    				adGridcommand = delegator.makeValue("AdGridcommand", UtilMisc.toMap("adGridcommandId", adGridcommandId));
    				adGridcommand.set("adGridId", adGridId);
    				adGridcommand.set("commandName", "edit"+adEntity.get("entityName")+"inform");
    				adGridcommand.set("commandType", "client");
    				adGridcommand.set("commandText", "update");
    				adGridcommand.set("commandTitle", "update");
    				adGridcommand.set("width", "60");
					adGridcommand.set("modelId", primkeyName);
    				adGridcommand.set("url", "edit"+adEntity.get("entityName"));
    				adGridcommand.set("lineNo",delegator.getNextLineNo("AdGridcommand", adGridId));
    				delegator.create(adGridcommand);
    			} catch (GenericEntityException e) {
    				e.printStackTrace();
    				Debug.logWarning(e.getMessage(), module);
    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
    			}
    			
    			try {
    				adGridcommand = null;
    				String adGridcommandId = delegator.getNextSeqId("AdGridcommand");
    				adGridcommand = delegator.makeValue("AdGridcommand", UtilMisc.toMap("adGridcommandId", adGridcommandId));
    				adGridcommand.set("adGridId", adGridId);
    				adGridcommand.set("commandName", "destroy");
    				adGridcommand.set("commandType", "standard");
    				adGridcommand.set("commandText", "delete");
    				adGridcommand.set("commandTitle", "delete");
    				adGridcommand.set("width", "100");
    				adGridcommand.set("lineNo",delegator.getNextLineNo("AdGridcommand", adGridId));
    				delegator.create(adGridcommand);
    			} catch (GenericEntityException e) {
    				e.printStackTrace();
    				Debug.logWarning(e.getMessage(), module);
    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
    			}
	    
	    	}else {
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
	    	}
	    	
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    
	    	
	    public static Map<String, Object> createControllerByEntity(DispatchContext ctx, Map<String, Object> context) {
	    	
	    	Map<String, Object> result = FastMap.newInstance();
	    	
	    	Delegator delegator = ctx.getDelegator();
	    	Locale locale = (Locale) context.get("locale");
	    	String adEntityId =  (String)context.get("adEntityId");
	    	
	    	GenericValue adEntity = null;
	    	GenericValue adModule = null;
	    	
	    	try {
	    		adEntity = delegator.findOne("AdEntity",false, UtilMisc.toMap("adEntityId", adEntityId));
	    		adModule = adEntity.getRelatedOne("ENTITY_MODULEAdModule",false);
	    	} catch (GenericEntityException e) {
	    		e.printStackTrace();
	    		Debug.logWarning(e.getMessage(), module);
	    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
	    	}
	    	
	    	List<String> requestmapList =  new ArrayList<String>();
	    	List<String> viewmapList = new ArrayList<String>();
	    	
	    	requestmapList.add("find"+adEntity.get("entityName"));
	    	requestmapList.add("edit"+adEntity.get("entityName"));
	    	requestmapList.add("create"+adEntity.get("entityName"));
	    	requestmapList.add("update"+adEntity.get("entityName"));
	    	requestmapList.add("delete"+adEntity.get("entityName"));
	    	
	    	viewmapList.add("find"+adEntity.get("entityName"));
	    	viewmapList.add("edit"+adEntity.get("entityName"));
	    	
	    	//创建viewmap
	    	try{
	    		
	    		for(String viewmap : viewmapList){
	    			EntityCondition whereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("viewName", viewmap));
	    			EntityListIterator eli =  delegator.find("AdViewmap", whereEntityCondition, null, null, null, null);
	    			List<GenericValue> listIt = null;
	    			if(eli!=null){
	    				listIt = eli.getCompleteList();
	    			}
	    			
	    			if(listIt.size()==0){
	    				String adViewmapId = delegator.getNextSeqId("AdViewmap");
    			    	
    			    	GenericValue adViewmap = null;
    			    	
    			    	adViewmap = delegator.makeValue("AdViewmap", UtilMisc.toMap("adViewmapId", adViewmapId));
    			    	
    			    	adViewmap.set("adModuleId", adModule.get("adModuleId"));
    			    	adViewmap.set("adEntityId", adEntity.get("adEntityId"));
    			    	adViewmap.set("viewName", viewmap);
    			    	if(viewmap.startsWith("find")){
    			    		adViewmap.set("pageuri",adModule.get("widgetPath")+"/"+adModule.get("screensFilename")+ "#"+"find"+adEntity.get("entityName"));
    			    	}else if(viewmap.startsWith("edit")){
    			    		adViewmap.set("pageuri",adModule.get("widgetPath")+"/"+adModule.get("screensFilename")+ "#"+"edit"+adEntity.get("entityName"));
    			    	}else{
    			    		
    			    	}
    			    	adViewmap.set("type", "screen");
    			    	try {
    			    		delegator.create(adViewmap);
    			    	} catch (GenericEntityException e) {
    			    		e.printStackTrace();
    			    		Debug.logWarning(e.getMessage(), module);
    			    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
    			    	}
	    			}else if (listIt.size()==1){
	    				
	    			
	    			}else {
	    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
	    			}
	    		}
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	
	    	//创建requestmap
    		try {
    			for(String requestmap : requestmapList){
    				System.out.println("requestmap:="+requestmap);
    				GenericValue adRequestmap = null;
    				
    				EntityCondition whereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("uri", requestmap));
	    			EntityListIterator eli =  delegator.find("AdRequestmap", whereEntityCondition, null, null, null, null);
	    			List<GenericValue> listIt = null;
	    			if(eli!=null){
	    				listIt = eli.getCompleteList();
	    			}
    				
    				if (listIt.size()==0) {
    					System.out.println("uri:="+requestmap);
    					String adRequestmapId = delegator.getNextSeqId("AdRequestmap");
    			    	adRequestmap = delegator.makeValue("AdRequestmap", UtilMisc.toMap("adRequestmapId", adRequestmapId));
    			    	
    			    	adRequestmap.set("adEntityId", adEntity.get("adEntityId"));
    			    	adRequestmap.set("adModuleId", adEntity.get("adModuleId"));
    			    	adRequestmap.set("uri", requestmap);
    			    	adRequestmap.set("requestName", requestmap);
    			    	adRequestmap.set("https", "Y");
    			    	adRequestmap.set("auth", "Y");
    			    	
    			    	if(requestmap.startsWith("create")||requestmap.startsWith("update")||requestmap.startsWith("delete")){
    			    		adRequestmap.set("eventType", "service");
    			    		//adRequestmap.set("eventPath", "");
    			    		adRequestmap.set("eventInvoke", requestmap);
    			    	}
    			    	
    			    	try {
    			    		delegator.create(adRequestmap);
    			    	} catch (GenericEntityException e) {
    			    		e.printStackTrace();
    			    	}
    			    	
    			    	
    			    	List<String> responseList = new ArrayList<String>();
    			    	responseList.add("success");
    			    	responseList.add("error");
    			    	
    			    	for(String response : responseList){
    			    		String adResponseId = delegator.getNextSeqId("AdResponse");
    			    		GenericValue adResponse = null;
    			    		adResponse = delegator.makeValue("AdResponse", UtilMisc.toMap("adResponseId", adResponseId));
    			    		adResponse.set("adRequestmapId", adRequestmap.get("adRequestmapId"));
    			    		adResponse.set("responseName", response);
    			    		adResponse.set("adModuleId", adEntity.get("adModuleId"));
    			    		//adResponse.set("adEntityId", adEntity.get("adEntityId"));
    			    		
    			    		if(requestmap.startsWith("find")){
    			    			EntityCondition con = EntityCondition.makeCondition(UtilMisc.toMap("viewName", "find"+adEntity.get("entityName")));
    			    			EntityListIterator viewli =  delegator.find("AdViewmap", con, null, null, null, null);
    			    			List<GenericValue> adViewmapList = null;
    			    			if(viewli!=null){
    			    				adViewmapList = viewli.getCompleteList();
    			    			}else{
    			    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
    			    			}
    			    			if(adViewmapList.size()==0){
    			    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
    			    			}else{
    			    				adResponse.set("type", "view");
    			    				adResponse.set("value", adViewmapList.get(0).get("viewName"));
    			    				adResponse.set("adViewmapId", adViewmapList.get(0).get("adViewmapId"));
    			    			}
    			    		}else if(requestmap.startsWith("edit")){
    			    			EntityCondition con = EntityCondition.makeCondition(UtilMisc.toMap("viewName", "edit"+adEntity.get("entityName")));
    			    			EntityListIterator viewli =  delegator.find("AdViewmap", con, null, null, null, null);
    			    			List<GenericValue> adViewmapList = null;
    			    			if(viewli!=null){
    			    				adViewmapList = viewli.getCompleteList();
    			    			}
    			    			
    			    			if(adViewmapList.size()==0){
    			    				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { }, locale));
    			    			}else{
    			    				adResponse.set("type", "view");
    			    				adResponse.set("value", adViewmapList.get(0).get("viewName"));
    			    				adResponse.set("adViewmapId", adViewmapList.get(0).get("adViewmapId"));
    			    			}
    			    		}
    			    		else if(requestmap.startsWith("delete")||requestmap.startsWith("create")||requestmap.startsWith("update")){
			    				adResponse.set("type", "request");
			    				adResponse.set("value", "json");
    			    		}else{
    			    			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { }, locale));
    			    		}
        			    	try {
        			    		delegator.create(adResponse);
        			    	} catch (GenericEntityException e) {
        			    		e.printStackTrace();
        			    		Debug.logWarning(e.getMessage(), module);
        			    		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
        			    	}
    			    	}
    			    	
//    			    	if(requestmap.startsWith("create")){
//    			    		
//    			    		System.out.println("invoke="+"create"+adEntity.get("entityName"));
//    			    		List<GenericValue> adEventList = delegator.findByAnd("AdEvent",UtilMisc.toMap("invoke", "create"+adEntity.get("entityName")));
//    			    		if(adEventList.size()==1){
//    			    			adRequestmap.set("adEventId", adEventList.get(0).get("adEventId"));
//    			    			adRequestmap.store();
//    			    		}else{
//    			    			System.out.println("adEventList.size()="+adEventList.size());
//    			    			throw new Exception("--------------------------------------------");
//    			    		}
//    			    	}
//    			    	if(requestmap.startsWith("update")){
//    			    		List<GenericValue> adEventList = delegator.findByAnd("AdEvent",UtilMisc.toMap("invoke", "update"+adEntity.get("entityName")));
//    			    		if(adEventList.size()==1){
//    			    			adRequestmap.set("adEventId", adEventList.get(0).get("adEventId"));
//    			    			adRequestmap.store();
//    			    		}else{
//    			    			System.out.println("adEventList.size()="+adEventList.size());
//    			    			throw new Exception("--------------------------------------------");
//    			    		}
//    			    	}
//    			    	if(requestmap.startsWith("delete")){
//    			    		List<GenericValue> adEventList = delegator.findByAnd("AdEvent",UtilMisc.toMap("invoke", "delete"+adEntity.get("entityName")));
//    			    		if(adEventList.size()==1){
//    			    			adRequestmap.set("adEventId", adEventList.get(0).get("adEventId"));
//    			    			adRequestmap.store();
//    			    		}else{
//    			    			System.out.println("adEventList.size()="+adEventList.size());
//    			    			throw new Exception("--------------------------------------------");
//    			    		}
//    			    	}
//    			    	if(requestmap.startsWith("save")){
//    			    		List<GenericValue> adEventList = delegator.findByAnd("AdEvent",UtilMisc.toMap("invoke", "save"+adEntity.get("entityName")));
//    			    		if(adEventList.size()==1){
//    			    			adRequestmap.set("adEventId", adEventList.get(0).get("adEventId"));
//    			    			adRequestmap.store();
//    			    		}else{
//    			    			System.out.println("adEventList.size()="+adEventList.size());
//    			    			throw new Exception("--------------------------------------------");
//    			    		}
//    			    	}
    			    	
    				}else if(listIt.size()== 1){
        				//该requestmap存在，不做任何操作。

        			}else{
        				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] {  }, locale));
    				}
    			}
			} catch (GenericEntityException e) {
				e.printStackTrace();
				Debug.logWarning(e.getMessage(), module);
				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
			} catch (Exception e) {
				e.printStackTrace();
				Debug.logWarning(e.getMessage(), module);
				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
			}
	    	
	    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
	    	return result;
	    }
	    
	    

	}
