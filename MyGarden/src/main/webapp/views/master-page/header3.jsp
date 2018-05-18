<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="MyGarden Co., Inc." />
</div>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<security:authorize access="isAnonymous()">
		
  		<a class="navbar-brand" href="security/login.do"><spring:message code="master.page.login" /></a>
 			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    			<span class="navbar-toggler-icon"></span>
  			</button>
  		
  		
  		<a class="navbar-brand" href="gardener/create.do"><spring:message code="master.page.register" /></a>
 			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    			<span class="navbar-toggler-icon"></span>
  			</button>
  		
	</security:authorize>
		
 
      
      <security:authorize access="isAuthenticated()">
      
      <div class="nav-item dropdown">
        <a class="navbar-brand dropdown-toggle" href="#" id="navbarDropdown"  role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
          <spring:message	code="master.page.messageEmails" />
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="messageEmail/listIn.do"><spring:message code="master.page.messageEmails.received" /></a>
          <a class="dropdown-item" href="messageEmail/listOut.do"><spring:message code="master.page.messageEmails.sent" /></a>
          <a class="dropdown-item" href="messageEmail/listArchived.do"><spring:message code="master.page.messageEmails.archived" /></a>
        </div>
      </div>
      
      <a class="navbar-brand" href="plant/list.do"><spring:message code="master.page.plant.list" /></a>
 	
	  <a class="navbar-brand" href="fertilizer/list.do"><spring:message code="master.page.fertilizer.list" /></a>	
      
      <div class="nav-item dropdown">
        <a class="navbar-brand dropdown-toggle" href="#" id="navbarDropdown"  role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
          <spring:message	code="master.page.profile" />
          (<security:authentication property="principal.username" />)
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="profile/myProfile.do"><spring:message code="master.page.profile.myProfile" /></a>
          <a class="dropdown-item" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a>
        </div>
      </div>
      
      </security:authorize>

  	<security:authorize access="hasRole('GARDENER')">
  	
  	<a class="navbar-brand" href="configuration/gardener/display.do"><spring:message code="master.page.configuration" /></a>
 	
 	<div class="nav-item dropdown">
        <a class="navbar-brand dropdown-toggle" href="#" id="navbarDropdown"  role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" >
          <spring:message	code="master.page.gardener" />
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="wateringArea/list.do"><spring:message code="master.page.gardener.wateringArea.list" /></a>
          <a class="dropdown-item" href="wateringArea/listMyWateringAreas.do"><spring:message code="master.page.gardener.wateringArea.myList" /></a>
        </div>
      </div>
      
  	</security:authorize>

</nav>


<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>