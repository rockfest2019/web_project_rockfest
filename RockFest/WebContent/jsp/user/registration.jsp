<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Registration</title>
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
	<header>Registration</header>
	<a href="${pageContext.request.contextPath }/index.jsp"> <fmt:message key="main"/> </a>
	<main>
		<c:set var="registration_failure" value="${current_page_attributes['registration_failure'] }"></c:set>
		<c:set var="invalid_email" value="${current_page_attributes['invalid_email'] }"></c:set>
		<p>Registration page</p>
		<c:if test="${not empty registration_failure }"><p><c:out value="${registration_failure }"></c:out></p></c:if>
		<c:if test="${not empty invalid_email }"><p><c:out value="${invalid_email }"></c:out></p></c:if>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
			<input type="text" name="command" value="registrate_user" hidden>
			<label for="user_login">Login</label>
			<input type="text" name="userLogin" required maxlength="45"><br>
			<label for="password">Password</label>
			<input type="password" id="password" name="password" required maxlength="20" onkeyup="check();"><br>
			<label for="confirm password">Confirm password</label>
			<input type="password" id="confirm_password" name="confirm password" required maxlength="20" onkeyup="check();"><br>
			<span id="message"></span>
			<label for="email">email</label>
			<input type="email" name="userEmail" required maxlength="50"><br>
			<p>
				<input type="submit" value="save">
				<input type="reset" value="reset">
			</p>
		</form>
	</main>
</body>
</html>