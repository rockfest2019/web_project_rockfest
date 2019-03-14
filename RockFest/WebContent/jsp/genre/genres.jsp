<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="genres"/></title>
	<style type="text/css">
		.rating{
			display: inline-block;
			background: #999;
		}
	</style>
</head>
<body>
	<header> <fmt:message key="genres"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="genres" value="${current_page_attributes['genres'] }"></c:set>
		<section>
				<c:forEach var="genre" items="${genres }" varStatus="Status">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${genre.genreId}" hidden></input>
					<button name="command" value="find_genre"><c:out value="${genre.title }"></c:out></button>
					<button name="command" value="find_genre_compositions"><fmt:message key="genre_compositions"/></button>
				</form>
				<c:if test="${ genre.votedUsersCount > 0}">
					<section>
						<form action = "${pageContext.request.contextPath }/RockFest">
							<input type="text" name="ratingType" value="general" hidden>
							<input type="text" name="command" value="find_genres_ratings" hidden>
							<input type="submit" value = <fmt:message key="rating"/>><c:out value="${(genre.melodyRating+genre.textRating+genre.musicRating+genre.vocalRating)/
									(genre.votedUsersCount*4)}"></c:out>
						</form>
						<br>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="melody" hidden>
								<input type="text" name="command" value="find_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="melody_rating"/>><h3><c:out value="${genre.melodyRating / genre.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="text" hidden>
								<input type="text" name="command" value="find_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="text_rating"/>><h3><c:out value="${genre.textRating / genre.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="music" hidden>
								<input type="text" name="command" value="find_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="music_rating"/>><h3><c:out value="${genre.musicRating / genre.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="vocal" hidden>
								<input type="text" name="command" value="find_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><c:out value="${genre.vocalRating / genre.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section><h2><b><fmt:message key="voted_users_count"/></b><c:out value=" ${genre.votedUsersCount }"></c:out></h2></section>
					</section>
				</c:if>
				</c:forEach>
		</section>
	</main>
</body>
</html>