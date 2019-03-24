<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/navigation menu.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/navigation menu.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/common.css">
<body>

	<c:set var="root" value="${pageContext.request.contextPath }"></c:set>
	<c:set var="login_failure" value="${current_page_attributes['login_failure'] }"></c:set>
	<c:set var="search_error" value="${current_page_attributes['search_error'] }"></c:set>

	<nav class="navigation">
		<fmt:requestEncoding value = "UTF-8" />
		<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
		<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
		<br>
		<section class="inline"><section class="searchForm">
			<form action = "${root }/RockFest" class="searchForm">
				<section id="searchFeature" class ="searchFeature">
					<input type="radio" required name="command" value="entities_search" checked="checked"><fmt:message key="all"/>
					<input type="radio" required name="command" value="compositions_search"><fmt:message key="compositions"/>
					<input type="radio" required name="command" value="genres_search"><fmt:message key="genres"/>
					<input type="radio" required name="command" value="singers_search"><fmt:message key="singers"/>
				</section>
				<input type="text" name="searchPattern" required maxlength="100">
				<input type="submit" value=<fmt:message key="search"/>>
			</form>
			<button id="extendedSearch" class="extendedSearch" onclick="extendedSearch();">Extended</button>
			<section class="error_message"><c:out value="${search_error }"></c:out></section>
		</section></section>


		<section class="inline"><fmt:setBundle basename="language"/>
		<form action = "${root }/RockFest">
			<select name="locale">
				<option selected value="en_US">English</option>
				<option value="ru_RU">Русский</option>
			</select>
			<input name="command" value="change_locale" hidden/>
			<input type="submit" value=<fmt:message key="change_locale"/>>
		</form>
		</section>
		<section class="error_message"><c:out value="${login_failure }"></c:out></section>
			<c:if test="${not empty user_id }">
					<form action = "${root }/RockFest" method="post" class="profileLink">
						<input name="id" value=${user_id } hidden>
						<button name="command" value="user_profile"><c:out value="${user_login }"></c:out></button>
					</form>
					<form action = "${root }/RockFest" method="post" class="logout">
						<input name="command" value="logout" hidden>
						<input type="submit" value=<fmt:message key="logout"/>>
					</form>
			</c:if>
			<c:if test="${empty user_id }">
				<form action = "${root }/RockFest" method="post" class="loginForm">
					<input name="command" value="login" hidden>
					<label for="login"><fmt:message key="login"/></label>
					<input type="text" id="login" name="userLogin" required maxlength="45">
					<label for="password"><fmt:message key="password"/></label>
					<input type="password" id="password" name="password" required maxlength="20">
					<input type="submit" value=<fmt:message key="login"/>>
				</form>
				<button id="forgotPasswordButton" class="forgotPasswordButton" onclick="forgotPassword();"><fmt:message key="forgot_password"/></button>
				<form action = "${root }/RockFest" method="post" id="forgotPasswordForm" class="forgotPasswordForm">
					<fmt:message key="send_password_to_email"/>:<br>
					<input name="command" value="send_password_to_email" hidden>
					<label for="login"><fmt:message key="login"/></label>
					<input type="text" id="login" name="userLogin" required maxlength="45">
					<input type="submit" value=<fmt:message key="send_password_to_email"/>>
				</form>
				<a class="registrationLink" href="${root }/jsp/user/registration.jsp"><fmt:message key="registration"/></a>
			</c:if>
		<br>
		<form action="${root }/RockFest">
			<input type="number" name="position" value="0" hidden>
			<input type="number" name="elementsCount" value="5" hidden>
			<button name="command" value="find_all_compositions"><fmt:message key="compositions"/></button>
			<button name="command" value="find_all_singers"><fmt:message key="singers"/></button>
			<button name="command" value="find_all_genres"><fmt:message key="genres"/></button>
			<c:if test="${not empty user_id }"><button name="command" value="add_composition"><fmt:message key="add_composition"/></button></c:if>
			<p>
				<a href="${root }/index.jsp"> <fmt:message key="main"/> </a>
				<a href="${root }/jsp/rating/ratings.jsp"><fmt:message key="ratings"/></a>
				<c:if test="${not empty user_id }">
					<a href="${root }/jsp/genre/saveGenre.jsp"><fmt:message key="add_genre"/></a>
					<a href="${root }/jsp/singer/saveSinger.jsp"><fmt:message key="add_singer"/></a>
				</c:if>
			</p>
		</form>
	</nav>
</body>
</html>