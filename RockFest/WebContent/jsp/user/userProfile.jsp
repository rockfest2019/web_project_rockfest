<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message key="user_profile"/></title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/userProfile.js"></script>
</head>
<body class="common_background">
	<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
	<c:set var="user_profile_change" value="${current_page_attributes['user_profile_change'] }"></c:set>
	<c:set var="user_profile" value="${current_page_attributes['user_profile'] }"></c:set>
	<c:set var="user_profile_error" value="${current_page_attributes['user_profile_error'] }"></c:set>
	<c:set var="user_info_change_failure" value="${current_page_attributes['user_info_change_failure'] }"></c:set>
	<header><fmt:message key="user_profile"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<section class="error_message"><c:out value="${user_profile_error }"></c:out></section>
	<section class="error_message"><c:out value="${error_message }"></c:out></section>
	<c:if test="${not empty user_id }">
	<main>
		<section class="error_message"><c:out value="${user_info_change_failure }"></c:out></section>
		<p><fmt:message key="user_profile"/></p>
		<c:out value="${user_profile_change }"></c:out>
		<section>
			<c:if test="${role eq 'admin' }">
				<form action="${pageContext.request.contextPath }/RockFest">
					<button name="command" value="admin_page">Admin page</button>
				</form>
			</c:if>
		</section>
		<c:if test="${not empty user_profile }">
		<p> <fmt:message key="added_compositions_count"/>: ${user_profile.addedCompositionsCount }</p>
		<p> <fmt:message key="added_genres_count"/>: ${user_profile.addedGenresCount }</p>
		<p> <fmt:message key="added_singers_count"/>: ${user_profile.addedSingersCount }</p>
		<p> <fmt:message key="compositions_ratings_count"/>: ${user_profile.assessedCompositionsCount }</p>
		<p> <fmt:message key="email"/>: ${user_profile.email }</p>
		<section class="error_message"><c:out value="${user_info_change_failure }"></c:out></section>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="change_email" hidden>
			<fmt:message key="new_eamil"/>:<input type="email" name="newEmail" required maxlength="50"><br>
			<fmt:message key="password"/>:<input type="password" name="password" required maxlength="20"><br>
			<input type="submit" value=<fmt:message key="change"/>>
		</form>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="change_login" hidden>
			<fmt:message key="new_login"/>:<input type="text" name="newLogin" required maxlength="45"><br>
			<fmt:message key="password"/>:<input type="password" name="password" required maxlength="20"><br>
			<input type="submit" value=<fmt:message key="change"/>>
		</form>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="change_password" hidden>
			<fmt:message key="new_password"/>:<input type="password" id="new_password" name="newPassword" required maxlength="20" onkeyup="checkNewPassword();"><br>
			<fmt:message key="confirm_new_password"/>:<input type="password" id="confirm_password" required maxlength="20" onkeyup="checkNewPassword();"><br>
			<span id="password_message"></span>
			<fmt:message key="password"/>:<input type="password" name="password" required maxlength="20"><br>
			<input type="submit" id="save_new_password" value=<fmt:message key="change"/>>
		</form>
		</c:if>
	</main>
	</c:if>
</body>
</html>