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
			<b><spring:message code="configuration.manualWatering"/>:</b>
			<jstl:out value="${configuration.manualWatering}" />
			<jstl:if test="${configuration.manualWatering==false}">
					<acme:button url="configuration/gardener/activateManualWatering.do?configurationId=${configuration.id}" code="configuration.activateManualWatering"/>
				</jstl:if>
			<jstl:if test="${configuration.manualWatering==true}">
					<acme:button url="configuration/gardener/deactivateManualWatering.do?configurationId=${configuration.id}" code="configuration.deactivateManualWatering"/>
			</jstl:if>
		</li>
		
		<li>
			<b><spring:message code="configuration.automaticWatering"/>:</b>
			<jstl:out value="${configuration.automaticWatering}" />
			<jstl:if test="${configuration.automaticWatering==false}">
					<acme:button url="configuration/gardener/activateAutomaticWatering.do?configurationId=${configuration.id}" code="configuration.activateAutomaticWatering"/>
				</jstl:if>
			<jstl:if test="${configuration.automaticWatering==true}">
					<acme:button url="configuration/gardener/deactivateAutomaticWatering.do?configurationId=${configuration.id}" code="configuration.deactivateAutomaticWatering"/>
			</jstl:if>
		</li>
		
		<b><spring:message code="configuration.sendMail"/>:</b>
		
		<li>
			<b><spring:message code="configuration.intrusionWarningActivated"/>:</b>
			<jstl:out value="${configuration.intrusionWarningActivated}" />
		</li>
		
		<li>
			<b><spring:message code="configuration.fertilizerWarningActivated"/>:</b>
			<jstl:out value="${configuration.fertilizerWarningActivated}"/>
		</li>
		
		<li>
			<b><spring:message code="configuration.tankWarningActivated"/>:</b>
			<jstl:out value="${configuration.tankWarningActivated}"/>
		</li>
	</ul>	
</div>

<security:authorize access="hasAnyRole('GARDENER')">
	<jstl:if test="${isOwner==true}">
	<div>
		<acme:button url="configuration/gardener/edit.do?configurationId=${configuration.id}" code="configuration.edit"/>
	</div>
	</jstl:if>
</security:authorize>