package testProject.tests.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.model.User;
import testProject.tests.model.TestResult;
import testProject.tests.repository.ResultRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/viewResult")
public class ViewResultServlet extends HttpServlet {

    @Override
    public void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("resultId");
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String username = ((User)req.getSession().getAttribute("user")).getUsername();
        ResultRepository repository = new ResultRepository(req.getServletContext());

        List<TestResult> mine = repository.findByUser(username);
        TestResult found = repository.getUserResultById(id,mine);

        req.setAttribute("result", found);
        req.getRequestDispatcher("/view/result.jsp").forward(req, resp);
    }
}
