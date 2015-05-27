package org.ofbiz.devtools.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.util.EntityListIterator;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AdModuleServices {

	public static final String module = AdModuleServices.class.getName();
	public static final String resource = "EntityUiLabels";
	public static final String resourceError = "EntityErrorUiLabels";
	/**
	 * @param ctx
	 * @param context
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> createEntityFile(DispatchContext ctx,Map<String, Object> context) {

		Delegator delegator = ctx.getDelegator();
		String adModuleId = (String) context.get("adModuleId");
		Locale locale = (Locale) context.get("locale");
		GenericValue adModule = null;

		String templatePath = "./framework/devtools/templates/entitymodel";
		String entityStr = new String();
		StringBuffer entitiesSb = new StringBuffer();
		try {
			// 获得模块
			adModule = delegator.findOne("AdModule",false,UtilMisc.toMap("adModuleId", adModuleId));
			HashMap root = new HashMap();
			
			// 获得与模块相关联的实体
			List<GenericValue> adEntityList = adModule.getRelated("ENTITY_MODULEAdEntity", null,null,false);

			// System.out.println("---------------------adEntityList.size:="+adEntityList.size());

			// adModule.
			StringBuffer sb = new StringBuffer();
			for (GenericValue adEntity : adEntityList) {
				sb = new StringBuffer();
				// 获得与实体相关联的字段
				List<GenericValue> adFieldList = adEntity.getRelated("FIELD_ENTITYAdField", null,null,false);
				for (GenericValue adField : adFieldList) {
					root = new HashMap();
					root.put("adField", adField);
					sb.append(assemblingFtl(templatePath,"field.ftl", root));
				}

				// 设置主键
				List<GenericValue> adPrimkeyList = adEntity.getRelated("PRIMKEY_ENTITYAdPrimkey",null,null,false);
				for (GenericValue adPrimkey : adPrimkeyList) {

					GenericValue AdField = adPrimkey.getRelatedOne("PRIMKEY_FIELDAdField",false);

					root = new HashMap();
					root.put("adPrimkey", adPrimkey);
					root.put("AdField", AdField);
					sb.append(assemblingFtl(templatePath,"prim-key.ftl", root));
				}

				// 获得与实体相关联的引用关系--one
				List<GenericValue> adRelationList = adEntity.getRelated("RELATION_ENTITYAdRelation",null,null,false);
				// System.out.println("=============================adRelationList.size:="+adRelationList.size());
				String adKeymapStr = null;
				String reladKeymapStr = null;
				for (GenericValue adRelation : adRelationList) {
					List<GenericValue> adKeymapList = adRelation.getRelated("KEYMAP_RELATIONAdKeymap",null,null,false);
					// System.out.println("=============================adKeymapList.size:="+adKeymapList.size());
					adKeymapStr = new String();
					for (GenericValue adKeymap : adKeymapList) {
						root = new HashMap();
						root.put("adKeymap", adKeymap);
						root.put("field",adKeymap.getRelatedOne("KEYMAP_FIELDAdField",false));
						root.put("relField", adKeymap.getRelatedOne("KEYMAP_REFFIELDAdField",false));
						adKeymapStr = adKeymapStr+ assemblingFtl(templatePath,"key-map.ftl", root);
						// System.out.println("adKeymap:="+adKeymapStr);
					}
					root = new HashMap();
					root.put("adKeymapStr", adKeymapStr);
					root.put("adRelation", adRelation);
					GenericValue relEntity = adRelation.getRelatedOne("RELATION_RELENTITYAdEntity",false);
					root.put("relEntity", relEntity);
					String temp = assemblingFtl(templatePath,"relation_one.ftl", root);
					sb.append(temp);
					// System.out.println("relation:="+temp);
				}

				// 获得与实体相关联的被引用关系--many
				List<GenericValue> relRelationList = adEntity.getRelated("RELATION_RELENTITYAdRelation",null,null,false);
				// System.out.println("=============================relRelationList.size:="+relRelationList.size());

				for (GenericValue relRelation : relRelationList) {
					List<GenericValue> adKeymapList = relRelation.getRelated("KEYMAP_RELATIONAdKeymap",null,null,false);
					// System.out.println("=============================adKeymapList.size:="+adKeymapList.size());
					//String relKeymapStr = new String();
					reladKeymapStr = new String();
					for (GenericValue adKeymap : adKeymapList) {
						root = new HashMap();
						root.put("adKeymap", adKeymap);
						root.put("field", adKeymap.getRelatedOne("KEYMAP_REFFIELDAdField",false));
						root.put("relField",adKeymap.getRelatedOne("KEYMAP_FIELDAdField",false));
						reladKeymapStr = reladKeymapStr+assemblingFtl(templatePath,"key-map.ftl", root);
						// System.out.println("relKeymap:="+reladKeymapStr);
					}
					root = new HashMap();
					root.put("adKeymapStr", reladKeymapStr);
					root.put("adRelation", relRelation);
					GenericValue relEntity = relRelation.getRelatedOne("RELATION_ENTITYAdEntity",false);
					root.put("relEntity", relEntity);
					String temp = assemblingFtl(templatePath,"relation_many.ftl", root);
					sb.append(temp);
					// System.out.println("relRelation:="+temp);
				}

				root = new HashMap();
				root.put("adEntity", adEntity);
				root.put("adModule", adModule);
				root.put("entityStr", sb.toString());
				entityStr = assemblingFtl(templatePath,"entity.ftl", root).toString();

				entitiesSb.append(entityStr);
				// System.out.println("entityStr:="+entityStr);
			}
			root = new HashMap();
			root.put("adModule", adModule);
			root.put("entitiesSb", entitiesSb);

			String modelString = assemblingFtl(templatePath,"entitymodel.ftl", root);

			generateConfFile((String)adModule.get("entityFilepath"),(String)adModule.get("entityFilename"), modelString);
		} catch (GenericEntityException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (IOException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (TemplateException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		}
		return ServiceUtil.returnSuccess();

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> createServiceFile(DispatchContext ctx,Map<String, Object> context) {
		
		Delegator delegator = ctx.getDelegator();
		String adModuleId = (String) context.get("adModuleId");
		Locale locale = (Locale) context.get("locale");
		GenericValue adModule = null;
		
		String templateFilepath = "./framework/devtools/templates/services";
		
		try {
			// 获得模块
			adModule = delegator.findOne("AdModule",false,UtilMisc.toMap("adModuleId", adModuleId));
			
			HashMap root = new HashMap();

			
			// 获得与模块相关联的服务
			List<GenericValue> adServiceList = adModule.getRelated("SERVICE_MODULEAdService", null,null,false);

			// adModule.
			StringBuffer serviceSb = new StringBuffer();
			for (GenericValue adService : adServiceList) {
				
				StringBuffer attributeSb = new StringBuffer();
				// 获得与实体相关联的字段
				List<GenericValue> adAttributeList = adService.getRelated("ATTRIBUTE_SERVICEAdAttribute", null,null,false);
				for (GenericValue adAttribute : adAttributeList) {
					root = new HashMap();
					
					GenericValue adEntity = adAttribute.getRelatedOne("ATTRIBUTE_ENTITYAdEntity",false);
					GenericValue adField = adAttribute.getRelatedOne("ATTRIBUTE_FIELDAdField",false);
					
					root.put("adAttribute", adAttribute);
					root.put("adEntity", adEntity);
					root.put("adField", adField);
					String temp = assemblingFtl(templateFilepath,"attribute.ftl", root);
					attributeSb.append(temp);
				}
				
				root = new HashMap();
				root.put("adService", adService);
				root.put("attributeSb", attributeSb.toString());
				String temp = assemblingFtl(templateFilepath,"service.ftl", root);
				serviceSb.append(temp);
			}
			root = new HashMap();
			
			root.put("adModule", adModule);
			root.put("serviceSb", serviceSb);

			String serviceString = assemblingFtl(templateFilepath,"services.ftl", root);

			generateConfFile((String)adModule.get("serviceFilepath"),(String)adModule.get("serviceFilename"), serviceString);

		} catch (GenericEntityException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (IOException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (TemplateException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		}
		return ServiceUtil.returnSuccess();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> createFormFile(DispatchContext ctx,Map<String, Object> context) {
		
		Delegator delegator = ctx.getDelegator();
		String adModuleId = (String) context.get("adModuleId");
		Locale locale = (Locale) context.get("locale");
		GenericValue adModule = null;
		
		String templateFilepath = "./framework/devtools/templates/form";
		
		
		try {
			// 获得模块
			adModule = delegator.findOne("AdModule",false,UtilMisc.toMap("adModuleId", adModuleId));
			
			HashMap root = new HashMap();
			
			
			// 获得与模块相关联的服务
			List<GenericValue> adFormList = adModule.getRelated("FORM_MODULEAdForm", null,null,false);
			
			// adModule.
			StringBuffer formSb = new StringBuffer();
			for (GenericValue adForm : adFormList) {
				
				StringBuffer formfieldSb = new StringBuffer();
				StringBuffer formbuttonSb = new StringBuffer();
				// 获得与实体相关联的字段
				List<GenericValue> adFormfieldList = adForm.getRelated("FORMFIELD_FORMAdFormfield", null,null,false);
				for (GenericValue adFormfield : adFormfieldList) {
					root = new HashMap();
					root.put("adFormfield", adFormfield);
					
					formfieldSb.append(assemblingFtl(templateFilepath,"formfield.ftl", root));
				}
				
				List<GenericValue> adFormcommandList = adForm.getRelated("FORMCOMMAND_FORMAdFormcommand", null,null,false);
				for (GenericValue adFormcommand : adFormcommandList) {
					root = new HashMap();
					root.put("adFormcommand", adFormcommand);
					formbuttonSb.append(assemblingFtl(templateFilepath,"formbutton.ftl", root));
				}
				
				root = new HashMap();
				root.put("adForm", adForm);
				root.put("formfieldSb", formfieldSb.toString());
				root.put("formbuttonSb", formbuttonSb.toString());
				formSb.append(assemblingFtl(templateFilepath,"form.ftl", root));
			}
			root = new HashMap();
			
			root.put("formSb", formSb);
			
			String serviceString = assemblingFtl(templateFilepath,"forms.ftl", root);
			
			generateConfFile((String)adModule.get("widgetPath"),(String)adModule.get("formsFilename"), serviceString);
			
		} catch (GenericEntityException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (IOException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (TemplateException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		}
		return ServiceUtil.returnSuccess();
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> createGridFile(DispatchContext ctx,Map<String, Object> context) {
		
		Delegator delegator = ctx.getDelegator();
		String adModuleId = (String) context.get("adModuleId");
		Locale locale = (Locale) context.get("locale");
		GenericValue adModule = null;
		GenericValue adEntity = null;
		
		String templateFilepath = "./framework/devtools/templates/grid";
		
		
		try {
			// 获得模块
			adModule = delegator.findOne("AdModule",false,UtilMisc.toMap("adModuleId", adModuleId));
			
			HashMap root = new HashMap();
			
			
			// 获得与模块相关联的服务
			List<GenericValue> adGridList = adModule.getRelated("GRID_MODULEAdGrid", null,null,false);
			
			// adModule.
			StringBuffer gridSb = new StringBuffer();
			for (GenericValue adGrid : adGridList) {
				adEntity = adGrid.getRelatedOne("GRID_ENTITYAdEntity", false);
				
				StringBuffer gridcolumeSb = new StringBuffer();
				StringBuffer gridcommandSb = new StringBuffer();
				StringBuffer gridfieldSb = new StringBuffer();
				// 获得与实体相关联的字段
				List<GenericValue> adGridfieldList = adGrid.getRelated("GRIDFIELD_GRIDAdGridfield", null,null,false);
				for (GenericValue adGridfield : adGridfieldList) {
					root = new HashMap();
					root.put("adGridfield", adGridfield);
					gridcolumeSb.append(assemblingFtl(templateFilepath,"gridcolume.ftl", root));
					gridfieldSb.append(assemblingFtl(templateFilepath,"gridfield.ftl", root));
				}
				List<GenericValue> adGridcommandList = adGrid.getRelated("GRIDCOMMAND_GRIDAdGridcommand", null,null,false);
				for (GenericValue adGridcommand : adGridcommandList) {
					root = new HashMap();
					root.put("adGridcommand", adGridcommand);
					gridcommandSb.append(assemblingFtl(templateFilepath,"gridcommand.ftl", root));
				}
				
				root = new HashMap();
				root.put("adGrid", adGrid);
				root.put("adEntity", adEntity);
				
				root.put("gridcolumeSb", gridcolumeSb.toString());
				root.put("gridcommandSb", gridcommandSb.toString());
				root.put("gridfieldSb", gridfieldSb.toString());
				gridSb.append(assemblingFtl(templateFilepath,"grid.ftl", root));
			}
			root = new HashMap();
			
			root.put("gridSb", gridSb);
			
			String gridString = assemblingFtl(templateFilepath,"grids.ftl", root);
			
			generateConfFile((String)adModule.get("widgetPath"),(String)adModule.get("gridsFilename"), gridString);
			
		} catch (GenericEntityException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (IOException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} catch (TemplateException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		}
		return ServiceUtil.returnSuccess();
	}	
	
	public static Map<String, Object> createMainPage(DispatchContext ctx, Map<String, Object> context) {
		Map<String, Object> result = FastMap.newInstance();
		Delegator delegator = ctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
    	String adModuleId =  (String)context.get("adModuleId");
    	GenericValue adModule = null;
    	try {
    		adModule = delegator.findOne("AdModule",false, UtilMisc.toMap("adModuleId", adModuleId));
    	} catch (GenericEntityException e) {
    		Debug.logWarning(e.getMessage(), module);
    	}
    	GenericValue adViewmap = null;
    	//创建viewmap
    	try{
    		EntityCondition whereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("viewName", "main","adModuleId",adModuleId));
    		EntityListIterator eli =  delegator.find("AdViewmap", whereEntityCondition, null, null, null, null);
    		List<GenericValue> listIt = null;
    		if(eli!=null){
    			listIt = eli.getCompleteList();
    		}
    		if(listIt.size()==0){
    			try {
    	    		String adViewmapId = delegator.getNextSeqId("AdViewmap");
    		    	adViewmap = delegator.makeValue("AdViewmap", UtilMisc.toMap("adViewmapId", adViewmapId));
    		    	adViewmap.set("adModuleId", adModuleId);
    		    	adViewmap.set("viewName", "main");
    		    	adViewmap.set("type", "screen");
    		    	adViewmap.set("pageuri",adModule.get("widgetPath")+"/"+adModule.get("screensFilename")+ "#"+"main");
    		    	delegator.create(adViewmap);
    			} catch (GenericEntityException e) {
    				e.printStackTrace();
    			}
    		}else if (listIt.size()==1){
    			
    		}else {
    			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { }, locale));
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	//创建requestmap
		try {
			GenericValue adRequestmap = null;
			
			EntityCondition whereEntityCondition = EntityCondition.makeCondition(UtilMisc.toMap("uri", "main","adModuleId",adModuleId));
			EntityListIterator eli =  delegator.find("AdRequestmap", whereEntityCondition, null, null, null, null);
			List<GenericValue> listIt = null;
			if(eli!=null){
				listIt = eli.getCompleteList();
			}
			
			if (listIt.size()==0) {
		    	try {
					String adRequestmapId = delegator.getNextSeqId("AdRequestmap");
			    	adRequestmap = delegator.makeValue("AdRequestmap", UtilMisc.toMap("adRequestmapId", adRequestmapId));
			    	adRequestmap.set("adModuleId", adModuleId);
			    	adRequestmap.set("uri", "main");
			    	adRequestmap.set("requestName", "main");
			    	adRequestmap.set("https", "Y");
			    	adRequestmap.set("auth", "Y");
		    		delegator.create(adRequestmap);
		    	} catch (GenericEntityException e) {
		    		e.printStackTrace();
					Debug.logWarning(e.getMessage(), module);
					return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		    	}

		    	try {
		    		String adResponseId = delegator.getNextSeqId("AdResponse");
		    		GenericValue adResponse = null;
		    		adResponse = delegator.makeValue("AdResponse", UtilMisc.toMap("adResponseId", adResponseId));
		    		adResponse.set("adRequestmapId", adRequestmap.get("adRequestmapId"));
		    		adResponse.set("responseName", "success");
		    		adResponse.set("adModuleId", adModuleId);
					adResponse.set("type", "view");
					adResponse.set("value", adViewmap.get("viewName"));
					adResponse.set("adViewmapId", adViewmap.get("adViewmapId"));
		    		delegator.create(adResponse);
		    	} catch (GenericEntityException e) {
		    		e.printStackTrace();
					Debug.logWarning(e.getMessage(), module);
					return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		    	}
			}else if(listIt.size()== 1){

			}else{
				return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { }, locale));
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
	
    public static Map<String, Object> createJavaByEntity(DispatchContext ctx, Map<String, Object> context) {
		Delegator delegator = ctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();
		String adEntityId =  (String)context.get("adEntityId");
    	GenericValue adEntity = null;
    	GenericValue adModule = null;
    	GenericValue pkField = null;
    	try {
    		adEntity = delegator.findOne("AdEntity",false, UtilMisc.toMap("adEntityId", adEntityId));
    		adModule = adEntity.getRelatedOne("ENTITY_MODULEAdModule",false);
    		pkField = adEntity.getRelated("FIELD_ENTITYAdField", UtilMisc.toMap("ispk", "Y"), null, false).get(0);
    	} catch (GenericEntityException e) {
    		e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
    	} 
		String templateFilepath = "./framework/devtools/templates/java";
		try {
			HashMap<String, Serializable> root = new HashMap<String, Serializable>();
			root.put("adModule", adModule);
			root.put("adEntity", adEntity);
			root.put("pkField", pkField);
			String javaString = assemblingFtl(templateFilepath,"Services.ftl", root);
			generateConfFile((String)adModule.get("javaFilepath"),adEntity.get("entityName")+"Services.java" , javaString);
			//generateConfFile("./applications/contentmgr/src/org/ofbiz/contentmgr",adEntity.get("entityName")+"Services.java" , javaString);
		} catch (Exception e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
//			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
//			return result;
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
		} 
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
    }
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> createControllerFile(DispatchContext ctx,Map<String, Object> context) {
		
		Delegator delegator = ctx.getDelegator();
		Locale locale = (Locale) context.get("locale");
		String adModuleId = (String) context.get("adModuleId");
		Map<String, Object> result = FastMap.newInstance();
		GenericValue adModule = null;
		
		String templateFilepath = "./framework/devtools/templates/controller";
		
		try {
			// 获得模块
			adModule = delegator.findOne("AdModule",false,UtilMisc.toMap("adModuleId", adModuleId));
			
			HashMap root = new HashMap();
			
			// 获得与模块相关联的服务
			List<GenericValue> adRequestmapList = adModule.getRelated("REQUESTMAP_MODULEAdRequestmap", null,null,false);

			StringBuffer requestMapSb = new StringBuffer();
			for (GenericValue adRequestmap : adRequestmapList) {
				                                                           
				List<GenericValue> responseList = adRequestmap.getRelated("RESPONSE_REQUESTMAPAdResponse", null,null,false);
				System.out.println("adRequestmap:="+adRequestmap.get("uri")+"  responseSize:="+responseList.size());
				StringBuffer responseSb = new StringBuffer();
				for(GenericValue adResponse : responseList){
					root = new HashMap();
					root.put("adResponse", adResponse);
					responseSb.append(assemblingFtl(templateFilepath,"response.ftl", root));
				}
				
				root = new HashMap();
				root.put("adRequestmap", adRequestmap);
				root.put("responseSb", responseSb.toString());
				requestMapSb.append(assemblingFtl(templateFilepath,"request-map.ftl", root));
			}
			
			StringBuffer viewMapSb = new StringBuffer();
			List<GenericValue> adViewmapList = adModule.getRelated("VIEWMAP_MODULEAdViewmap", null,null,false);
			for (GenericValue adViewmap : adViewmapList) {
				root = new HashMap();
				root.put("adViewmap", adViewmap);
				viewMapSb.append(assemblingFtl(templateFilepath,"view-map.ftl", root));
			}
			
			root = new HashMap();
			root.put("requestMapSb", requestMapSb.toString());
			root.put("viewMapSb", viewMapSb.toString());
			
			String controllerString = assemblingFtl(templateFilepath,"site-conf.ftl", root);
			                   
			generateConfFile((String)adModule.get("controllerFilepath"),(String)adModule.get("controllerFilename"), controllerString);
			
		} catch (GenericEntityException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
//			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
//			return result;
			System.out.println("==========1");
			System.out.println(UtilProperties.getMessage(resourceError,  "AdModuleService.createControllerFile", new Object[] { e.getMessage() }, locale));
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "AdModuleService.createControllerFile", new Object[] { e.getMessage() }, locale));
		} catch (Exception e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
//			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "to be defined error", new Object[] { e.getMessage() }, locale));
//			return result;
			System.out.println("==========2");
			System.out.println(UtilProperties.getMessage(resourceError,  "AdModuleService.createControllerFile", new Object[] { e.getMessage() }, locale));
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,  "AdModuleService.createControllerFile", new Object[] { e.getMessage() }, locale));

		}
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}	
	/**
	 * 将flt文件及其内容组装为一个字符串
	 * @param path flt文件路径
	 * @param ftlFileName flt文件名
	 * @param root flt文件内容
	 * @return 组装完已字符串形式返回
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	private static String assemblingFtl(String path, String ftlFileName, HashMap<String, Serializable> root) throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		File filePath = new File(path);
		StringWriter out = new StringWriter();
		try {
			cfg.setDirectoryForTemplateLoading(filePath);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			Template temp = cfg.getTemplate(ftlFileName);
			temp.process(root, out);
		} catch (IOException e) {
			throw e;
		} catch (TemplateException e) {
			throw e;
		} catch(Exception e){
			try {
				throw new Exception(e.getMessage());
			} catch (Exception e1) {
			}
		}
		return out.getBuffer().toString();
	}

	
	private static void generateConfFile(String path, String fileName, String s) throws IOException {
		File file = new File(path+"/"+fileName);
		if (file.exists()) {
			Date now =  new Date();
			file.renameTo(new File(path+"/"+fileName+now.getTime()));
		}
		try {
			OutputStream ops = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(ops);
			//s = s.replace("\r\r", "\r");
			s = s.replace("\r\n\r\n", "\r\n");
			s = s.replace("\r\n\r\n", "\r\n");
			osw.write(s, 0, s.length());
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}

	}
}
