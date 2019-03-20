<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Title</title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/registration.js"></script>
</head>
<body>
	<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
	<c:set var="user_profile_change" value="${current_page_attributes['user_profile_change'] }"></c:set>
	<c:set var="user_profile" value="${current_page_attributes['user_profile'] }"></c:set>
	<c:set var="user_profile_error" value="${current_page_attributes['user_profile_error'] }"></c:set>
	<c:set var="user_info_change_failure" value="${current_page_attributes['user_info_change_failure'] }"></c:set>
	<header>User profile</header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<section class="error_message"><c:out value="${user_profile_error }"></c:out></section>
	<section class="error_message"><c:out value="${error_message }"></c:out></section>
	<c:if test="${not empty user_id }">
	<main>
		<section class="error_message"><c:out value="${user_info_change_failure }"></c:out></section>
		<p>User profile</p>
		<c:out value="${user_profile_change }"></c:out>
		<section>
			<c:if test="${role eq 'admin' }">
				<form action="${pageContext.request.contextPath }/RockFest">
					<button name="command" value="admin_page">Admin page</button>
				</form>
			</c:if>
		</section>
		<c:if test="${not empty user_profile }">
		<p> Added compositions count: ${user_profile.addedCompositionsCount }</p>
		<p> Added genres count: ${user_profile.addedGenresCount }</p>
		<p> Added singers count: ${user_profile.addedSingersCount }</p>
		<p> Compositions ratings count: ${user_profile.assessedCompositionsCount }</p>
		<p> Email: ${user_profile.email }</p>
		<section class="error_message"><c:out value="${user_info_change_failure }"></c:out></section>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="change_email" hidden>
			New email:<input type="email" name="newEmail" required maxlength="50"><br>
			Enter password:<input type="password" name="password" required maxlength="20"><br>
			<input type="submit" value="change">
		</form>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="change_login" hidden>
			New login:<input type="text" name="newLogin" required maxlength="45"><br>
			Enter password:<input type="password" name="password" required maxlength="20"><br>
			<input type="submit" value="change">
		</form>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="change_password" hidden>
			New password:<input type="password" id="password" name="newPassword" required maxlength="20" onkeyup="check();"><br>
			Confirm new password:<input type="password" id="confirm_password" required maxlength="20" onkeyup="check();"><br>
			<span id="message"></span>
			Enter password:<input type="password" name="password" required maxlength="20"><br>
			<input type="submit" value="change">
		</form>
		</c:if>
	</main>
	</c:if>
</body>
</html>