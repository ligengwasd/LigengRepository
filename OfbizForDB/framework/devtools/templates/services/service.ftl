	<service name="${adService.serviceName}" engine="${adService.engine}" auth="<#if adService.auth=="Y">true<#else>false</#if>" <#if adService.defaultEntityName??>default-entity-name="${adService.defaultEntityName}"</#if>	<#if adService.location??>location="${adService.location}"</#if> invoke="${adService.invoke}">
${attributeSb}
	</service>
