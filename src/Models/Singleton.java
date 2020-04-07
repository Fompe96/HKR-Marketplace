package Models;

public class Singleton {

    private static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private User loggedInUser;  // Variable used to keep track of the currently logged in user.

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public String getLoggedInEmail() {
        return loggedInUser.getEmail();
    }

    public String getLoggedInName() {
        return loggedInUser.getUserName();
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

}