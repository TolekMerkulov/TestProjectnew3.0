package testProject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import testProject.DAO.UserDao;
import testProject.model.User;
import testProject.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String VIEW = "public/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Показываем форму логина
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserRepository repo = new UserRepository(getServletContext());
        try {
            // MOD: пытаемся аутентифицировать
            Optional<User> userOpt = repo.authenticate(username, password);
            if (userOpt.isPresent()) {
                // успешный логин — сохраняем в сессии и переходим на main.jsp
                req.getSession().setAttribute("user", userOpt.get());
                resp.sendRedirect(req.getContextPath() + "/view/main.jsp");
            } else {
                // неверные данные — возвращаемся на логин с ошибкой
                req.setAttribute("error", "Неверное имя или пароль");
                req.getRequestDispatcher(VIEW).forward(req, resp);
            }
        } catch (IOException e) {
            throw new ServletException("Ошибка при чтении данных пользователей", e);
        }
    }
}
