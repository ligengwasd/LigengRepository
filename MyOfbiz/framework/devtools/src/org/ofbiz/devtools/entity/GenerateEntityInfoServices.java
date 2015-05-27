package org.ofbiz.devtools.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityExpr;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.datasource.GenericHelperInfo;
import org.ofbiz.entity.jdbc.SQLProcessor;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ModelService;

public class GenerateEntityInfoServices {

	public static final String module = GenerateEntityInfoServices.class.getName();
	public static final String resource = "GenerateEntityInfoUiLabels";
	public static final String resourceError = "GenerateEntityInfoErrorUiLabels";
 
    public static Map<String, Object> generateEntityInfo(DispatchContext ctx, Map<String, Object> context) throws Exception {
    	
    	Delegator delegator = ctx.getDelegator();
    	Map<String, Object> result = FastMap.newInstance();
    	GenericHelperInfo helperInfo = delegator.getGroupHelperInfo(delegator.getEntityGroupName("org.ofbiz"));
    	SQLProcessor p = new SQLProcessor(helperInfo);
    	
    	String tableName = (String)context.get("tableName");
    	String schema="ofbiz";
    	
    	//查询“tablename”这个表是否存在
    	String sql1 = "SELECT TABLE_COMMENT TABLECOMMENT   FROM INFORMATION_SCHEMA.TABLES K WHERE K.TABLE_SCHEMA =\""+schema+"\" AND K.TABLE_NAME=\""+tableName.toUpperCase()+"\"";
    	//查询表的列,列类型,列长度
    	String sql2 = "SELECT S.COLUMN_NAME COLUMNNAME,S.DATA_TYPE DATATYPE, S.CHARACTER_MAXIMUM_LENGTH DATALENGTH,S.NUMERIC_PRECISION  DATAPRECISION,S.NUMERIC_SCALE DATASCALE,S.COLUMN_COMMENT COMMENT FROM  INFORMATION_SCHEMA.COLUMNS S WHERE TABLE_SCHEMA=\""+schema+"\" AND  TABLE_NAME=\""+tableName.toUpperCase()+"\" ORDER BY ORDINAL_POSITION";
    	
    	//查询表的主键
    	String sql3 = "SELECT  c.COLUMN_NAME COLUMNNAME FROM  INFORMATION_SCHEMA.TABLE_CONSTRAINTS AS t LEFT JOIN   INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS c ON t.TABLE_NAME = c.TABLE_NAME WHERE t.TABLE_SCHEMA = \""+schema+"\"  AND t.CONSTRAINT_TYPE = \"PRIMARY KEY\"  AND c.CONSTRAINT_NAME=\"PRIMARY\"   AND t.TABLE_NAME=\""+tableName.toUpperCase()+"\"";
    	//查询表的外键
    	String sql4 = "SELECT  CONSTRAINT_NAME  FKNAME, COLUMN_NAME    FIELDNAME, REFERENCED_TABLE_NAME  RELENTITYNAME, REFERENCED_COLUMN_NAME  RELFIELDNAME FROM  INFORMATION_SCHEMA.KEY_COLUMN_USAGE   WHERE  TABLE_SCHEMA= \""+schema+"\" AND TABLE_NAME = \""+tableName.toUpperCase()+"\" AND REFERENCED_TABLE_NAME IS NOT NULL  ";
    	
    	String adEntityId = null;
    	try {
			p.prepareStatement(sql1);
			ResultSet r1 = p.executeQuery();
			p.prepareStatement(sql2);
			ResultSet r2 = p.executeQuery();
			p.prepareStatement(sql3);
			ResultSet r3 = p.executeQuery();
			p.prepareStatement(sql4);
			ResultSet r4 = p.executeQuery();
			
			GenericValue adEntity = null;
			
			
			//如果数据库中存在该表，则在ad_entity表中插入该表的信息,不存在则抛出异常
			//如果数据库中存在该表，ad_entity表中也存在该表，则提取该表
			if(r1.next()){
		         try {
		        	List<GenericValue> adEntityList = delegator.findByAnd("AdEntity", UtilMisc.toMap("tableName", tableName));
		        	 
		        	if(adEntityList.size() == 0){
		        		adEntityId = delegator.getNextSeqId("AdEntity");
				        adEntity = delegator.makeValue("AdEntity", UtilMisc.toMap("adEntityId", adEntityId));
				        adEntity.setNonPKFields(context);
				        adEntity.set("entityName", UtilMisc.tableName2EntityName(tableName));
				        adEntity.set("description", r1.getString("TABLECOMMENT"));
				        try {
							delegator.create(adEntity);
				        } catch (GenericEntityException e) {
							e.printStackTrace();
				        }
		        	}else if(adEntityList.size()==1){
		        		adEntity = adEntityList.get(0);
		        		adEntityId = adEntity.get("adEntityId").toString();
		        	}else{
		        		throw new Exception("the table was exists!  "+tableName);
		        	}
		        	
		         } catch (GenericEntityException e) {
		             Debug.logWarning(e.getMessage(), module);
		         }
			}else{
				throw new Exception("the table : "+tableName +" does not exist or exist in ad_entity!");
			}
			
			//将表的各列及列信息插入ad_field表中
			while(r2.next()){
				
				GenericValue adField = null;
				
				String columnName = r2.getString("COLUMNNAME");
				String dataType = r2.getString("DATATYPE");
				String dataLength = r2.getString("DATALENGTH");
				String dataPrecision = r2.getString("DATAPRECISION");
				String dataScale = r2.getString("DATASCALE");
				String comment = r2.getString("COMMENT");
				if(columnName.equalsIgnoreCase("LAST_UPDATED_STAMP")
						||columnName.equalsIgnoreCase("LAST_UPDATED_TX_STAMP")
						||columnName.equalsIgnoreCase("CREATED_STAMP")
						||columnName.equalsIgnoreCase("CREATED_TX_STAMP")){
					continue;
				}
		        //如果该列已经存在，则不进行任何操作
				if(delegator.findByAnd("AdField", UtilMisc.toMap("adEntityId", adEntity.get("adEntityId"),"colName", columnName)).size()==1){
	        		 continue;
	        	}
	        	
				String adFieldId = delegator.getNextSeqId("AdField");

		        try {
		        	adField = delegator.findByPrimaryKey("AdField", UtilMisc.toMap("adFieldId", adFieldId));
		        	adField = delegator.makeValue("AdField",  UtilMisc.toMap("adFieldId", adFieldId));
		        } catch (GenericEntityException e) {
		            Debug.logWarning(e.getMessage(), module);
		        }
		         
		        adField.set("adEntityId", adEntity.get("adEntityId"));
				adField.set("fieldName", UtilMisc.columnName2EntityFiled(columnName));
				adField.set("colName", columnName);
				adField.set("description", comment);
				
			 	String sqlType = "";
			 	String javaType = "";
			 	int dataLength2 = 0;
			 	int dataScale2 = 0;
			 	if(dataType.equalsIgnoreCase("BLOB")){
			 		sqlType = "BLOB";
			 		javaType = "Blob";
			 	}else if(dataType.equalsIgnoreCase("TIMESTAMP(6)")){
			 		sqlType = "date-time";
			 		javaType = "Timestamp";
			 	}else if(dataType.equalsIgnoreCase("DATETIME")){
			 		sqlType = "date-time";
			 		javaType = "Timestamp";
			 	}else if(dataType.equalsIgnoreCase("DATE")){
			 		sqlType = "date";
			 		javaType = "Date";
			 	}else if(dataType.equalsIgnoreCase("TIME")){
			 		sqlType = "time";
			 		javaType = "Time";
			 	}else if(dataType.equalsIgnoreCase("CHAR") && dataLength.equalsIgnoreCase("1")){
			 		sqlType = "indicator";
			 		javaType = "String";
			 		dataLength2 = 1;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("VARCHAR") && dataLength.equalsIgnoreCase("10")){
			 		sqlType = "very-short";
			 		javaType = "String";
			 		dataLength2 = 10;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("VARCHAR") && dataLength.equalsIgnoreCase("20")){
			 		sqlType = "id-ne";
			 		javaType = "String";
			 		dataLength2 = 20;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("VARCHAR") && dataLength.equalsIgnoreCase("60")){
			 		sqlType = "id-long";
			 		javaType = "String";
			 		dataLength2 = 60;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("VARCHAR") && dataLength.equalsIgnoreCase("100")){
			 		sqlType = "name";
			 		javaType = "String";
			 		dataLength2 = 100;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("VARCHAR") &&  dataLength.equalsIgnoreCase("250")){
			 		sqlType = "id-vlong";
			 		javaType = "String";
			 		dataLength2 = 250;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("VARCHAR") &&  dataLength.equalsIgnoreCase("255")){
			 		sqlType = "long-varchar";
			 		javaType = "String";
			 		dataLength2 = 255;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("LONGTEXT")){
			 		sqlType = "very-long";
			 		javaType = "String";
			 	}else if(dataType.equalsIgnoreCase("DECIMAL") && dataPrecision.equalsIgnoreCase("18") && dataScale.equalsIgnoreCase("2")) {
			 		sqlType = "currency-amount";
			 		javaType = "BigDecimal";
			 		dataLength2 = 18;
			 		dataScale2 = 2;
			 	}else if(dataType.equalsIgnoreCase("DECIMAL")&& dataPrecision.equalsIgnoreCase("18") && dataScale.equalsIgnoreCase("3")){
			 		sqlType = "currency-precise";
			 		javaType = "BigDecimal";
			 		dataLength2 = 18;
			 		dataScale2 = 3;
			 	}else if(dataType.equalsIgnoreCase("DECIMAL")&& dataPrecision.equalsIgnoreCase("18") && dataScale.equalsIgnoreCase("6")){
			 		sqlType = "fixed-point";
			 		javaType = "BigDecimal";
			 		dataLength2 = 18;
			 		dataScale2 = 6;
			 	}else if(dataType.equalsIgnoreCase("DOUBLE")){
			 		sqlType = "floating-point";
			 		javaType = "Double";
			 		dataLength2 = 20;
			 		dataScale2 = 0;
			 	}else if(dataType.equalsIgnoreCase("DECIMAL")&& dataPrecision.equalsIgnoreCase("20") && dataScale.equalsIgnoreCase("0")){
			 		sqlType = "numeric";
			 		javaType = "Long";
			 		dataLength2 = 20;
			 		dataScale2 = 0;
			 	}else{
			 		throw new Exception("this dataType : "+dataType +"  dataLength :"+dataLength+" is not allowed!");
			 	}
			 	adField.set("dataLength", (long)dataLength2);
			 	adField.set("dataScale", (long)dataScale2);
			 	adField.set("type", sqlType);
			 	adField.set("javaType", javaType);
			 	adField.create();
			}
			
			//找出表中的主键,将其对应的列ad_field中ispk设置为"Y";并在ad_primkey表中插入对应信息
			while(r3.next()){
				//修改主键对应的ad_field,并修改ispk
				List<GenericValue> adFieldList = delegator.findByAnd("AdField", UtilMisc.toMap("adEntityId",adEntity.get("adEntityId"),"colName", r3.getString("COLUMNNAME").toUpperCase()));
				for(GenericValue adField: adFieldList ){
					int temp = delegator.findByAnd("AdPrimkey", UtilMisc.toMap("adFieldId", adField.get("adFieldId"))).size();
					if(temp==1){
						continue;
					}else if(temp ==0){
						adField.set("ispk", "Y");
						adField.store();
						//在ad_primkey中插入数据
						GenericValue adPrimkey = null;
						String adPrimkeyId = delegator.getNextSeqId("AdPrimkey");
						try {
							adPrimkey = delegator.findByPrimaryKey("AdPrimkey", UtilMisc.toMap("adPrimkeyId", adPrimkeyId));
						} catch (GenericEntityException e) {
							Debug.logWarning(e.getMessage(), module);
						}
						
						adPrimkey = delegator.makeValue("AdPrimkey", UtilMisc.toMap("adPrimkeyId", adPrimkeyId));
						adPrimkey.set("adEntityId", adEntity.get("adEntityId"));
						adPrimkey.set("adFieldId", adFieldList.get(0).get("adFieldId"));
						try {
							delegator.create(adPrimkey);
						} catch (GenericEntityException e) {
							e.printStackTrace();
						}
					}else{
						throw new Exception("1111111");
					}
				}
			}
			while(r4.next()){
	            
				//查询外键引用的表
	            List<EntityExpr> exprs = FastList.newInstance();
	            exprs.add(EntityCondition.makeCondition("tableName", EntityOperator.EQUALS, r4.getString("RELENTITYNAME").toUpperCase()));
	            EntityFindOptions opts = new EntityFindOptions();
	            List<GenericValue> relEntityList = delegator.findList("AdEntity", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, opts, false);
	           
	            //如果 没有引用的表的信息或者引用多张表,则抛出异常
	            if(relEntityList.size()!=1){
	            	
	            	throw new Exception("the related entity "+UtilMisc.tableName2EntityName(r4.getString("RELENTITYNAME")).toUpperCase()+"dose not exist");
	            }
	            GenericValue relEntity = relEntityList.get(0);
	            
	            
	            if(delegator.findByAnd("AdRelation", UtilMisc.toMap("adEntityId",adEntity.get("adEntityId"),"relEntityId", relEntity.get("adEntityId"))).size()==1){
	            	continue;
	            }	            
	            
	            //在ad_relation表中插入对应的外键信息
				GenericValue adRelation = null;
				String adRelationId = delegator.getNextSeqId("AdRelation");
		         try {
		        	 adRelation = delegator.findByPrimaryKey("AdRelation", UtilMisc.toMap("adRelationId", adRelationId));
		         } catch (GenericEntityException e) {
		             Debug.logWarning(e.getMessage(), module);
		         }
		         adRelation = delegator.makeValue("AdRelation",UtilMisc.toMap("adRelationId", adRelationId));
		         adRelation.set("adEntityId", adEntity.get("adEntityId"));
		         adRelation.set("relEntityId",  relEntity.get("adEntityId"));
		         adRelation.set("fkName", r4.getString("FKNAME").toUpperCase());
		         adRelation.set("type", "one");
		         
		         
		         try {
					delegator.create(adRelation);
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}
		         
		       //建立外键的映射
				GenericValue adKeymap = null;
				
				String adKeymapId = delegator.getNextSeqId("AdKeymap");

		         try {
		        	 adKeymap = delegator.findByPrimaryKey("AdKeymap", UtilMisc.toMap("adKeymapId", adKeymapId));
		         } catch (GenericEntityException e) {
		             Debug.logWarning(e.getMessage(), module);
		         }

		         adKeymap = delegator.makeValue("AdKeymap",UtilMisc.toMap("adKeymapId", adKeymapId));
		         adKeymap.set("adRelationId", adRelationId);
	         
		         //查询 field
				exprs = FastList.newInstance();
				exprs.add(EntityCondition.makeCondition("adEntityId", EntityOperator.EQUALS,adEntity.get("adEntityId")));
				exprs.add(EntityCondition.makeCondition("colName", EntityOperator.EQUALS, r4.getString("FIELDNAME")));
				
				List<GenericValue> adFieldList = delegator.findList("AdField", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, opts, false);
				 
				    if(adFieldList.size()!=1){
				    	throw new Exception("11111");
				}
		        adKeymap.set("adFieldId", adFieldList.get(0).getString("adFieldId"));
		         
		        exprs = FastList.newInstance();
				exprs.add(EntityCondition.makeCondition("tableName", EntityOperator.EQUALS, r4.getString("RELENTITYNAME")));
				
				List<GenericValue> relEntityList2 = delegator.findList("AdEntity", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, opts, false);
				if(relEntityList2.size()!=1){
					throw new Exception("11111");
				}
			     
				//查询rel_field
				exprs = FastList.newInstance();
				exprs.add(EntityCondition.makeCondition("adEntityId", EntityOperator.EQUALS,relEntityList2.get(0).getString("adEntityId")));
				exprs.add(EntityCondition.makeCondition("colName", EntityOperator.EQUALS, r4.getString("RELFIELDNAME")));
				
				List<GenericValue> relFieldList = delegator.findList("AdField", EntityCondition.makeCondition(exprs, EntityOperator.AND), null, null, opts, false);
				 
				if(relFieldList.size()!=1){
					throw new Exception("11111");	
				}  
		         
		         adKeymap.set("relFieldId", relFieldList.get(0).get("adFieldId"));
		         
		         
		         try {
					delegator.create(adKeymap); //存储外键映射
				} catch (GenericEntityException e) {
					e.printStackTrace();
				}				
			}
		} catch (GenericEntityException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			result.put(ModelService.RESPONSE_MESSAGE, ModelService.ERROR_MESSAGE);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			Debug.logWarning(e.getMessage(), module);
			result.put(ModelService.RESPONSE_MESSAGE, ModelService.ERROR_MESSAGE);
			return result;
		}
    	
    	result.put("adEntityId", adEntityId);
    	result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
    	return result;
    }
	
}
