import DAO.ConnectionDAO;
import Models.Key;
import keybord.KeybordBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ArtgetKeyBot extends TelegramLongPollingBot {
    Properties properties = new Properties();
    InputStream input = null;
    ConnectionDAO connectionDAO = null;
    SendMessage message = null;






    public void onUpdateReceived(Update update) {
        Key key = null;
        connectionDAO = new ConnectionDAO();

        String command = update.getMessage().getText();
        message = new SendMessage();


        switch (command) {
            case "/go": {
                message.setText("Привет").setReplyMarkup(new KeybordBot().inlineKeyboard());
                break;
            }
            case "/1": {
                getOneKey("1", update);
                String nameKey = messCatcher(message, update).getText();
                connectionDAO.updateStatus(nameKey);
                break;
            }
            case "/12": {
                getOneKey("12", update);
                String nameKey = messCatcher(message, update).getText();
                connectionDAO.updateStatus(nameKey);
                break;
            }
            case "/15": {
                getOneKey("15", update);
                String nameKey = messCatcher(message, update).getText();
                connectionDAO.updateStatus(nameKey);
                break;
            }
            case "/36": {
                getOneKey("36", update);
                String nameKey = messCatcher(message, update).getText();
                connectionDAO.updateStatus(nameKey);
                break;
            }
            case "/set_key": {
                key = new Key("12", "5532699951", "y");
                connectionDAO.insertKey(key);
                message.setText("Добавлен ключ в базу данных");
                break;
            }
            case "/give_me_success_key": {
                String fullKeyMessage = connectionDAO.sampleKey("y");
                message.setText(fullKeyMessage);
                break;
            }
            case "/give_me_delete_key": {
                String fullKeyMessage = connectionDAO.sampleKey("n");
                message.setText(fullKeyMessage);
                break;
            }

        }


            message.setChatId(update.getMessage().getChatId());



            String chatId = message.getChatId();

        //тут добавляем приватность  наших пользователей, кому отвечает бот
        if (chatId.equals("273426130")) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    public String getBotUsername() {
        getProperties();
        return properties.getProperty("name_bot");
    }

    public String getBotToken() {
        getProperties();
        return properties.getProperty("token_bot");
    }

    void getProperties() {
        input = getClass().getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getOneKey(String sizeKey, Update update) {
        String oneKey = connectionDAO.sampleOneKeyFromTableKey(sizeKey);
        message.setText(oneKey);


    }
    public SendMessage messCatcher(SendMessage sendMessage, Update up){
        SendMessage messageCatcher = sendMessage.setChatId(up.getMessage().getChatId());

        return messageCatcher;
    }
}
