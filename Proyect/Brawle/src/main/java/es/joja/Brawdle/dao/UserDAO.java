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

import es.joja.Brawdle.contract.GamesUsersContract;
import es.joja.Brawdle.contract.RolesContract;
import es.joja.Brawdle.contract.UsersContract;
import es.joja.Brawdle.entity.Game;
import es.joja.Brawdle.entity.GameDetails;
import es.joja.Brawdle.entity.User;

@Repository
public class UserDAO implements ICrud<User,Integer>{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private GameDAO gameDAO;
	
	public UserDAO() {
	}
	
	@Override
	public User save(User dao) {
		User user = null;
		
		boolean ok = true;
    	
        String usersql = "INSERT INTO " + UsersContract.TABLE_NAME + "("
        		+ UsersContract.ID + ","
        		+ UsersContract.NICK + ","
        		+ UsersContract.EMAIL + ","
        		+ UsersContract.PASSWORD + ","
        		+ UsersContract.ROLE + ") "
        		+ "VALUES(?,?,?,?,?);";
        
        String detailsql = "INSERT INTO " + GamesUsersContract.TABLE_NAME + "("
        		+ GamesUsersContract.GAME_ID + ","
        		+ GamesUsersContract.USER_ID + ","
        		+ GamesUsersContract.NUM_TRIES + ","
        		+ GamesUsersContract.GUESSED + ") "
        		+ "VALUES(?,?,?,?);";
        
        
    	
        try(
        		Connection cn = jdbcTemplate.getDataSource().getConnection();
        		PreparedStatement psUser = cn.prepareStatement(usersql, PreparedStatement.RETURN_GENERATED_KEYS);
        		PreparedStatement psDetail = cn.prepareStatement(detailsql);
        ) {
        	
        	cn.setAutoCommit(false);
        	
        	if (dao.getId() == null) {
        		psUser.setNull(1, Types.NULL);
        	} else {
        		psUser.setInt(1, dao.getId());
        	}
        	
        	psUser.setString(2, dao.getNick());
        	psUser.setString(3, dao.getEmail());
        	psUser.setString(4, dao.getPassword());
        	psUser.setString(5, dao.getRole());
        	
        	ok = psUser.executeUpdate() > 0;
        	if(ok) {
        		ArrayList<Game> games = gameDAO.findAll();
        		ArrayList<GameDetails> details = new ArrayList();
        		for (Game game : games) {
        			GameDetails detail = new GameDetails(game, 0, false);
					details.add(detail);
				}
        		dao.setDetails(details);
        		for (int i = 0; i < details.size() && ok; i++) {
        			GameDetails detail = details.get(i);
        			psDetail.setInt(1, detail.getGame().getId());
            		psDetail.setInt(2, dao.getId());
            		psDetail.setInt(3, detail.getNumTries());
            		psDetail.setBoolean(4, detail.isGuessed());
            		
            		ok = psDetail.executeUpdate() > 0;
				}
        		if (ok) {
        			cn.commit();
        			user = dao;
        			if(dao.getId() == null) {
            			ResultSet rs = psUser.getGeneratedKeys();
            			if (rs != null && rs.next()) {
            				int idNew = rs.getInt(1);
            				user.setId(idNew);
            			}
            		}
        		}
        	}
        	
        	if (ok) {
        		cn.commit();
        	} else {
        		cn.rollback();
        		user = null;
        	}
        	cn.setAutoCommit(true);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			user = null;
		}
        
    	return user;
	}

