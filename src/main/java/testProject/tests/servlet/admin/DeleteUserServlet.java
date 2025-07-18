package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.repository.UserRepository;
import testProject.store.JsonDataStore;
import testProject.model.User;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/admin/users/delete")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");

        UserRepository repo = new UserRepository(getServletContext());
        List<User> all = repo.findAll();

        all.removeIf(u -> u.getUsername().equals(username));
        repo.getDao().saveAll(all);

        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
