<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="registration"/></title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/registration.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/bootstrap.css">
</head>
<body class="common_background">
	<header><fmt:message key="registration"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="registration_failure" value="${current_page_attributes['registration_failure'] }"></c:set>
		<c:set var="invalid_email" value="${current_page_attributes['invalid_email'] }"></c:set>
		<p><fmt:message key="registration_page"/></p>
		<c:if test="${not empty registration_failure }"><p><c:out value="${registration_failure }"></c:out></p></c:if>
		<c:if test="${not empty invalid_email }"><p><c:out value="${invalid_email }"></c:out></p></c:if>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="registrate_user" hidden>
			<label for="user_login"><fmt:message key="login"/></label>
			<input type="text" name="userLogin" required maxlength="45"><br>
			<label for="password"><fmt:message key="password"/></label>
			<input type="password" id="registration_password" name="registration_password" required maxlength="20" onkeyup="checkPassword();"><br>
			<label for="confirm password"><fmt:message key="confirm_password"/></label>
			<input type="password" id="confirm_password" name="confirm password" required maxlength="20" onkeyup="checkPassword();"><br>
			<span id="password_message"></span>
			<label for="email"><fmt:message key="email"/></label>
			<input type="email" name="userEmail" required maxlength="50"><br>
			<p>
				<input type="submit" id="submit_registration" value=<fmt:message key="save"/>>
				<input type="reset" value=<fmt:message key="reset"/>>
			</p>
		</form>
	</main>
</body>
</html>