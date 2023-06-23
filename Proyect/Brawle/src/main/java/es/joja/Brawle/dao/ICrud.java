package es.joja.Brawle.dao;

import java.util.ArrayList;

public interface ICrud<T, E> {


    public T save(T dao);


    public T findById(E id);


    public boolean update(T dao);


    public boolean delete(E id);


    public ArrayList<T> findAll();
}
