<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 15.06.2025
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="userRole" value="${sessionScope.user.role.name()}" />


<html>
<head>
    <title>Список тестов</title>
</head>
<body>
<h2>Список тестов</h2>
<c:if test="${not empty error}">
    <div class="alert">${error}</div>
</c:if>
<c:if test="${userRole == 'ADMIN'}">
    <p>
        <a href="${pageContext.request.contextPath}/admin/tests/count">
            Создать новый тест
        </a>
    </p>
</c:if>

<ul>
    <c:forEach var="t" items="${tests}">
        <li>
            <!-- Ссылка на прохождение для всех -->
            <a href="${pageContext.request.contextPath}/startTest?testId=${t.id}">
                <c:out value="${t.title}" />
            </a>

            <!-- Если админ — показываем доп. ссылки -->
            <c:if test="${userRole == 'ADMIN'}">
                &nbsp;|&nbsp;
                <a href="${pageContext.request.contextPath}/admin/tests/edit?testId=${t.id}">
                    Редактировать
                </a>
                &nbsp;|&nbsp;
                <a href="${pageContext.request.contextPath}/admin/tests/delete?testId=${t.id}">
                    Удалить
                </a>
            </c:if>
        </li>
    </c:forEach>
</ul>
<p>
    <a href="${pageContext.request.contextPath}/view/main.jsp">Вернуться на главную</a>
</p>


</body>
</html>
