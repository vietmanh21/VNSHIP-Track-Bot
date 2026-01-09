package com.vima.vnshiptrackbot;

import com.vima.vnshiptrackbot.service.TrackerBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class VnshipTrackBotApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(VnshipTrackBotApplication.class, args);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Đăng ký bot từ Bean context
            botsApi.registerBot(ctx.getBean(TrackerBot.class));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
