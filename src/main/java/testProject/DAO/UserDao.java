package testProject.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
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
    private final Path realFilePath;

    public UserDao(ServletContext context) {
        this.realFilePath = Paths.get(context.getRealPath("/WEB-INF/data/users.json"));
        ensureFileExistsFromResources();
    }

    public List<User> loadUsers() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = Files.newInputStream(realFilePath)) {
            return mapper.readValue(is, UserList.class).getUsers();
        } catch (Exception e) {
            System.err.println("Ошибка при чтении users.json: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveUsers(List<User> users) {
        ObjectMapper mapper = new ObjectMapper();
        try (OutputStream os = Files.newOutputStream(realFilePath)) {
            UserList wrapper = new UserList();
            wrapper.setUsers(users);
            mapper.writerWithDefaultPrettyPrinter().writeValue(os, wrapper);
            System.out.println("users.json сохранён: " + realFilePath);
        } catch (Exception e) {
            System.err.println("Ошибка при записи users.json: " + e.getMessage());
        }
    }

    private void ensureFileExistsFromResources() {
        try {
            if (Files.notExists(realFilePath)) {
                Files.createDirectories(realFilePath.getParent());
                URL resource = getClass().getClassLoader().getResource("data/users.json");
                if (resource == null) {
                    System.err.println("Шаблон users.json не найден в resources/data/");
                    return;
                }

                try (InputStream is = resource.openStream();
                     OutputStream os = Files.newOutputStream(realFilePath)) {
                    is.transferTo(os);
                    System.out.println("users.json скопирован из resources в: " + realFilePath);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при копировании шаблона users.json: " + e.getMessage());
        }
    }
}
