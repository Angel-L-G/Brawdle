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
    		for (int i = 0; i < dao.getWeapons().length && ok; i++) {//Checks if the wepons exists
    			String weaponToCheck = dao.getWeapons()[i];
				String weapon = checkWeapons(weaponToCheck);
				if (weapon == null) {//This checks if it exists
					weapon = saveWeapon(weaponToCheck);//This inserts if it does not exist
					if (weapon == null) {//This checks if something went wrong
						ok = false;
					}
				}
			}
    		if (ok) {
    			for (int i = 0; i < dao.getRaces().size() && ok; i++) {//Checks if the races exists
    				String raceToCheck = dao.getRaces().get(i);
    				String race = checkRaces(raceToCheck);
    				if (race == null) {//This checks if it exists
    					race = saveRace(raceToCheck);//This inserts if it does not exist
    					if (race == null) {//This checks if something went wrong
    						ok = false;
    					}
    				}
    			}
    			if (ok) {
        			String gender = checkGenders(dao.getGender());//Checks if the gender exists
                	if (gender == null) {//This checks if it exists
                		gender = saveGender(dao.getGender());//This inserts it if it does not exist
                		if (gender == null) {//This checks if something went wrong
                			ok = false;
                		}
                	}
                	if (ok) {
            			int year = checkYears(dao.getYear());//Checks if the year exists
                    	if (year == 0) {//This checks if it exists
                    		year = saveYear(dao.getYear());//This inserts it if it does not exist
                    		if (year == 0) {//This checks if something went wrong
                    			ok = false;
                    		}
                    	}
            		}
        		}
    		}
    		
    		
    		if (ok) {
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
	    		} else {
	    			cn.rollback();
	    			legend = null;
	    		}
	    		
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
        return null;
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
        return false;
    }

    @Override
    public ArrayList<Legend> findAll() {
        return null;
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
    
    public String checkWeapons(String weaponToCheck) {
		String weapon = null;
		
		String sql = "SELECT * FROM " + WeaponsContract.TABLE_NAME + ";";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ResultSet rs = ps.executeQuery();
			while (rs.next() && !weaponToCheck.equals(weapon)) {//This checks if the weapon exists
				weapon = rs.getString(1);
			}
			if (!weaponToCheck.equals(weapon)) {//This double checks if the weapon exists, because it could have exited the while but it doesnt exist
				weapon = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			weapon = null;
		}
		
		return weapon;
	}
    
    public String saveRace(String name) {
    	String race = null;
    	
    	String sql = "INSERT INTO " + RacesContract.TABLE_NAME + "("
    			+ RacesContract.NAME + ")"
    			+ " VALUES(?);";
    	
    	try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
    	){
    		
    		ps.setString(1, name);
    		int cantidad = ps.executeUpdate();
    		if (cantidad > 0) {
    			race = name;
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			race = null;
		}
    	
    	return race;
    }
    
    public String checkRaces(String raceToCheck) {
		String race = null;
		
		String sql = "SELECT * FROM " + RacesContract.TABLE_NAME + ";";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ResultSet rs = ps.executeQuery();
			while (rs.next() && !raceToCheck.equals(race)) {//This checks if the role exists
				race = rs.getString(1);
			}
			if (!raceToCheck.equals(race)) {//This double checks if the role exists, because it could have exited the while but it doesnt exist
				race = null;
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
    
    public int checkYears(int yearToCheck) {
		int year = 0;
		
		String sql = "SELECT * FROM " + YearsContract.TABLE_NAME + ";";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ResultSet rs = ps.executeQuery();
			while (rs.next() && yearToCheck != year) {//This checks if the role exists
				year = rs.getInt(1);
			}
			if (yearToCheck != year) {//This double checks if the role exists, because it could have exited the while but it doesnt exist
				year = 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			year = 0;
		}
		
		return year;
	}
    
    public String saveGender(String name) {
    	String gender = null;
    	
    	String sql = "INSERT INTO " + GendersContract.TABLE_NAME + "("
    			+ GendersContract.NAME + ")"
    			+ " VALUES(?);";
    	
    	try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
    	){
    		
    		ps.setString(1, name);
    		int cantidad = ps.executeUpdate();
    		if (cantidad > 0) {
    			gender = name;
    		}
    		
    	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gender = null;
		}
    	
    	return gender;
    }
    
    public String checkGenders(String genderToCheck) {
		String gender = null;
		
		String sql = "SELECT * FROM " + GendersContract.TABLE_NAME + ";";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ResultSet rs = ps.executeQuery();
			while (rs.next() && !genderToCheck.equals(gender)) {//This checks if the role exists
				gender = rs.getString(1);
			}
			if (!genderToCheck.equals(gender)) {//This double checks if the role exists, because it could have exited the while but it doesnt exist
				gender = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gender = null;
		}
		
		return gender;
	}
}
