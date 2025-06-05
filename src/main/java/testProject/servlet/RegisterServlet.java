package testProject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.model.Role;
import testProject.model.User;
import testProject.repository.UserRepository;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (userRepository.findByUsername(username) != null) {
            req.setAttribute("error", "Username is already taken");
            req.getRequestDispatcher("/view/register.jsp").forward(req,resp);
            return;
        }
        String id = UUID.randomUUID().toString();
        User newUser = new User(id, username, password, Role.USER);

        userRepository.addUser(newUser);
        userRepository.saveAllUsers();

        resp.sendRedirect(req.getContextPath() + "/view/login.jsp");
    }
}

