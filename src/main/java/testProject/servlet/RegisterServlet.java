package testProject.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import testProject.DAO.UserDao;
import testProject.model.Role;
import testProject.model.User;
import testProject.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String VIEW = "view/register.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Показываем форму регистрации
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserRepository repo = new UserRepository(getServletContext());
        try {
            // MOD: бизнес-логика регистрации (проверка дубликатов, генерация UUID)
            repo.register(username, password);
            // при успешной регистрации — переходим на страницу логина
            resp.sendRedirect(req.getContextPath() + "/view/login.jsp");
        } catch (IllegalArgumentException e) {
            // MOD: ловим ошибку «пользователь уже существует»
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(VIEW).forward(req, resp);
        } catch (IOException e) {
            // MOD: критическая ошибка работы с файлом
            throw new ServletException("Ошибка при сохранении пользователя", e);
        }
    }
}

