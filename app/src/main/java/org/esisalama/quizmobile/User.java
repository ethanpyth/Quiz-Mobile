package org.esisalama.quizmobile;

public class User {
    private String id;

    public User(){}

    public User(String id, String login){
        this.id = id;
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String login;
}
