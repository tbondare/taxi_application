<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create manufacturer</title>
</head>
<body>
<h1>Create manufacturer</h1>

<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    Please provide manufacturer's name: <input type="text" name="name">
    Please provide manufacturer's country: <input type="text" name="country">
    <button type="submit">create</button>
</form>

</body>
</html>
