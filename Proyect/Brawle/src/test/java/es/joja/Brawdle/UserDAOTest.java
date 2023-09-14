package es.joja.Brawdle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import es.joja.Brawdle.dao.UserDAO;
import es.joja.Brawdle.entity.Game;
import es.joja.Brawdle.entity.GameDetails;
import es.joja.Brawdle.entity.User;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/brawdletest.sql"})
class UserDAOTest {

	@Autowired
	UserDAO userDAO;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	private static GameDetails detail1 = new GameDetails(new Game(1, null), 5, true);
	private static GameDetails detail2 = new GameDetails(new Game(2, null), 8, false);
	
	private static ArrayList<GameDetails> details() {
		ArrayList<GameDetails> details = new ArrayList();
		
		details.add(detail1);
		details.add(detail2);
		
		return details;
	}
	
	private static User user1 = new User(1, "admin123@gmail.com", "Admin", "1q2w3e4r", "Admin");
	private static User user2 = new User(2, "coso@gmail.com", "Coso", "CosoPassword", "User");
	
	private static User[] listUsers = {
		user1,
		user2
	};
	
	private static Stream<User> streamUsers() {
		return Stream.of(listUsers);
	}
	
	@Test
	@Order(1)
	void testFindAll() {
		ArrayList<User> findAll = userDAO.findAll();
		assertNotNull(findAll);
		assertTrue(findAll.size() == listUsers.length);
		
		for (int i = 0; i < listUsers.length; i++) {
			User userFind = findAll.get(i);
			User userList = listUsers[i];
			userList.setDetails(details());
			
			assertTrue(userFind.getId() == userList.getId());
			assertTrue(userFind.getEmail().equals(userList.getEmail()));
			assertTrue(userFind.getNick().equals(userList.getNick()));
			assertTrue(userFind.getPassword().equals(userList.getPassword()));
			assertTrue(userFind.getRole().equals(userList.getRole()));
			for (int j = 0; j < userList.getDetails().size(); j++) {
				GameDetails detailFind = userFind.getDetails().get(j);
				GameDetails detailList = userList.getDetails().get(j);
				
				assertTrue(detailFind.getGame().getId() == detailList.getGame().getId());
				assertTrue(detailFind.isGuessed() == detailList.isGuessed());
				assertTrue(detailFind.getNumTries() == detailList.getNumTries());
			}
		}
	}

	@ParameterizedTest
	@MethodSource("streamUsers")
	@Order(2)
	void testFindById(User user) {
		user.setDetails(details());
		User findById = userDAO.findById(user.getId());
		
		assertNotNull(findById);
		
		assertTrue(findById.getId() == user.getId());
		assertTrue(findById.getEmail().equals(user.getEmail()));
		assertTrue(findById.getNick().equals(user.getNick()));
		assertTrue(findById.getPassword().equals(user.getPassword()));
		assertTrue(findById.getRole().equals(user.getRole()));
		for (int j = 0; j < findById.getDetails().size(); j++) {
			GameDetails detailFind = findById.getDetails().get(j);
			GameDetails detailList = user.getDetails().get(j);
			
			assertTrue(detailFind.getGame().getId() == detailList.getGame().getId());
			assertTrue(detailFind.isGuessed() == detailList.isGuessed());
			assertTrue(detailFind.getNumTries() == detailList.getNumTries());
		}
	}
}
