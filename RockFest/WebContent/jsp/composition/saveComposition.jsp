<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="new_composition"/></title>
</head>
<body>
	<header><fmt:message key="new_composition"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="singers" value="${current_page_attributes['singers'] }"></c:set>
		<c:set var="genres" value="${current_page_attributes['genres'] }"></c:set>
		<c:set var="save_composition_error" value="${current_page_attributes['save_composition_error'] }"></c:set>
		<section class="error_message"><c:out value="${save_composition_error }"></c:out></section>
		<form action="${pageContext.request.contextPath }/RockFest" method="post">
		<input name="command" value="save_composition" hidden>
			<p><b><fmt:message key="title"/></b></p>
			<input type="text" required name="title" maxlength="100">
			<section>
				<p><b><fmt:message key="singer"/></b></p>
				<c:forEach var="singer" items="${singers }" varStatus="Status">
					<input type="radio" required name="singerId" value="${singer.singerId}"><c:out value="${singer.title }"> </c:out><br>
				</c:forEach>
			</section>

			<section>
				<p><b><fmt:message key="genres"/></b></p>
				<c:forEach var="genre" items="${genres }" varStatus="Status">
					<input type="checkbox" name="genresIds" value="${genre.genreId}"><c:out value="${genre.title }"> </c:out><br>
				</c:forEach>
			</section>

			<p><fmt:message key="composition_year"/>
				<select id="year" name="year">
					<script>
						for (var i=1940;i<2030;i++){
							document.write('<option value="'+i+'">'+i+'</option>');
						}
					</script>
				</select>
			</p>
			<p>
				<input type="submit" value="save">
				<input type="reset" value="reset">
			</p>
		</form>
	</main>
</body>
</html>