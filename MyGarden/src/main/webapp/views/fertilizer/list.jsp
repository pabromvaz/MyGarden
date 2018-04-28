<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasAnyRole('ADMIN')">
	<div>
		<acme:button url="fertilizer/administrator/create.do" code="fertilizer.create"/>
	</div>
</security:authorize>

<display:table name="fertilizers" id="fertilizer" requestURI="fertilizer/list.do" class="displaytag">
	

	
	<acme:column code="fertilizer.name" property="name"/>
	<acme:column code="fertilizer.description" property="description"/>
	
	<security:authorize access="hasAnyRole('ADMIN','GARDENER')">
	<display:column>
		<a href="fertilizer/display.do?fertilizerId=${fertilizer.id}"><spring:message
		code="fertilizer.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>




