<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create driver</title>
</head>
<body>
<h1>Create driver</h1>

<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Please provide your name: <input type="text" name="name"><br>
    Please provide your license number: <input type="number" name="number"><br>
    Please provide your login: <input type="text" name="login"><br>
    Please provide your password: <input type="password" name="pwd">
    <button type="submit">create</button>
</form>
</body>
</html>
