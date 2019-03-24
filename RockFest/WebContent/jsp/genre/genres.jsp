<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
     <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="genres"/></title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ratingDetails.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/ratings.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/entity.css">
</head>
<body class="common_background">
	<header> <fmt:message key="genres"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="genres" value="${current_page_attributes['genres'] }"></c:set>
		<c:set var="position" value="${current_page_attributes['position'] }"></c:set>
		<c:set var="elements_count" value="${current_page_attributes['elements_count'] }"></c:set>
		<c:set var="size" value="${ fn:length(genres)}"></c:set>
		<section class="entities">
				<c:forEach var="genre" items="${genres }" varStatus="Status">
				<section class="entity">
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
							<input type="submit" value = <fmt:message key="rating"/>><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${(genre.melodyRating+genre.textRating+genre.musicRating+genre.vocalRating)/
									(genre.votedUsersCount*4)}"/>
						</form>
						<button id="showRating${genre.genreId}" onclick="showRating(${genre.genreId})"><fmt:message key="show_ratings"/></button>
						<button id="hideRating${genre.genreId}" onclick="hideRating(${genre.genreId})" class="hideRatings"><fmt:message key="hide_ratings"/></button>
						<section id="${genre.genreId}" class="ratings">
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
								<input type="submit" value = <fmt:message key="text_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${genre.textRating / genre.votedUsersCount}"/></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="music" hidden>
								<input type="text" name="command" value="find_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="music_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${genre.musicRating / genre.votedUsersCount}"/></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="vocal" hidden>
								<input type="text" name="command" value="find_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${genre.vocalRating / genre.votedUsersCount}"/></h3>
							</form>
						</section>
						<section><h2><b><fmt:message key="voted_users_count"/></b><c:out value=" ${genre.votedUsersCount }"></c:out></h2></section>
					</section>
					</section>
					<br>
				</c:if>
				</section>
				</c:forEach>
		</section>
		<section class="inline">
			<c:if test="${ position gt 0}">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="position" value ="${position - elements_count}" hidden>
					<input name="elementsCount" value ="${elements_count}" hidden>
					<button name="command" value="find_all_genres"><fmt:message key="prev_page"/></button>
				</form>
			</c:if>
		</section>
		<section class="inline">
			<c:if test="${ elements_count eq size}">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="position" value ="${position + elements_count}" hidden>
					<input name="elementsCount" value ="${elements_count}" hidden>
					<button name="command" value="find_all_genres"><fmt:message key="next_page"/></button>
				</form>
			</c:if>
		</section>
	</main>
</body>
</html>