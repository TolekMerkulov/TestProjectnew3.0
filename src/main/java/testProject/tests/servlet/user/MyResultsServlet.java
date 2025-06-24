package testProject.tests.servlet.user;

import jakarta.servlet.ServletContext;
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

@WebServlet("/myResults")
public class MyResultsServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String username = ((User)req.getSession().getAttribute("user")).getUsername();
        System.out.println(">>> DEBUG myResults: session username = " + username);
        ServletContext ctx = getServletContext();

        ResultRepository repository = new ResultRepository(req.getServletContext());
        List<TestResult> all = repository.findAll();
        System.out.println(">>> DEBUG myResults: total results = " + all.size());


        List<TestResult> mine = repository.findByUser(username);

        System.out.println(">>> DEBUG myResults: filtered results = " + mine.size());

        req.setAttribute("results", mine);
        req.getRequestDispatcher("/view/myResults.jsp").forward(req, resp);
    }
}
