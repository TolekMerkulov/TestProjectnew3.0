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
<a href="${pageContext.request.contextPath}/listTests">Пройти тест</a>
<a href="${pageContext.request.contextPath}/myResults">Мои результаты</a>
<a href="viewStats.jsp">Посмотреть статистику</a>
<% } else if ("USER".equals(role)) { %>
<a href="${pageContext.request.contextPath}/listTests">Пройти тест</a>
<a href="${pageContext.request.contextPath}/myResults">Мои результаты</a>
<% } %>

<a href="${pageContext.request.contextPath}/logout">Выйти</a>
</body>
</html>


