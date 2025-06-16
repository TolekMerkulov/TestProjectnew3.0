package testProject.tests;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/startTest")
public class StartTestServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        String testId = req.getParameter("testId");
        if (testId == null) {
            req.setAttribute("error", "Такого теста не существует, выберите другой");
            req.getRequestDispatcher("/view/listTests.jsp")
                    .forward(req, resp);
            return;
        }
        TestRepository testRepository = new TestRepository(req.getServletContext());
        try {
            Test test = testRepository.findById(testId);
            req.setAttribute("test", test);
            req.getRequestDispatcher("/view/Test.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new ServletException("Error in doGet", e);
        }
    }
}
