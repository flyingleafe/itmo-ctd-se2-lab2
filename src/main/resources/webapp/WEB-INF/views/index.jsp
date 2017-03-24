<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Search aggregator</title>
</head>
<body>

<h2>My Super Search</h2>

<form method="GET" action="/search">
    <input name="query" placeholder="Enter search query..." value="${query}"/>
    <input type="submit" value="search">
</form>

<c:choose>
    <c:when test="${timeout}">
        <p>Request timed out</p>
    </c:when>
    <c:otherwise>
        <c:if test="${results.isEmpty()}">
            <p>No results found</p>
        </c:if>
        <c:forEach var="item" items="${results}">
        <p>
            <h4><a href="${item.getUrl()}" target="_blank">${item.getTitle()}</a>&nbsp;<i>${item.getSystem()}</i></h4>
            <span>${item.getContents()}</span>
        </p>
        </c:forEach>
    </c:otherwise>
</c:choose>
</body>
</html>
