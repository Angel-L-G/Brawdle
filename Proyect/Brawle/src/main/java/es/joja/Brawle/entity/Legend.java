package es.joja.Brawle.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Legend implements Serializable {
    private Integer id;
    private String name;
    private ArrayList<String> race;
    private String gender;
    private Integer year;
    private String[] weapons;

    public Legend() {
    }

    public Legend(int id, String name, ArrayList<String> race, String gender, int year, String[] weapons) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.gender = gender;
        this.year = year;
        this.weapons = weapons;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getRace() {
        return race;
    }

    public void setRace(ArrayList<String> race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String[] getWeapons() {
        return weapons;
    }

    public void setWeapons(String[] weapons) {
        this.weapons = weapons;
    }
}
