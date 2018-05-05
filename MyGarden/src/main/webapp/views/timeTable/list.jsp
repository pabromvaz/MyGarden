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

<acme:button url="timeTable/wateringArea/create.do?wateringAreaId=${wateringArea.id}" code="timeTable.create"/>

<display:table name="timeTables" id="timeTable" requestURI="timeTable/list.do" class="displaytag">
	

	
	<acme:column code="timeTable.activationMoment" property="activationMoment"/>
	<acme:column code="timeTable.deactivationMoment" property="deactivationMoment"/>
	
	<security:authorize access="hasAnyRole('GARDENER')">
	<display:column>
		<a href="timeTable/wateringArea/display.do?timeTableId=${timeTable.id}"><spring:message
		code="timeTable.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>

<acme:button code="timeTable.back" url="wateringArea/display.do?wateringAreaId=${wateringArea.id}"/>



