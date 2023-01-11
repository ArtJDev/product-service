package ru.itbooks.productservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "itbooks")
public class ItBooksProperties {
    /**
     * Приветственное сообщение пользователей
     */
    private String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
