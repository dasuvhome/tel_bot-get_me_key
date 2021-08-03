import DAO.ConnectionDAO;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {

        InlineKeyboardButton button1 = new  InlineKeyboardButton();
        button1.setText("1");
        button1.setCallbackData("1");
        InlineKeyboardButton button2 = new  InlineKeyboardButton();
        button2.setText("15");
        button2.setCallbackData("15");
        InlineKeyboardButton button3 = new  InlineKeyboardButton();
        button2.setText("36");
        button2.setCallbackData("36");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(button1);
        keyboardButtonsRow1.add(button2);
        keyboardButtonsRow1.add(button3);

        List<List<InlineKeyboardButton>> rowList= new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new ArtgetKeyBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
