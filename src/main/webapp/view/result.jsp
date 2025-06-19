<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 18.06.2025
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>${r.testTitle}</title>
</head>
<body>
<c:set var="r" value="${result}" />
<h2>${r.testTitle}</h2>
<c:forEach var="qr" items="${r.questionResults}" varStatus="st">
    <div>
        <p>Вопрос ${st.index + 1}: ${qr.questionText}</p>
        <p>Ваш ответ: ${qr.userAnswerText} —
            <c:choose>
                <c:when test="${qr.correct}">Верно</c:when>
                <c:otherwise>Неверно</c:otherwise>
            </c:choose>
        </p>
        <p>Правильный ответ: ${qr.correctAnswerText}</p>
    </div>
    <hr/>
</c:forEach>
<p>Всего вопросов: ${r.totalQuestions}</p>
<p>Правильных ответов: ${r.correctCount}</p>

</body>
</html>
