<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message key="locale_validation_error"/></title>
</head>
<body>
	<header><fmt:message key="locale_validation_error"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main class="common_background">
		<h1><fmt:message key="locale_validation_error"/></h1>
	</main>
</body>
</html>