<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 01.06.2025
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <h2>Вход</h2>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    <fieldset>
        <label>Введите логин: <input type="text" name="username" /></label><br/>
        <label>Введите пароль: <input type="password" name="password" /></label><br/>
        <c:if test="${not empty error}">
            <p style="color: red">${error}</p>
        </c:if>
        <button type="submit">Войти</button>
    </fieldset>

</form>


</body>
</html>

