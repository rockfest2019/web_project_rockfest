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
	<title><fmt:message key="compositions"/></title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ratingDetails.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/ratings.css">
</head>
<body>
	<header><fmt:message key="compositions"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="compositions" value="${current_page_attributes['compositions'] }"></c:set>
		<section>
				<c:forEach var="composition" items="${compositions }" varStatus="Status">
					<form action="${pageContext.request.contextPath }/RockFest">
						<input name="id" value ="${composition.compositionId}" hidden>
						<button name="command" value="find_composition"><c:out value="${composition.title }"></c:out></button>
					</form>
					<c:if test="${ composition.votedUsersCount > 0}">
						<section>
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="general" hidden>
								<input type="text" name="command" value="find_compositions_ratings" hidden>
								<input type="submit" value = <fmt:message key="rating"/>><c:out value="${(composition.melodyRating+composition.textRating+composition.musicRating+composition.vocalRating)/
										(composition.votedUsersCount*4)}"></c:out>
							</form>
							<button id="showRating${composition.compositionId}" onclick="showRating(${composition.compositionId})">show ratings</button>
							<button id="hideRating${composition.compositionId}" onclick="hideRating(${composition.compositionId})" class="hideRatings">hide ratings</button>
							<section id="${composition.compositionId}" class="ratings">
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="melody" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="melody_rating"/>><h3><c:out value="${composition.melodyRating / composition.votedUsersCount}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="text" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="text_rating"/>><h3><c:out value="${composition.textRating / composition.votedUsersCount}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="music" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="music_rating"/>><h3><c:out value="${composition.musicRating / composition.votedUsersCount}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="vocal" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><c:out value="${composition.vocalRating / composition.votedUsersCount}"></c:out></h3>
								</form>
							</section>
							<section><h2><b><fmt:message key="voted_users_count"/></b><c:out value=" ${composition.votedUsersCount }"></c:out></h2></section>
							</section>
						</section>
						<br>
					</c:if>
				</c:forEach>
		</section>
	</main>
</body>
</html>