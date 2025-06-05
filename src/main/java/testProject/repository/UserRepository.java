package testProject.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import testProject.model.User;
import testProject.model.UserList;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private static final String USERS_FILE = "data/users.json";
    private final Map<String, User> usersByUsername = new HashMap<>();


    public UserRepository() {
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Path path = resolvePath();
            if (Files.exists(path)) {
                try (InputStream is = Files.newInputStream(path)) {
                    UserList wrapper = mapper.readValue(is, UserList.class);
                    for (User user : wrapper.getUsers()) {
                        usersByUsername.put(user.getUsername(), user);
                    }
                }
            } else {
                System.out.println("Файл users.json не найден — будет создан при первой регистрации");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке users.json: " + e.getMessage());
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

        try {
            Path path = resolvePath();

            // Создать директорию, если её нет
            Files.createDirectories(path.getParent());

            // Создать файл и записать
            try (OutputStream os = Files.newOutputStream(path)) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(os, wrapper);
            }

            System.out.println("Файл users.json успешно обновлён по пути: " + path);

        } catch (Exception e) {
            System.err.println("Ошибка при сохранении users.json: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Path resolvePath() throws URISyntaxException, IOException {
        // Чтение файла как ресурса из classpath
        URL resource = getClass().getClassLoader().getResource(USERS_FILE);

        if (resource != null && resource.getProtocol().equals("file")) {
            return Paths.get(resource.toURI());
        }

        // fallback: проект запускается из IDE — использовать путь к resources
        Path fallback = Paths.get("src/main/resources", USERS_FILE);
        return fallback.toAbsolutePath();
    }
}
