<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 18.07.2025
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Статистика для ${username}</title></head>
<body>
<h1>Статистика пользователя «${username}»</h1>
<p><a href="${pageContext.request.contextPath}/admin/users">← Назад к списку пользователей</a></p>
<table border="1" cellpadding="5" cellspacing="0">
  <tr><th>Тест</th><th>Дата</th><th>Правильно</th><th>Всего</th></tr>
  <c:forEach var="r" items="${results}">
    <tr>
      <td>${r.testId}</td>
      <td>${r.timestamp}</td>
      <td>${r.correctCount}</td>
      <td>${r.totalCount}</td>
    </tr>
  </c:forEach>
</table>
<c:if test="${empty results}">
  <p><i>У пользователя нет пройденных тестов.</i></p>
</c:if>
</body>
</html>
