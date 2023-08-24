package es.joja.Brawdle.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private Integer id;
	private String nick;
	private String email;
	private String password;
	private String role;
	private ArrayList<GameDetails> gameDetails;

    public User() {
    }

    public User(int id, String nick, String email, String password, String role) {
        this.id = id;
        this.nick = nick;
        this.email = email;
        this.password = password;
        this.gameDetails = new ArrayList<>();
        if(role.isBlank() || role.isEmpty() || role == null) {
        	this.setRole("User");
        } else {
			this.setRole(role);
        }
    }

    public Integer getId() {
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

    public ArrayList<GameDetails> getDetails() {
        return gameDetails;
    }

    public void setDetails(ArrayList<GameDetails> gameDetails) {
        this.gameDetails = gameDetails;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
