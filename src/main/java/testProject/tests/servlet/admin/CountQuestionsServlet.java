package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/tests/count")
public class CountQuestionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String cnt = req.getParameter("count");
        if (cnt == null) {
            // Показываем страницу с выбором числа вопросов
            req.getRequestDispatcher("/view/admin/countQuestions.jsp")
                    .forward(req, resp);
        } else {
            // Передаём в форму создания
            int count = Integer.parseInt(cnt);
            req.setAttribute("mode", "create");
            req.setAttribute("count", count);
            req.getRequestDispatcher("/view/admin/testForm.jsp")
                    .forward(req, resp);
        }
    }
}
