package testProject.tests.servlet.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.model.User;
import testProject.model.Role;
import testProject.repository.UserRepository;
import testProject.store.JsonDataStore;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users/role")
public class ChangeRoleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username     = req.getParameter("username");
        String makeAdminVal = req.getParameter("makeAdmin");

        boolean makeAdmin = Boolean.parseBoolean(makeAdminVal);

        UserRepository repo = new UserRepository(getServletContext());
        List<User> all = repo.findAll();
        for (User u : all) {
            if (u.getUsername().equals(username)) {
                u.setRole(makeAdmin ? Role.ADMIN : Role.USER);
                break;
            }
        }
        repo.getDao().saveAll(all);

        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}
