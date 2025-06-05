package testProject.repository;

import testProject.DAO.UserDao;
import testProject.model.User;

import java.util.*;


public class UserRepository {
    private final Map<String, User> usersByUsername = new HashMap<>();
    private final UserDao userDao = new UserDao();

    public UserRepository() {
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        List<User> users = userDao.loadUsers();
        for (User user : users) {
            usersByUsername.put(user.getUsername(), user);
        }
    }

    public void addUser(User user) {
        usersByUsername.put(user.getUsername(), user);
        saveAllUsers(); // Сохраняем после добавления
    }

    public User findByUsername(String username) {
        return usersByUsername.get(username);
    }

    public Collection<User> findAllUsers() {
        return usersByUsername.values();
    }

    public void saveAllUsers() {
        userDao.saveUsers(new ArrayList<>(usersByUsername.values()));
    }
}
