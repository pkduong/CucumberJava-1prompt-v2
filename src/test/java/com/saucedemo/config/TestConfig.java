package com.saucedemo.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public final class TestConfig {
    private static final Properties PROPERTIES = loadProperties();

    private TestConfig() {
    }

    public static String baseUrl() {
        return requiredProperty("base.url");
    }

    public static List<String> usernames() {
        List<String> usernames = Arrays.stream(requiredProperty("users").split(","))
                .map(String::trim)
                .filter(username -> !username.isEmpty())
                .toList();
        if (usernames.isEmpty()) {
            throw new IllegalStateException("Configuration property 'users' must contain at least one username.");
        }
        return usernames;
    }

    public static String password() {
        return requiredProperty("password");
    }

    public static String lockedUsername() {
        return requiredProperty("locked.username");
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("test-data.properties")) {
            if (input == null) {
                throw new IllegalStateException("Could not find test-data.properties on the test classpath.");
            }
            properties.load(input);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not load test-data.properties.", exception);
        }
    }

    private static String requiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required test configuration property: " + key);
        }
        return value.trim();
    }
}
