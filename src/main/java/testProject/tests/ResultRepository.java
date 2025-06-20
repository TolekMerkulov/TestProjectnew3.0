package testProject.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletContext;
import testProject.model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ResultRepository {
    private final ServletContext ctx;
    private final ObjectMapper mapper;



    public ResultRepository(ServletContext ctx) {
        this.ctx = ctx;
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private File getResultFile() throws IOException {

        String extParam = ctx.getInitParameter("resultFolder");
        File file;
        if (extParam != null && !extParam.isBlank()) {
            file = new File(extParam, "result.json");
        } else if (System.getProperty("catalina.base") != null) {
            file = new File(System.getProperty("catalina.base")
                    + File.separator + "data"
                    + File.separator + "TestProject",
                    "result.json");
        } else {
            file = new File(System.getProperty("result.home")
                    + File.separator + "TestProject-resultData",
                    "result.json");
        }

        File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IOException("Не удалось создать папку: " + parent);
        }

        if (!file.exists()) {
            try (InputStream is = ctx.getResourceAsStream("/WEB-INF/data/result.json")) {
                if (is != null) {
                    List<TestResult> defaults = mapper.readValue(is, new TypeReference<List<TestResult>>() {
                    });
                    mapper.writeValue(file, defaults);
                } else {
                    mapper.writeValue(file, new ArrayList<TestResult>());
                }
            }
        }
        return file;
    }

    public void save(TestResult result) throws IOException {
        List<TestResult> list = mapper.readValue(getResultFile(),
                new TypeReference<List<TestResult>>() {
                });
        list.add(result);
        mapper.writeValue(getResultFile(), list);

    }

    public List<TestResult> findAll() throws IOException {
        return mapper.readValue(getResultFile(), new TypeReference<List<TestResult>>() {
        });
    }
}

