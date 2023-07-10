package es.joja.Brawle.entity;

import java.io.Serializable;

public class Game implements Serializable {
    int id;
    Legend legend;

    public Game(int id, Legend legend) {
        this.id = id;
        this.legend = legend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }
}
