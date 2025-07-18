<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 21.06.2025
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Управление тестами</title>
</head>
<body>
<p>
    <a href="${pageContext.request.contextPath}/view/main.jsp">← Вернуться на главную</a>
    |
    <a href="${pageContext.request.contextPath}/admin/tests">↻ Перезагрузить список тестов</a>
    |
    <a href="${pageContext.request.contextPath}/admin/tests/count">＋ Создать новый тест</a>
</p>

<c:if test="${param.msg == 'updated'}">
    <div style="padding:10px; background:#e0ffe0; border:1px solid #0a0; margin-bottom:15px;">
        ✅ Изменения сохранены!
    </div>
</c:if>

<h1>Список тестов</h1>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Действия (ADMIN)</th>
    </tr>
    <c:forEach var="test" items="${tests}">
        <tr>
            <td>${test.id}</td>
            <td>${test.title}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/tests/edit?testId=${test.id}">Редактировать</a>
                |
                <a href="${pageContext.request.contextPath}/admin/tests/delete?testId=${test.id}"
                   onclick="return confirm('Удалить тест «${test.title}»?');">
                    Удалить
                </a>
                |
                <a href="${pageContext.request.contextPath}/startTest?testId=${test.id}">
                    Пройти тест
                </a>
            </td>
        </tr>
    </c:forEach>
</table>

<c:if test="${empty tests}">
    <p><i>Тесты ещё не созданы.</i></p>
</c:if>
</body>
</html>