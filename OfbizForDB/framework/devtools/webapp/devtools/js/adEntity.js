function getEntityDetail(){
	var temp = jQuery("#editAdEntity_adEntityId").val();
	var jsonData = "{\"adEntityId\":\""+temp+"\"}";
	gridAdField_RefreshByJson(jsonData);
	gridAdPrimkey_RefreshByJson(jsonData);
	gridAdRelation_RefreshByJson(jsonData);
	//adKeymap_RefreshByJson(jsonData);
}

function getServiceDetail(){
	var temp = jQuery("#editAdService_adServiceId").val();
	var jsonData = "{\"adServiceId\":\""+temp+"\"}";
	gridAdAttribute_RefreshByJson(jsonData);
	gridAdEvent_RefreshByJson(jsonData);
	//adKeymap_RefreshByJson(jsonData);
}

function getRequestmapDetail(){
	var temp = jQuery("#editAdRequestmap_adRequestmapId").val();
	var jsonData = "{\"adRequestmapId\":\""+temp+"\"}";
	gridAdResponse_RefreshByJson(jsonData);
	gridAdEvent_RefreshByJson(jsonData);
}

function getViewmapDetail(){
	var temp = jQuery("#editAdViewmap_adViewmapId").val();
	var jsonData = "{\"adViewmapId\":\""+temp+"\"}";
	gridAdGrid_RefreshByJson(jsonData);
	gridAdForm_RefreshByJson(jsonData);
	gridAdResponse_RefreshByJson(jsonData);
}

function getFormDetail(){
	var temp = jQuery("#editAdForm_adFormId").val();
	var jsonData = "{\"adFormId\":\""+temp+"\"}";
	gridAdFormfield_RefreshByJson(jsonData);
	gridAdFormcommand_RefreshByJson(jsonData);
}

function getGridDetail(){
	var temp = jQuery("#editAdGrid_adGridId").val();
	var jsonData = "{\"adGridId\":\""+temp+"\"}";
	gridAdGridfield_RefreshByJson(jsonData);
	gridAdGridcommand_RefreshByJson(jsonData);
}