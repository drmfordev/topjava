<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add meal</title>
</head>
<body>

  <form method="post" action="meals" name="frmAddMeal">

      <p>Meal ID:</p> <input readonly="readonly" name="mealId" value="${meal.id}"> <br>
      <p>Description:</p> <input   name="description" value="${meal.description}">  <br>
      <p>Calories:</p> <input   name="calories" value="${meal.calories}">  <br>
      <p>DateTime:</p> <input type="datetime"   name="dateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}">  <br>

        <br/>
      <input type="submit" value="Save">
  </form>

</body>
</html>
