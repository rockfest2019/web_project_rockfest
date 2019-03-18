<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Title</title>
</head>
<body>
	<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
	<c:set var="users" value="${current_page_attributes['users'] }"></c:set>
	<c:set var="current_date" value="${current_page_attributes['date'] }"></c:set>
	<c:set var="day_ban" value="${current_date + 24*60*60*1000 }"></c:set>
	<c:set var="week_ban" value="${current_date + 7*24*60*60*1000 }"></c:set>
	<c:set var="month_ban" value="${current_date + 30*24*60*60*1000 }"></c:set>
	<c:set var="forever_ban" value="${current_date + 100*12*30*24*60*60*1000 }"></c:set>
	<header>Admin page</header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<h1>Admin page</h1>
		<c:out value="${error_message }"></c:out>
		<c:if test="${role eq 'admin' }">
			<section><c:forEach var="user" items="${users }">
				<section>
					<c:out value="${user.login }"></c:out>
					<c:if test="${user.banExpirationDate gt current_date }">
						 User is banned
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
						<input type="radio" name="date" value="${day_ban }"> Day ban
						<input type="radio" name="date" value="${week_ban }"> Week ban
						<input type="radio" name="date" value="${month_ban }"> Month ban
						<input type="radio" name="date" value="${forever_ban }"> Forever ban
						<input type="submit" value="ban user">
					</form>
					</c:if>
				</section>
			</c:forEach></section>
		</c:if>
	</main>
</body>
</html>