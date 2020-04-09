package Database;

import Models.Account;
import Models.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

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

    public Account getUserInformation(String userEmail) {  // Returns an object containing all information about a user. Takes the email as parameter
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

    public ObservableList<Account> retrieveAllAccounts() { // Returns a observableList with all accounts from the account table
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

    public ObservableList<Sale> retrieveAllSales() {    // Returns a observableList with all sales from the product table
        String query = "SELECT * FROM product;";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ObservableList<Sale> sales = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    sales.add(new Sale(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getBlob(7)));
                }
                return sales;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public void uploadImage(String userEmail, String imagePath) throws SQLException, FileNotFoundException {
        PreparedStatement ps = getConnection().prepareStatement("UPDATE `hkrmarketplace`.`account` SET `Picture` = ? WHERE (`Email` = '" + userEmail + "');");
        InputStream is = new FileInputStream(new File(imagePath));
        ps.setBlob(1, is);
        ps.executeUpdate();
    }

    public void removeAccounts(ArrayList<String> emailsToRemove) {
        StringBuilder query = new StringBuilder("DELETE FROM account WHERE Email IN (");
        for (int i = 0; i < emailsToRemove.size(); i++) {
            if (i == emailsToRemove.size()-1) {
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


    public void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

//"jdbc:mysql://den1.mysql6.gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");