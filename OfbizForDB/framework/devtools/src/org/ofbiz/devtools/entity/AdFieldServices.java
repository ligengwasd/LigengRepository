package org.ofbiz.devtools.entity;

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

public class AdFieldServices {

	public static final String module = AdFieldServices.class.getName();
	public static final String resource = "EntityUiLabels";
	public static final String resourceError = "EntityErrorUiLabels";

	public static Map<String, Object> createAdField(DispatchContext ctx,Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();

		Delegator delegator = ctx.getDelegator();

		String adFieldId = delegator.getNextSeqId("AdField");

		GenericValue adField = null;

		try {
			adField = delegator.findOne("AdField",UtilMisc.toMap("adFieldId", adFieldId),false);
		} catch (GenericEntityException e) {
			Debug.logWarning(e.getMessage(), module);
		}

		adField = delegator.makeValue("AdField",UtilMisc.toMap("adFieldId", adFieldId));
		adField.setNonPKFields(context);
		try {
			delegator.create(adField);
		} catch (GenericEntityException e) {
			e.printStackTrace();
			return ServiceUtil.returnError(UtilProperties.getMessage(
					resourceError, "field.create_error", locale));
		}

		result.put("adFieldId", adFieldId);
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}

	public static Map<String, Object> updateAdField(DispatchContext ctx,
			Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();

		Delegator delegator = ctx.getDelegator();

		String adFieldId = (String) context.get("adFieldId");
	
		GenericValue adField = null;
		System.out.println("field update=======================1");
		System.out.println("field.lineNo:="+context.get("lineNo"));

		try {
			adField = delegator.findOne("AdField", UtilMisc.toMap("adFieldId", adFieldId), false);
		} catch (GenericEntityException e) {
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(
					resourceError, "field.already_exists", locale));
		}

		try {
			adField.setNonPKFields(context);
			adField.store();
			System.out.println("field update=======================2");
		}catch (GenericEntityException e) {
			System.out.println("field update=======================3");
			e.printStackTrace();
			return ServiceUtil.returnError(UtilProperties.getMessage(
					resourceError, "field.update_error", locale));
		}

		
		
		
		System.out.println("field update=======================4");
		result.put("adFieldId", adFieldId);
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}

	public static Map<String, Object> deleteAdField(DispatchContext ctx,
			Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		Delegator delegator = ctx.getDelegator();
		return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,
				"partyservices.cannot_delete_party_not_implemented", locale));

	}
}
