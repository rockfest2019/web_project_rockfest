<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Profile error</title>
</head>
<body class="common_background">
	<header>Profile error</header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
		<h1>Profile error</h1>
		<h2><c:out value="${error_message }"></c:out></h2>
	</main>
</body>
</html>