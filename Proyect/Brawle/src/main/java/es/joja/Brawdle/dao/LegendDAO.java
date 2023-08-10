package es.joja.Brawdle.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.joja.Brawdle.entity.Legend;

@Repository
public class LegendDAO implements ICrud<Legend, Integer>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Override
    public Legend save(Legend dao) {
        return null;
    }

    @Override
    public Legend findById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Legend dao) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public ArrayList<Legend> findAll() {
        return null;
    }
}
