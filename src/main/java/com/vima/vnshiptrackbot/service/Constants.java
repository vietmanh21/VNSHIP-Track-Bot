package com.vima.vnshiptrackbot.service;

public final class Constants {
    public static final String START_DESCRIPTION = "Bắt đầu tương tác với Bot";
    public static final String CHAT_STATES = "CHAT_STATES";
    public static final String START_TEXT = "👋 Chào *%s*,\n" +
            "Dùng /add để thêm đơn hàng (SPX, LEX, EMS, JT, GHN).\n" +
            "Dùng /list để xem danh sách.\n" +
            "Dùng /donate để ủng hộ admin.";
    public static final String ADD_INSTRUCTION = "🚛 *SPX (10ph)*\n➡️ Không tra được đơn hỏa tốc 4h\n\n" +
            "🚛 *LEX (20ph)*\n➡️ Add đơn, nếu lỗi vẫn ghi nhận sau\n\n" +
            "🚛 *JT (10ph)*\n➡️ Mã-4SốCuốiSĐT\n\n" +
            "🚛 *VNPOST (10ph)*\n➡️ Add đơn lâu hơn (xử lý captcha)\n\n" +
            "🚛 *VIETTELPOST (30ph)*\n➡️ Add đơn lâu hơn (xử lý captcha)\n\n" +
            "🚛 *GHTK (60ph)*\n➡️ Đơn ghi nhận trước, gửi status sau\n\n" +
            "🚛 *EMS (10ph)*\n\n🚛 *GHN (10ph)*\n\n🚛 *247express (10ph)*\n\n" +
            "📦 *Vui lòng nhập mã đơn hàng.*\n" +
            "💡 *Mẹo:* Nhập nhiều mã cách nhau bởi dấu gạch đứng \" | \".\n" +
            "Ví dụ: `SPX... Tên món A | SPX... Tên món B`";
}
