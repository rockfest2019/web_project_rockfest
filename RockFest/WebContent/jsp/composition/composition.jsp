<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <%@ taglib prefix="ctg" uri="video" %>
<html>
<head>
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="composition"/></title>
	<style type="text/css">
		.rating{
			display: inline-block;
			background: #999;
		}
	</style>
</head>
<body>
	<header><fmt:message key="composition"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="composition" value="${current_page_attributes['composition'] }"></c:set>
		<c:set var="user_rating" value="${current_page_attributes['user_rating'] }"></c:set>
		<c:set var="comments" value="${current_page_attributes['comments'] }"></c:set>
		<c:set var="links" value="${current_page_attributes['links'] }"></c:set>
		<h1><fmt:message key="composition"/></h1>
				<section><b><fmt:message key="title"/></b><h2><c:out value="${composition.title }"></c:out></h2></section>
				<section><b><fmt:message key="singer"/></b>
					<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${composition.singerId}" hidden></input>
					<button name="command" value="find_singer"><c:out value="${composition.singerTitle }"></c:out></button>
				</form>
				</section>
				<section>
					<b><fmt:message key="genres"/></b>
					<c:forEach var="genre" items="${composition.genres }" varStatus="Status">
				<form action="${pageContext.request.contextPath }/RockFest">
					<input name="id" value ="${genre.genreId}" hidden></input>
					<button name="command" value="find_genre"><c:out value="${genre.title }"></c:out></button>
				</form>
				</c:forEach>
				</section>
				<section>
					<b><fmt:message key="links"/></b>
					<c:forEach var="link" items="${links }" varStatus="Status">
						<section>
							<p><a href="${link.url }">Link</a></p>
							<ctg:video url="${link.url }"/>
						</section>
					</c:forEach>
					<section>
						<c:if test="${not empty user_id }">
							<p> Add new link </p>
							<form action="${pageContext.request.contextPath }/RockFest">
								<input name="id" value ="${composition.compositionId}" hidden></input>
								<input name="command" value="save_composition_link" hidden>
								<input type="url" name="url" required>
								<input type="submit" value="Save">
							</form>
						</c:if>
					</section>
				</section>
				<section><b><fmt:message key="year"/></b><h2><c:out value="${composition.year }"></c:out></h2></section>
				<section>
				<c:if test="${ composition.votedUsersCount == 0}">
					<fmt:message key="rating_lack_message"/>
				</c:if>
				<c:if test="${ composition.votedUsersCount > 0}">
					<section>
						<form action = "${pageContext.request.contextPath }/RockFest">
							<input type="text" name="ratingType" value="general" hidden>
							<input type="text" name="command" value="find_compositions_ratings" hidden>
							<input type="submit" value = <fmt:message key="rating"/>><c:out value="${(composition.melodyRating+composition.textRating+composition.musicRating+composition.vocalRating)/
									(composition.votedUsersCount*4)}"></c:out>
						</form>
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
					</c:if>
					<section>
						<c:if test="${not empty user_id}">
							<h3><c:out value="${rating_failure}"></c:out></h3>
							<section>
								<c:if test="${not empty user_rating and not empty user_id}">
									<form action = "${pageContext.request.contextPath }/RockFest">
										<input type="text" name="ratingType" value="general" hidden>
										<input type="text" name="command" value="find_user_compositions_ratings" hidden>
										<input type="submit" value = <fmt:message key="user_rating"/>><c:out value="${(user_rating.melodyRating+user_rating.textRating+user_rating.musicRating+user_rating.vocalRating)/4}"></c:out>
									</form>
									<br>
									<section class = "rating">
										<form action = "${pageContext.request.contextPath }/RockFest">
											<input type="text" name="ratingType" value="melody" hidden>
											<input type="text" name="command" value="find_user_compositions_ratings" hidden>
											<input type="submit" value = <fmt:message key="user_melody_rating"/>><h3><c:out value="${user_rating.melodyRating }"></c:out></h3>
										</form>
									</section>
									<section class = "rating">
										<form action = "${pageContext.request.contextPath }/RockFest">
											<input type="text" name="ratingType" value="text" hidden>
											<input type="text" name="command" value="find_user_compositions_ratings" hidden>
											<input type="submit" value = <fmt:message key="user_text_rating"/>><h3><c:out value="${user_rating.textRating }"></c:out></h3>
										</form>
									</section>
									<section class = "rating">
										<form action = "${pageContext.request.contextPath }/RockFest">
											<input type="text" name="ratingType" value="music" hidden>
											<input type="text" name="command" value="find_user_compositions_ratings" hidden>
											<input type="submit" value = <fmt:message key="user_music_rating"/>><h3><c:out value="${user_rating.musicRating}"></c:out></h3>
										</form>
									</section>
									<section class = "rating">
										<form action = "${pageContext.request.contextPath }/RockFest">
											<input type="text" name="ratingType" value="vocal" hidden>
											<input type="text" name="command" value="find_user_compositions_ratings" hidden>
											<input type="submit" value = <fmt:message key="user_vocal_rating"/>><h3><c:out value="${user_rating.vocalRating}"></c:out></h3>
										</form>
								</section>
							</c:if>
						</section>
						<c:if test="${empty user_rating }">
						<form action = "${pageContext.request.contextPath }/RockFest" method="post">
							<section><fmt:message key="rating_insertion_hint"/></section>
							<input name="command" value="save_user_rating" hidden>
							<input name="id" value ="${composition.compositionId}" hidden>
							<input type="range" name="melodyRating" min="1" max="10"><fmt:message key="melody_rating"/><br>
							<input type="range" name="textRating" min="1" max="10"><fmt:message key="text_rating"/><br>
							<input type="range" name="musicRating" min="1" max="10"><fmt:message key="music_rating"/><br>
							<input type="range" name="vocalRating" min="1" max="10"><fmt:message key="vocal_rating"/><br>
							<input type="submit" value = "submit">
						</form>
						</c:if>
						</c:if>
					</section>
				</section>
				<b><fmt:message key="adding_date"/></b><h4><c:out value="${composition.addingDate }"></c:out></h4>
				<section>
					<c:forEach var="comment" items="${comments }" varStatus="Status">
						<section>
							<c:out value="${comment.authorLogin }"></c:out>
							<c:out value="${comment.date }"></c:out><br>
							<c:out value="${comment.content }"></c:out>
						</section>
					</c:forEach>
				</section>
				<c:if test="${not empty user_id }">
					Your comment:<br>
					<form action="${pageContext.request.contextPath }/RockFest" method="post">
					<input type="text" name="command" value="save_comment" hidden>
					<input type="text" name="id" value="${composition.compositionId }" hidden>
						<textarea required name="commentContent" cols="60" rows="3" maxlength="200"></textarea>
						<input type="submit" value="Save comment">
					</form>
				</c:if>
	</main>
</body>
</html>