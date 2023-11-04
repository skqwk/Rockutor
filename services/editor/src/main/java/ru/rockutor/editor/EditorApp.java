package ru.rockutor.editor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Исключаем SecurityAutoConfiguration,
 * т.к. используем собственную конфигурацию
 */
@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
})
public class EditorApp {

    public static void main(String[] args) {
        SpringApplication.run(EditorApp.class, args);
    }

}
