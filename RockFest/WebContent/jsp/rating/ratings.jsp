<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	<%@ taglib prefix="ctg" uri="rating" %>
<html>
<head>
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="ratings"/></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/ratings.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/ajax.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxRating.js"></script>
</head>
<body  class="common_background">
	<header><fmt:message key="ratings"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<c:set var="user_ratings" value="${current_page_attributes['user_rating'] }"></c:set>
	<c:set var="ratings" value="${current_page_attributes['ratings'] }"></c:set>
	<c:set var="specific_rating" value="${current_page_attributes['specific_rating'] }"></c:set>
	<c:set var="comparing_entity" value="${current_page_attributes['comparing_entity'] }"></c:set>
	<c:set var="comparator" value="${current_page_attributes['comparator'] }"></c:set>
	<c:set var="rating_failure" value="${current_page_attributes['rating_failure'] }"></c:set>
	<c:set var="entity_command" value="${current_page_attributes['entity_command'] }"></c:set>
	<c:set var="rating_command" value="${current_page_attributes['rating_command'] }"></c:set>
	<c:set var="ajax_command" value="${current_page_attributes['ajax_command'] }"></c:set>
	<c:set var="rating_end" value="${current_page_attributes['rating_end'] }"></c:set>
	<main>
			<h1><fmt:message key="ratings"/></h1>
			<button id="composition_rating" onclick="compositionRating();"><fmt:message key="compositions_rating"/></button>
			<button id="singer_rating" onclick="singerRating();"><fmt:message key="singers_rating"/></button>
			<button id="genre_rating" onclick="genreRating();"><fmt:message key="genres_rating"/></button>
			<c:out value="${rating_failure }"></c:out>
			<section id="ratings">
				<input type="number" id="position" value="5" hidden>
				<input type="text" id="start" value="true" hidden>
				<input type="text" id="end" value="${rating_end }" hidden>
				<c:if test="${not empty specific_rating }">
					<c:if test="${comparing_entity eq 'composition' }"><h2><fmt:message key="composition"/></h2></c:if>
					<c:if test="${comparing_entity eq 'genre' }"><h2><fmt:message key="genre"/></h2></c:if>
					<c:if test="${comparing_entity eq 'singer' }"><h2><fmt:message key="singer"/></h2></c:if>
					<c:if test="${comparator eq 'general' }"><h2><fmt:message key="rating"/></h2></c:if>
					<c:if test="${comparator eq 'melody' }"><h2><fmt:message key="melody_rating"/></h2></c:if>
					<c:if test="${comparator eq 'text' }"><h2><fmt:message key="text_rating"/></h2></c:if>
					<c:if test="${comparator eq 'music' }"><h2><fmt:message key="music_rating"/></h2></c:if>
					<c:if test="${comparator eq 'vocal' }"><h2><fmt:message key="vocal_rating"/></h2></c:if>
					<c:forEach var="rating" items="${ratings }" varStatus="Status">
						<form action="${pageContext.request.contextPath }/RockFest">
							<input name="id" value ="${rating.entityId}" hidden>
							<button name="command" value="${entity_command }"><c:out value="${rating.entityTitle }"></c:out></button>
						</form>
						<section class="rating_bg">
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="general" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${rating.rating}"/></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="melody" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="melody_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${rating.melodyRating}"/></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="text" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="text_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${rating.textRating}"/></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="music" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="music_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${rating.musicRating}"/></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="vocal" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${rating.vocalRating}"/></h3>
								</form>
							</section>
							<section><h2><b><fmt:message key="voted_users_count"/></b><c:out value=" ${rating.votedUsersCount }"></c:out></h2></section>
						</section>
					</c:forEach>
					<c:forEach var="user_rating" items="${user_ratings }" varStatus="Status">
						<form action="${pageContext.request.contextPath }/RockFest">
							<input name="id" value ="${rating.entityId}" hidden>
							<button name="command" value="${entity_command }"><c:out value="${user_rating.entityTitle }"></c:out></button>
						</form>
						<section>
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="general" hidden>
								<input type="text" name="command" value="${rating_command }" hidden>
								<input type="submit" value = <fmt:message key="rating"/>><c:out value="${user_rating.rating}"></c:out>
							</form>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="melody" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="melody_rating"/>><h3><c:out value="${user_rating.melodyRating}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="text" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="text_rating"/>><h3><c:out value="${user_rating.textRating}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="music" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="music_rating"/>><h3><c:out value="${user_rating.musicRating}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="vocal" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><c:out value="${user_rating.vocalRating}"></c:out></h3>
								</form>
							</section>
						</section>
					</c:forEach>
					<button id='prev' class="visibleNavigationAjax" onclick="prev();"><fmt:message key="prev_page"/></button>
					<button id='next' class="visibleNavigationAjax" onclick="next();"><fmt:message key="next_page"/></button>
				</c:if>

			</section>
			<button id='prev' class="navigationAjax" onclick="prev();"><fmt:message key="prev_page"/></button>
			<button id='next' class="navigationAjax" onclick="next();"><fmt:message key="next_page"/></button>
			<input type="text" id="url" value='http://localhost:8080/RockFest/RockFest?ver=4&ratingType=${comparator }&command=${ajax_command}&elementsCount=5' hidden>

	</main>

</body>
</html>