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
<head><title>Управление пользователями</title></head>
<body>
<h1>Пользователи</h1>
<table border="1" cellpadding="5" cellspacing="0">
    <tr><th>Логин</th><th>Роль</th><th>Действия</th></tr>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.username}</td>
            <td>${u.role}</td>
            <td>
                <!-- Статистика -->
                <a href="${pageContext.request.contextPath}/admin/users/stats?username=${u.username}">
                    Посмотреть статистику
                </a>
                |
                <!-- Переключить роль -->
                <form method="post" action="${pageContext.request.contextPath}/admin/users/role" style="display:inline">
                    <input type="hidden" name="username" value="${u.username}"/>
                    <c:choose>
                        <c:when test="${u.role=='ADMIN'}">
                            <button name="makeAdmin" value="">Сделать USER</button>
                        </c:when>
                        <c:otherwise>
                            <button name="makeAdmin" value="true">Сделать ADMIN</button>
                        </c:otherwise>
                    </c:choose>
                </form>
                |
                <!-- Удалить -->
                <form method="post" action="${pageContext.request.contextPath}/admin/users/delete" style="display:inline"
                      onsubmit="return confirm('Удалить пользователя ${u.username}?');">
                    <input type="hidden" name="username" value="${u.username}"/>
                    <button>Удалить</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
