import DAO.ConnectionDAO;
import Models.Key;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ArtgetKeyBot extends TelegramLongPollingBot {
    Properties properties = new Properties();
    InputStream input = null;

    public void onUpdateReceived(Update update) {
           Key key = null;
//            System.out.println(update.getMessage().getText());
//            System.out.println(update.getMessage().getFrom().getFirstName() );
        ConnectionDAO connectionDAO = new ConnectionDAO();

        String command = update.getMessage().getText();
          if(command.equals("/set_key")){
              key = new Key("36", "3336554", "y");
            connectionDAO.insertKey(key);
          }
        System.out.println(command);

        SendMessage message = new SendMessage();

        if (command.equals("/give_me_key")) {

           // System.out.println(update.getMessage().getFrom().getFirstName());
            String fullKeyMessage = connectionDAO.sampleKey();
            message.setText(fullKeyMessage);
        //    message.setText(update.getMessage().getFrom().getFirstName());
        }

        if (command.equals("/mylastname")) {

            System.out.println(update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getLastName());
        }

        if (command.equals("/myfullname")) {
            System.out.println(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName());
        }

        message.setChatId(update.getMessage().getChatId());


        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
}
