		<attribute name="${adAttribute.attributeName}" type="${adAttribute.type}" mode="${adAttribute.modeIn}" optional="<#if adAttribute.optional=="Y">true<#else>false</#if>" <#if adEntity??> entity-name="${adEntity.entityName}"  field-name="${adField.fieldName}"</#if>/>
