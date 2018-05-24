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
<display:table name="events" id="event" requestURI="event/list.do" class="displaytag">
	

	<jstl:if test="${event.readed == false}">
		<jstl:set var="style" value="font-weight:bold;text-shadow: 0.1em 0.1em 0.2em darkgrey"/>
	</jstl:if>
	<jstl:if test="${event.readed == true}">
		<jstl:set var="style" value="none"/>
	</jstl:if>
	
	<acme:column code="event.moment" property="moment" style="${style}"/>
	<acme:column code="event.name" property="name" style="${style}"/>
	
	<security:authorize access="hasAnyRole('GARDENER')">
	<display:column>
		<a href="event/display.do?eventId=${event.id}"><spring:message
		code="event.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>
</div>
<acme:button code="event.back" url="wateringArea/display.do?wateringAreaId=${wateringArea.id}"/>


