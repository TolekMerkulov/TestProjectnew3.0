<%--
  Created by IntelliJ IDEA.
  User: Tolik
  Date: 16.06.2025
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>${test.title}</h2>
<input type="hidden" name="testId" value="${test.id}" />
<form method="post" action="${pageContext.request.contextPath}/submitTest">
    <c:forEach var="q" items="${test.questions}" varStatus="st">
        <div>
            <p>Вопрос ${st.index + 1}: ${q.text}</p>
            <c:forEach var="opt" items="${q.options}" varStatus="os">
                <input type="radio"
                       name="answer${st.index}"
                       value="${os.index}"
                       id="q${st.index}_o${os.index}"
                    ${os.index == 0 ? "required" : ""} />
                <label for="q${st.index}_o${os.index}">${opt}</label><br/>
            </c:forEach>
        </div>
    </c:forEach>
    <button type="submit">Отправить ответы</button>
</form>
</body>
</html>