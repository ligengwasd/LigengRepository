package org.ofbiz.webtools.securitymanage;

import java.util.Locale;
import java.util.Map;

import javolution.util.FastMap;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceUtil;

public class SecurityGroupPermissionServices {
	public static final String module = SecurityGroupPermissionServices.class.getName();
	public static final String resource = "SecurityGroupPermissionServices";
	public static final String resourceError = "SecurityGroupPermissionServices";
	
	public static Map<String, Object> createSecurityGroupPermission(DispatchContext ctx,Map<String, Object> context){
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();
		Delegator delegator = ctx.getDelegator();
		GenericValue gv = null;
		try {
			gv = delegator.makeValue("SecurityGroupPermission", UtilMisc.toMap("groupId", context.get("groupId"),"permissionId",context.get("permissionId")));
			gv.create();
		} catch (GenericEntityException e) {
			Debug.logWarning(e.getMessage(), module);
			e.printStackTrace();
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError, e.getMessage(), locale));
		}
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}
	public static Map<String, Object> deleteSecurityGroupPermission(DispatchContext ctx,Map<String, Object> context){
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();
		Delegator delegator = ctx.getDelegator();
		GenericValue gv = null;
		try {
			gv = delegator.findByAnd("SecurityGroupPermission", UtilMisc.toMap("groupId", context.get("groupId"),"permissionId",context.get("permissionId")), null, false).get(0);
			gv.remove();
		} catch (GenericEntityException e) {
			Debug.logWarning(e.getMessage(), module);
			e.printStackTrace();
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError, e.getMessage(), locale));
		}
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}
}
