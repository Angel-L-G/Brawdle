package es.joja.Brawdle.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GameDAO implements ICrud{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Override
    public Object save(Object dao) {
        return null;
    }

    @Override
    public Object findById(Object id) {
        return null;
    }

    @Override
    public boolean update(Object dao) {
        return false;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

    @Override
    public ArrayList findAll() {
        return null;
    }
}
