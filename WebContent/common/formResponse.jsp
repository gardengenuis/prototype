<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<script language="javascript">
<c:choose>
	<c:when test="${returnResponse.code == 'error' }">
	popError("操作失败!原因:[${returnResponse.msg}]", function() {
		setTimeout(
				function(){
					art.dialog.close();
		}, 100);
	});
	</c:when>
	<c:when test="${returnResponse.code == 'succeed' }">
	popSuccess("操作成功!", function() {
		setTimeout(
				function(){
					art.dialog.close();
		}, 100);
	});
	</c:when>
	<c:otherwise>
	// nothing to do...
	</c:otherwise>
</c:choose>
</script>

