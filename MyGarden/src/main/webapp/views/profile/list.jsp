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

<div class="table-responsive">
<display:table name="profiles" pagesize="10" id="profile" requestURI="profile/list.do" class="displaytag">
	

	<acme:column code="profile.name" property="name" sortable="true"/>
	<acme:column code="profile.surname" property="surname"/>
	
	
	<security:authorize access="hasAnyRole('ADMIN')">
	<display:column>
		<a href="profile/display.do?actorId=${profile.id}"><spring:message
		code="profile.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>
</div>