	@Override
	public User findById(Integer id) {
		User user = null;
		
		String usersql = "SELECT * FROM " + UsersContract.TABLE_NAME
				+ " WHERE " + UsersContract.ID + " = ?;";
		
		String detailsql = "SELECT * FROM " + GamesUsersContract.TABLE_NAME
				+ " WHERE " + GamesUsersContract.USER_ID + " = ?;";
		
		
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection(); 
				PreparedStatement psUser = cn.prepareStatement(usersql);
				PreparedStatement psDetail = cn.prepareStatement(detailsql);
		){
			psUser.setInt(1, id);
			ResultSet rsUser = psUser.executeQuery();
			
			if(rsUser.next()) {
				String nick = rsUser.getString(UsersContract.NICK);
				String email = rsUser.getString(UsersContract.EMAIL);
				String hashpw = rsUser.getString(UsersContract.PASSWORD);
				String role = rsUser.getString(UsersContract.ROLE);
				
				user = new User(id,nick,email,hashpw,role);
				
				psDetail.setInt(1, id);
				ResultSet rsDetail = psDetail.executeQuery();
        		ArrayList<GameDetails> details = new ArrayList();
				while (rsDetail.next()) {
					int gameId = rsDetail.getInt(GamesUsersContract.GAME_ID);
					Game game = gameDAO.findById(gameId);
					int numTries = rsDetail.getInt(GamesUsersContract.NUM_TRIES);
					boolean guessed = false;
					if (rsDetail.getInt(GamesUsersContract.GUESSED) != 0) {
						guessed = true;
					}
					GameDetails detail = new GameDetails(game, numTries, guessed);
					details.add(detail);
				}
				user.setDetails(details);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	public User findByNick(String nick) {
		User user = null;
		
		String usersql = "SELECT * FROM " + UsersContract.TABLE_NAME
				+ " WHERE " + UsersContract.NICK + " = ?;";
		
		String detailsql = "SELECT * FROM " + GamesUsersContract.TABLE_NAME
				+ " WHERE " + GamesUsersContract.USER_ID + " = ?;";
		
		
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection(); 
				PreparedStatement psUser = cn.prepareStatement(usersql);
				PreparedStatement psDetail = cn.prepareStatement(detailsql);
		){
			psUser.setString(1, nick);
			ResultSet rsUser = psUser.executeQuery();
			
			if(rsUser.next()) {
				int id = rsUser.getInt(UsersContract.ID);
				String email = rsUser.getString(UsersContract.EMAIL);
				String hashpw = rsUser.getString(UsersContract.PASSWORD);
				String role = rsUser.getString(UsersContract.ROLE);
				
				user = new User(id,nick,email,hashpw,role);
				
				psDetail.setInt(1, id);
				ResultSet rsDetail = psDetail.executeQuery();
        		ArrayList<GameDetails> details = new ArrayList();
				while (rsDetail.next()) {
					int gameId = rsDetail.getInt(GamesUsersContract.GAME_ID);
					Game game = gameDAO.findById(gameId);
					int numTries = rsDetail.getInt(GamesUsersContract.NUM_TRIES);
					boolean guessed = false;
					if (rsDetail.getInt(GamesUsersContract.GUESSED) != 0) {
						guessed = true;
					}
					GameDetails detail = new GameDetails(game, numTries, guessed);
					details.add(detail);
				}
				user.setDetails(details);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	@Override
	public boolean update(User dao) {
		boolean ok = false;
		
		if (delete(dao.getId())) {
			if (save(dao) != null) {
				ok = true;
			}
		}
		
		return ok;
	}

	@Override
	public boolean delete(Integer id) {//Completly deletes the user
		boolean ok = false;
		
		String detailsql = "DELETE FROM " + GamesUsersContract.TABLE_NAME
				+ " WHERE " + GamesUsersContract.USER_ID + " = ?;";
		
		String usersql = "DELETE FROM " + UsersContract.TABLE_NAME
				+ " WHERE " + UsersContract.ID + " = ?;";
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement psUser = cn.prepareStatement(usersql);
				PreparedStatement psDetail = cn.prepareStatement(detailsql);
			){
			cn.setAutoCommit(false);
			psDetail.setInt(1, id);
			ok = psDetail.executeUpdate() > 0;
			if (ok) {
				psUser.setInt(1, id);
				ok = psUser.executeUpdate() > 0;
			}
			
			if (ok) {
				cn.commit();
			} else {
				cn.rollback();
			}
			cn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			ok = false;
		}
		
		return ok;
	}

	public boolean deleteSavely(Integer id) {//Only changes the "deleted" attribute
		boolean res = false;
		String sql = "UPDATE " + UsersContract.TABLE_NAME + " SET "
				+ UsersContract.DELETED + " = 1"
				+ " WHERE " + UsersContract.ID + " = ?;";
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement ps = cn.prepareStatement(sql);
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
		
		String usersql = "SELECT * FROM " + UsersContract.TABLE_NAME;
		
		String detailsql = "SELECT * FROM " + GamesUsersContract.TABLE_NAME
				+ " WHERE " + GamesUsersContract.USER_ID + " = ?;";
		
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement psUser = cn.prepareStatement(usersql);
				PreparedStatement psDetail = cn.prepareStatement(detailsql);
			){
			ResultSet rsUser = psUser.executeQuery();
			users = new ArrayList();
			
			while(rsUser.next()) {
				int id = rsUser.getInt(UsersContract.ID);
				String nick = rsUser.getString(UsersContract.NICK);
				String email = rsUser.getString(UsersContract.EMAIL);
				String hashpw = rsUser.getString(UsersContract.PASSWORD);
				String role = rsUser.getString(UsersContract.ROLE);
				User user = new User(id,nick,email,hashpw,role);
				
				psDetail.setInt(1, id);
				ArrayList<GameDetails> details = new ArrayList();
				ResultSet rsDetail = psDetail.executeQuery();
				
				while (rsDetail.next()) {
					int gameId = rsDetail.getInt(GamesUsersContract.GAME_ID);
					Game game = gameDAO.findById(gameId);
					int numTries = rsDetail.getInt(GamesUsersContract.NUM_TRIES);
					boolean guessed = false;
					if (rsDetail.getInt(GamesUsersContract.GUESSED) != 0) {
						guessed = true;
					}
					GameDetails detail = new GameDetails(game, numTries, guessed);
					details.add(detail);
				}
				
				user.setDetails(details);
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			users = null;
		}
		
		return users;
	}
	
	public String saveRole(String name) {
		String role = null;
		
		String sql = "INSERT INTO " + RolesContract.TABLE_NAME + "("
				+ RolesContract.NAME + ") "
				+ "VALUES(?);";
		
		try(
				Connection cn = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement ps = cn.prepareStatement(sql);
		){
			ps.setString(1, name);
			int cantidad = ps.executeUpdate();
			if (cantidad > 0) {
				role = name;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			role = null;
		}
		
		return role;
	}
	
	public String findRole(String roleToCheck) {
		String role = null;
		
		String sql = "SELECT * FROM " + RolesContract.TABLE_NAME
				+ " WHERE " + RolesContract.NAME + " = ?;";
		
		try(
			Connection cn = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement ps = cn.prepareStatement(sql);
		){
			ps.setString(1, roleToCheck);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				role = rs.getString(RolesContract.NAME);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			role = null;
		}
		
		return role;
	}
}
