<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 01.06.2025
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <h2>Регистрация</h2>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/register">
  <label>Логин: <input type="text" name="username" /></label><br/>
  <label>Пароль: <input type="password" name="password" /></label><br/>
  <button type="submit">Зарегистрироваться</button>
</form>

</body>
</html>
