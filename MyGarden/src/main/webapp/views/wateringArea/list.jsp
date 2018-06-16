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


<security:authorize access="hasAnyRole('GARDENER')">
	<div>
		<acme:button url="wateringArea/gardener/create.do" code="wateringArea.create"/>
	</div>
<br/>
<spring:message code="wateringArea.searchText"/>
<input type="text" value="" id="textSearch" />
<input type="button" id="buttonSearch"
value="<spring:message code="wateringArea.searchButton"/>" />
<br>



<div class="table-responsive">
<display:table name="wateringAreas" id="wateringArea" pagesize="10" requestURI="wateringArea/list.do" class="displaytag">
	

	<acme:column code="wateringArea.plant" property="plant.name" sortable="true"/>
	<acme:column code="wateringArea.name" property="name" sortable="true"/>
	<acme:column code="wateringArea.place" property="place" sortable="true"/>
	<acme:column code="wateringArea.description" property="description"/>
	
	<security:authorize access="hasAnyRole('GARDENER')">
	<display:column>
		<a href="wateringArea/display.do?wateringAreaId=${wateringArea.id}"><spring:message
		code="wateringArea.display"/>
		</a>
	</display:column>
	</security:authorize>
	
</display:table>
</div>
</security:authorize>

<script type="text/javascript">
	$(document).ready(function(){
		$(document).on('click',"#buttonSearch",function(wateringArea){
			wateringArea.preventDefault();
			window.location.replace('wateringArea/search.do?key=' + $("#textSearch").val());
		});
	});		
</script>