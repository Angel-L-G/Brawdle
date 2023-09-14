package es.joja.Brawdle.entity;

import java.io.Serializable;

public class Game implements Serializable {
    private Integer id;
    private Legend legend;

<<<<<<< HEAD
    public Game() {}
    
=======
>>>>>>> bafbe0b5e769546919ac303719857f9d0d77f48e
    public Game(Integer id, Legend legend) {
        this.id = id;
        this.legend = legend;
    }

    public Integer getId() {
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
