package com.vima.vnshiptrackbot.service;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Map;

public class ResponseHandler {
    private final SilentSender sender;
    private final Map<Long, UserState> chatStates;

    public ResponseHandler(SilentSender sender, DBContext db) {
        this.sender = sender;
        this.chatStates = db.getMap(Constants.CHAT_STATES);
    }

    public void replyToStart(long chatId, User user) {
        // Trích xuất tên để chào hỏi linh hoạt
        String firstName = user.getFirstName();
        String lastName = user.getLastName() != null ? user.getLastName() : "";
        String fullName = (firstName + " " + lastName).trim();

        String welcomeMsg = String.format(Constants.START_TEXT, fullName);
        sendMessage(chatId, welcomeMsg);
        chatStates.put(chatId, UserState.FREE);
    }

    public void replyToAdd(long chatId) {
        sendMessage(chatId, Constants.ADD_INSTRUCTION);
        chatStates.put(chatId, UserState.AWAITING_TRACKING_CODE);
    }

    public void handleIncomingMessage(long chatId, String text) {
        UserState state = chatStates.getOrDefault(chatId, UserState.FREE);

        if (state == UserState.AWAITING_TRACKING_CODE) {
            processTrackingCodes(chatId, text);
        }
    }

    private void processTrackingCodes(long chatId, String text) {
        String[] codes = text.split("\\|");
        for (String code : codes) {
            String cleanCode = code.trim();
            if (!cleanCode.isEmpty()) {
                System.out.println("Lưu DB đơn hàng: " + cleanCode);
            }
        }
        sendMessage(chatId, "✅ Đã nhận mã! Bot sẽ thông báo khi có cập nhật mới.");
        chatStates.put(chatId, UserState.FREE); // Trả về trạng thái tự do
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setParseMode("Markdown");
        sender.execute(message);
    }
}
