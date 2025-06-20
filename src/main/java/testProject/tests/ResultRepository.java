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
        ctx.log(">>> ResultRepository.getResultFile() looking for results.json");

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
            ctx.log(">>> ResultRepository will use file: " + file.getAbsolutePath());

        } else if (System.getProperty("catalina.base") != null) {
            file = new File(System.getProperty("catalina.base")
                    + File.separator + "data"
                    + File.separator + "TestProject",
                    "result.json");
            ctx.log(">>> ResultRepository will use file: " + file.getAbsolutePath());

        } else {
            file = new File(System.getProperty("result.home")
                    + File.separator + "TestProject-resultData",
                    "result.json");
            ctx.log(">>> ResultRepository will use file: " + file.getAbsolutePath());

        }
        ctx.log("ResultRepository: using file = " + file.getAbsolutePath());


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
                    ctx.log(">>> ResultRepository will use file: " + file.getAbsolutePath());

                } else {
                    mapper.writeValue(file, new ArrayList<TestResult>());
                    ctx.log(">>> ResultRepository will use file: " + file.getAbsolutePath());

                }
                ctx.log("ResultRepository: using file = " + file.getAbsolutePath());
            }
        }
        ctx.log("ResultRepository: using file = " + file.getAbsolutePath());

        return file;
    }

    public void save(TestResult result) throws IOException {
        File file = getResultFile();
        List<TestResult> list = mapper.readValue(getResultFile(),
                new TypeReference<List<TestResult>>() {
                });
        ctx.log(">>> ResultRepository saving " + result + " into " + file.getAbsolutePath());

        list.add(result);
        mapper.writeValue(getResultFile(), list);
        ctx.log(">>> ResultRepository successfully wrote results.json");


    }

    public List<TestResult> findAll() throws IOException {
        return mapper.readValue(getResultFile(), new TypeReference<List<TestResult>>() {
        });
    }
}

