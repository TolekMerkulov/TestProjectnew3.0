package testProject.repository;

import jakarta.servlet.ServletContext;
import testProject.DAO.UserDao;
import testProject.model.Role;
import testProject.model.User;

import java.io.IOException;
import java.util.*;


public class UserRepository {
    private final UserDao dao;

    public UserRepository(ServletContext ctx) {
        this.dao = new UserDao(ctx);
    }

    /** Возвращает всех пользователей */
    public List<User> findAll() throws IOException {
        return dao.findAll();
    }

    /** Ищет пользователя по username */
    public Optional<User> findByUsername(String username) throws IOException {
        return findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    /** Регистрирует нового пользователя (с проверкой) */
    public void register(String username, String password) throws IOException {
        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username(username)
                .password(password)
                .role(Role.USER)
                .build();
        dao.add(newUser);
    }

    /** Проверяет логин-пароль */
    public Optional<User> authenticate(String username, String password) throws IOException {
        return findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }
}

