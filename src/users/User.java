package users;

public class User {
    private String name;
    private String email;
    private String username;
    private String password;
    private String type;

    public User(String name, String email, String username, String password, String type) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }
}
