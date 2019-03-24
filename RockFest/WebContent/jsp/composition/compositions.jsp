<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="compositions"/></title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ratingDetails.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/ratings.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/entity.css">
</head>
<body class="common_background">
	<header><fmt:message key="compositions"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="compositions" value="${current_page_attributes['compositions'] }"></c:set>
		<c:set var="position" value="${current_page_attributes['position'] }"></c:set>
		<c:set var="elements_count" value="${current_page_attributes['elements_count'] }"></c:set>
		<c:set var="size" value="${ fn:length(compositions)}"></c:set>
		<section class="entities">
				<c:forEach var="composition" items="${compositions }" varStatus="Status">
					<section class="entity">
					<form action="${pageContext.request.contextPath }/RockFest">
						<input name="id" value ="${composition.compositionId}" hidden>
						<button name="command" value="find_composition"><c:out value="${composition.title }"></c:out></button>
					</form>
					<c:if test="${ composition.votedUsersCount > 0}">
						<section>
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="general" hidden>
								<input type="text" name="command" value="find_compositions_ratings" hidden>
								<input type="submit" value = <fmt:message key="rating"/>><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${(composition.melodyRating+composition.textRating+composition.musicRating+composition.vocalRating)/
										(composition.votedUsersCount*4)}"/>
							</form>
							<button id="showRating${composition.compositionId}" onclick="showRating(${composition.compositionId})"><fmt:message key="show_ratings"/></button>
							<button id="hideRating${composition.compositionId}" onclick="hideRating(${composition.compositionId})" class="hideRatings"><fmt:message key="hide_ratings"/></button>
							<section id="${composition.compositionId}" class="ratings">
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="melody" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="melody_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${composition.melodyRating / composition.votedUsersCount}"/></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="text" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="text_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${composition.textRating / composition.votedUsersCount}"/></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="music" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="music_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${composition.musicRating / composition.votedUsersCount}"/></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="vocal" hidden>
									<input type="text" name="command" value="find_compositions_ratings" hidden>
									<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${composition.vocalRating / composition.votedUsersCount}"/></h3>
								</form>
							</section>
							<section><h2><b><fmt:message key="voted_users_count"/></b><c:out value=" ${composition.votedUsersCount }"></c:out></h2></section>
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
					<button name="command" value="find_all_compositions"><fmt:message key="prev_page"/></button>
				</form>
			</c:if>
		</section>
		<section class="inline">
			<c:if test="${ elements_count eq size}">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="position" value ="${position + elements_count}" hidden>
					<input name="elementsCount" value ="${elements_count}" hidden>
					<button name="command" value="find_all_compositions"><fmt:message key="next_page"/></button>
				</form>
			</c:if>
		</section>
	</main>
</body>
</html>