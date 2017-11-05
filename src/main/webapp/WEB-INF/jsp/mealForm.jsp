<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="meal.title"/> </title>
    <link rel="stylesheet" href="resources/css/style.css">
</head>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><a href="index.html"><spring:message code="meal.home"/> </a></h3>
    <h2> <spring:message code="meal.message.${message}"/> </h2>
    <hr>
    <jsp:useBean id="meal" class="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="${pageContext.request.contextPath}/meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.datetime"/> </dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.form.description"/> </dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.form.calories"/> </dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit"><spring:message code="meal.form.save"/> </button>
        <button onclick="window.history.back()" type="button"><spring:message code="meal.form.cancel"/> </button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
