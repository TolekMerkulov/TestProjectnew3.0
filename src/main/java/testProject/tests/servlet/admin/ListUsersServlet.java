package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.model.User;
import testProject.repository.UserRepository;
import testProject.store.JsonDataStore;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users")
public class ListUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserRepository repo = new UserRepository(getServletContext());
        List<User> users = repo.findAll();

        req.setAttribute("users", users);
        req.getRequestDispatcher("/view/admin/users.jsp")
                .forward(req, resp);
    }
}