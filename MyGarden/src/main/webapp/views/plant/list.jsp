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
		<acme:button url="plant/administrator/create.do" code="plant.create"/>
	</div>
</security:authorize>

<display:table name="plants" id="plant" requestURI="plant/list.do" class="displaytag">
	

	
	<acme:column code="plant.name" property="name"/>
	<acme:column code="plant.description" property="description"/>
	
	
	<security:authorize access="hasAnyRole('ADMIN','GARDENER')">
	<display:column>
		<a href="plant/display.do?plantId=${plant.id}"><spring:message
		code="plant.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>




