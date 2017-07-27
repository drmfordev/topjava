<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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
    <th colspan="2">Действие</th>
    </tr>
    </thead>
    <tbody>
 <c:forEach var="meal" items="${meals}">
     <c:set var="color" value="green"></c:set>
     <c:if test="${meal.exceed}">
        <c:set var="color" value="red"></c:set>
     </c:if>
     <tr style="color: ${color}">

             <c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}" />

             <td>${cleanedDateTime}</td>
         <td>${meal.description}</td>
         <td>${meal.calories}</td>
         <td><a href="meals?action=edit&mealId=${meal.id}">Update</a></td>
         <td><a href="meals?action=delete&mealId=${meal.id}">Delete</a></td>
     </tr>
 </c:forEach>
    </tbody
</table>
<p><a href="meals?action=insert">Add meal</a></p>
</body>
</html>
