package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.tests.model.Test;
import testProject.tests.model.TestResult;
import testProject.tests.repository.TestRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/admin/tests/new")
public class NewTestServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        req.setAttribute("mode", "create");

        req.getRequestDispatcher("/view/admin/testForm.jsp")
                .forward(req, resp);
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        String testID = UUID.randomUUID().toString();
        String title = req.getParameter("title");
        String questionText = req.getParameter("questionText");
        List<String> options = req.getParameterValues("options");
        String answerText = req.getParameter("answerText");
        String rightAnswer = req.getParameter("rightAnswer");



    }

}
