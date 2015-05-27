<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<#macro renderScreenBegin>
<!DOCTYPE html>
</#macro>

<#macro renderScreenEnd>
</#macro>

<#macro renderSectionBegin boundaryComment>
<#if boundaryComment?has_content>
<!-- ${boundaryComment} -->
</#if>
</#macro>

<#macro renderSectionEnd boundaryComment>
<#if boundaryComment?has_content>
<!-- ${boundaryComment} -->
</#if>
</#macro>

<#macro renderContainerBegin id style inlineStyle autoUpdateLink autoUpdateInterval inlineStyle>
<#if autoUpdateLink?has_content>
<script type="text/javascript">ajaxUpdateAreaPeriodic('${id}', '${autoUpdateLink}', '', '${autoUpdateInterval}');</script>
</#if>
<div<#if id?has_content> id="${id}"</#if><#if style?has_content> class="${style}"</#if> style="${inlineStyle}">
</#macro>
<#macro renderContainerEnd></div></#macro>

<!--Kd-Tab-Strip add by ligeng -->
<#macro renderKdTabStripBegin id>
	<div id = "${id}">
	
</#macro>
<#macro renderKdTabStripEnd id>
	<script>
   	$("#${id}").kendoTabStrip({
    	animation:false
    });
   	
	</script>
	</div>
</#macro>

<!--Kd-Ul add by ligeng -->
<#macro renderKdUlBegin id>
	<ul id="${id}">
</#macro>
<#macro renderKdUlEnd>
	</ul>
</#macro>

<!--Kd-Li add by ligeng -->
<#macro renderKdLiBegin title url type>
	<li class="${type}">
		<a href = "${url}">${title}</a>
</#macro>
<#macro renderKdLiEnd>
	</li>
</#macro>

