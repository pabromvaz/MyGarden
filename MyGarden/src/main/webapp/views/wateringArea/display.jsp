<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<div>
	<ul>
		<li>
			<b><spring:message code="wateringArea.plant"/>:</b>
			<jstl:out value="${wateringArea.plant.name}" />
		</li>
		
		<li>
			<b><spring:message code="wateringArea.name"/>:</b>
			<jstl:out value="${wateringArea.name}" />
		</li>
		
		<li>
			<img src="${wateringArea.picture}" style = "max-width: 400 px; max-height: 400px;"/>
		</li>
		
		<li>
			<b><spring:message code="wateringArea.place"/>:</b>
			<jstl:out value="${wateringArea.place}"/>
		</li>
		
		<li>
			<b><spring:message code="wateringArea.description"/>:</b>
			<jstl:out value="${wateringArea.description}"/>
		</li>
		
		<li>
			<b><spring:message code="wateringArea.visible" />:</b>
			<jstl:out value="${wateringArea.visible}" />
		</li>
		
		<jstl:if test="${isOwner==true && configuration.manualWatering==true}">
		<li>
			<b><spring:message code="wateringArea.valveActivated" />:</b>
			<jstl:out value="${wateringArea.valveActivated}" />	
				<jstl:if test="${wateringArea.valveActivated==false}">
					<acme:button url="wateringArea/gardener/activateValve.do?wateringAreaId=${wateringArea.id}" code="wateringArea.activateValve"/>
				</jstl:if>
				<jstl:if test="${wateringArea.valveActivated==true}">
					<acme:button url="wateringArea/gardener/deactivateValve.do?wateringAreaId=${wateringArea.id}" code="wateringArea.deactivateValve"/>
				</jstl:if>
		</li>
		</jstl:if>
		
		<security:authorize access="hasAnyRole('GARDENER')" >
		<li>
			<b><spring:message code="wateringArea.comments" />:</b>
			<display:table name="${wateringArea.comments}" id="comment" class="displaytag" pagesize="5" keepStatus="true" requestURI="wateringArea/display.do?wateringAreaId=${wateringArea.id}" >
					
				<acme:column code="wateringArea.comments.gardener" property="gardener.name" />
				<acme:column code="wateringArea.comments.title" property="title"/>
				<acme:column code="wateringArea.comments.description" property="description"/>
				<acme:column code="wateringArea.comments.moment" property="moment"/>
				
			</display:table>
			<acme:button url="comment/gardener/create.do?wateringAreaId=${wateringArea.id}" code="wateringArea.comments.create"/>
		</li>
		</security:authorize>
	</ul>
	
</div>

<jstl:if test="${isOwner==true}">
	<form:form method="post" action="wateringArea/gardener/delete.do" modelAttribute="wateringArea" >
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="name" />
		<form:hidden path="place" />
		<form:hidden path="description" />
		<form:hidden path="picture" />
		<form:hidden path="visible" />
		<form:hidden path="valveActivated" />
		<form:hidden path="gardener" />
		<form:hidden path="timeTables" />
		<form:hidden path="measurements"/>
		<form:hidden path="events"/>
		<form:hidden path="plant"/>
		<form:hidden path="tastes"/>
		<form:hidden path="comments"/>
		<form:hidden path="predictions"/>
		
		<jstl:if test="${wateringArea.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="wateringArea.delete" />"
				onclick="return confirm('<spring:message code="wateringArea.confirm.delete" />')" />&nbsp;
		</jstl:if>
	</form:form>
</jstl:if>

<security:authorize access="hasAnyRole('GARDENER')">
	<jstl:if test="${isOwner==true}">
	<div>
		<acme:button url="wateringArea/gardener/edit.do?wateringAreaId=${wateringArea.id}" code="wateringArea.edit"/>
	</div>
	
	<div>
		<acme:button url="measurement/wateringArea/list.do?wateringAreaId=${wateringArea.id}" code="wateringArea.measurements"/>
	</div>
	
	<div>
		<acme:button url="event/wateringArea/list.do?wateringAreaId=${wateringArea.id}" code="wateringArea.events"/>
	</div>
	
	<div>
		<acme:button url="timeTable/wateringArea/list.do?wateringAreaId=${wateringArea.id}" code="wateringArea.timeTables"/>
	</div>
	
	<div>
		<acme:button url="prediction/wateringArea/list.do?wateringAreaId=${wateringArea.id}" code="wateringArea.predictions"/>
	</div>
	
	<div>
		<acme:button url="plant/wateringArea/listRecommendedPlants.do?wateringAreaId=${wateringArea.id}" code="wateringArea.listRecommendedPlants"/>
	</div>
	</jstl:if>
</security:authorize>

<jstl:if test="${isOwner==false}">
<security:authorize access="hasRole('GARDENER')">
			<div>
					<jstl:set var="haveLike" value="${false}"/>
					<jstl:set var="haveDislike" value="${false}"/>
					<jstl:if test="${!actor.tastes.isEmpty()}">
						<jstl:forEach var="taste" items="${actor.tastes}">
							<jstl:choose>
								<jstl:when test="${tastes.contains(taste)}">
									<jstl:if test="${taste.wateringArea.id==wateringArea.id}">
										<jstl:if test="${taste.like==true}">
											<jstl:set var="haveLike" value="${true}"/>
										</jstl:if>
										<jstl:if test="${taste.like==false}">
											<jstl:set var="haveDislike" value="${true}"/>
										</jstl:if>
									</jstl:if>
								</jstl:when>
							</jstl:choose>
						</jstl:forEach>
					</jstl:if>
					<jstl:if test="${haveLike==false}">
						<li><a href="taste/gardener/like.do?wateringAreaId=${wateringArea.id}">
							<spring:message code="wateringArea.like"/>
						</a></li>
					</jstl:if>
					<jstl:if test="${haveDislike==false}">
						<li><a href="taste/gardener/dislike.do?wateringAreaId=${wateringArea.id}">
							<spring:message code="wateringArea.dislike"/></a></li>
					</jstl:if>
			</div>
			
</security:authorize>
</jstl:if>