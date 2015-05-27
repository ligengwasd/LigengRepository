package ${adEntity.packageName};
	
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
	
public class ${adEntity.entityName}Services {
	public static final String module = ${adEntity.entityName}Services.class.getName();
	public static final String resource = "${adModule.title}UiLabels";
	public static final String resourceError = "${adModule.title}UiLabels";
	
	public static Map<String, Object> create${adEntity.entityName}(DispatchContext ctx,Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();
		Delegator delegator = ctx.getDelegator();
		GenericValue gv = null;
		try {
			String ${pkField.fieldName} = delegator.getNextSeqId("${adEntity.entityName}");
			gv = delegator.findOne("${adEntity.entityName}",UtilMisc.toMap("${pkField.fieldName}", ${pkField.fieldName}),false);
			gv = delegator.makeValue("${adEntity.entityName}",UtilMisc.toMap("${pkField.fieldName}", ${pkField.fieldName}));
			gv.setNonPKFields(context);
			delegator.create(gv);
		} catch (GenericEntityException e) {
			Debug.logWarning(e.getMessage(), module);
			e.printStackTrace();
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError, e.getMessage(), locale));
		}
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}
	
	public static Map<String, Object> update${adEntity.entityName}(DispatchContext ctx,Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		Map<String, Object> result = FastMap.newInstance();
		Delegator delegator = ctx.getDelegator();
		String ${pkField.fieldName} = (String) context.get("${pkField.fieldName}");
		GenericValue gv = null;
		try {
			gv = delegator.findOne("${adEntity.entityName}", UtilMisc.toMap("${pkField.fieldName}", ${pkField.fieldName}), false);
			gv.setNonPKFields(context);
			gv.store();
		} catch (GenericEntityException e) {
			Debug.logWarning(e.getMessage(), module);
			e.printStackTrace();
			return ServiceUtil.returnError(UtilProperties.getMessage(resourceError, e.getMessage(), locale));
		}
		result.put(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);
		return result;
	}
	
	public static Map<String, Object> delete${adEntity.entityName}(DispatchContext ctx,Map<String, Object> context) {
		Locale locale = (Locale) context.get("locale");
		Delegator delegator = ctx.getDelegator();
		GenericValue gv = null;
		String ${pkField.fieldName} = (String) context.get("${pkField.fieldName}");
		Map<String, Object> result = FastMap.newInstance();
		try {
			gv = delegator.findOne("${adEntity.entityName}", UtilMisc.toMap("${pkField.fieldName}", ${pkField.fieldName}), false);
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