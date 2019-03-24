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
	<title><fmt:message key="singers"/></title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/entity.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/ratings.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/entity.css">
</head>
<body  class="common_background">
	<header><fmt:message key="singer"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="error_message" value="${current_page_attributes['error_message'] }"></c:set>
		<c:set var="singer_error" value="${current_page_attributes['singer_error'] }"></c:set>
		<c:set var="update_title_error" value="${current_page_attributes['update_title_error'] }"></c:set>
		<c:set var="update_description_error" value="${current_page_attributes['update_description_error'] }"></c:set>
		<c:set var="comments_failure" value="${current_page_attributes['comments_failure'] }"></c:set>
		<c:set var="singer" value="${current_page_attributes['singer'] }"></c:set>
		<c:set var="user_rating" value="${current_page_attributes['user_rating'] }"></c:set>
		<c:set var="comments" value="${current_page_attributes['comments'] }"></c:set>
		<c:set var="save_genre_error" value="${current_page_attributes['save_genre_error'] }"></c:set>
		<section class="error_message"><c:out value="${singer_error }"></c:out></section>
		<h1><fmt:message key="singer"/></h1>
				<section>
					<b><fmt:message key="title"/></b><h2><c:out value="${singer.title }"></c:out></h2>
					<c:if test="${role eq 'admin' }">
						<button id="showTitleChange" class="showTitleChange" onclick="showElement('titleChangeForm', 'hideTitleChange', 'showTitleChange');"><fmt:message key="title_change"/></button>
						<button id="hideTitleChange" class="hideTitleChange" onclick="hideElement('titleChangeForm', 'showTitleChange', 'hideTitleChange');"><fmt:message key="title_change"/></button>
						<form id="titleChangeForm" class="titleChangeForm" action="${pageContext.request.contextPath }/RockFest" method="post">
							<input type="text" name="command" value="change_singer_title" hidden>
							<input type="text" name="id" value="${singer.singerId }" hidden>
							<input type="text" name="title"><fmt:message key="new_title"/>
							<input type="submit" value=<fmt:message key="change"/>>
						</form>
					</c:if>
					<section class="error_message"><c:out value="${update_title_error }"></c:out></section>
				</section>
				<form action="${pageContext.request.contextPath }/RockFest">
				<input name="id" value ="${singer.singerId}" hidden></input>
					<button name="command" value="find_singer_compositions"><fmt:message key="singer_compositions"/></button>
				</form>
				<section class="description">
					<b><fmt:message key="description"/></b><section id="description" class="well"><h3><c:out value="${singer.description }"></c:out></h3></section>
					<c:if test="${not empty user_id }">
						<button id="descriptionUpdateButton" onclick="displayUpdateDescriptionForm();"><fmt:message key="change_description"/></button>
						<section id="descriptionUpdateForm" class="descriptionUpdateForm"><form action="${pageContext.request.contextPath }/RockFest">
							<input name="id" value ="${singer.singerId}" hidden></input>
							<input name="command" value ="update_singer_description" hidden></input>
							<textarea required id="update_entity_description" name="description" cols="60" rows="10" maxlength="65535"></textarea>
							<p>
								<input type="submit" value=<fmt:message key="save"/>>
								<input type="reset" value=<fmt:message key="reset"/>>
							</p>
						</form></section>
					</c:if>
				</section>
				<section class="error_message"><c:out value="${update_description_error }"></c:out></section>
				<section>
				<c:if test="${ singer.votedUsersCount == 0}">
					<fmt:message key="rating_lack_message"/>
				</c:if>
				<c:if test="${ singer.votedUsersCount > 0}">
					<section>
						<form action = "${pageContext.request.contextPath }/RockFest">
							<input type="text" name="ratingType" value="general" hidden>
							<input type="text" name="command" value="find_singers_ratings" hidden>
							<input type="submit" value = <fmt:message key="rating"/>><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${(singer.melodyRating+singer.textRating+singer.musicRating+singer.vocalRating)/
									(singer.votedUsersCount*4)}"/>
						</form>
						<br>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="melody" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="melody_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${singer.melodyRating / singer.votedUsersCount}"/></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="text" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="text_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${singer.textRating / singer.votedUsersCount}"/></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="music" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="music_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${singer.musicRating / singer.votedUsersCount}"/></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="vocal" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><fmt:formatNumber type = "number" maxFractionDigits = "2" value="${singer.vocalRating / singer.votedUsersCount}"/></h3>
							</form>
						</section>
						<section><h2><b><fmt:message key="voted_users_count"/></b><c:out value=" ${singer.votedUsersCount }"></c:out></h2></section>
					</section>
					<section class="error_message"><c:out value="${rating_failure }"></c:out></section>
						<c:if test="${not empty user_rating and not empty user_id}">
						<form action = "${pageContext.request.contextPath }/RockFest">
							<input type="text" name="ratingType" value="general" hidden>
							<input type="text" name="command" value="find_user_singers_ratings" hidden>
							<input type="submit" value = <fmt:message key="user_rating"/>><c:out value="${(user_rating.melodyRating+user_rating.textRating+user_rating.musicRating+user_rating.vocalRating)/4}"></c:out>
						</form>
						<br>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="melody" hidden>
								<input type="text" name="command" value="find_user_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_melody_rating"/>><h3><c:out value="${user_rating.melodyRating }"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="text" hidden>
								<input type="text" name="command" value="find_user_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_text_rating"/>><h3><c:out value="${user_rating.textRating }"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="music" hidden>
								<input type="text" name="command" value="find_user_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_music_rating"/>><h3><c:out value="${user_rating.musicRating}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="vocal" hidden>
								<input type="text" name="command" value="find_user_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_vocal_rating"/>><h3><c:out value="${user_rating.vocalRating}"></c:out></h3>
							</form>
						</section>
					</c:if>
				</c:if>
				</section>
				<b><fmt:message key="adding_date"/></b><h4><c:out value="${singer.addingDate }"></c:out></h4>
				<b><fmt:message key="author"/></b><h4><c:out value="${singer.authorTitle }"></c:out></h4>
				<b><fmt:message key="description_editor"/></b><h4><c:out value="${singer.descriptionEditorTitle }"></c:out></h4>
				<section class="comments">
					<c:forEach var="comment" items="${comments }" varStatus="Status">
						<section class="comment">
							<c:out value="${comment.authorLogin }"></c:out>
							<c:out value="${comment.date }"></c:out><br>
							<c:out value="${comment.content }"></c:out>
							<c:if test="${role eq 'admin' }">
								<form id="commentDeletionForm" class="commentDeletionForm" action="${pageContext.request.contextPath }/RockFest" method="post">
									<input type="text" name="command" value="delete_singer_comment" hidden>
									<input type="text" name="id" value="${comment.commentId }" hidden>
									<input type="submit" value=<fmt:message key="delete_comment"/>>
								</form>
							</c:if>
						</section>
					</c:forEach>
				</section>
				<section class="error_message"><c:out value="${comments_failure }"></c:out></section>
				<c:if test="${not empty user_id }">
					<fmt:message key="your_comment"/>:<br>
					<form action="${pageContext.request.contextPath }/RockFest" method="post">
					<input type="text" name="command" value="save_singer_comment" hidden>
					<input type="text" name="id" value="${singer.singerId }" hidden>
						<textarea required name="commentContent" cols="60" rows="3" maxlength="200"></textarea>
						<input type="submit" value=<fmt:message key="save_comment"/>>
					</form>
				</c:if>
	</main>
</body>
</html>