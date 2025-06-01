package testProject.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import testProject.model.User;
import testProject.model.UserList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    //Задачи:
    //
    //читать users.json
    //
    //добавлять нового пользователя
    //
    //находить пользователя по логину и паролю
    //
    //генерировать новый ID
    private static final String USERS_FILE_PATH = "data/users/users.json";
    private final Map<String, User> usersByUsername = new HashMap<>();

    public UserRepository() {
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = new FileInputStream(USERS_FILE_PATH)) {
            UserList wrapper = mapper.readValue(is, UserList.class);
            for (User user : wrapper.getUsers()) {
                usersByUsername.put(user.getUsername(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findByUsername(String username) {
        return usersByUsername.get(username);
    }

    public void addUser(User user) {
        usersByUsername.put(user.getUsername(), user);

    }

    public void saveAllUsers() {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = new ArrayList<>(usersByUsername.values());
        UserList wrapper = new UserList();
        wrapper.setUsers(users);

        try (FileOutputStream out = new FileOutputStream(USERS_FILE_PATH)) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, wrapper);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
