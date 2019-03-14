<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<body>
	<c:set var="root" value="${pageContext.request.contextPath }"></c:set>
	<c:set var="login_failure" value="${current_page_attributes['login_failure'] }"></c:set>
	<nav>
		<fmt:requestEncoding value = "UTF-8" />
		<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
		<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
		<fmt:setBundle basename="language"/>
		<form action = "${root }/RockFest">
			<select name="locale">
				<option selected value="en_US">English</option>
				<option value="ru_RU">Русский</option>
			</select>
			<input name="command" value="change_locale" hidden/>
			<input type="submit" value=<fmt:message key="change_locale"/>>
		</form>
		<section>
			<c:if test="${not empty login_failure }"><p><c:out value="${login_failure }"></c:out></p></c:if>
			<c:if test="${not empty user_id }">
					<form action = "${root }/RockFest" method="post">
						<input name="id" value=${user_id } hidden>
						<button name="command" value="user_profile"><c:out value="${user_login }"></c:out></button>
					</form>
					<form action = "${root }/RockFest" method="post">
						<input name="command" value="logout" hidden>
						<input type="submit" value=<fmt:message key="logout"/>>
					</form>
			</c:if>
			<c:if test="${empty user_id }">
				<form action = "${root }/RockFest" method="post">
					<input name="command" value="login" hidden>
					<label for="login"><fmt:message key="login"/></label>
					<input type="text" id="login" name="userLogin" required maxlength="45">
					<label for="password"><fmt:message key="password"/></label>
					<input type="password" id="password" name="password" required maxlength="20">
					<input type="submit" value=<fmt:message key="login"/>>
				</form>
				<form action = "${root }/RockFest" method="post">
					Send password to email:<br>
					<input name="command" value="send_password_to_email" hidden>
					<label for="login"><fmt:message key="login"/></label>
					<input type="text" id="login" name="userLogin" required maxlength="45">
					<input type="submit" value=<fmt:message key="send_password_to_email"/>>
				</form>
				<a href="${root }/jsp/user/registration.jsp">Registration</a>
			</c:if>
		</section>
		<section>
			Search
			<form action = "${root }/RockFest">
				<input name="command" value="entities_search" hidden>
				<input type="text" name="searchPattern" required maxlength="100">
				<input type="submit" value="Search">
			</form>
			Extended search
			<form action = "${root }/RockFest">
				<input type="text" name="searchPattern" required maxlength="100">
				<input type="submit" value="Search">
				Look in compositions<input type="checkbox" name="command" value="compositions_search">
				Look in singers<input type="checkbox" name="command" value="singers_search">
				Look in genres<input type="checkbox" name="command" value="genres_search">
			</form>
		</section>
		<form action="${root }/RockFest">
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