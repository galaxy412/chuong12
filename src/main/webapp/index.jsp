<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/main.css" type="text/css"/>
</head>
<body>

<c:if test="${empty sqlStatement}">
    <c:set var="sqlStatement" value="select * from User" />
</c:if>

<h1>The SQL Gateway</h1>
<p>Enter an SQL statement and click the Execute button.</p>
<p><b>SQL statement:</b></p>
<form action="${pageContext.request.contextPath}/sqlGateway" method="post">
    <textarea name="sqlStatement" cols="60" rows="8"><c:out value="${sqlStatement}" /></textarea>
    <input type="submit" value="Execute">
</form>

<p><b>SQL result:</b></p>
<!-- If sqlResult contains HTML (table), set escapeXml="false"; otherwise remove that attribute -->
<c:out value="${sqlResult}" escapeXml="false" />

</body>
</html>
