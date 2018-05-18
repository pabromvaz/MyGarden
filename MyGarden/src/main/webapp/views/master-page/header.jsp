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

   <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="#">My garden</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
          
          	<security:authorize access="isAnonymous()">
            <li class="nav-item active">
              <a class="nav-link" href="security/login.do"><spring:message code="master.page.login" />
                <span class="sr-only">(current)</span>
              </a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="gardener/create.do"><spring:message code="master.page.register" /></a>
            </li>
          	</security:authorize>  
          	
            <security:authorize access="isAuthenticated()">
            <li class="nav-item active">
              <a class="nav-link" href="plant/list.do"><spring:message code="master.page.plant.list" /></a>
            </li>
            <li class="nav-item active">
              <a class="nav-link" href="fertilizer/list.do"><spring:message code="master.page.fertilizer.list" /></a>
            </li>
            <li class="nav-item dropdown">
              <a class="navbar-brand dropdown-toggle" href="#" id="navbarDropdown" style="font-size: 16px; padding: 8px; padding-left: 0px" data-toggle="dropdown">
          		<spring:message	code="master.page.messageEmails" />
        	  </a>
        	  <div class="dropdown-menu">
          	  	<a class="dropdown-item" href="messageEmail/listIn.do"><spring:message code="master.page.messageEmails.received" /></a>
          	  	<a class="dropdown-item" href="messageEmail/listOut.do"><spring:message code="master.page.messageEmails.sent" /></a>
          	  	<a class="dropdown-item" href="messageEmail/listArchived.do"><spring:message code="master.page.messageEmails.archived" /></a>
        	  </div>
            </li>
            <li class="nav-item dropdown">
              <a class="navbar-brand dropdown-toggle" href="#" id="navbarDropdown" style="font-size: 16px; padding: 8px; padding-left: 0px" data-toggle="dropdown">
          		<spring:message	code="master.page.profile" />
          		(<security:authentication property="principal.username" />)
        	  </a>
        	  <div class="dropdown-menu">
          	  	<a class="dropdown-item" href="profile/myProfile.do"><spring:message code="master.page.profile.myProfile" /></a>
          	  	<a class="dropdown-item" href="j_spring_security_logout"><spring:message code="master.page.logout" /></a>
        	  </div>
            </li>
             </security:authorize>
             
             <security:authorize access="hasRole('GARDENER')">
             <li class="nav-item dropdown">
              <a class="navbar-brand dropdown-toggle" href="#" id="navbarDropdown" style="font-size: 16px; padding: 8px; padding-left: 0px" data-toggle="dropdown">
          		<spring:message	code="master.page.gardener" />
        	  </a>
        	  <div class="dropdown-menu">
          	  	<a class="dropdown-item" href="wateringArea/listMyWateringAreas.do"><spring:message code="master.page.gardener.wateringArea.myList" /></a>
          	  	<a class="dropdown-item" href="wateringArea/list.do"><spring:message code="master.page.gardener.wateringArea.list" /></a>
        	  </div>
             </li>
             <li class="nav-item active">
              <a class="nav-link" href="configuration/gardener/display.do"><spring:message code="master.page.configuration" /></a>
            </li>
            </security:authorize>
            
          </ul>
        </div>
      </div>
    </nav>


<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>