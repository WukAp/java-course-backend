package edu.java;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class BodyReadHelper {
    Map<String, Object> data;
    public static final String DEFAULT_FILE_PATH = "src/test/resources/mockBody.yml";

    public BodyReadHelper(String filePath) throws FileNotFoundException {
        // Создаем объект Yaml
        Yaml yaml = new Yaml();
        FileInputStream input = new FileInputStream(filePath);
        data = yaml.load(input);
    }

    public BodyReadHelper() throws FileNotFoundException {
        this(DEFAULT_FILE_PATH);
    }

    public String getBody(String propertyName) {
        return (String) data.get(propertyName);
    }

    public String getGithubBody() {
        return getBody("github");
    }

    public String getStackoverflowBody() {
        return getBody("stackoverflow");
    }
}
