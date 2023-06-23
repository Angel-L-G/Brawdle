package es.joja.Brawle.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Weapon implements Serializable {
    String name;
    ArrayList<Leyend> leyends;

    public Weapon() {
    }

    public Weapon(String name, ArrayList<Leyend> leyends) {
        this.name = name;
        this.leyends = leyends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Leyend> getLeyends() {
        return leyends;
    }

    public void setLeyends(ArrayList<Leyend> leyends) {
        this.leyends = leyends;
    }
}
