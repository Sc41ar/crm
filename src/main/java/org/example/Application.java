package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Класс для запуска приложения
 */
@SpringBootApplication
@ConfigurationPropertiesScan("org.example.config")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        /*String bot_token = "6826124563:AAHI5WaLVmKylqyMs22Y3LszUTcQ6GrcK_I";
        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(bot_token, new TelegramBot(bot_token));
            System.out.println("MyAmazingBot successfully started!");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}
