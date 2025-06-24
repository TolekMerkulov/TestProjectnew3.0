package testProject.tests.servlet.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.tests.model.Test;
import testProject.tests.repository.TestRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/listTests")
public class ListTestsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        TestRepository testRepository = new TestRepository(req.getServletContext());
        try {
            List<Test> tests = testRepository.findAllTests();

            req.setAttribute("tests", tests);
            req.getRequestDispatcher("/view/listTests.jsp").forward(req, resp);

        } catch (IOException e) {
            throw new ServletException("Ошибка при чтении данных ", e);

        }
    }
}
