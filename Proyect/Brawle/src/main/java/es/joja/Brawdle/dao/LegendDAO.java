package es.joja.Brawdle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import es.joja.Brawdle.contract.GamesContract;
import es.joja.Brawdle.contract.GendersContract;
import es.joja.Brawdle.contract.LegendsContract;
import es.joja.Brawdle.contract.LegendsRacesContract;
import es.joja.Brawdle.contract.LegendsWeaponsContract;
import es.joja.Brawdle.contract.RacesContract;
import es.joja.Brawdle.contract.WeaponsContract;
import es.joja.Brawdle.contract.YearsContract;
import es.joja.Brawdle.entity.Legend;

@Repository
public class LegendDAO implements ICrud<Legend, Integer>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Override
    public Legend save(Legend dao) {
    	Legend legend = null;
    	
    	boolean ok = true;
    	
    	String legendsql = "INSERT INTO " + LegendsContract.TABLE_NAME + "("
    			+ LegendsContract.ID + ","
    			+ LegendsContract.NAME + ","
    			+ LegendsContract.GENDER_NAME + ","
    			+ LegendsContract.YEAR_NUM + ")"
    			+ " VALUES(?,?,?,?);";
    	
    	String lwsql = "INSERT INTO " + LegendsWeaponsContract.TABLE_NAME + "("
    			+ LegendsWeaponsContract.LEGEND_ID + ","
    			+ LegendsWeaponsContract.WEAPON_NAME + ")"
    			+ " VALUES(?,?);";
    	
    	String lrsql = "INSERT INTO " + LegendsRacesContract.TABLE_NAME + "("
    			+ LegendsRacesContract.LEGEND_ID + ","
    			+ LegendsRacesContract.RACE_NAME + ")"
    			+ " VALUES(?,?);";
    	
    	try(
    		Connection cn = jdbcTemplate.getDataSource().getConnection();
    		PreparedStatement psLegend = cn.prepareStatement(legendsql, PreparedStatement.RETURN_GENERATED_KEYS);
    		PreparedStatement psLW = cn.prepareStatement(lwsql);
    		PreparedStatement psLR = cn.prepareStatement(lrsql);
    	){
    		cn.setAutoCommit(false);
    		
    		if (dao.getId() == null) {
    			psLegend.setNull(1, Types.NULL);
    		} else {
    			psLegend.setInt(1, dao.getId());
    		}
    		
    		psLegend.setString(2, dao.getName());
    		psLegend.setString(3, dao.getGender());
    		psLegend.setInt(4, dao.getYear());
    		ok = psLegend.executeUpdate() > 0;
    		
    		if (ok) {
    			legend = dao;
    			if(dao.getId() == null) {
        			ResultSet rs = psLegend.getGeneratedKeys();
        			if (rs != null && rs.next()) {
        				int idNew = rs.getInt(1);
        				legend.setId(idNew);
        			}
        		}
    			
    			for (int i = 0; i < dao.getWeapons().length && ok; i++) {//This is to insert on the middle table
					psLW.setInt(1, dao.getId());
					psLW.setString(2, dao.getWeapons()[i]);
					
					ok = psLW.executeUpdate() > 0;
				}
    			
    			if (ok) {
    				for (int i = 0; i < dao.getRaces().size() && ok; i++) {//This is to insert on the middle table
    					psLR.setInt(1, dao.getId());
						psLR.setString(2, dao.getRaces().get(i));
						
						ok = psLR.executeUpdate() > 0;
					}
    			}
    			
    		}
    		
    		if (ok) {
    			cn.commit();
    		} else {
    			cn.rollback();
    			legend = null;
    		}
    		
    		cn.setAutoCommit(true);
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			legend = null;
		}
    	
    	
        return legend;
    }

    @Override
    public Legend findById(Integer id) {
        Legend legend = null;
        
        String legendsql = "SELECT * FROM " + LegendsContract.TABLE_NAME
        		+ " WHERE " + LegendsContract.ID + " = ?;";
        
        String lwsql = "SELECT * FROM " + LegendsWeaponsContract.TABLE_NAME
        		+ " WHERE " + LegendsWeaponsContract.LEGEND_ID + " = ?;";

        String lrsql = "SELECT * FROM " + LegendsRacesContract.TABLE_NAME
        		+ " WHERE " + LegendsRacesContract.LEGEND_ID + " = ?;";
        
        try(
        	Connection cn = jdbcTemplate.getDataSource().getConnection();
        	PreparedStatement psLegend = cn.prepareStatement(legendsql);
    		PreparedStatement psLW = cn.prepareStatement(lwsql);
    		PreparedStatement psLR = cn.prepareStatement(lrsql);
        ){
        	
        	psLegend.setInt(1, id);
        	ResultSet rsLegend = psLegend.executeQuery();
        	
        	if (rsLegend.next()) {
        		String name = rsLegend.getString(LegendsContract.NAME);
        		String gender = rsLegend.getString(LegendsContract.GENDER_NAME);
        		int year = rsLegend.getInt(LegendsContract.YEAR_NUM);
        		
        		psLW.setInt(1, id);
        		ResultSet rsLW = psLW.executeQuery();
        		String weapons[] = new String[2];
        		for (int i = 0; rsLW.next(); i++) {
        			String weapon = rsLW.getString(LegendsWeaponsContract.WEAPON_NAME);
        			weapons[i] = weapon;
        		}
        		
        		psLR.setInt(1, id);
        		ResultSet rsLR = psLR.executeQuery();
        		ArrayList<String> races = new ArrayList();
        		while (rsLR.next()) {
        			String race = rsLR.getString(LegendsRacesContract.RACE_NAME);
        			races.add(race);
        		}
        		
        		legend = new Legend(id, name, races, gender, year, weapons);
        	}
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			legend = null;
		}
        
        return legend;
    }
    
    public Legend findByName(String name) {
        Legend legend = null;
        
        String legendsql = "SELECT * FROM " + LegendsContract.TABLE_NAME
        		+ " WHERE " + LegendsContract.NAME + " = ?;";
        
        String lwsql = "SELECT * FROM " + LegendsWeaponsContract.TABLE_NAME
        		+ " WHERE " + LegendsWeaponsContract.LEGEND_ID + " = ?;";

        String lrsql = "SELECT * FROM " + LegendsRacesContract.TABLE_NAME
        		+ " WHERE " + LegendsRacesContract.LEGEND_ID + " = ?;";
        
        try(
        	Connection cn = jdbcTemplate.getDataSource().getConnection();
        	PreparedStatement psLegend = cn.prepareStatement(legendsql);
    		PreparedStatement psLW = cn.prepareStatement(lwsql);
    		PreparedStatement psLR = cn.prepareStatement(lrsql);
        ){
        	
        	psLegend.setString(1, name);
        	ResultSet rsLegend = psLegend.executeQuery();
        	
        	if (rsLegend.next()) {
        		int id = rsLegend.getInt(LegendsContract.ID);
        		String gender = rsLegend.getString(LegendsContract.GENDER_NAME);
        		int year = rsLegend.getInt(LegendsContract.YEAR_NUM);
        		
        		psLW.setInt(1, id);
        		ResultSet rsLW = psLW.executeQuery();
        		String weapons[] = new String[2];
        		for (int i = 0; rsLW.next(); i++) {
        			String weapon = rsLW.getString(LegendsWeaponsContract.WEAPON_NAME);
        			weapons[i] = weapon;
        		}
        		
        		psLR.setInt(1, id);
        		ResultSet rsLR = psLR.executeQuery();
        		ArrayList<String> races = new ArrayList();
        		while (rsLR.next()) {
        			String race = rsLR.getString(LegendsRacesContract.RACE_NAME);
        			races.add(race);
        		}
        		
        		legend = new Legend(id, name, races, gender, year, weapons);
        	}
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			legend = null;
		}
        
        return legend;
    }

    @Override
    public boolean update(Legend dao) {
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
        boolean ok = true;
        
        String legendsql = "DELETE FROM " + LegendsContract.TABLE_NAME
        		+ " WHERE " + LegendsContract.ID + " = ?;";
        
        String lwsql = "DELETE FROM " + LegendsWeaponsContract.TABLE_NAME
        		+ " WHERE " + LegendsWeaponsContract.LEGEND_ID + " = ?;";

        String lrsql = "DELETE FROM " + LegendsRacesContract.TABLE_NAME
        		+ " WHERE " + LegendsRacesContract.LEGEND_ID + " = ?;";
        
        String gamesql = "DELETE FROM " + GamesContract.TABLE_NAME
        		+ " WHERE " + GamesContract.LEGEND_ID + " = ?;";
        
        try(
        	Connection cn = jdbcTemplate.getDataSource().getConnection();
        	PreparedStatement psLegend = cn.prepareStatement(legendsql);
    		PreparedStatement psLW = cn.prepareStatement(lwsql);
    		PreparedStatement psLR = cn.prepareStatement(lrsql);
        	PreparedStatement psGame = cn.prepareStatement(gamesql);
        ){
        	cn.setAutoCommit(false);
        	
        	psLW.setInt(1, id);
        	ok = psLW.executeUpdate() > 0;
        	
        	if (ok) {
        		psLR.setInt(1, id);
        		ok = psLR.executeUpdate() > 0;
        		
        		if (ok) {
        			psGame.setInt(1, id);
        			psGame.executeUpdate();
        			
    				psLegend.setInt(1, id);
    				ok = psLegend.executeUpdate() > 0;
        		}
        	}
        	
        	if (ok) {
        		cn.commit();
        	} else {
        		cn.rollback();
        	}
        	
        	cn.setAutoCommit(true);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok = false;
		}
        
        return ok;
    }

    @Override
    public ArrayList<Legend> findAll() {
    	ArrayList<Legend> legends = new ArrayList();
        
        String legendsql = "SELECT * FROM " + LegendsContract.TABLE_NAME + ";";
        
        String lwsql = "SELECT * FROM " + LegendsWeaponsContract.TABLE_NAME
        		+ " WHERE " + LegendsWeaponsContract.LEGEND_ID + " = ?;";

        String lrsql = "SELECT * FROM " + LegendsRacesContract.TABLE_NAME
        		+ " WHERE " + LegendsRacesContract.LEGEND_ID + " = ?;";
        
        try(
        	Connection cn = jdbcTemplate.getDataSource().getConnection();
        	PreparedStatement psLegend = cn.prepareStatement(legendsql);
    		PreparedStatement psLW = cn.prepareStatement(lwsql);
    		PreparedStatement psLR = cn.prepareStatement(lrsql);
        ){
        	ResultSet rsLegend = psLegend.executeQuery();
        	
        	while (rsLegend.next()) {
        		int id = rsLegend.getInt(LegendsContract.ID);
        		String name = rsLegend.getString(LegendsContract.NAME);
        		String gender = rsLegend.getString(LegendsContract.GENDER_NAME);
        		int year = rsLegend.getInt(LegendsContract.YEAR_NUM);
        		
        		psLW.setInt(1, id);
        		ResultSet rsLW = psLW.executeQuery();
        		String weapons[] = new String[2];
        		for (int i = 0; rsLW.next(); i++) {
        			String weapon = rsLW.getString(LegendsWeaponsContract.WEAPON_NAME);
        			weapons[i] = weapon;
        		}
        		
        		psLR.setInt(1, id);
        		ResultSet rsLR = psLR.executeQuery();
        		ArrayList<String> races = new ArrayList();
        		while (rsLR.next()) {
        			String race = rsLR.getString(LegendsRacesContract.RACE_NAME);
        			races.add(race);
        		}
        		
        		Legend legend = new Legend(id, name, races, gender, year, weapons);
        		legends.add(legend);
        	}
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			legends = null;
		}
        
        return legends;
    }
    
    public String saveWeapon(String name) {
    	String weapon = null;
    	
    	String sql = "INSERT INTO " + WeaponsContract.TABLE_NAME + "("
    			+ WeaponsContract.NAME + ")"
    			+ " VALUES(?);";
    	
    	try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
    	){
    		
    		ps.setString(1, name);
    		int cantidad = ps.executeUpdate();
    		if (cantidad > 0) {
    			weapon = name;
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			weapon = null;
		}
    	
    	return weapon;
    }
    
    public String findWeapon(String weaponToCheck) {
		String weapon = null;
		
		String sql = "SELECT * FROM " + WeaponsContract.TABLE_NAME 
				+ " WHERE " + WeaponsContract.NAME + " = ?;";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ps.setString(1, weaponToCheck);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				weapon = rs.getString(WeaponsContract.NAME);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			weapon = null;
		}
		
		return weapon;
	}
    
    public boolean updateWeapon(String newName, String oldName) {
    	boolean ok = true;
    	
    	if (saveWeapon(newName) != null) {
    		
    		String sql = "UPDATE " + LegendsWeaponsContract.TABLE_NAME + " SET " + LegendsWeaponsContract.WEAPON_NAME
    				+ " = ? WHERE " + LegendsWeaponsContract.WEAPON_NAME + " = ?;";
    		
    		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement ps = cn.prepareStatement(sql);
    		){
    			ps.setString(1, newName);
    			ps.setString(2, oldName);
    			ok = ps.executeUpdate() > 0;
    			
    		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		if (ok) {
    			deleteWeapon(oldName);
    		}
    	}
    	
    	return ok;
    }
    
    public boolean deleteWeapon(String name) {
    	boolean ok = true;
    	
    	String wsql = "DELETE FROM " + WeaponsContract.TABLE_NAME
    			+ " WHERE " + WeaponsContract.NAME + " = ?;";
    	
    	String lwsql = "DELETE FROM " + LegendsWeaponsContract.TABLE_NAME
    			+ " WHERE " + LegendsWeaponsContract.WEAPON_NAME + " = ?;";
    	
    	try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement psW = cn.prepareStatement(wsql);
    		PreparedStatement psLW = cn.prepareStatement(lwsql);
    	){
    		cn.setAutoCommit(false);
    		if (findWeapon(name) != null) {
    			psLW.setString(1, name);
        		ok = psLW.executeUpdate() > 0;
    		}
			psW.setString(1, name);
    		ok = psW.executeUpdate() > 0;
    		
    		if (ok) {
    			cn.commit();
    		} else {
    			cn.rollback();
    		}
    		cn.setAutoCommit(true);
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok = false;
		}
    	
    	return ok;
    }
    
    public String findRace(String raceToCheck) {
		String race = null;
		
		String sql = "SELECT * FROM " + RacesContract.TABLE_NAME 
				+ " WHERE " + RacesContract.NAME + " = ?;";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ps.setString(1, raceToCheck);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				race = rs.getString(RacesContract.NAME);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			race = null;
		}
		
		return race;
	}
    
    public int saveYear(int num) {
    	int year = 0;
    	
    	String sql = "INSERT INTO " + YearsContract.TABLE_NAME + "("
    			+ YearsContract.NUM + ")"
    			+ " VALUES(?);";
    	
    	try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
    	){
    		
    		ps.setInt(1, num);
    		int cantidad = ps.executeUpdate();
    		if (cantidad > 0) {
    			year = num;
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			year = 0;
		}
    	
    	return year;
    }
    
    public int findYear(int yearToCheck) {
		int year = 0;
		
		String sql = "SELECT * FROM " + YearsContract.TABLE_NAME 
				+ " WHERE " + YearsContract.NUM + " = ?;";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ps.setInt(1, yearToCheck);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				year = rs.getInt(YearsContract.NUM);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			year = 0;
		}
		
		return year;
	}
    
    public String findGender(String genderToCheck) {
		String gender = null;
		
		String sql = "SELECT * FROM " + GendersContract.TABLE_NAME 
				+ " WHERE " + GendersContract.NAME + " = ?;";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ps.setString(1, genderToCheck);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				gender = rs.getString(GendersContract.NAME);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gender = null;
		}
		
		return gender;
	}
}
