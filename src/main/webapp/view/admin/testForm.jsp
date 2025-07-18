<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 18.07.2025
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html><head><title>
  <c:choose>
    <c:when test="${mode=='create'}">Создание теста</c:when>
    <c:otherwise>Редактирование теста</c:otherwise>
  </c:choose>
</title></head><body>
<h2>
  <c:choose>
    <c:when test="${mode=='create'}">Создать новый тест</c:when>
    <c:otherwise>Редактировать тест</c:otherwise>
  </c:choose>
</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/tests/${mode=='create' ? 'create' : 'edit'}">
  <input type="hidden" name="count" value="${count}"/>
  <input type="hidden" name="testId"  value="${test.id}"/>
  <label>Заголовок:<br/>
    <input type="text" name="title"
           value="${test.title}" required/>
  </label><br/><br/>

  <c:forEach var="i" begin="0" end="${count-1}">
    <fieldset>
      <legend>Вопрос ${i+1}</legend>
      <label>Текст вопроса:<br/>
        <input type="text"
               name="questions[${i}].text"
               value="${test.questions[i].text}"
               required/>
      </label><br/>
      <c:forEach var="j" begin="0" end="3">
        <label>Вариант ${j+1}:
          <input type="text"
                 name="questions[${i}].options[${j}]"
                 value="${test.questions[i].options[j]}"
                 required/>
        </label><br/>
      </c:forEach>
      <label>Правильный (0–3):
        <input type="number"
               name="questions[${i}].correctIndex"
               min="0" max="3"
               value="${test.questions[i].correctIndex}"
               required/>
      </label>
    </fieldset><br/>
  </c:forEach>

  <button type="submit">
    <c:out value="${mode=='create' ? 'Создать' : 'Сохранить'}"/>
  </button>
</form>

<p><a href="${pageContext.request.contextPath}/admin/tests">← Назад к списку</a></p>
</body></html>
