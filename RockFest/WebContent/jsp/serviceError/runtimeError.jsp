<%@ page isErrorPage="true" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Error page</title>
</head>
<body>
	<header>Error</header>
	<jsp:include page="../included pages/navigation menu.jsp"></jsp:include>
	<main class="common_background">
		Request from ${pageContext.errorData.requestURI} is failed
		<br/>
		Servlet name: ${pageContext.errorData.servletName}
		<br/>
		Status code: ${pageContext.errorData.statusCode}
		<br/>
		Exception: ${pageContext.exception}
		<br/>
		Message from exception: ${pageContext.exception.message}
	</main>
</body>
</html>