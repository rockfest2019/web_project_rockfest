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
	<script type="text/javascript">
		window.onload = function (){
			var showUpdateDescriptionForm = true;
			document.getElementById('descriptionUpdateButton').onclick = function (){
				if (showUpdateDescriptionForm == true){
					document.getElementById('descriptionUpdateForm').style.display = "block";
					showUpdateDescriptionForm = false;
				} else {
					document.getElementById('descriptionUpdateForm').style.display = "none";
					showUpdateDescriptionForm = true;
				}
			}
		}
	</script>
	<style type="text/css">
		.rating{
			display: inline-block;
			background: #999;
		}
		.descriptionUpdateForm{
		display: none;
		}
	</style>
</head>
<body>
	<header><fmt:message key="singer"/></header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main>
		<c:set var="singer" value="${current_page_attributes['singer'] }"></c:set>
		<c:set var="user_rating" value="${current_page_attributes['user_rating'] }"></c:set>
		<h1><fmt:message key="singer"/></h1>
				<section><b><fmt:message key="title"/></b><h2><c:out value="${singer.title }"></c:out></h2></section>
				<form action="${pageContext.request.contextPath }/RockFest">
				<input name="id" value ="${singer.singerId}" hidden></input>
					<button name="command" value="find_singer_compositions"><fmt:message key="singer_compositions"/></button>
				</form>
				<section>
					<b><fmt:message key="description"/></b><h3><c:out value="${singer.description }"></c:out></h3>
					<c:if test="${not empty user_id }">
						<button id="descriptionUpdateButton">Change description</button>
						<section id="descriptionUpdateForm" class="descriptionUpdateForm"><form action="${pageContext.request.contextPath }/RockFest">
							<input name="id" value ="${singer.singerId}" hidden></input>
							<input name="command" value ="update_singer_description" hidden></input>
							<textarea required name="description" cols="60" rows="10" maxlength="65535"></textarea>
							<p>
								<input type="submit" value="save">
								<input type="reset" value="reset">
							</p>
						</form></section>
					</c:if>
				</section>
				<section>
				<c:if test="${ singer.votedUsersCount == 0}">
					<fmt:message key="rating_lack_message"/>
				</c:if>
				<c:if test="${ singer.votedUsersCount > 0}">
					<section>
						<form action = "${pageContext.request.contextPath }/RockFest">
							<input type="text" name="ratingType" value="general" hidden>
							<input type="text" name="command" value="find_singers_ratings" hidden>
							<input type="submit" value = <fmt:message key="rating"/>><c:out value="${(singer.melodyRating+singer.textRating+singer.musicRating+singer.vocalRating)/
									(singer.votedUsersCount*4)}"></c:out>
						</form>
						<br>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="melody" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="melody_rating"/>><h3><c:out value="${singer.melodyRating / singer.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="text" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="text_rating"/>><h3><c:out value="${singer.textRating / singer.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="music" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="music_rating"/>><h3><c:out value="${singer.musicRating / singer.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section class = "rating">
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="vocal" hidden>
								<input type="text" name="command" value="find_singers_ratings" hidden>
								<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><c:out value="${singer.vocalRating / singer.votedUsersCount}"></c:out></h3>
							</form>
						</section>
						<section><h2><b><fmt:message key="voted_users_count"/></b><c:out value=" ${singer.votedUsersCount }"></c:out></h2></section>
					</section>
					<section>
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
	</main>
</body>
</html>