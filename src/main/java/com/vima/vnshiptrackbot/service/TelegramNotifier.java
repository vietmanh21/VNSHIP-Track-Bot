//package com.vima.vnshiptrackbot.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.User;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class TelegramNotifier extends TelegramLongPollingBot {
//
//    @Value("${bot.token}")
//    private String botToken;
//
//    @Value("${bot.name}")
//    private String botName;
//
//    // Quản lý trạng thái người dùng (Memory-based)
//    private final Map<Long, String> userStates = new ConcurrentHashMap<>();
//    private static final String STATE_WAITING_ADD = "WAITING_FOR_TRACKING_CODE";
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            Message incomingMessage = update.getMessage();
//            Long chatId = incomingMessage.getChatId();
//            String text = incomingMessage.getText();
//            User user = incomingMessage.getFrom();
//
//            // 1. Kiểm tra nếu là các lệnh hệ thống (bắt đầu bằng /)
//            if (text.startsWith("/")) {
//                handleCommands(chatId, text, user);
//            }
//            // 2. Nếu không phải lệnh, kiểm tra trạng thái xem có đang trong tiến trình /add không
//            else if (STATE_WAITING_ADD.equals(userStates.get(chatId))) {
//                handleSaveTrackingCode(chatId, text);
//            }
//            // 3. Nếu không phải lệnh và không trong trạng thái chờ, thì không làm gì (theo yêu cầu)
//        }
//    }
//
//    private void handleCommands(Long chatId, String command, User user) {
//        // Reset trạng thái khi người dùng gõ lệnh mới
//        userStates.remove(chatId);
//
//        switch (command) {
//            case "/start":
//                handleStartCommand(chatId, user);
//                break;
//            case "/add":
//                handleAddCommand(chatId);
//                break;
//            case "/list":
//                sendMessage(chatId, "📋 Danh sách đơn hàng của bạn hiện đang trống.");
//                break;
//            default:
//                sendMessage(chatId, "❓ Lệnh không xác định. Dùng /start để xem menu.");
//                break;
//        }
//    }
//
//    private void handleStartCommand(Long chatId, User user) {
//        String firstName = user.getFirstName();
//        String lastName = user.getLastName() != null ? user.getLastName() : "";
//        String fullName = (firstName + " " + lastName).trim();
//
//        String welcomeMsg = String.format(
//                "👋 Chào *%s*,\n" +
//                        "Dùng /add để thêm đơn hàng (SPX, LEX, EMS, JT, GHN).\n" +
//                        "Dùng /list để xem danh sách.\n" +
//                        "Dùng /donate để ủng hộ admin.",
//                fullName
//        );
//        sendMessage(chatId, welcomeMsg);
//    }
//
//    private void handleAddCommand(Long chatId) {
//        // Đặt trạng thái chờ nhập mã vận chuyển
//        userStates.put(chatId, STATE_WAITING_ADD);
//
//        String addMsg = "🚛 *SPX (10ph)*\n➡️ Không tra được đơn hỏa tốc 4h\n\n" +
//                "🚛 *LEX (20ph)*\n➡️ Add đơn, nếu lỗi vẫn ghi nhận sau\n\n" +
//                "🚛 *JT (10ph)*\n➡️ Mã-4SốCuốiSĐT\n\n" +
//                "🚛 *VNPOST (10ph)*\n➡️ Add đơn lâu hơn (xử lý captcha)\n\n" +
//                "🚛 *VIETTELPOST (30ph)*\n➡️ Add đơn lâu hơn (xử lý captcha)\n\n" +
//                "🚛 *GHTK (60ph)*\n➡️ Đơn ghi nhận trước, gửi status sau\n\n" +
//                "🚛 *EMS (10ph)*\n\n🚛 *GHN (10ph)*\n\n🚛 *247express (10ph)*\n\n" +
//                "📦 *Vui lòng nhập mã đơn hàng.*\n" +
//                "💡 *Mẹo:* Nhập nhiều mã cách nhau bởi dấu gạch đứng \" | \".\n" +
//                "Ví dụ: `SPX... Tên món A | SPX... Tên món B`";
//
//        sendMessage(chatId, addMsg);
//    }
//
//    private void handleSaveTrackingCode(Long chatId, String text) {
//        // Tách các mã đơn hàng bằng dấu "|"
//        String[] entries = text.split("\\|");
//
//        for (String entry : entries) {
//            String trimmedEntry = entry.trim();
//            if (trimmedEntry.isEmpty()) continue;
//
//            // Tại đây bạn sẽ gọi TrackingService để lưu vào Database
//            // logic: parse mã, parse tên món, xác định nhà vận chuyển
//            System.out.println("Đang lưu đơn hàng: " + trimmedEntry + " cho chatID: " + chatId);
//        }
//
//        sendMessage(chatId, "✅ Đã nhận mã vận đơn của bạn. Hệ thống sẽ bắt đầu theo dõi!");
//
//        // Sau khi lưu xong, xóa trạng thái để tin nhắn tiếp theo không bị tự động lưu
//        userStates.remove(chatId);
//    }
//
//    private void sendMessage(Long chatId, String text) {
//        SendMessage message = new SendMessage();
//        message.setChatId(String.valueOf(chatId));
//        message.setText(text);
//        message.setParseMode("Markdown");
//        try {
//            execute(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public String getBotUsername() { return botName; }
//
//    @Override
//    public String getBotToken() { return botToken; }
//}