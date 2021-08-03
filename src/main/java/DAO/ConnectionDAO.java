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
//SELECT * FROM `key` WHERE `status_key` = 'n' ORDER BY `size_key`
    //выборка всех ключей из таблицы кей
    public String sampleKey(String status) {
        String fullKeyMessage = "";
        String sql = "SELECT * FROM `key` WHERE `status_key` = '"+ status +"' ORDER BY `size_key`";
        ArrayList<Key> keys = new ArrayList<>();
        try {
            Statement stmt = getConnectionSql().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                keys.add(new Key(rs.getString("size_key"),rs.getString("name_key"), rs.getString("status_key")));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            sb.append(keys.get(i).getSizeKey());
            sb.append("      ");
            sb.append(keys.get(i).getNameKey());
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
        String sql = "INSERT INTO `key` ( `size_key`, `name_key`, `status_key`) VALUES (?, ?, ?)";

        try {
            PreparedStatement psmt = getConnectionSql().prepareStatement(sql);
            psmt.setString(1, sizeKey);
            psmt.setString(2, nameKey);
            psmt.setString(3, statusKey);
            psmt.executeUpdate();
            psmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public String sampleOneKeyFromTableKey(String size_key){
        Key key = null;
        StringBuilder sb = new StringBuilder();
        String oneKey = "";
        String sql = "SELECT * FROM `key` WHERE `size_key` LIKE '"+ size_key +"' AND `status_key` LIKE 'y' LIMIT 1";
         try {
             Statement stmt = getConnectionSql().createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                if(rs.next()){
                    key = new Key(rs.getString("size_key"), rs.getString("name_key"), rs.getString("status_key"));


                }
                stmt.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }
            sb.append(key.getSizeKey());
            sb.append("      ");
            sb.append(key.getNameKey());
             oneKey = sb.toString();
            return oneKey;
    }
    public void updateStatus(String nameKey){
        String sql = "UPDATE `key` SET `status_key`= 'n' WHERE `name_key`= ?";
        try {
            PreparedStatement pstmt = getConnectionSql().prepareStatement(sql);
            pstmt.setString(1, nameKey);
            pstmt.executeUpdate();
            getConnectionSql().close();
            } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}