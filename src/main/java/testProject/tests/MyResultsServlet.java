package testProject.tests;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/myResults")
public class MyResultsServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String username = req.getParameter("username");

        ResultRepository resultRepository = new ResultRepository(req.getServletContext());
        List<TestResult> all = resultRepository.findAll();
        List<TestResult> mine = all.stream()
                .filter(r -> r.getUsername().equals(username))
                .collect(Collectors.toList());
        req.setAttribute("results", mine);
        req.getRequestDispatcher("/view/myResults.jsp").forward(req, resp);
    }
}
