			<kd-grid-command type="${adGridcommand.commandType}" name="${adGridcommand.commandName}" text="${adGridcommand.commandText}"  title="${adGridcommand.commandTitle}" width="${adGridcommand.width}" <#if adGridcommand.url??> url="${adGridcommand.url}" </#if> <#if adGridcommand.modelId??>modelId ="${adGridcommand.modelId}"</#if> <#if adGridcommand.click??>click ="${adGridcommand.click}"</#if>  />