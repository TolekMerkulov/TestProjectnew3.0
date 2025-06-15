<%@ page import="testProject.model.User" %><%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 02.06.2025
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная</title>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("/public/login.jsp");
        return;
    }

    String role = user.getRole().name();
    String username = user.getUsername();
%>
<h2>Добро пожаловать, <%= username %>!</h2>

<% if ("ADMIN".equals(role)) { %>
<a href="startTest.jsp">Пройти тест</a><br>
<a href="myResults.jsp">Мои результаты</a>
<a href="createTest.jsp">Создать тест</a><br>
<a href="editTests.jsp">Редактировать тесты</a><br>
<a href="viewStats.jsp">Посмотреть статистику</a>
<% } else if ("USER".equals(role)) { %>
<a href="listTests.jsp">Пройти тест</a><br>
<a href="myResults.jsp">Мои результаты</a>
<% } %>

<a href="${pageContext.request.contextPath}/logout">Выйти</a>
</body>
</html>


