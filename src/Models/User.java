package Models;

public class User {
    private String userName;
    private String password;
    private String email;
    private boolean admin;

    public User(String userName, String password, String email, boolean admin) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.admin = admin;
    }

    public User() {

    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
