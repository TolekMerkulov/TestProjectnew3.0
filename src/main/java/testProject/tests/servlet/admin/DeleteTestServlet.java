package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.tests.repository.TestRepository;

import java.io.IOException;

@WebServlet("/admin/tests/delete")
public class DeleteTestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("testId");
        if (id != null) {
            new TestRepository(getServletContext()).deleteById(id);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/tests");
    }
}
