package Database;

public abstract class DBConfig {    // TEST IF IT CAN BE ABSTRACT

    // Sets up all required variables for connecting to the DB.
    static final  String host = "den1.mysql6.gear.host";
    static final  String user = "hkrmarketplace";
    static final  String pass = "Ez0ezh-~e3pf";
    static final  String dbName = "hkrmarketplace";

    @Override
    public String toString() {
        return "Host: " + host + "\n" +
                "User: " + user + "\n" +
                "Pass: " + pass + "\n" +
                "Name: " + dbName;
    }
}
