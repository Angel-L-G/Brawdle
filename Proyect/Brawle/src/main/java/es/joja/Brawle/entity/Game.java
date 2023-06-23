package es.joja.Brawle.entity;

import java.io.Serializable;

public class Game implements Serializable {
    int id;
    Leyend leyend;

    public Game(int id, Leyend leyend) {
        this.id = id;
        this.leyend = leyend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Leyend getLeyend() {
        return leyend;
    }

    public void setLeyend(Leyend leyend) {
        this.leyend = leyend;
    }
}
