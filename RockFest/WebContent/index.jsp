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
			<button id="composition_rating">composition rating</button>
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