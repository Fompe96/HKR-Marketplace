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
                            resultSet.getString(5), resultSet.getString(6), convertBlobToFile(resultSet.getBlob(7)), resultSet.getString(8), resultSet.getBoolean(9)));
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
                            resultSet.getString(5), resultSet.getString(6), convertBlobToFile(resultSet.getBlob(7)), resultSet.getString(8), resultSet.getBoolean(9)));
                }
                return items;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public static ObservableList<Item> retrieveUserSales(String userEmail) {    // Returns a observableList with all sales from the product table
        String query = "select * from product where product.email = '" + userEmail + "';";;
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ObservableList<Item> items = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    items.add(new Item(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6), convertBlobToFile(resultSet.getBlob(7)), resultSet.getString(8), resultSet.getBoolean(9)));
                }
                return items;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;    // Returns null if something went wrong
        }
    }

    public static void addProductToFavorites(String userEmail, int productId) {
        String query = "INSERT INTO `hkrmarketplace`.`favorite` (`account_Email`, `product_idProduct`) VALUES ('" + userEmail + "', '" + productId + "');";
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            MessageHandler.getErrorAlert("Error", "Error", "Item already in favorites");
        }
    }

    public static void removeProductFromFavorites(String userEmail, int productId) {
        String query = "DELETE FROM `hkrmarketplace`.`favorite` WHERE (`account_Email` = '" + userEmail + "') and (`product_idProduct` = '" + productId + "');";
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            MessageHandler.getErrorAlert("Error", "Error", "Item not in favorites");
        }
    }

    public static void uploadImage(String userEmail, String imagePath) {
        String query = "UPDATE `hkrmarketplace`.`account` SET `Picture` = ? WHERE (`Email` = '" + userEmail + "');";
        try (PreparedStatement ps = getConnection().prepareStatement(query)) {
            InputStream is = new FileInputStream(new File(imagePath));
            ps.setBlob(1, is);
            ps.executeUpdate();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
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
        //DELETE favorite, product FROM favorite INNER JOIN product ON product.idProduct = favorite.product_idProduct WHERE product.idProduct = ?;      REPLACE ? WITH ID's TO BE REMOVED
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
            if (account.getImageFile() == null) {
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

    public static void changeUserPassword(String userEmail, String newPassword) {
        String query = "UPDATE `hkrmarketplace`.`account` SET `Password` = '" + newPassword + "' WHERE (`Email` = '" + userEmail + "');";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUserIntoDatabase(String userName, String userPassword, String userEmail) {
        String query = "INSERT INTO `hkrmarketplace`.`account` (`Username`, `Password`, `Email`, `Admin`) VALUES (?, ?, ?, ?);";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, userName);
            stmt.setString(2, userPassword);
            stmt.setString(3, userEmail);
            stmt.setBoolean(4, false);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EmailSender.sendEmail(userEmail, "Your new account", "Welcome to HKR Marketplace! Here are your account details. \n \n" +
                "Username: " + userName + "\n" + "Password: " + userPassword + "\n" + "Account-Email: " + userEmail);
    }

    public static void updateAccountInformation(Account oldAccount, Account newAccount) {
        FileInputStream fis = null;
        String query = "UPDATE account SET Username = ?, Password = ?, Email = ?, Admin = ?, Picture = ? WHERE Email = '" + oldAccount.getEmail() + "';";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, newAccount.getUserName());
            statement.setString(2, newAccount.getPassword());
            statement.setString(3, newAccount.getEmail());
            if (newAccount.isAdmin()) {
                statement.setBoolean(4, true);
            } else {
                statement.setBoolean(4, false);
            }
            if (newAccount.getImageFile() != null) {
                fis = new FileInputStream(newAccount.getImageFile());
                statement.setBinaryStream(5, fis, newAccount.getImageFile().length());
            } else {
                statement.setNull(5, Types.BLOB);
            }
            statement.executeUpdate();
            MessageHandler.getInformationAlert("Success!", "Success!", "Account is now updated!").showAndWait();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            //MessageHandler.getErrorAlert("Error", "Error", "Something went wrong when trying to update the account.").showAndWait();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateItemInformation(Item newItem) {
        FileInputStream fis = null;
        String query = "UPDATE product SET name = ?, price = ?, description = ?, product.condition = ?, category = ?, picture = ?, email = ?, saleActive = ? WHERE idProduct = " + newItem.getId() + ";";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, newItem.getName());
            statement.setDouble(2, newItem.getPrice());
            statement.setString(3, newItem.getDescription());
            statement.setString(4, newItem.getCondition());
            statement.setString(5, newItem.getCategory());
            if (newItem.getImageFile() != null) {
                fis = new FileInputStream(newItem.getImageFile());
                statement.setBinaryStream(6, fis, newItem.getImageFile().length());
            } else {
                statement.setNull(6, Types.BLOB);
            }
            statement.setString(7, newItem.getOwner());
            statement.setBoolean(8, newItem.isSaleActive());
            statement.executeUpdate();
            MessageHandler.getInformationAlert("Success!", "Success!", "Item is now updated!").showAndWait();
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            //MessageHandler.getErrorAlert("Error", "Error", "Something went wrong when trying to update the account.").showAndWait();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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