<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create car</title>
</head>
<body>
<h1>Create car</h1>

<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Please provide car model: <input type="text" name="model">
    Please provide car manufacturerId: <input type="number" name="manufacturerId">

    <button type="submit">create</button>
</form>
</body>
</html>
