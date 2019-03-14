<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	 <fmt:requestEncoding value = "UTF-8" />
	<c:if test="${empty locale }"> <fmt:setLocale value="en_US"/></c:if>
	<c:if test="${not empty locale }"> <fmt:setLocale value="${locale }"/></c:if>
	<fmt:setBundle basename="language"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title><fmt:message key="main_page_title"/></title>
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
				console.log(urlWithParameters);
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
				console.log(urlWithParameters);
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
				console.log(urlWithParameters);
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
		display : none;
		}
	</style>
</head>
<body>
	<c:set var="registration_success" value="${current_page_attributes['registration_success'] }"></c:set>
	<header><fmt:message key="greeting"/></header>
	<jsp:include page="jsp/included pages/navigation menu.jsp"></jsp:include>
	<section>
		<main>
			<c:if test="${not empty registration_success }"> Registration success</c:if>
			<article>Article</article>
			<article>Article</article>
			<article>Article</article>
			<article>Article</article>

			<button id="composition_rating">compositions rating</button>
			<button id="singer_rating">singer rating</button>
			<button id="genre_rating">genres rating</button>
			<section id="ratings">

			</section>
			<button id='prev' class="navigationAjax">Prev page</button>
			<button id='next' class="navigationAjax">Next page</button>
			<input type="text" id="url" hidden>
		</main>
	</section>
	<aside>Aside</aside>
	<footer>Footer</footer>
</body>
</html>