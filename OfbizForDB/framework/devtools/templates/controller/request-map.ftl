	<request-map uri="${adRequestmap.uri}">
		<security https="<#if adRequestmap.https=="Y">true<#else>false</#if>" auth="<#if adRequestmap.auth=="Y">true<#else>false</#if>"/><#if adRequestmap.eventInvoke??>
		<event type="${adRequestmap.eventType}" <#if adRequestmap.eventPath??> path="${adRequestmap.eventPath}"</#if>  invoke="${adRequestmap.eventInvoke}"/></#if>
${responseSb}
	</request-map>
