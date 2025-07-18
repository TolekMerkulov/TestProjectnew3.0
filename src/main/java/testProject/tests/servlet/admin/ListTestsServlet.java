package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.tests.model.Test;
import testProject.tests.repository.TestRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/tests")
public class ListTestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // получаем все тесты
        TestRepository repo = new TestRepository(req.getServletContext());
        List<Test> tests = repo.findAllTests();

        // кладём список и параметр msg (если есть) в request
        req.setAttribute("tests", tests);
        req.setAttribute("msg", req.getParameter("msg"));

        // диспачим на JSP
        req.getRequestDispatcher("/view/admin/tests.jsp")
                .forward(req, resp);
    }
}