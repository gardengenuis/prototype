<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<c:choose>
	<c:when test="${fn:length(subMenu.subMenus) > 0}">
		<li class="dropdown-submenu">
        	<a href="#">${subMenu.name}</a>
        	<ul class="dropdown-menu">
        		<c:forEach var="subMenu" items="${subMenu.subMenus }">
        			<c:set var="subMenu" value="${subMenu}" scope="request"/>  
					<jsp:include page="/common/bootstrap/menuItems.jsp"/>
        		</c:forEach>
        	</ul>
	</c:when>
	<c:otherwise>
		<li>
			<a href="#" onclick="openURL('<c:url value="${subMenu.url }"/>',this)">${subMenu.name}</a>
	</c:otherwise>
</c:choose>
		</li>