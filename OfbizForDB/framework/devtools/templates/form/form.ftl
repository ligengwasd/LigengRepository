	<form name="${adForm.formName}" type="${adForm.type}" <#if adForm.defaultMapName??> default-map-name="${adForm.defaultMapName}"</#if> header-row-style="header-row" default-table-style="basic-table" >
${formfieldSb}
		<button-list>
${formbuttonSb}
		</button-list>
	</form>
