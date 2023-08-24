package es.joja.Brawdle.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Legend implements Serializable {
    private Integer id;
    private String name;
    private ArrayList<String> races;
    private String gender;
    private int year;
    private String[] weapons;

    public Legend() {
    }

    public Legend(Integer id, String name, ArrayList<String> races, String gender, int year, String[] weapons) {
        this.id = id;
        this.name = name;
        this.races = races;
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

    public ArrayList<String> getRaces() {
        return races;
    }

    public void setRaces(ArrayList<String> races) {
        this.races = races;
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
