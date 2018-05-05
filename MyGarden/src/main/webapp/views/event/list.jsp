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

<display:table name="events" id="event" requestURI="event/list.do" class="displaytag">
	

	
	<acme:column code="event.moment" property="moment"/>
	<acme:column code="event.name" property="name"/>
	
	<security:authorize access="hasAnyRole('GARDENER')">
	<display:column>
		<a href="event/wateringArea/display.do?eventId=${event.id}"><spring:message
		code="event.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>

<acme:button code="event.back" url="wateringArea/display.do?wateringAreaId=${wateringArea.id}"/>


