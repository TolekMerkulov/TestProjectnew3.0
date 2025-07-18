<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 18.07.2025
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>Новый тест</title></head><body>
<h2>Создать новый тест</h2>
<form method="get" action="${pageContext.request.contextPath}/admin/tests/count">
  <label>Сколько вопросов?<br/>
    <input type="number" name="count" min="1" max="50" value="5" required/>
  </label><br/><br/>
  <button type="submit">Дальше</button>
</form>
<p><a href="${pageContext.request.contextPath}/admin/tests">← Назад к списку</a></p>
</body></html>
