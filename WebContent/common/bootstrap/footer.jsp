<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<div class="container">
	<br/>
	<br/>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<div class="footer">
		<p align="center">Powered by<a href="http://jgarden.com.cn/portal" style="text-decoration:none;" target="_blank">桂花园</a>&copy;2014-<fmt:formatDate pattern="yyyy" value="${now}" />  </p>
	</div>
</div>