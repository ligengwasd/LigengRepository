<script>
function findSecurityGroupDetail(para){
	gridSecurityGroup_Refresh(para);
	gridSecurityGroupPermission_Refresh(para);
}
function deleteUserLoginSecurityGroup(e){
	var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
	$.post("deleteUserLoginSecurityGroup?userLoginId="+dataItem.userLoginId+"&groupId="+dataItem.groupId, function(data) {
		$("input[value='查找']").trigger("onclick");
		successMessages("删除成功");
	});
}
function deleteSecurityGroupPermission(e){
	var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
	$.post("deleteSecurityGroupPermission?permissionId="+dataItem.permissionId+"&groupId="+dataItem.groupId, function(data) {
		$("input[value='查找']").trigger("onclick");
		successMessages("删除成功");
	});
}


</script>
