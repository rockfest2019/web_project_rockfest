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
	<title><fmt:message key="genre"/></title>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/entity.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/ratings.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/entity.css">
</head>
<body>
	<header><fmt:message key="genre"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="genre" value="${current_page_attributes['genre'] }"></c:set>
		<c:set var="user_rating" value="${current_page_attributes['user_rating'] }"></c:set>
		<c:set var="rating_failure" value="${current_page_attributes['rating_failure'] }"></c:set>
		<c:set var="comments" value="${current_page_attributes['comments'] }"></c:set>
		<h1><fmt:message key="genre"/></h1>
				<section>
					<b><fmt:message key="title"/></b><h2><c:out value="${genre.title }"></c:out></h2>
					<c:if test="${role eq 'admin' }">
						<button id="showTitleChange" class="showTitleChange" onclick="showElement('titleChangeForm', 'hideTitleChange', 'showTitleChange');">Title change</button>
						<button id="hideTitleChange" class="hideTitleChange" onclick="hideElement('titleChangeForm', 'showTitleChange', 'hideTitleChange');">Title change</button>
						<form id="titleChangeForm" class="titleChangeForm" action="${pageContext.request.contextPath }/RockFest" method="post">
							<input type="text" name="command" value="change_genre_title" hidden>
							<input type="text" name="id" value="${genre.genreId }" hidden>
							<input type="text" name="title">New title
							<input type="submit" value="change title">
						</form>
					</c:if>
				</section>
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${genre.genreId}" hidden></input>
					<button name="command" value="find_genre_compositions"><fmt:message key="genre_compositions"/></button>
				</form>
				<section>
					<b><fmt:message key="description"/></b><h3><c:out value="${genre.description }"></c:out></h3>
					<c:if test="${not empty user_id }">
						<button id="descriptionUpdateButton" onclick="displayUpdateDescriptionForm();">Change description</button>
						<section id="descriptionUpdateForm" class="descriptionUpdateForm"><form action="${pageContext.request.contextPath }/RockFest">
							<input name="id" value ="${genre.genreId}" hidden></input>
							<input name="command" value ="update_genre_description" hidden></input>
							<textarea required name="description" cols="60" rows="10" maxlength="65535"></textarea>
							<p>
								<input type="submit" value="save">
								<input type="reset" value="reset">
							</p>
						</form></section>
					</c:if>
				</section>

				<section>
				<c:if test="${ genre.votedUsersCount == 0}">
					<fmt:message key="rating_lack_message"/>
				</c:if>
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
					<section>
						<c:if test="${not empty user_rating and not empty user_id}">
						<form action = "${pageContext.request.contextPath }/RockFest">
							<input type="text" name="ratingType" value="general" hidden>
							<input type="text" name="command" value="find_user_genres_ratings" hidden>
							<input type="submit" value = <fmt:message key="user_rating"/>><c:out value="${(user_rating.melodyRating+user_rating.textRating+user_rating.musicRating+user_rating.vocalRating)/4}"></c:out>
						</form>
						<br>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="melody" hidden>
								<input type="text" name="command" value="find_user_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_melody_rating"/>><h3><c:out value="${user_rating.melodyRating }"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="text" hidden>
								<input type="text" name="command" value="find_user_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_text_rating"/>><h3><c:out value="${user_rating.textRating }"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="music" hidden>
								<input type="text" name="command" value="find_user_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_music_rating"/>><h3><c:out value="${user_rating.musicRating}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="vocal" hidden>
								<input type="text" name="command" value="find_user_genres_ratings" hidden>
								<input type="submit" value = <fmt:message key="user_vocal_rating"/>><h3><c:out value="${user_rating.vocalRating}"></c:out></h3>
							</form>
						</section>
						</c:if>
						<h3><c:out value="${rating_failure}"></c:out></h3>
					</section>
				</c:if>
				</section>
				<section>
					<c:forEach var="comment" items="${comments }" varStatus="Status">
						<section>
							<c:out value="${comment.authorLogin }"></c:out>
							<c:out value="${comment.date }"></c:out><br>
							<c:out value="${comment.content }"></c:out>
							<c:if test="${role eq 'admin' }">
								<form id="commentDeletionForm" class="commentDeletionForm" action="${pageContext.request.contextPath }/RockFest" method="post">
									<input type="text" name="command" value="delete_genre_comment" hidden>
									<input type="text" name="id" value="${comment.commentId }" hidden>
									<input type="submit" value="delete comment">
								</form>
							</c:if>
						</section>
					</c:forEach>
				</section>
				<c:if test="${not empty user_id }">
					Your comment:<br>
					<form action="${pageContext.request.contextPath }/RockFest" method="post">
					<input type="text" name="command" value="save_genre_comment" hidden>
					<input type="text" name="id" value="${genre.genreId }" hidden>
						<textarea required name="commentContent" cols="60" rows="3" maxlength="200"></textarea>
						<input type="submit" value="Save comment">
					</form>
				</c:if>
	</main>
</body>
</html>