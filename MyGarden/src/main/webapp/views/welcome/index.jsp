<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<meta name="viewport" content="width=device-width, initial-scale=1"> 

<p><spring:message code="welcome.greeting.prefix" /> ${name}<spring:message code="welcome.greeting.suffix" /></p>

	<security:authorize access="hasAnyRole('GARDENER')" >
	<!-- Page Content -->
    <div class="container">

      <h1 class="my-4 text-center text-lg-left"><spring:message code="thumbnailGallery" /></h1>

      <div class="row text-center text-lg-left">
		<jstl:forEach items="${wateringAreas}" var="wateringArea">
   			<div class="col-lg-3 col-md-4 col-xs-6">
          		<a href="wateringArea/display.do?wateringAreaId=${wateringArea.id}" class="d-block mb-4 h-100">
            		<img class="img-fluid img-thumbnail" src="${wateringArea.picture}" style = " max-width: 350px!important; max-height: 300px!important;" alt="">
         		</a>
        	</div>
		</jstl:forEach>
        
       
      </div>
      
    </div>
    <!-- /.container -->
    </security:authorize>
<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 

