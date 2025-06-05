package testProject.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import testProject.model.User;
import testProject.model.UserList;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String USERS_FILE = "data/users.json";

    public List<User> loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Path path = resolvePath();
            if (Files.exists(path)) {
                try (InputStream is = Files.newInputStream(path)) {
                    UserList wrapper = mapper.readValue(is, UserList.class);
                    return wrapper.getUsers();
                }
            } else {
                System.out.println("Файл users.json не найден — будет создан при первой регистрации");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке users.json: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void saveUsers(List<User> users) {
        ObjectMapper mapper = new ObjectMapper();
        UserList wrapper = new UserList();
        wrapper.setUsers(users);

        try {
            Path path = resolvePath();
            Files.createDirectories(path.getParent());
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
        URL resource = getClass().getClassLoader().getResource(USERS_FILE);
        if (resource != null && resource.getProtocol().equals("file")) {
            return Paths.get(resource.toURI());
        }
        return Paths.get("src/main/resources", USERS_FILE).toAbsolutePath();
    }
}
