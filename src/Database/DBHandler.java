package Database;

import Models.Account;
import Models.EmailSender;
import Models.Item;
import Models.MessageHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
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

    public static Account findUser(String userEmail, String userPassword) {
        String query = "SELECT * FROM ACCOUNT WHERE Email = " + "'" + userEmail + "'" + " AND Password = " + "'" + userPassword + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    resultSet.first();
                    return new Account(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), convertBlobToFile(resultSet.getBlob(5)));
                } else {
                    return null;
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
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

    public static Account findUser(String userEmail) {  // Returns an object containing all information about a user. Takes the email as parameter
        String query = "SELECT * FROM account WHERE Email = '" + userEmail + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    resultSet.first();
                    return new Account(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), convertBlobToFile(resultSet.getBlob(5)));
                } else {
                    return null;
                }
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
                    accounts.add(new Account(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), convertBlobToFile(resultSet.getBlob(5))));
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
                    items.add(new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6), convertBlobToFile(resultSet.getBlob(7)), resultSet.getString(8)));
                }
                return items;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public static ObservableList<Item> retrieveAllFavorites(String userEmail) {    // Returns a observableList with all sales from the product table
        String query = "select product.* from product inner join favorite ON favorite.product_idProduct = product.idProduct and favorite.account_Email = '" + userEmail + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ObservableList<Item> items = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    items.add(new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6), convertBlobToFile(resultSet.getBlob(7)), resultSet.getString(8)));
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

    public static void insertAccountIntoDB(Account account) {
        String query = "INSERT INTO `hkrmarketplace`.`account` (`Username`, `Password`, `Email`, `Admin`, `Picture`) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, account.getUserName());
            statement.setString(2, account.getPassword());
            statement.setString(3, account.getEmail());
            statement.setBoolean(4, account.isAdmin());
            if (account.getImage() == null) {
                statement.setBinaryStream(5, null);
            } else {
                FileInputStream fis = new FileInputStream(account.getImageFile());
                statement.setBinaryStream(5, fis, (int) account.getImageFile().length());
            }
            statement.executeUpdate();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void insertItemIntoDB(Item item) {
        String query = "INSERT INTO `hkrmarketplace`.`product` (`name`, `price`, `description`, `condition`, `category`, `picture`, `email`) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setString(3, item.getDescription());
            statement.setString(4, item.getCondition());
            statement.setString(5, item.getCategory());
            FileInputStream fis = new FileInputStream(item.getImageFile());
            statement.setBinaryStream(6, fis, item.getImageFile().length());
            statement.setString(7, item.getOwner());
            statement.executeUpdate();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void changeUserPassword(String userEmail, String newPassword) throws SQLException {
        PreparedStatement stmt = getConnection().prepareStatement("UPDATE `hkrmarketplace`.`account` SET `Password` = '" + newPassword + "' WHERE (`Email` = '" + userEmail + "');\n");
        stmt.executeUpdate();
    }


    public static void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUserIntoDatabase(String userName, String userPassword, String userEmail) throws SQLException {
        Connection dbConnection = DBHandler.getConnection();
        PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO `hkrmarketplace`.`account` (`Username`, `Password`, `Email`, `Admin`) VALUES (?, ?, ?, ?);\n");

        stmt.setString(1, userName);
        stmt.setString(2, userPassword);
        stmt.setString(3, userEmail);
        stmt.setBoolean(4, false);
        stmt.executeUpdate();
        DBHandler.closeConnection();
        EmailSender.sendEmail(userEmail, "Your new account", "Welcome to HKR Marketplace! Here are your account details. \n \n" +
                "Username: " + userName + "\n" + "Password: " + userPassword + "\n" + "Account-Email: " + userEmail);
    }
    // Method used to convert the retrieved blobs into File objects used by the model class constructors.
    private static File convertBlobToFile(Blob blob) {
        if (blob != null) {
            File imageFile = null;
            try {
                byte[] bytes = blob.getBytes(1, (int) blob.length());
                imageFile = File.createTempFile("ImageTempFile", ".bin");
                try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                    fos.write(bytes);
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } finally {
                if (imageFile != null) {
                    imageFile.deleteOnExit();
                }
            }
            return imageFile;
        } else {
            return null;
        }
    }

}

//"jdbc:mysql://den1.mysql6.gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");gear.host/hkrmarketplace", "hkrmarketplace", "Ez0ezh-~e3pf");