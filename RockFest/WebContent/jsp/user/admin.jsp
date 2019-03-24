<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message key="admin_page"/></title>
</head>
<body class="common_background">
	<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
	<c:set var="users" value="${current_page_attributes['users'] }"></c:set>
	<c:set var="current_date" value="${current_page_attributes['date'] }"></c:set>
	<c:set var="day_ban" value="${current_date + 24*60*60*1000 }"></c:set>
	<c:set var="week_ban" value="${current_date + 7*24*60*60*1000 }"></c:set>
	<c:set var="month_ban" value="${current_date + 30*24*60*60*1000 }"></c:set>
	<c:set var="forever_ban" value="${current_date + 100*12*30*24*60*60*1000 }"></c:set>
	<c:set var="admin_profile_error" value="${current_page_attributes['admin_profile_error'] }"></c:set>
	<c:set var="user_info_change_failure" value="${current_page_attributes['user_info_change_failure'] }"></c:set>
	<c:set var="ban_date_saving_failure" value="${current_page_attributes['ban_date_saving_failure'] }"></c:set>
	<header><fmt:message key="admin_page"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<h1><fmt:message key="admin_page"/></h1>
		<section class="error_message"><c:out value="${error_message }"></c:out></section>
		<section class="error_message"><c:out value="${admin_profile_error }"></c:out></section>
		<c:if test="${role eq 'admin' }">
			<section class="error_message"><c:out value="${user_info_change_failure }"></c:out></section>
			<section class="error_message"><c:out value="${ban_date_saving_failure }"></c:out></section>
			<section><c:forEach var="user" items="${users }">
				<section>
					<c:out value="${user.login }"></c:out>
					<c:if test="${user.banExpirationDate gt current_date }">
						<fmt:message key="user_is_banned"/>
						 <form action="${pageContext.request.contextPath }/RockFest" method="post">
						 	<input type="text" name="command" value="save_user_ban_date" hidden>
						 	<input name="id" value=${user.userId } hidden>
							<input type="text" name="date" value="0" hidden>
							<input type="submit" value="unban user">
						 </form>
					</c:if>
					<c:if test="${user.banExpirationDate lt current_date }">
					<form action="${pageContext.request.contextPath }/RockFest" method="post">
						<input type="text" name="command" value="save_user_ban_date" hidden>
						<input type="text" name="id" value="${user.userId }" hidden>
						<input type="radio" name="date" value="${day_ban }"> <fmt:message key="day_ban"/>
						<input type="radio" name="date" value="${week_ban }"> <fmt:message key="week_ban"/>
						<input type="radio" name="date" value="${month_ban }"> <fmt:message key="month_ban"/>
						<input type="radio" name="date" value="${forever_ban }"> <fmt:message key="forever_ban"/>
						<input type="submit" value="ban user">
					</form>
					</c:if>
				</section>
			</c:forEach></section>
		</c:if>
	</main>
</body>
</html>