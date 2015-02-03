		<field name="${adFormfield.formfieldName}" title="${adFormfield.title}" <#if adFormfield.position??>position="${adFormfield.position}"</#if>>
			<#if adFormfield.ddltype?? && adFormfield.ddltype=="tableDir"><kd-drop-down-list type="tableDir" entity="${adFormfield.entity}" valueField="${adFormfield.valuefield}" nameField="${adFormfield.namefield}" />
			<#elseif adFormfield.ddltype?? && adFormfield.ddltype=="refList"><kd-drop-down-list type="refList" refenceValue="${adFormfield.referencevalue}" /> 
			<#elseif adFormfield.inputtype=="datePicker"><kd-date-picker/>
			<#elseif adFormfield.inputtype=="dateTimePicker"><kd-datetime-picker/> 
			<#elseif adFormfield.inputtype=="checkbox"><check></check>
			<#elseif adFormfield.inputtype=="numericTextBox"><kd-numeric-text-box  <#if adFormfield.format??>format="${adFormfield.format}"</#if> /> 
			<#elseif adFormfield.inputtype=="hidden"><hidden/> 
			<#else><text size="${adFormfield.size}" maxlength="${adFormfield.maxlength}"/></#if>
		</field>
