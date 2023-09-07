package es.joja.Brawdle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.joja.Brawdle.contract.GamesContract;
import es.joja.Brawdle.entity.Game;
import es.joja.Brawdle.entity.Legend;

@Repository
public class GameDAO implements ICrud<Game ,Integer>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LegendDAO legendDAO;
	
    @Override
    public Game save(Game dao) {
    	Game game = null;
    	
    	String sql = "INSERT INTO " + GamesContract.TABLE_NAME + "("
    			+ GamesContract.ID + ","
    			+ GamesContract.LEGEND_ID + ") "
    			+ "VALUES (?,?);";
    	
    	try(
    		Connection cn = jdbcTemplate.getDataSource().getConnection();
    		PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    	){
    		if (dao.getId() == null) {
        		ps.setNull(1, Types.NULL);
        	} else {
        		ps.setInt(1, dao.getId());
        	}
    		ps.setInt(2, dao.getLegend().getId());
    		int cantidad = ps.executeUpdate();
    		
    		if (cantidad > 0) {
    			game = dao;
	    		if(dao.getId() == null) {
	    			ResultSet rs = ps.getGeneratedKeys();
	    			if (rs != null && rs.next()) {
	    				int idNew = rs.getInt(1);
	    				game.setId(idNew);
	    			}
	    		}
    		}
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			game = null;
		}
        return game;
    }

    @Override
    public Game findById(Integer id) {
        Game game = null;
        
        String sql = "SELECT * FROM " + GamesContract.TABLE_NAME
        		+ " WHERE " + GamesContract.ID + " = ?;";
        
        try(
	        	Connection cn = jdbcTemplate.getDataSource().getConnection();
	        	PreparedStatement ps = cn.prepareStatement(sql);
        	){
        	
        	ps.setInt(1, id);
        	ResultSet rs = ps.executeQuery();
        	if (rs.next()) {
        		int legendId = rs.getInt(GamesContract.LEGEND_ID);
        		Legend legend = legendDAO.findById(legendId);
        		game = new Game(id, legend);
        	}
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			game = null;
		}
        
        return game;
    }

    @Override
    public boolean update(Game dao) {
        boolean ok = false;
        
        if (delete(dao.getId())) {
			if (save(dao) != null) {
				ok = true;
			}
		}
        
        return ok;
    }

    @Override
    public boolean delete(Integer id) {
        boolean ok = false;
        
        String sql = "DELETE FROM " + GamesContract.TABLE_NAME
        		+ " WHERE " + GamesContract.ID + " = ?;";
        
        try(
        		Connection cn = jdbcTemplate.getDataSource().getConnection();
	        	PreparedStatement ps = cn.prepareStatement(sql);
        	){
        	
        	ps.setInt(1, id);
        	ok = ps.executeUpdate() > 0;
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return ok;
    }

    @Override
    public ArrayList<Game> findAll() {
        ArrayList<Game> games = new ArrayList();
        
        String sql = "SELECT * FROM " + GamesContract.TABLE_NAME;
        
        try(
	        	Connection cn = jdbcTemplate.getDataSource().getConnection();
	        	PreparedStatement ps = cn.prepareStatement(sql);
        	){
        	
        	ResultSet rs = ps.executeQuery();
        	while (rs.next()) {
        		int id = rs.getInt(GamesContract.ID);
        		int legendId = rs.getInt(GamesContract.LEGEND_ID);
        		Legend legend = legendDAO.findById(legendId);
        		Game game = new Game(id, legend);
        		games.add(game);
        	}
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			games = null;
		}
        
        return games;
    }
    
    public Game getLast() {
    	Game game = null;
    	
    	String sql = "SELECT * FROM " + GamesContract.TABLE_NAME 
    			+ " ORDER BY " + GamesContract.ID + " DESC LIMIT 1;";
    	
    	try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
        	PreparedStatement ps = cn.prepareStatement(sql);
    	){
    		ResultSet rs = ps.executeQuery();
    		
    		if (rs.next()) {
    			int id = rs.getInt(GamesContract.ID);
    			int legendId = rs.getInt(GamesContract.LEGEND_ID);
        		Legend legend = legendDAO.findById(legendId);
        		game = new Game(id, legend);
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			game = null;
		}
    	
    	
    	return game;
    }
}
