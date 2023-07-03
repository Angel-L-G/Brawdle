package es.joja.Brawle.entity;

import java.io.Serializable;

public class Leyend implements Serializable {
    int id;
    String name;
    String race;
    String gender;
    int year;
    String[] weapons;

    public Leyend() {
    }

    public Leyend(int id, String name, String race, String gender, int year, String[] weapons) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.gender = gender;
        this.year = year;
        this.weapons = weapons;
    }

    public int getId() {
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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYear() {
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