<!--Kd-Grid start add by ligeng -->
<#macro renderKdGridBegin name height selectable editable sortableMode filterable groupable resizable pageSizes pageSizesInput refresh>
	<div id="${name}"></div>
	
	<script type="text/javascript">
		
		$(document).ready(function() {
			
			$("#${name}").kendoGrid({
				
				pageable:{
            		<#if pageSizesInput?has_content>input: ${pageSizesInput},</#if>
            		<#if refresh?has_content>refresh: ${refresh},</#if>
            		<#if pageSizes=="true">pageSizes: [5,10,20,30,50,100],</#if>
            	},
            	<#if height?has_content>height: ${height},</#if>
            	<#if resizable?has_content>resizable: ${resizable},</#if>
            	
            	<#if selectable?has_content>
            		<#if "${selectable}"=="true" || "${selectable}"=="false">
            			selectable: ${selectable},
            		<#else>
            			selectable: "${selectable}",
            		</#if>
            	</#if>
            	<#if sortableMode?has_content>
            		sortable: {
				    	mode: "${sortableMode}"
			  		},
            	</#if>
            	<#if filterable?has_content>filterable:${filterable},</#if>
            	<#if groupable?has_content>groupable:${groupable},</#if>
            	<#if editable?has_content>editable:"${editable}",</#if>
</#macro>
<#macro renderKdGridColumnsBegin>
				columns: [
</#macro>
<#macro renderKdGridColumn name filterable width format dropDownType refenceValue entity nameField valueField title  conditionJson values>
					{field:"${name}",
					 <#if dropDownType=="refList">values:findRefListEvent('${refenceValue}'),</#if>
					 <#if dropDownType=="tableDir">values:findTableDirOptions('${entity}','${nameField}','${valueField}',"${conditionJson}"),</#if>
					 <#if dropDownType=="json">values:${values} ,</#if>
					 <#if format?has_content>format:"${format}",</#if>
					 <#if filterable?has_content>filterable:${filterable},</#if>
					 <#if width?has_content> <#if (width?number)==0> hidden: true, <#else> width: "${width}px",</#if> <#else>width: "100px", </#if>
					 <#if title?has_content>title: "${title}"</#if>
					},
</#macro> 
<#macro renderKdGridCommand type name text title width  url modelId click>
					<#if width?? && (width?number)!=0 >
					{ command: [{
								name:"${name}",
								text:"${text}",							
							   	<#if type=="server">
								 click:function(e){
								 	
								 	var tr = $(e.target).closest("tr");
          							var data2 = this.dataItem(tr);
								 	$.ajax({
								 		type:"post",
								 		data:JSON.parse(JSON.stringify(data2)),
								 		url:"${url}",
								 		success: function(result) {
								          var exp = result._ERROR_MESSAGE_LIST_;
								          var exp2 =result._ERROR_MESSAGE_;
										  if ( typeof(exp)=="undefined" && typeof(exp2)=="undefined" ){
										  	successMessages("修改成功");
										  }else{
										    if(typeof(exp)=="undefined"){
								          		errorMessages(exp2);
										    }else{
								          		errorMessages(exp);
										    }
										    
										  }
								        }
								 	})
								 }
								 <#elseif type=="client">
								 click:function(e){
								 	var tr = $(e.target).closest("tr");
          							var data2 = this.dataItem(tr);
								 	window.location.href="${url}?"+"${modelId}="+data2.${modelId};
								 	//encode(JSON.parse(JSON.stringify(data2)));
								 }
								 <#elseif type=="click">
								 click:${click}
								 </#if>
							   }],width:"${width}px",title:"${title}"},
					</#if>
</#macro>
<#macro renderKdGridColumnsEnd>
                ],
</#macro>
<#macro renderKdGridToolbarBegin>
				toolbar: [
</#macro>
<#macro renderKdGridToolbarSub name text>
					{name:"${name}",<#if text?has_content> text: "${text}"</#if>},
</#macro>
<#macro renderKdGridToolbarEnd>
				],
</#macro>
<#macro renderKdGridDataSourceBegin gridName entityName pageSize modelId read update destroy create>
			});
		});
		
		function ${gridName}_Refresh(obj){
			var form = $(obj).closest("form");
        	var jsonData = JSON.stringify(form.serializeObjectToJson());
        	${gridName}_RefreshByJson(jsonData);
		}
		
		function ${gridName}_RefreshByJson(jsonData){
        	
        	var dataSource = new kendo.data.DataSource({
                    type: "json",
                    <#if pageSize?has_content>pageSize: ${pageSize}, <#else>pageSize: 10,</#if>
                   
                    serverPaging: true,
                    serverFiltering: true,
                    serverSorting: true,
                    transport: {
					    read: function(options) {
					      $.ajax({
					      	type:"post",
					        url: "performFindKendoGrid?entityName=${entityName}&inputFields="+jsonData+"&"+encode(options.data),
					        dataType: "json",
					        success: function(result) {
					          options.success(result);
					        },
					        
					      });
					    },
					    
					    <#if destroy?has_content>
		            	destroy: function (options) {
					      $.ajax({
					      	type:"post",
					        url: "${destroy}",
					        data: {
					          ${modelId}: options.data.${modelId},
					        },
					        success: function(result) {
					          var exp = result._ERROR_MESSAGE_LIST_;
					          var exp2 =result._ERROR_MESSAGE_;
					          
							  if ( typeof(exp)=="undefined" && typeof(exp2)=="undefined" ){
							    options.success(result);
							  	successMessages("删除成功");
							  }else{
							    options.error(result);
							    if(typeof(exp)=="undefined"){
					          		errorMessages(exp2);
							    }else{
					          		errorMessages(exp);
							    }
							    
							  }
					        }
					      });
					    },
					    </#if>
					    <#if update?has_content>
					    update: function(options) {
					 
					      $.ajax({
					      	type:"post",
					        url: "${update}",
					        data:JSON.parse(JSON.stringify(options.data)),
					        success: function(result) {
					          var exp = result._ERROR_MESSAGE_LIST_;
					          var exp2 =result._ERROR_MESSAGE_;
							  if ( typeof(exp)=="undefined" && typeof(exp2)=="undefined" ){
							    options.success(result);
							  	successMessages("修改成功");
							  }else{
							    options.error(result);
							    if(typeof(exp)=="undefined"){
					          		errorMessages(exp2);
							    }else{
					          		errorMessages(exp);
							    }
							    
							  }
					        }
					      });
					    },
					    </#if>
					    <#if create?has_content>
					    create:function(options) {
					      $.ajax({
					      	type:"post",
					        url: "${create}",
					        data:JSON.parse(JSON.stringify(options.data)),
					        success: function(result) {
					          var exp = result._ERROR_MESSAGE_LIST_;
					          var exp2 =result._ERROR_MESSAGE_;
					          
							  if ( typeof(exp)=="undefined" && typeof(exp2)=="undefined" ){
							    options.success(result);
							    $("input[value='查找追溯信息']").trigger("onclick");
							  	successMessages("修改成功");
							  }else{
							    options.error(result);
							    if(typeof(exp)=="undefined"){
					          		errorMessages(exp2);
							    }else{
					          		errorMessages(exp);
							    }
							    
							  }
					        }
					      });
					    }
					    </#if>
					    
                    },
                    schema: {
                    	data : function(d) {
									return d.rows;
								},
						total : function(d) {
									return d.total;
								},
                        model: {
                        	id:"${modelId}",
                            fields: {
</#macro>
<#macro renderKdGridField name type editable>
                                	${name}:{type:"${type}",<#if editable?has_content>editable:${editable},</#if>} ,   
</#macro>
<#macro renderKdGridDataSourceEnd gridName>
							}
                        }
                    },
            });
            var grid = $("#${gridName}").data("kendoGrid");
            grid.setDataSource(dataSource);
         }
</#macro>
<#macro renderKdGridEnd>
	</script>
	
</#macro>

<!--Kd-Grid start add by ligeng -->
<#macro renderKdMenuBegin target direction orientation>
	<script>
	    $(document).ready(function() {
	        $("#${target}").kendoMenu({
				direction: "${direction}",
				orientation: "${orientation}"
	        
	        });
	    });
    </script>

</#macro>
<#macro renderKdMenuEnd>
</#macro>

<!--Kd-Window  add by ligeng -->
<#macro renderKdWindowBegin id title modal visible resizable width height>
	<div id="${id}">
	<script>
	var ${id};
	$(document).ready(function () {
		${id} = $("#${id}").kendoWindow({
	            title: "${title}",
	            modal: ${modal},
	            visible: ${visible},
	            resizable: ${resizable},
	            width: ${width},
	            height: ${height}
	        }).data("kendoWindow");
	  }
	)
	function ${id}_open(){
		${id}.center().open();
	}
	</script>
	
</#macro>
<#macro renderKdWindowEnd>
	</div>
</#macro>

<#macro renderContentBegin editRequest enableEditValue editContainerStyle><#if editRequest?has_content && enableEditValue == "true"><div class=${editContainerStyle}></#if></#macro>
<#macro renderContentBody></#macro>
<#macro renderContentEnd urlString editMode editContainerStyle editRequest enableEditValue>
<#if editRequest?exists && enableEditValue == "true">
<#if urlString?exists><a href="${urlString}">${editMode}</a><#rt/></#if>
<#if editContainerStyle?exists></div><#rt/></#if>
</#if>
</#macro>
<#macro renderSubContentBegin editContainerStyle editRequest enableEditValue><#if editRequest?exists && enableEditValue == "true"><div class="${editContainerStyle}"></#if></#macro>
<#macro renderSubContentBody></#macro>
<#macro renderSubContentEnd urlString editMode editContainerStyle editRequest enableEditValue>
<#if editRequest?exists && enableEditValue == "true">
<#if urlString?exists><a href="${urlString}">${editMode}</a><#rt/></#if>
<#if editContainerStyle?exists></div><#rt/></#if>
</#if>
</#macro>

<#macro renderHorizontalSeparator id style><hr<#if id?has_content> id="${id}"</#if><#if style?has_content> class="${style}"</#if>/></#macro>

<#macro renderLabel text id style>
  <#if text?has_content>
    <#-- If a label widget has one of the h1-h6 styles, then it is considered block level element.
         Otherwise it is considered an inline element. -->
    <#assign idText = ""/>
    <#if id?has_content><#assign idText = " id=\"${id}\""/></#if>
    <#if style?has_content>
      <#if style=="h1">
        <h1${idText}>${text}</h1>
      <#elseif style=="h2">
        <h2${idText}>${text}</h2>
      <#elseif style=="h3">
        <h3${idText}>${text}</h3>
      <#elseif style=="h4">
        <h4${idText}>${text}</h4>
      <#elseif style=="h5">
        <h5${idText}>${text}</h5>
      <#elseif style=="h6">
        <h6${idText}>${text}</h6>
      <#else>
        <span${idText} class="${style}">${text}</span>
      </#if>
    <#else>
      <span${idText}>${text}</span>
    </#if>
  </#if>
</#macro>

<#macro renderLink parameterList targetWindow target uniqueItemName linkType actionUrl id style name height width linkUrl text imgStr>
<#if "ajax-window" != linkType>
<#if "hidden-form" == linkType>
<form method="post" action="${actionUrl}" <#if targetWindow?has_content>target="${targetWindow}"</#if> onsubmit="javascript:submitFormDisableSubmits(this)" name="${uniqueItemName}"><#rt/>
<#list parameterList as parameter>
<input name="${parameter.name}" value="${parameter.value}" type="hidden"/><#rt/>
</#list>
</form><#rt/>
</#if>
<a <#if id?has_content>id="${id}"</#if> <#if style?has_content>class="${style}"</#if> <#if name?has_content>name="${name}"</#if> <#if targetWindow?has_content>target="${targetWindow}"</#if> href="<#if "hidden-form"==linkType>javascript:document.${uniqueItemName}.submit()<#else>${linkUrl}</#if>"><#rt/>
<#if imgStr?has_content>${imgStr}</#if><#if text?has_content>${text}</#if></a>
<#else>
<div id="${uniqueItemName}"></div>

<a href="javascript:void(0);" id="${uniqueItemName}_link" <#if style?has_content>class="${style}"</#if>><#if text?has_content>${text}</#if></a>
<script type="text/javascript">
    function getRequestData () {
        var data =  {
            <#list parameterList as parameter>
                "${parameter.name}": "${parameter.value}",
            </#list>
            "presentation": "layer"
        };

        return data;
    }
    jQuery("#${uniqueItemName}_link").click( function () {
        jQuery("#${uniqueItemName}").dialog("open");
    });
    jQuery("#${uniqueItemName}").dialog({
         autoOpen: false,
         <#if text?has_content>title: "${text}",</#if>
         height: ${height},
         width: ${width},
         modal: true,
         open: function() {
                 jQuery.ajax({
                     url: "${target}",
                     type: "POST",
                     data: getRequestData(),
                     success: function(data) {jQuery("#${uniqueItemName}").html(data);}
                 });
         }
    });
</script>
</#if>
</#macro>
<#macro renderImage src id style wid hgt border alt urlString>
<#if src?has_content>
<img <#if id?has_content>id="${id}"</#if><#if style?has_content> class="${style}"</#if><#if wid?has_content> width="${wid}"</#if><#if hgt?has_content> height="${hgt}"</#if><#if border?has_content> border="${border}"</#if> alt="<#if alt?has_content>${alt}</#if>" src="${urlString}" />
</#if>
</#macro>

<#macro renderContentFrame fullUrl width height border><iframe src="${fullUrl}" width="${width}" height="${height}" <#if border?has_content>border="${border}"</#if> /></#macro>
<#macro renderScreenletBegin id title collapsible saveCollapsed collapsibleAreaId expandToolTip collapseToolTip fullUrlString padded menuString showMore collapsed javaScriptEnabled>
<div class="screenlet"<#if id?has_content> id="${id}"</#if>><#rt/>
<#if showMore>
<div class="screenlet-title-bar"><ul><#if title?has_content><li class="h3">${title}</li></#if>
<#if collapsible>
<li class="<#rt/>
<#if collapsed>
collapsed"><a <#if javaScriptEnabled>onclick="javascript:toggleScreenlet(this, '${collapsibleAreaId}', '${saveCollapsed?string}', '${expandToolTip}', '${collapseToolTip}');"<#else>href="${fullUrlString}"</#if><#if expandToolTip?has_content> title="${expandToolTip}"</#if>
<#else>
expanded"><a <#if javaScriptEnabled>onclick="javascript:toggleScreenlet(this, '${collapsibleAreaId}', '${saveCollapsed?string}', '${expandToolTip}', '${collapseToolTip}');"<#else>href="${fullUrlString}"</#if><#if collapseToolTip?has_content> title="${collapseToolTip}"</#if>
</#if>
>&nbsp;</a></li>
</#if>
<#--
<#if !collapsed>
${menuString}
</#if>
 -->
${menuString}
</ul><br class="clear" /></div>
</#if>
<div <#if collapsibleAreaId?has_content> id="${collapsibleAreaId}" <#if collapsed> style="display: none;"</#if></#if><#if padded> class="screenlet-body"<#else> class="screenlet-body no-padding"</#if>>
</#macro>
<#macro renderScreenletSubWidget></#macro>
<#macro renderScreenletEnd></div></div></#macro>
<#macro renderScreenletPaginateMenu lowIndex actualPageSize ofLabel listSize paginateLastStyle lastLinkUrl paginateLastLabel paginateNextStyle nextLinkUrl paginateNextLabel paginatePreviousStyle paginatePreviousLabel previousLinkUrl paginateFirstStyle paginateFirstLabel firstLinkUrl>
    <li class="${paginateLastStyle}<#if !lastLinkUrl?has_content> disabled</#if>"><#if lastLinkUrl?has_content><a href="${lastLinkUrl}">${paginateLastLabel}</a><#else>${paginateLastLabel}</#if></li>
    <li class="${paginateNextStyle}<#if !nextLinkUrl?has_content> disabled</#if>"><#if nextLinkUrl?has_content><a href="${nextLinkUrl}">${paginateNextLabel}</a><#else>${paginateNextLabel}</#if></li>
    <#if (listSize?number > 0) ><li>${lowIndex?number + 1} - ${lowIndex?number + actualPageSize?number} ${ofLabel} ${listSize}</li><#rt/></#if>
    <li class="${paginatePreviousStyle?default("nav-previous")}<#if !previousLinkUrl?has_content> disabled</#if>"><#if previousLinkUrl?has_content><a href="${previousLinkUrl}">${paginatePreviousLabel}</a><#else>${paginatePreviousLabel}</#if></li>
    <li class="${paginateFirstStyle?default("nav-first")}<#if !firstLinkUrl?has_content> disabled</#if>"><#if firstLinkUrl?has_content><a href="${firstLinkUrl}">${paginateFirstLabel}</a><#else>${paginateFirstLabel}</#if></li>
</#macro>

<#macro renderPortalPageBegin originalPortalPageId portalPageId confMode="false" addColumnLabel="Add column" addColumnHint="Add a new column to this portal">
  <#if confMode == "true">
    <a class="buttontext" href="javascript:document.addColumn_${portalPageId}.submit()" title="${addColumnHint}">${addColumnLabel}</a> <b>PortalPageId: ${portalPageId}</b>
    <form method="post" action="addPortalPageColumn" name="addColumn_${portalPageId}">
      <input name="portalPageId" value="${portalPageId}" type="hidden"/>
    </form>
  </#if>
  <table width="100%">
    <tr>
</#macro>

<#macro renderPortalPageEnd>
    </tr>
  </table>
</#macro>

<#macro renderPortalPageColumnBegin originalPortalPageId portalPageId columnSeqId confMode="false" width="auto" delColumnLabel="Delete column" delColumnHint="Delete this column" addPortletLabel="Add portlet" addPortletHint="Add a new portlet to this column" colWidthLabel="Col. width:" setColumnSizeHint="Set column size">
  <#assign columnKey = portalPageId+columnSeqId>
  <#assign columnKeyFields = '<input name="portalPageId" value="' + portalPageId + '" type="hidden"/><input name="columnSeqId" value="' + columnSeqId + '" type="hidden"/>'>
  <script type="text/javascript">
    if (typeof SORTABLE_COLUMN_LIST != "undefined") {
      if (SORTABLE_COLUMN_LIST == null) {
        SORTABLE_COLUMN_LIST = "#portalColumn_${columnSeqId}";
      } else {
        SORTABLE_COLUMN_LIST += ", #portalColumn_${columnSeqId}";
      }
    }
  </script>
  <td class="portal-column<#if confMode == "true">-config</#if> connectedSortable" style="vertical-align: top; <#if width?has_content> width:${width};</#if>" id="portalColumn_${columnSeqId}">
    <#if confMode == "true">
      <div class="portal-column-config-title-bar">
        <ul>
          <li>
            <form method="post" action="deletePortalPageColumn" name="delColumn_${columnKey}">
              ${columnKeyFields}
            </form>
            <a class="buttontext" href="javascript:document.delColumn_${columnKey}.submit()" title="${delColumnHint}">${delColumnLabel}</a>
          </li>
          <li>
            <form method="post" action="addPortlet" name="addPortlet_${columnKey}">
              ${columnKeyFields}
            </form>
            <a class="buttontext" href="javascript:document.addPortlet_${columnKey}.submit()" title="${addPortletHint}">${addPortletLabel}</a>
          </li>
          <li>
            <form method="post" action="editPortalPageColumnWidth" name="setColumnSize_${columnKey}">
              ${columnKeyFields}
            </form>
            <a class="buttontext" href="javascript:document.setColumnSize_${columnKey}.submit()" title="${setColumnSizeHint}">${colWidthLabel}: ${width}</a>
          </li>
        </ul>
      </div>
      <br class="clear"/>
    </#if>
</#macro>

<#macro renderPortalPageColumnEnd>
  </td>
</#macro>

<#macro renderPortalPagePortletBegin originalPortalPageId portalPageId portalPortletId portletSeqId prevPortletId="" prevPortletSeqId="" nextPortletId="" nextPortletSeqId="" columnSeqId="" prevColumnSeqId="" nextColumnSeqId="" confMode="false" delPortletHint="Remove this portlet" editAttribute="false" editAttributeHint="Edit portlet parameters">
  <#assign portletKey = portalPageId+portalPortletId+portletSeqId>
  <#assign portletKeyFields = '<input name="portalPageId" value="' + portalPageId + '" type="hidden"/><input name="portalPortletId" value="' + portalPortletId + '" type="hidden"/><input name="portletSeqId" value="' + portletSeqId  + '" type="hidden"/>'>
  <div id="PP_${portletKey}" name="portalPortlet" class="noClass" portalPageId="${portalPageId}" portalPortletId="${portalPortletId}" columnSeqId="${columnSeqId}" portletSeqId="${portletSeqId}">
    <#if confMode == "true">
      <div class="portlet-config" id="PPCFG_${portletKey}">
        <div class="portlet-config-title-bar">
          <ul>
            <li class="title">Portlet : [${portalPortletId}]</li>
            <li class="remove">
              <form method="post" action="deletePortalPagePortlet" name="delPortlet_${portletKey}">
                ${portletKeyFields}
              </form>
              <a href="javascript:document.delPortlet_${portletKey}.submit()" title="${delPortletHint}">&nbsp;&nbsp;&nbsp;</a>
            </li>
            <#if editAttribute == "true">
              <li class="edit">
                <form method="post" action="editPortalPortletAttributes" name="editPortlet_${portletKey}">
                  ${portletKeyFields}
                </form>
                <a href="javascript:document.editPortlet_${portletKey}.submit()" title="${editAttributeHint}">&nbsp;&nbsp;&nbsp;</a>
              </li>
            </#if>
            <#if prevColumnSeqId?has_content>
              <li class="move-left">
                <form method="post" action="updatePortletSeqDragDrop" name="movePortletLeft_${portletKey}">
                  <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                  <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                  <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                  <input name="destinationColumn" value="${prevColumnSeqId}" type="hidden"/>
                  <input name="mode" value="DRAGDROPBOTTOM" type="hidden"/>
                </form>
                <a href="javascript:document.movePortletLeft_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
            </#if>
            <#if nextColumnSeqId?has_content>
              <li class="move-right">
                <form method="post" action="updatePortletSeqDragDrop" name="movePortletRight_${portletKey}">
                  <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                  <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                  <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                  <input name="destinationColumn" value="${nextColumnSeqId}" type="hidden"/>
                  <input name="mode" value="DRAGDROPBOTTOM" type="hidden"/>
                </form>
                <a href="javascript:document.movePortletRight_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
            </#if>
            <#if prevPortletId?has_content>
              <li class="move-up">
                <form method="post" action="updatePortletSeqDragDrop" name="movePortletUp_${portletKey}">
                  <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                  <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                  <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                  <input name="d_portalPageId" value="${portalPageId}" type="hidden"/>
                  <input name="d_portalPortletId" value="${prevPortletId}" type="hidden"/>
                  <input name="d_portletSeqId" value="${prevPortletSeqId}" type="hidden"/>
                  <input name="mode" value="DRAGDROPBEFORE" type="hidden"/>
                </form>
                <a href="javascript:document.movePortletUp_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
            </#if>
            <#if nextPortletId?has_content>
              <li class="move-down">
                <form method="post" action="updatePortletSeqDragDrop" name="movePortletDown_${portletKey}">
                  <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                  <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                  <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                  <input name="d_portalPageId" value="${portalPageId}" type="hidden"/>
                  <input name="d_portalPortletId" value="${nextPortletId}" type="hidden"/>
                  <input name="d_portletSeqId" value="${nextPortletSeqId}" type="hidden"/>
                  <input name="mode" value="DRAGDROPAFTER" type="hidden"/>
                </form>
                <a href="javascript:document.movePortletDown_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
            </#if>
          </ul>
          <br class="clear"/>
        </div>
      </#if>
</#macro>

<#macro renderPortalPagePortletEnd confMode="false">
  </div>
  <#if confMode == "true">
    </div>
  </#if>
</#macro>

<#macro renderColumnContainerBegin id style>
  <table cellspacing="0"<#if id?has_content> id="${id}"</#if><#if style?has_content> class="${style}"</#if>>
  <tr>
</#macro>

<#macro renderColumnContainerEnd>
  </tr>
  </table>
</#macro>

<#macro renderColumnBegin id style>
  <td<#if id?has_content> id="${id}"</#if><#if style?has_content> class="${style}"</#if>>
</#macro>

<#macro renderColumnEnd>
  </td>
</#macro>
