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
<display:table name="predictions" pagesize="10" id="prediction" requestURI="prediction/list.do" class="displaytag">
	

	
	<acme:column code="prediction.moment" property="moment" sortable="true"/>
	<acme:column code="prediction.place" property="place"/>
	<acme:column code="prediction.precipitation" property="precipitation"/>
	
	<security:authorize access="hasAnyRole('GARDENER')">
	<display:column>
		<a href="prediction/wateringArea/display.do?predictionId=${prediction.id}"><spring:message
		code="prediction.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>
</div>
<acme:button code="prediction.back" url="wateringArea/display.do?wateringAreaId=${wateringArea.id}"/>



