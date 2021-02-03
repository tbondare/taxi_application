<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authentication driver</title>
</head>
<body>
<h1>Authentication driver</h1>

<h2 style="color: darkred">${errorMsg}</h2>

<a href="${pageContext.request.contextPath}/drivers/create">Create driver </a><br>

<form method="post" action="${pageContext.request.contextPath}/drivers/authentication">
    Please provide your login: <input type="text" name="login"><br>
    Please provide your password: <input type="password" name="pwd">
    <button type="submit">Login</button>
</form>
</body>
</html>
