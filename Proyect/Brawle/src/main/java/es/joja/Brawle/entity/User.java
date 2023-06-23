package es.joja.Brawle.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    int id;
    String nick;
    String email;
    String password;
    ArrayList<GameDetails> games;

    public User() {
    }

    public User(int id, String nick, String email, String password, ArrayList<GameDetails> games) {
        this.id = id;
        this.nick = nick;
        this.email = email;
        this.password = password;
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<GameDetails> getGames() {
        return games;
    }

    public void setGames(ArrayList<GameDetails> games) {
        this.games = games;
    }
}
