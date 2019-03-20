<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Search page</title>
</head>
<body>
	<header>Search</header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="entities" value="${current_page_attributes['entities'] }"></c:set>
		<c:set var="compositions" value="${current_page_attributes['compositions'] }"></c:set>
		<c:set var="genres" value="${current_page_attributes['genres'] }"></c:set>
		<c:set var="singers" value="${current_page_attributes['singers'] }"></c:set>
		<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
		<c:set var="search_error" value="${current_page_attributes['error_message'] }"></c:set>
		<section>
			<section class="error_message"><c:out value="${error_message }"></c:out></section>
			<section class="error_message"><c:out value="${search_error }"></c:out></section>
			<c:forEach var="entity" items="${entities }">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${entity.id}" hidden>
					<button name="command" value="${entity.command}"><c:out value="${entity.title }"></c:out></button>
				</form>
			</c:forEach>
			<c:forEach var="composition" items="${compositions }" varStatus="Status">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${composition.compositionId}" hidden>
					<button name="command" value="find_composition"><c:out value="${composition.title }"></c:out></button>
				</form>
			</c:forEach>
			<c:forEach var="genre" items="${genres }" varStatus="Status">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${genre.genreId}" hidden>
					<button name="command" value="find_genre"><c:out value="${genre.title }"></c:out></button>
				</form>
			</c:forEach>
			<c:forEach var="singer" items="${singers }" varStatus="Status">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${singer.singerId}" hidden>
					<button name="command" value="find_singer"><c:out value="${singer.title }"></c:out></button>
				</form>
			</c:forEach>
		</section>
	</main>
</body>
</html>