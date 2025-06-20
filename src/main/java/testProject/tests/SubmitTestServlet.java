package testProject.tests;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/submitTest")
public class SubmitTestServlet extends HttpServlet {
    private final TestService testService = new TestService();




    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        TestRepository testRepository = new TestRepository(req.getServletContext());
        Test test = testRepository.findById(req.getParameter("testId"));
        int questionsCount = test.getQuestions().size();
        Map<Integer, Integer> answersMap = new HashMap<Integer, Integer>();

        for (int i = 0; i < questionsCount; i++) {
            String answer = req.getParameter("answer" + i);
            Integer userAnswer = Integer.valueOf(answer);
            answersMap.put(i, userAnswer);
        }
        User user = (User) req.getSession().getAttribute("user");
        String username = user.getUsername();

        TestResult result = testService.evaluate(test, answersMap,username);
        ctx.log("Saving result for user=" + username);
        ResultRepository resultRepository = new ResultRepository(req.getServletContext());
        resultRepository.save(result);
        req.setAttribute("result", result);
        req.getRequestDispatcher("/view/result.jsp").forward(req, resp);

    }

}
