<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 15.06.2025
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Список тестов</title>
</head>
<body>
<c:forEach var="t" items="${tests}">
    <a href="${pageContext.request.contextPath}/startTest?id=${t.id}">
            ${t.title}
    </a><br/>
</c:forEach>

</body>
</html>
