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

public class AdGridcommandServices {

	public static final String module = AdGridcommandServices.class.getName();
	public static final String resource = "EntityUiLabels";
	public static final String resourceError = "EntityErrorUiLabels";


	public static Map<String, Object> hideGridcommand(DispatchContext ctx,Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();
		Delegator delegator = ctx.getDelegator();
		String adGridcommandId = (String) context.get("adGridcommandId");
		GenericValue adGridcommand = null;
		try {
			adGridcommand = delegator.findOne("AdGridcommand", UtilMisc.toMap("adGridcommandId", adGridcommandId), false);
		} catch (GenericEntityException e) {
			Debug.logWarning(e.getMessage(), module);
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError, "field.already_exists", locale));
		}

		try {
			adGridcommand.set("width", "0");
			adGridcommand.store();
		}catch (GenericEntityException e) {
			e.printStackTrace();
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError, "field.update_error", locale));
		}
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}
}
