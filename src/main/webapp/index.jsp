<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h2>Добро пожаловать в систему тестирования</h2>
<a href="${pageContext.request.contextPath}/view/login.jsp">Войти</a>
<a href="${pageContext.request.contextPath}/view/register.jsp">Зарегистрироваться</a>
</body>
</html>
