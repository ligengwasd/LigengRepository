	<kd-grid name="${adGrid.gridName}" resizable="true"   <#if adGrid.editableIn??>  editable="${adGrid.editableIn}"</#if>  selectable="multiple, row"  <#if adGrid.height??> height="${adGrid.height}"  </#if> >
		<kd-grid-columns>
${gridcolumeSb}
${gridcommandSb}
		</kd-grid-columns>
		<kd-grid-toolbar>
			<kd-grid-toolbar-sub name="create"/>
			<kd-grid-toolbar-sub name="save"/>
			<kd-grid-toolbar-sub name="cancel"/>
		</kd-grid-toolbar>
		<kd-grid-datasource gridName="${adGrid.gridName}" entityName="${adEntity.entityName}" <#if adGrid.modelId??>modelId="${adGrid.modelId}"</#if> <#if adGrid.pagesize??> pageSize="${adGrid.pagesize}"</#if> <#if adGrid.createCommand??>create="${adGrid.createCommand}"</#if> <#if adGrid.deleteCommand??>destroy="${adGrid.deleteCommand}"</#if>  <#if adGrid.updateCommand??>update="${adGrid.updateCommand}"</#if> >
${gridfieldSb}
		</kd-grid-datasource>
	</kd-grid>
