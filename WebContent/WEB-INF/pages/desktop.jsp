<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<style type="text/css" ></style>
	<script type="text/javascript" src="<c:url value="/js/jquery-2.1.1/jquery-picklist.js"/>"></script>
	<link href="<c:url value="/css/jquery/jquery-picklist.css"/>" rel="stylesheet" type="text/css" />
	<title>欢迎</title>
</head>
<body>
		<a href="javascript:clickshow()">click</a>
		<div class="container">
			<select id="form_stgIds" name="form.stgIds" multiple class="form-control">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3" selected>3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
		</div>
				
		
		<script type="text/javascript">
		function clickshow()  {
			
			
			$("#form_stgIds option:selected").each(function(){
	            alert($(this).text()); //这里得到的就是
	        });
			
		}
		
		$(document).ready(function(){
			$("#form_stgIds").pickList();
		});
		</script>
		
</body>
</html>