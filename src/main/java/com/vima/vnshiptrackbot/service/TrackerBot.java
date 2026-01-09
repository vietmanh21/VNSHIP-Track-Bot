package com.vima.vnshiptrackbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class TrackerBot extends AbilityBot {
    private final ResponseHandler responseHandler;

    public TrackerBot(@Value("${bot.token}") String botToken,
                            @Value("${bot.name}") String botName) {
        super(botToken, botName);
        this.responseHandler = new ResponseHandler(silent, db);
    }

    @Override
    public long creatorId() {
        return 1L; // Thay bằng ID Telegram của bạn
    }

    public Ability startBot() {
        return Ability.builder()
                .name("start")
                .info(Constants.START_DESCRIPTION)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
                    // Lấy thông tin user từ context của Ability
                    User user = ctx.update().getMessage().getFrom();
                    responseHandler.replyToStart(ctx.chatId(), user);
                })
                .build();
    }

    public Ability addTracking() {
        return Ability.builder()
                .name("add")
                .info("Thêm vận đơn mới")
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> responseHandler.replyToAdd(ctx.chatId()))
                .build();
    }

    // Xử lý các tin nhắn văn bản không phải là lệnh
    public Reply replyToMessage() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) ->
                responseHandler.handleIncomingMessage(upd.getMessage().getChatId(), upd.getMessage().getText());

        return Reply.of(action, Flag.TEXT, isNotCommand());
    }

    private Predicate<Update> isNotCommand() {
        return upd -> upd.getMessage().hasText() && !upd.getMessage().getText().startsWith("/");
    }
}
