package Database;

import Models.User;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

public class DBHandler extends DBConfig {
    private Connection dbConnection;

    public Connection getConnection() {
        String connectionString = "jdbc:mysql://" + host + "/" + dbName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dbConnection = DriverManager.getConnection(connectionString, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbConnection;
    }

    public boolean findUser(String userEmail, String userPassword) {
        String query = "SELECT * FROM ACCOUNT WHERE Email = " + "'" + userEmail + "'" + " AND Password = " + "'" + userPassword + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    public boolean seeIfEmailAlreadyRegistered(String userEmail) {
        String query = "SELECT * FROM ACCOUNT WHERE Email = " + "'" + userEmail + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }
    }

    public User getUserInformation(String userEmail) {  // Returns an object containing all information about a user. Takes the email as parameter
        String query = "SELECT * FROM account WHERE Email = '" + userEmail + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.first();
                return new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getBoolean(5));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public void uploadImage(String userEmail, String imagePath) throws SQLException, FileNotFoundException {
        PreparedStatement ps = getConnection().prepareStatement("UPDATE `hkrmarketplace`.`account` SET `Picture` = ? WHERE (`Email` = '" + userEmail + "');");
        InputStream is = new FileInputStream(new File(imagePath));
        ps.setBlob(1,is);
        ps.executeUpdate();
        System.out.println("Done");
    }

    public void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//"jdbc:mysql://den1.mysql6.gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");