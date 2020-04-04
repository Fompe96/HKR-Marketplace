package Database;

public class DBConfig {

    // Sets up all required variables for connecting to the DB.
    String host = "den1.mysql6.gear.host";
    String user = "hkrmarketplace";
    String pass = "Ez0ezh-~e3pf";
    String dbName = "hkrmarketplace";

    @Override
    public String toString() {
        return "Host: " + host + "\n" +
                "User: " + user + "\n" +
                "Pass: " + pass + "\n" +
                "Name: " + dbName;
    }
}
