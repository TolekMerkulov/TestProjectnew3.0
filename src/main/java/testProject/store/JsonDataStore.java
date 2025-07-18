package testProject.store;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import testProject.model.User;
import testProject.tests.model.Test;
import testProject.tests.model.TestResult;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JsonDataStore {
    private static final Map<String, JsonDataStore> INSTANCES = new ConcurrentHashMap<>();
    private final Path dataDir;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Lock lock = new ReentrantLock();

    private List<User> users;
    private List<Test> tests;
    private List<TestResult> results;


    private JsonDataStore(String dataFolderPath) throws IOException {
        this.dataDir = Path.of(dataFolderPath);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        loadAll();
    }

    public static JsonDataStore getInstance(String dataFolderPath) throws IOException {
        try {
            return INSTANCES.computeIfAbsent(dataFolderPath, path -> {
                try {
                    return new JsonDataStore(path);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (UncheckedIOException uioe) {
            throw uioe.getCause();
        }
    }

    private void loadAll() throws IOException {
        loadUsers();
        reloadTests();
        loadResults();
    }

    // Чтобы репозитории могли строить пути
    public String getDataDir() {
        return dataDir.toString();
    }

    // --- Users ---
    private void loadUsers() throws IOException {
        Path f = dataDir.resolve("users.json");
        if (Files.exists(f)) {
            users = objectMapper.readValue(f.toFile(), new TypeReference<List<User>>() {});
        } else {
            users = new ArrayList<>();
        }
    }

    public User getUserByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst().orElse(null);
    }

    public void addUser(User u) {
        users.add(u);
    }

    public void saveUsers() throws IOException {
        lock.lock();
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(dataDir.resolve("users.json").toFile(), users);
        } finally {
            lock.unlock();
        }
    }

    // --- Tests ---
    public void reloadTests() throws IOException {
        Path testsDir = dataDir.resolve("tests");
        tests = new ArrayList<>();
        if (Files.exists(testsDir) && Files.isDirectory(testsDir)) {
            Files.list(testsDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> {
                        try {
                            Test t = objectMapper.readValue(p.toFile(), Test.class);
                            tests.add(t);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    public List<Test> getAllTests() {
        return tests;
    }

    public Test getTestById(String testId) {
        return tests.stream()
                .filter(t -> t.getId().equals(testId))
                .findFirst().orElse(null);
    }

    public void saveTest(Test test) throws IOException {
        lock.lock();
        try {
            Path p = dataDir.resolve("tests").resolve(test.getId() + ".json");
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(p.toFile(), test);
        } finally {
            lock.unlock();
        }
        reloadTests();
    }

    public void deleteTest(String testId) throws IOException {
        lock.lock();
        try {
            Path p = dataDir.resolve("tests").resolve(testId + ".json");
            if (Files.exists(p)) {
                Files.delete(p);
            }
        } finally {
            lock.unlock();
        }
        reloadTests();
    }

    // --- Results ---
    private void loadResults() throws IOException {
        Path f = dataDir.resolve("results.json");
        if (Files.exists(f)) {
            results = objectMapper.readValue(f.toFile(), new TypeReference<List<TestResult>>() {});
        } else {
            results = new ArrayList<>();
        }
    }

    public List<TestResult> getAllResults() {
        return results;
    }

    public List<TestResult> getResultsByUser(String username) {
        List<TestResult> out = new ArrayList<>();
        for (TestResult r : results) {
            if (r.getUsername().equals(username)) out.add(r);
        }
        return out;
    }

    public void addResult(TestResult result) {
        results.add(result);
    }

    public void saveResults() throws IOException {
        lock.lock();
        try {
            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(dataDir.resolve("results.json").toFile(), results);
        } finally {
            lock.unlock();
        }
    }

    public List<User> getAllUsers() {
        return users;
    }
    public void reloadUsers() throws IOException {
        lock.lock();
        try {
            loadUsers();
        } finally {
            lock.unlock();
        }
    }
}

