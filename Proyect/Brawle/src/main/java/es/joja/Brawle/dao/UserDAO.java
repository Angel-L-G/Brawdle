package es.joja.Brawle.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import es.joja.Brawle.contract.UserContract;
import es.joja.Brawle.entity.User;

public class UserDAO implements ICrud<User,Integer>{
	
	DDBBConnManager gc;
	
	public UserDAO(DDBBConnManager gc) {
		this.gc = gc;
	}
	
	
	//Falta revisar el como insertar los roles desde aquí 
	
	@Override
	public User save(User dao) {
		User res = null;
    	
        String insertsql = "INSERT INTO " + UserContract.TABLE_NAME + "("
        		+ UserContract.ID + ","
        		+ UserContract.NICK + ","
        		+ UserContract.EMAIL + ","
        		+ UserContract.PASSWORD + ","
        		+ UserContract.ROLE_NAME + ","
        		+ ") "
        		+ "VALUES(?,?,?,?);";
    	
        try(
        		Connection cn = gc.getConnection(); //PreparedStatement.RETURN_GENERATED_KEYS se usa para obtener el autoincremental al insertar en la DDBB
        		PreparedStatement psInsert = cn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
        		) {
        	if (dao.getId() == null) {
        		psInsert.setNull(1, Types.NULL);
        	} else {
        		psInsert.setInt(1, dao.getId());
        	}
        	
        	psInsert.setString(2, dao.getNick());
        	psInsert.setString(3, dao.getEmail());
        	psInsert.setString(4, dao.getPassword());
        	psInsert.setString(5, dao.getRole());
        	
        	int cantidad = psInsert.executeUpdate();
        	if( cantidad > 0) {
        		res = dao;
        		if(dao.getId() == null) {
        			ResultSet rs = psInsert.getGeneratedKeys();
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
				Connection cn = gc.getConnection(); PreparedStatement ps = cn.prepareStatement(findsql); 
		){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String nick = rs.getString(UserContract.NICK);
				String email = rs.getString(UserContract.EMAIL);
				String hashpw = rs.getString(UserContract.PASSWORD);
				String role = rs.getString(UserContract.ROLE_NAME);
				
				user = new User(id,nick,email,hashpw,role);
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public boolean update(User dao) {
		boolean res = false;
		String Updatesql = "UPDATE " + UserContract.TABLE_NAME + " SET " +
		UserContract.ID + "=" + dao.getId() + "," +
		UserContract.NICK + "=" + dao.getNick() + "," +
		UserContract.EMAIL + "=" + dao.getEmail() + "," +
		UserContract.PASSWORD + "=" + dao.getPassword() + "," +
		UserContract.ROLE_NAME + "=" + dao.getRole() + ";"; 
		
		try(
				Connection cn = gc.getConnection();
				PreparedStatement psUp = cn.prepareStatement(Updatesql);
		){
			psUp.executeQuery();
			res = true;
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
				+ " WHERE " + UserContract.ID + "=" + 
				id + ";";
		try(
				Connection cn = gc.getConnection();
				PreparedStatement psDel = cn.prepareStatement(Delsql);
			){
			psDel.executeQuery();
			res = true;
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
				Connection cn = gc.getConnection();
				PreparedStatement ps = cn.prepareStatement(Allsql);
			){
			ResultSet rs = ps.executeQuery();
			users = new ArrayList();
			while(rs.next()) {
				int id = rs.getInt(UserContract.ID);
				String nick = rs.getString(UserContract.NICK);
				String email = rs.getString(UserContract.EMAIL);
				String hashpw = rs.getString(UserContract.PASSWORD);
				String role = rs.getString(UserContract.ROLE_NAME);
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
