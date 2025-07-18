package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.tests.model.Question;
import testProject.tests.model.Test;
import testProject.tests.model.TestResult;
import testProject.tests.repository.TestRepository;

import java.io.IOException;
import java.util.ArrayList;
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int count = Integer.parseInt(req.getParameter("count"));
        String title = req.getParameter("title");

        List<Question> qs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String text = req.getParameter("questions[" + i + "].text");
            List<String> opts = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                opts.add(req.getParameter("questions[" + i + "].options[" + j + "]"));
            }
            int correct = Integer.parseInt(
                    req.getParameter("questions[" + i + "].correctIndex"));
            qs.add(new Question(text, opts, correct));
        }

        String id = UUID.randomUUID().toString();
        Test test = new Test(id, title, qs);
        new TestRepository(getServletContext()).save(test);

        resp.sendRedirect(req.getContextPath() + "/admin/tests");
    }

}
