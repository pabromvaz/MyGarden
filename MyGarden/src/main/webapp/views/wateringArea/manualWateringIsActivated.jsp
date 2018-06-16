<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>



		
		<jstl:if test="${isOwner==true && configuration.manualWatering==true}">
			<jstl:if test="${wateringArea.valveActivated==false}">
					<b><spring:message code="valveDeactivated" /></b>
			</jstl:if>
			<jstl:if test="${wateringArea.valveActivated==true}">
					<b><spring:message code="valveActivated" /></b>
			</jstl:if>
		</jstl:if>
		
