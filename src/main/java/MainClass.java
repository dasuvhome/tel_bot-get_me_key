import DAO.ConnectionDAO;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MainClass {

    public static void main(String[] args) {
        ConnectionDAO connectionDAO = new ConnectionDAO();
        connectionDAO.sampleKey();

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new ArtgetKeyBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
