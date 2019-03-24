<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	 <fmt:requestEncoding value = "UTF-8" />
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="main_page_title"/></title>
</head>
<body class="common_background">
	<c:set var="registration_success" value="${current_page_attributes['registration_success'] }"></c:set>
	<jsp:include page="jsp/included pages/navigation menu.jsp"></jsp:include>
	<header><section class="container"><fmt:message key="greeting"/></section></header>
	<section>
		<main>
			<c:if test="${not empty registration_success }"> <fmt:message key="registration_success"/></c:if>
		</main>
	</section>
</body>
</html>