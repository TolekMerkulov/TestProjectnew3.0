package testProject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import testProject.model.User;
import testProject.repository.UserRepository;

import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepository();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = userRepository.findByUsername(username);

        if (user == null) {
            req.setAttribute("error", "User doesn't exist");
            req.getRequestDispatcher("/view/login.jsp").forward(req,resp);
            return;
        }

        if (user.getPassword().equals(password)) {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/view/main.jsp");

        } else {
            req.setAttribute("error", "Password doesn't work");
            req.getRequestDispatcher("/view/login.jsp").forward(req,resp);
        }
        return;


    }

}
