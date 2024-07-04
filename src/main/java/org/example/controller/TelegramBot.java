package org.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    String bot_name;
    @Value("${bot.token}")
    String bot_token;

    @Override
    public String getBotUsername() {
        return bot_name;
    }

    public String getBotToken() {
        return bot_token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var msg = update.getMessage();
        System.out.println(msg);
    }

}
