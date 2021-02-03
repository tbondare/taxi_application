<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add driver to car</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/cars/driver/add">
    Provide car id <input type="number" name="carId">
    Provide driver id <input type="number" name="driverId">

    <button type="submit">Creation</button>
</form>
</body>
</html>
