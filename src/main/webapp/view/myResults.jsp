<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 20.06.2025
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
  <title>Моя статистика</title></head>
<body>
<h2>Мои результаты</h2>
<c:if test="${empty results}">
  <p>У вас ещё нет пройденных тестов.</p>
</c:if>
<c:if test="${not empty results}">
  <table border="1">
    <tr>
      <th>Тест</th>
      <th>Дата</th>
      <th>Действие</th>
    </tr>
    <c:forEach var="r" items="${results}" varStatus="st">
      <tr>
        <td><c:out value="${r.testTitle}" /></td>

        <td>
          <fmt:formatDate value="${r.timestamp}"
                          pattern="yyyy-MM-dd HH:mm" />
        </td>

        <td>
          <a href="${pageContext.request.contextPath}/viewResult?index=${st.index}">
            Посмотреть результат
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
</c:if>

<p>
  <a href="${pageContext.request.contextPath}/view/main.jsp">Вернуться на главную</a>
  |
  <a href="${pageContext.request.contextPath}/listTests">К списку тестов</a>
</p>


</body>
</html>
