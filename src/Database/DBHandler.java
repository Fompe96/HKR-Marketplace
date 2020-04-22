package Database;

import Models.Account;
import Models.Item;
import Models.MessageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public abstract class DBHandler extends DBConfig {
    private static Connection dbConnection;

    public static Connection getConnection() {
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

    public static boolean findUser(String userEmail, String userPassword) {
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

    public static boolean seeIfEmailAlreadyRegistered(String userEmail) {
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

    public static Account getUserInformation(String userEmail) {  // Returns an object containing all information about a user. Takes the email as parameter
        String query = "SELECT * FROM account WHERE Email = '" + userEmail + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.first();
                return new Account(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), resultSet.getBlob(5));
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public static ObservableList<Account> retrieveAllAccounts() { // Returns a observableList with all accounts from the account table
        String query = "SELECT * FROM account;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ObservableList<Account> accounts = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    accounts.add(new Account(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), resultSet.getBlob(5)));
                }
                return accounts;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public static ObservableList<Item> retrieveAllSales() {    // Returns a observableList with all sales from the product table
        String query = "SELECT * FROM product;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ObservableList<Item> items = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    items.add(new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getBlob(7)));
                }
                return items;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public static void uploadImage(String userEmail, String imagePath) throws SQLException, FileNotFoundException {
        PreparedStatement ps = getConnection().prepareStatement("UPDATE `hkrmarketplace`.`account` SET `Picture` = ? WHERE (`Email` = '" + userEmail + "');");
        InputStream is = new FileInputStream(new File(imagePath));
        ps.setBlob(1, is);
        ps.executeUpdate();
    }

    public static void removeAccounts(ArrayList<String> emailsToRemove) {  // Currently takes an arraylist of account emails and removes them from DB.
        StringBuilder query = new StringBuilder("DELETE FROM account WHERE Email IN (");
        for (int i = 0; i < emailsToRemove.size(); i++) {
            if (i == emailsToRemove.size() - 1) {
                query.append("'").append(emailsToRemove.get(i)).append("'");
            } else {
                query.append("'").append(emailsToRemove.get(i)).append("'").append(", ");
            }
        }
        query.append(");");
        System.out.println(query.toString());
        try (PreparedStatement statement = getConnection().prepareStatement(query.toString())) {
            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void removeSales(ArrayList<Integer> salesIDsToRemove) {  // Currently takes an arraylist of sale IDS and removes them from DB.
        StringBuilder query = new StringBuilder("DELETE FROM product WHERE idProduct IN (");
        for (int i = 0; i < salesIDsToRemove.size(); i++) {
            if (i == salesIDsToRemove.size() - 1) {
                query.append("'").append(salesIDsToRemove.get(i)).append("'");
            } else {
                query.append("'").append(salesIDsToRemove.get(i)).append("'").append(", ");
            }
        }
        query.append(");");
        System.out.println(query.toString());
        try (PreparedStatement statement = getConnection().prepareStatement(query.toString())) {
            statement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static String findUserPassword(String userEmail) {
        String query = "SELECT * FROM ACCOUNT WHERE Email = " + "'" + userEmail + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getString(2);
            }
        } catch (SQLException se) {
            MessageHandler.getErrorAlert("Error", "Error", "No account with that email is registered!").showAndWait();
        }
        return null;
    }


    public static void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//"jdbc:mysql://den1.mysql6.gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");