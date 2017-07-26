
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a> </h3>

<h2>Meals</h2>
<table border="3">
    <thead>
    <tr>
    <th>Дата/Время</th>
    <th>Описание</th>
    <th>Калории</th>
    </tr>
    </thead>
    <tbody>
 <c:forEach var="mealWithExceed" items="${mealsListWithExceeds}">
     <c:set var="color" value="green"></c:set>
     <c:if test="${mealWithExceed.exceed}">
        <c:set var="color" value="red"></c:set>
     </c:if>
     <tr style="color: ${color}">
         <td>${fn:replace(mealWithExceed.dateTime, 'T', ' ')}</td>
         <td>${mealWithExceed.description}</td>
         <td>${mealWithExceed.calories}</td>
     </tr>
 </c:forEach>
    </tbody>
</table>
</body>
</html>
