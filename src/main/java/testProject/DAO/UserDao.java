package testProject.DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import testProject.model.User;
import testProject.model.UserList;

import java.io.File;
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
    private final ServletContext ctx;
    private final ObjectMapper mapper = new ObjectMapper();
    private static final String INWAR_REL = "/WEB-INF/data/users.json";

    public UserDao(ServletContext ctx) {
        this.ctx = ctx;
    }

    private File getDataFile() throws IOException {
        // 1) Выбор внешней папки (как у вас сейчас)
        String extParam = ctx.getInitParameter("dataFolder");
        File file;
        if (extParam != null && !extParam.isBlank()) {
            file = new File(extParam, "users.json");
        } else if (System.getProperty("catalina.base") != null) {
            file = new File(System.getProperty("catalina.base")
                    + File.separator + "data"
                    + File.separator + "TestProject",
                    "users.json");
        } else {
            file = new File(System.getProperty("user.home")
                    + File.separator + "TestProject-data",
                    "users.json");
        }

        // 2) Создаем директорию при необходимости
        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IOException("Не удалось создать папку: " + parent);
        }

        // 3) Если файл не существует — seed из шаблона или пустой массив
        if (!file.exists()) {
            // Пытаемся загрузить шаблон из WEB-INF/data/users.json внутри WAR
            try (InputStream is = ctx.getResourceAsStream("/WEB-INF/data/users.json")) {
                if (is != null) {
                    // Если шаблон есть, записываем его содержимое
                    List<User> defaults = mapper.readValue(is, new TypeReference<List<User>>(){});
                    mapper.writeValue(file, defaults);
                } else {
                    // Шаблона нет (какой-то странный WAR) — просто пустой список
                    mapper.writeValue(file, new ArrayList<User>());
                }
            }
        }
        return file;
    }

    public List<User> findAll() throws IOException {
        return mapper.readValue(getDataFile(), new TypeReference<List<User>>(){});
    }

    public void saveAll(List<User> users) throws IOException {
        mapper.writeValue(getDataFile(), users);
    }

    public void add(User user) throws IOException {
        List<User> list = findAll();
        list.add(user);
        saveAll(list);

    }
}
