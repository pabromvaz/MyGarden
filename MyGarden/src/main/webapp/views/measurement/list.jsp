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
<display:table name="measurements" id="measurement" pagesize="2" requestURI="${requestURI}" class="displaytag">
	

	
	<acme:column code="measurement.moisture" property="moisture"/>
	<acme:column code="measurement.humidity" property="humidity"/>
	<acme:column code="measurement.temperature" property="temperature"/>
	<acme:column code="measurement.moment" property="moment"/>
	
	<security:authorize access="hasAnyRole('GARDENER')">
	<display:column>
		<a href="measurement/wateringArea/display.do?measurementId=${measurement.id}"><spring:message
		code="measurement.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>
</div>
<acme:button code="measurement.back" url="wateringArea/display.do?wateringAreaId=${wateringArea.id}"/>



