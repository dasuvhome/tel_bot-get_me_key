package DAO;

import Models.Key;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectionDAO {
    Properties properties = new Properties();
    InputStream input = null;

    public Connection getConnectionSql() {
        Connection connection = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(input);
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String driver = properties.getProperty("driver-class-name");
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //выборка всех ключей из таблицы кей
    public String sampleKey() {
        String fullKeyMessage = "";
        String url = "SELECT * FROM `key`";
        ArrayList<String> keys = new ArrayList<>();
        try {
            Statement stmt = getConnectionSql().createStatement();
            ResultSet rs = stmt.executeQuery(url);
            while (rs.next()) {
                keys.add(rs.getString("name_key"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            sb.append(keys.get(i));
            sb.append("\n");
        }
        fullKeyMessage = sb.toString();

        return fullKeyMessage;
    }

    //загрузка ключа в таблицу кей
    public void insertKey(Key key) {
        String nameKey = key.getNameKey();
        String sizeKey = key.getSizeKey();
        key.setStatusKey("y");
        String statusKey = key.getStatusKey();
        String url = "INSERT INTO `key` ( `size_key`, `name_key`, `status_key`) VALUES (?, ?, ?)";

        try {
            PreparedStatement psmt = getConnectionSql().prepareStatement(url);
            psmt.setString(1, sizeKey);
            psmt.setString(2, nameKey);
            psmt.setString(3, statusKey);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}