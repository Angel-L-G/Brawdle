package es.joja.Brawdle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.joja.Brawdle.contract.GameContract;
import es.joja.Brawdle.entity.Game;

@Repository
public class GameDAO implements ICrud<Game ,Integer>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LegendDAO legendDAO;
	
    @Override
    public Game save(Game dao) {
    	Game game = null;
    	
    	String sql = "INSERT INTO " + GameContract.TABLE_NAME + "("
    			+ GameContract.ID + ","
    			+ GameContract.LEGEND_ID + ") "
    			+ "VALUES (?,?);";
    	
    	try(
    		Connection cn = jdbcTemplate.getDataSource().getConnection();
    		PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
    	){
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			game = null;
		}
        return game;
    }

    @Override
    public Game findById(Integer id) {
        return null;
    }

    @Override
    public boolean update(Game dao) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public ArrayList<Game> findAll() {
        return null;
    }
}
