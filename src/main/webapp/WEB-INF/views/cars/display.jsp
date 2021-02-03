<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Display current driver's cars</title>
</head>
<body>
<h1>All current cars page</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>licenseNumber</th>
    </tr>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>
                <c:out value="${car.id}"/>
            </td>
            <td>
                <c:out value="${car.model}"/>
            </td>
            <td>
                <c:out value="${car.manufacturer}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
