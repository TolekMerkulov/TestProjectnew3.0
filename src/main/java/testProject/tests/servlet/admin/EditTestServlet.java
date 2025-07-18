package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.tests.model.Test;
import testProject.tests.repository.TestRepository;

import java.io.IOException;

@WebServlet("/admin/tests/edit")
public class EditTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String testId = req.getParameter("testId");
        if (testId == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/tests");
            return;
        }
        TestRepository repository = new TestRepository(req.getServletContext());
        Test test = repository.findById(testId);
        if (test == null) {
            throw new ServletException("Test not found");
        }
        req.setAttribute("mode", "edit");
        req.setAttribute("test", test);
        req.getRequestDispatcher("/view/admin/testForm.jsp")
                .forward(req, resp);
    }
}
