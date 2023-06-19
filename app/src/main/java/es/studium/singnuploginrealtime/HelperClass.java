package es.studium.singnuploginrealtime;

public class HelperClass {
    String name, email, username, password, lvlAccesses;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getlvlAccesses() {
        return lvlAccesses;
    }
    public void setlvlAccesses(String lvlAccesses) {
        this.lvlAccesses = lvlAccesses;
    }

    public HelperClass(String name, String email, String username, String password, String lvlAccesses) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.lvlAccesses = lvlAccesses;
    }
    public HelperClass() {
    }
}
