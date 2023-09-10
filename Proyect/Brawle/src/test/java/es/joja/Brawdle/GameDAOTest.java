package es.joja.Brawdle;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

import es.joja.Brawdle.dao.GameDAO;
import es.joja.Brawdle.entity.Game;
import es.joja.Brawdle.entity.Legend;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/brawdletest.sql"})
class GameDAOTest {

	@Autowired
	GameDAO gameDAO;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	private static Legend legend = new Legend(1, "Bodvar", null, "Male", 2014, null);
	
	private static Game game1 = new Game(1, legend);
	private static Game game2 = new Game(2, legend);
	private static Game game3 = new Game(3, legend);
	
	private static Game listGamesTest[] = {
		game1,
		game2,
		game3
	};
	
	private static Stream<Game> streamGames(){
		return Stream.of(listGamesTest);
	}
	
	@Test
	@Order(1)
	void testFindAll() {
		ArrayList<Game> findAll = gameDAO.findAll();
		assertNotNull(findAll);
		assertTrue(findAll.size() == listGamesTest.length);
		
		for (int i = 0; i < findAll.size(); i++) {
			Game gameFindAll = findAll.get(i);
			Game gameList = listGamesTest[i];
			assertTrue(gameFindAll.getId() == gameList.getId());
			assertTrue(gameFindAll.getLegend().getId() == gameList.getLegend().getId());
		}
	}

	@ParameterizedTest
	@MethodSource("streamGames")
	@Order(2)
	void testFindById(Game game) {
		Game findById = gameDAO.findById(game.getId());
		
		assertNotNull(findById);
		
		assertTrue(findById.getLegend().getId() == game.getLegend().getId());
	}
	
	@Test
	@Order(3)
	void testSave() {
		Game game = new Game(20, legend);
		Game save = gameDAO.save(game);
		
		assertNotNull(save);
		Game findById = gameDAO.findById(save.getId());
		assertTrue(20 == findById.getId());
		assertTrue(legend.getId() == findById.getLegend().getId());
		
		game = new Game(null, legend);
		save = gameDAO.save(game);
		
		assertNotNull(save);
		
		findById = gameDAO.findById(save.getId());
		assertNotNull(findById);
		assertTrue(save.getLegend().getId() == findById.getLegend().getId());
	}
	
	@Test
	@Order(4)
	void testDelete() {
		boolean ok = gameDAO.delete(2);
		assertTrue(ok);
		Game findById = gameDAO.findById(2);
		assertNull(findById);
	}
	
	@Test
	@Order(5)
	void testUpdate() {
		Legend legendUp = new Legend(2, "Cassidy", null, "Female", 2014, null);
		Game game = new Game(1, legendUp);
		
		boolean ok = gameDAO.update(game);
		assertTrue(ok);
		
		Game findById = gameDAO.findById(1);
		
		assertTrue(findById.getLegend().getId() == legendUp.getId());
	}
	
	@Test
	@Order(6)
	void testGetLast() {
		
		Game getLast = gameDAO.getLast();
		
		assertNotNull(getLast);
	}
}
