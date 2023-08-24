package es.joja.Brawdle.entity;

import java.io.Serializable;

public class GameDetails implements Serializable {
    private Game game;
    private Integer numTries;
    private Boolean guessed;

    public GameDetails() {
    }

    public GameDetails(Game game, Integer numTries, Boolean guessed) {
        this.game = game;
        this.numTries = numTries;
        this.guessed = guessed;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getNumTries() {
        return numTries;
    }

    public void setNumTries(int numTries) {
        this.numTries = numTries;
    }

    public Boolean isGuessed() {
        return guessed;
    }

    public void setGuessed(boolean guessed) {
        this.guessed = guessed;
    }
}
