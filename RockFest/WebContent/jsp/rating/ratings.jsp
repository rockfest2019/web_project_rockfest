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
	<script type="text/javascript">
		window.onload = function (){
			var url = 'http://localhost:8080/RockFest/RockFest';
			var elementsCount = 5;
			var parameters = 'ver=9&elementsCount=' + elementsCount;
			document.getElementById('composition_rating').onclick = function (){
				document.getElementById('prev').style.display = 'block';
				document.getElementById('next').style.display = 'block';
				var commandParameter = 'command=compositions_ratings_ajax';
				var ratingTypeParameter = 'ratingType=general';
				var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
				document.getElementById('url').value = urlWithParameters;
				var position = 'position=0';
				urlWithParameters = urlWithParameters + '&'+ position;
				ajax(urlWithParameters);
			}

			document.getElementById('singer_rating').onclick = function (){
				document.getElementById('prev').style.display = 'block';
				document.getElementById('next').style.display = 'block';
				var commandParameter = 'command=singers_ratings_ajax';
				var ratingTypeParameter = 'ratingType=general';
				var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
				document.getElementById('url').value = urlWithParameters;
				var position = 'position=0';
				urlWithParameters = urlWithParameters + '&'+ position;
				ajax(urlWithParameters);
			}

			document.getElementById('genre_rating').onclick = function (){
				document.getElementById('prev').style.display = 'block';
				document.getElementById('next').style.display = 'block';
				var commandParameter = 'command=genres_ratings_ajax';
				var ratingTypeParameter = 'ratingType=general';
				var urlWithParameters = url + '?' + parameters + '&' + commandParameter + '&' + ratingTypeParameter;
				document.getElementById('url').value = urlWithParameters;
				var position = 'position=0';
				urlWithParameters = urlWithParameters + '&'+ position;
				ajax(urlWithParameters);
			}

			document.getElementById('next').onclick = function (){
				var urlWithParameters = document.getElementById('url').value;
				position = 'position=' + document.getElementById('position').value;
				var end = document.getElementById('end').value;
				if (end == 'false'){
					urlWithParameters = urlWithParameters + '&'+ position;
					document.getElementById('prev').style.display = 'block';
					ajax(urlWithParameters);
				} else {
					document.getElementById('next').style.display = 'none';
				}

			}

			document.getElementById('prev').onclick = function (){
				var urlWithParameters = document.getElementById('url').value;
				var newPosition = document.getElementById('position').value;
				var start = document.getElementById('start').value;
				if (start == 'false'){
					newPosition = newPosition - elementsCount*2;
					position = 'position=' + newPosition;
					urlWithParameters = urlWithParameters + '&'+ position;
					document.getElementById('next').style.display = 'block';
					ajax(urlWithParameters);
				} else {
					document.getElementById('prev').style.display = 'none';
				}

			}

		}
		function ajax(urlWithParameters) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				if (request.readyState==4){
					document.getElementById('ratings').innerHTML = request.responseText;
				}
			}

			request.open('GET', urlWithParameters);
			request.send();
		}
	</script>
	<style type="text/css">
		.rating{
			display: inline-block;
			background: #999;
		}
		.rating_bg{
			border-bottom-width: 1px;
    		border-bottom-style: dotted;
    		border-bottom-color: black;
		}
		.navigationAjax{
			display : block;
		}
	</style>
</head>
<body>
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
			<button id="composition_rating">compositions rating</button>
			<button id="singer_rating">singer rating</button>
			<button id="genre_rating">genres rating</button>
			<c:out value="${rating_failure }"></c:out>
			<section id="ratings">
				<input type="number" id="position" value="5" hidden>
				<input type="text" id="start" value="true" hidden>
				<input type="text" id="end" value="${rating_end }" hidden>
				<c:if test="${not empty specific_rating }">
					<c:if test="${comparing_entity eq 'composition' }">Compositions rating</c:if>
					<c:if test="${comparing_entity eq 'genre' }">Genres rating</c:if>
					<c:if test="${comparing_entity eq 'singer' }">Singers rating</c:if>
					<c:if test="${comparator eq 'GENERAL' }">General rating</c:if>
					<c:if test="${comparator eq 'MELODY' }">Melody rating</c:if>
					<c:if test="${comparator eq 'TEXT' }">Text rating</c:if>
					<c:if test="${comparator eq 'MUSIC' }">Music rating</c:if>
					<c:if test="${comparator eq 'VOCAL' }">Vocal rating</c:if>
					<c:forEach var="rating" items="${ratings }" varStatus="Status">
						<form action="${pageContext.request.contextPath }/RockFest">
							<input name="id" value ="${rating.entityId}" hidden>
							<button name="command" value="${entity_command }"><c:out value="${rating.entityTitle }"></c:out></button>
						</form>
						<section>
							<form action = "${pageContext.request.contextPath }/RockFest">
								<input type="text" name="ratingType" value="general" hidden>
								<input type="text" name="command" value="${rating_command }" hidden>
								<input type="submit" value = <fmt:message key="rating"/>><c:out value="${rating.rating}"></c:out>
							</form>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="melody" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="melody_rating"/>><h3><c:out value="${rating.melodyRating}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="text" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="text_rating"/>><h3><c:out value="${rating.textRating}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="music" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="music_rating"/>><h3><c:out value="${rating.musicRating}"></c:out></h3>
								</form>
							</section>
							<section class = "rating">
								<form action = "${pageContext.request.contextPath }/RockFest">
									<input type="text" name="ratingType" value="vocal" hidden>
									<input type="text" name="command" value="${rating_command }" hidden>
									<input type="submit" value = <fmt:message key="vocal_rating"/>><h3><c:out value="${rating.vocalRating}"></c:out></h3>
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
				</c:if>

			</section>
			<button id='prev' class="navigationAjax">Prev page</button>
			<button id='next' class="navigationAjax">Next page</button>
			<input type="text" id="url" value='http://localhost:8080/RockFest/RockFest?ver=4&ratingType=${comparator }&command=${ajax_command}&elementsCount=5' hidden>




	</main>

</body>
</html>