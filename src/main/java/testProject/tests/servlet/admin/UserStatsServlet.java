package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.store.JsonDataStore;
import testProject.tests.model.TestResult;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/admin/users/stats")
public class UserStatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");

        String dataFolder = req.getServletContext().getRealPath(
                req.getServletContext().getInitParameter("dataFolder")
        );
        JsonDataStore store = JsonDataStore.getInstance(dataFolder);

        List<TestResult> all = store.getAllResults();
        List<TestResult> userResults = all.stream()
                .filter(r -> r.getUsername().equals(username))
                .collect(Collectors.toList());

        req.setAttribute("username", username);
        req.setAttribute("results", userResults);
        req.getRequestDispatcher("/view/admin/userStats.jsp")
                .forward(req, resp);
    }
}