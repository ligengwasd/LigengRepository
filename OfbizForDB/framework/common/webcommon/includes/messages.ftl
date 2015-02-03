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

<#if requestAttributes.errorMessageList?has_content><#assign errorMessageList=requestAttributes.errorMessageList></#if>
<#if requestAttributes.eventMessageList?has_content><#assign eventMessageList=requestAttributes.eventMessageList></#if>
<#if requestAttributes.serviceValidationException?exists><#assign serviceValidationException = requestAttributes.serviceValidationException></#if>
<#if requestAttributes.uiLabelMap?has_content><#assign uiLabelMap = requestAttributes.uiLabelMap></#if>

<#if !errorMessage?has_content>
  <#assign errorMessage = requestAttributes._ERROR_MESSAGE_?if_exists>
</#if>
<#if !errorMessageList?has_content>
  <#assign errorMessageList = requestAttributes._ERROR_MESSAGE_LIST_?if_exists>
</#if>
<#if !eventMessage?has_content>
  <#assign eventMessage = requestAttributes._EVENT_MESSAGE_?if_exists>
</#if>
<#if !eventMessageList?has_content>
  <#assign eventMessageList = requestAttributes._EVENT_MESSAGE_LIST_?if_exists>
</#if>

<#-- display the error messages -->
<#if (errorMessage?has_content || errorMessageList?has_content)>
  <div id="content-messages" class="content-messages errorMessage" onclick="document.getElementById('content-messages').parentNode.removeChild(this)">
    <p>${uiLabelMap.CommonFollowingErrorsOccurred}:</p>
    <#if errorMessage?has_content>
      <p>${errorMessage}</p>
    </#if>
    <#if errorMessageList?has_content>
      <#list errorMessageList as errorMsg>
        <p>${errorMsg}</p>
      </#list>
    </#if>
  </div>
</#if>

<#-- display the event messages -->
<#if (eventMessage?has_content || eventMessageList?has_content)>
  <div id="content-messages" class="content-messages eventMessage" onclick="document.getElementById('content-messages').parentNode.removeChild(this)">
    <p>${uiLabelMap.CommonFollowingOccurred}:</p>
    <#if eventMessage?has_content>
      <p>${eventMessage}</p>
    </#if>
    <#if eventMessageList?has_content>
      <#list eventMessageList as eventMsg>
        <p>${eventMsg}</p>
      </#list>
    </#if>
  </div>
</#if>

<div id="success_msg"  style="background:url('/bambook/images/go.png')  no-repeat  transparent; padding-left:128px;">  </div>
<div id="error_msg"  style="background:url('/bambook/images/Private.png')  no-repeat  transparent; padding-left:128px;"></div>
<script type="text/javascript">
	
	function successMessages(messages){
	  var msgbox=$('#success_msg').kendoWindow({
			visible: false,
		  	title: "success",
		  	actions: ["Close"],
		  	width: "270",
		  	height:"130px",
		  	position: {
			  	top: 200,
			  	left:1,
	  		}
		});
		var dialog = msgbox.data("kendoWindow");
		dialog.content(messages);
		dialog.center();
		dialog.open();
		setTimeout(function() {  dialog.close();}, 1000);
	}
	function errorMessages(messages){
		var msgbox=$('#error_msg').kendoWindow({
			visible: false,
		  	title: "faild",
		  	actions: ["Close"],
		  	width: "500",
		  	height:"130px",
		  	position: {
			  	top: 700,
			  	left:1,
	  		}
		});
		var dialog = msgbox.data("kendoWindow");
		dialog.content(messages);
		dialog.center();
		dialog.open();
		//setTimeout(function() {  dialog.close();}, 500000);
	}
	function fun1(obj,url1){
		var form = $(obj).closest("form");
		$.ajax({
			type:"post",
			url: url1,
			data:form.serializeObjectToJson(),
	        success: function(result) {
	          var exp = result._ERROR_MESSAGE_LIST_;
	          var exp2 =result._ERROR_MESSAGE_;
	          
			  if ( typeof(exp)=="undefined" && typeof(exp2)=="undefined" ){
			  	successMessages("执行成功");
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
</script>

