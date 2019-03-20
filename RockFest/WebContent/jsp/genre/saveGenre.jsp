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
	<title><fmt:message key="new_genre"/></title>
</head>
<body>
	<header><fmt:message key="new_genre"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="save_genre_error" value="${current_page_attributes['save_genre_error'] }"></c:set>
		<section class="error_message"><c:out value="${save_genre_error }"></c:out></section>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
		<input name="command" value="save_genre" hidden>
			<p><b><fmt:message key="title"/></b></p>
			<input type="text" required name="title" maxlength="100">
			<p><b><fmt:message key="description"/></b></p>
			<textarea required name="description" cols="60" rows="10" maxlength="65535"></textarea>
			<p>
				<input type="submit" value="save">
				<input type="reset" value="reset">
			</p>
		</form>
	</main>
</body>
</html>