<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Title</title>
	<script type="text/javascript">
			var check = function() {
				  if (document.getElementById('password').value ==
					document.getElementById('confirm_password').value) {
					document.getElementById('message').style.color = 'green';
					document.getElementById('message').innerHTML = 'matching';
				  } else {
					document.getElementById('message').style.color = 'red';
					document.getElementById('message').innerHTML = 'not matching';
				  }
				}
		</script>
</head>
<body>
	<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
	<c:set var="user_profile_change" value="${current_page_attributes['user_profile_change'] }"></c:set>
	<c:set var="user_profile" value="${current_page_attributes['user_profile'] }"></c:set>
	<header>Header</header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<p>User profile</p>
		<c:out value="${error_message }"></c:out>
		<c:out value="${user_profile_change }"></c:out>
		<c:if test="${not empty user_profile }">
		<p> Added compositions count: ${user_profile.addedCompositionsCount }</p>
		<p> Added genres count: ${user_profile.addedGenresCount }</p>
		<p> Added singers count: ${user_profile.addedSingersCount }</p>
		<p> Compositions ratings count: ${user_profile.assessedCompositionsCount }</p>
		<p> Email: ${user_profile.email }</p>
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
</body>
</html>