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

import es.joja.Brawdle.contract.UserContract;
import es.joja.Brawdle.entity.User;

@Repository
public class UserDAO implements ICrud<User,Integer>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public UserDAO() {
	}
	
	
	//Angel: Falta revisar el como insertar los roles desde aqui 
	//Owen: do you really want to insert,delete,update roles from the application? 
	//or just from mysql?
	
	@Override
	public User save(User dao) {
		User res = null;
    	
        String insertsql = "INSERT INTO " + UserContract.TABLE_NAME + "("
        		+ UserContract.ID + ","
        		+ UserContract.NICK + ","
        		+ UserContract.EMAIL + ","
        		+ UserContract.PASSWORD + ","
        		+ UserContract.ROLE + ","
        		+ ") "
        		+ "VALUES(?,?,?,?,?);";
    	
        try(
        		Connection cn = jdbcTemplate.getDataSource().getConnection(); //PreparedStatement.RETURN_GENERATED_KEYS se usa para obtener el autoincremental al insertar en la DDBB
        		PreparedStatement ps = cn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
        		) {
        	if (dao.getId() == null) {
        		ps.setNull(1, Types.NULL);
        	} else {
        		ps.setInt(1, dao.getId());
        	}
        	
        	ps.setString(2, dao.getNick());
        	ps.setString(3, dao.getEmail());
        	ps.setString(4, dao.getPassword());
        	ps.setString(5, dao.getRole());
        	
        	int cantidad = ps.executeUpdate();
        	if( cantidad > 0) {
        		res = dao;
        		if(dao.getId() == null) {
        			ResultSet rs = ps.getGeneratedKeys();
        			if (rs != null && rs.next()) {
        				int idNew = rs.getInt(1);
        				res.setId(idNew);
        			}
        		}
        	}
        	
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = null;
		}
        
    	return res;
	}

	@Override
	public User findById(Integer id) {
		User user = null;
		
		String findsql = "SELECT * FROM " + UserContract.TABLE_NAME
				+ "WHERE " + UserContract.ID + " = ?";
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection(); PreparedStatement ps = cn.prepareStatement(findsql); 
		){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String nick = rs.getString(UserContract.NICK);
				String email = rs.getString(UserContract.EMAIL);
				String hashpw = rs.getString(UserContract.PASSWORD);
				String role = rs.getString(UserContract.ROLE);
				
				user = new User(id,nick,email,hashpw,role);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public boolean update(User dao) {//Owen: I left it as we could not change the ids
		boolean res = false;
		String Updatesql = "UPDATE " + UserContract.TABLE_NAME + " SET " +
		UserContract.NICK + "= ?," +
		UserContract.EMAIL + "= ?," +
		UserContract.PASSWORD + "= ?," +
		UserContract.ROLE + "= ?" +
		" WHERE " + UserContract.ID + " = ?;"; 
		
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement ps = cn.prepareStatement(Updatesql);
		){
			ps.setString(1, dao.getNick());
			ps.setString(2, dao.getEmail());
			ps.setString(3, dao.getPassword());
			ps.setString(4, dao.getRole());
			ps.setInt(5, dao.getId());
			res = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return res;
	}

	@Override
	public boolean delete(Integer id) {
		boolean res = false;
		String Delsql = "DELETE FROM " + UserContract.TABLE_NAME
				+ " WHERE " + UserContract.ID + " = ?;";
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement ps = cn.prepareStatement(Delsql);
			){
			ps.setInt(1, id);
			res = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	public ArrayList<User> findAll() {
		ArrayList<User> users = null;
		String Allsql = "SELECT * FROM " + UserContract.TABLE_NAME;
		
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement ps = cn.prepareStatement(Allsql);
			){
			ResultSet rs = ps.executeQuery();
			users = new ArrayList();
			while(rs.next()) {
				int id = rs.getInt(UserContract.ID);
				String nick = rs.getString(UserContract.NICK);
				String email = rs.getString(UserContract.EMAIL);
				String hashpw = rs.getString(UserContract.PASSWORD);
				String role = rs.getString(UserContract.ROLE);
				users.add(new User(id,nick,email,hashpw,role));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			users = null;
		}
		
		return users;
	}
}
