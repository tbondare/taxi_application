<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Taxi Service</title>
</head>
<body>
    <h1>Taxi Service</h1>

    <a href="${pageContext.request.contextPath}/drivers/create">Create driver </a><br>
    <a href="${pageContext.request.contextPath}/drivers/authentication">Authentication driver </a><br>
    <a href="${pageContext.request.contextPath}/drivers">Display drivers </a><br>
    <a href="${pageContext.request.contextPath}/manufacturers/create">Create manufacturer </a><br>
    <a href="${pageContext.request.contextPath}/cars/create">Create car </a><br>
    <a href="${pageContext.request.contextPath}/cars/driver/add">Add driver to car </a><br>
    <a href="${pageContext.request.contextPath}/cars">Get current cars </a><br>
</body>
</html>
