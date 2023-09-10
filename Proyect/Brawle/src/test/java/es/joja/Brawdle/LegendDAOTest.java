package es.joja.Brawdle;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import es.joja.Brawdle.dao.LegendDAO;
import es.joja.Brawdle.entity.Legend;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Sql(scripts = {"/brawdletest.sql"})
class LegendDAOTest {

	@Autowired
	private LegendDAO legendDAO;
	
	private static ArrayList<String> races1(){
		ArrayList<String> races1 = new ArrayList();
		
		races1.add("Humanoid");
		races1.add("Animaloid");
		
		return races1;
	}
	
	private static String[] weapons1 = {
		"Grapple Hammer",
		"Sword"
	};
	
	private static Legend legend1 = new Legend(1, "Bödvar", races1(), "Male", 2014, weapons1);
	
	private static ArrayList<String> races2(){
		ArrayList<String> races2 = new ArrayList();
		
		races2.add("Humanoid");
		
		return races2;
	}
	
	private static String[] weapons2 = {
		"Blasters",	
		"Grapple Hammer"
	};
	
	private static Legend legend2 = new Legend(2, "Cassidy", races2(), "Female", 2014, weapons2);
	
	private static Legend[] listLegends = {
		legend1,
		legend2
	};
	
	private static Stream<Legend> streamLegends() {
		return Stream.of(listLegends);
	}
	
	private static String[] listWeapons = {
		"Grapple Hammer",
		"Sword",
		"Blasters"
	};
	
	private static Stream<String> streamWeapons(){
		return Stream.of(listWeapons);
	}
	
	private static String[] listRaces = {
			"Humanoid",
			"Fictional",
			"Animaloid",
			"Robotic"
	};
	
	private static Stream<String> streamRaces(){
		return Stream.of(listRaces);
	}
	
	private static Integer[] listYears = {
			2014,
			2015
	};
	
	private static Stream<Integer> streamYears(){
		return Stream.of(listYears[1]);
	}
	
	private static String[] listGenders = {
			"Male",
			"Female",
			"Unknown"
	};
	
	private static Stream<String> streamGenders(){
		return Stream.of(listGenders);
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	@Order(1)
	void testFindAll() {
		ArrayList<Legend> findAll = legendDAO.findAll();
		assertNotNull(findAll);
		assertTrue(findAll.size() == listLegends.length);
		
		for (int i = 0; i < listLegends.length; i++) {
			Legend legendFindAll = findAll.get(i);
			Legend legendList = listLegends[i];
			
			assertTrue(legendFindAll.getId() == legendList.getId());
			assertTrue(legendFindAll.getName().equals(legendList.getName()));
			assertTrue(legendFindAll.getGender().equals(legendList.getGender()));
			assertTrue(legendFindAll.getYear() == legendList.getYear());
			
			for (int j = 0; j < legendList.getRaces().size(); j++) {
				assertTrue(legendFindAll.getRaces().get(j).equals(legendList.getRaces().get(j)));
			}
			for (int j = 0; j < legendList.getWeapons().length; j++) {
				assertTrue(legendFindAll.getWeapons()[j].equals(legendList.getWeapons()[j]));
			}
		}
	}
	
	@ParameterizedTest
	@MethodSource("streamLegends")
	@Order(2)
	void testFindById(Legend legend) {
		Legend findById = legendDAO.findById(legend.getId());
		
		assertNotNull(findById);
		
		assertTrue(findById.getId() == legend.getId());
	}
	
	@ParameterizedTest
	@MethodSource("streamLegends")
	@Order(3)
	void testFindByName(Legend legend) {
		Legend findByName = legendDAO.findByName(legend.getName());
		
		assertNotNull(findByName);
		
		assertTrue(findByName.getName().equals(legend.getName()));
	}
	
	@ParameterizedTest
	@MethodSource("streamWeapons")
	@Order(4)
	void testFindWeapon(String weapon) {
		String findWeapon = legendDAO.findWeapon(weapon);
		
		assertNotNull(findWeapon);
		
		assertTrue(findWeapon.equals(weapon));
	}
	
	@ParameterizedTest
	@MethodSource("streamRaces")
	@Order(5)
	void testFindRace(String race) {
		String findRace = legendDAO.findRace(race);
		
		assertNotNull(findRace);
		
		assertTrue(findRace.equals(race));
	}
	
	@ParameterizedTest
	@MethodSource("streamYears")
	@Order(6)
	void testFindYear(Integer year) {
		int intYear = (int) year;
		int findYear = legendDAO.findYear(intYear);
		
		assertNotNull(findYear);
		
		assertTrue(findYear == intYear);
	}
	
	@ParameterizedTest
	@MethodSource("streamGenders")
	@Order(7)
	void testFindGender(String gender) {
		String findGender = legendDAO.findGender(gender);
		
		assertNotNull(findGender);
		
		assertTrue(findGender.equals(gender));
	}
	
	@Test
	@Order(4)
	void testSave() {
		ArrayList<String> races = new ArrayList();
		races.add("Humanoid");
		String[] weapons = {"Sword", "Blasters"};
		Legend legend = new Legend(20, "Test1", races, "Unknown", 2015, weapons);
		
		Legend save = legendDAO.save(legend);
		assertNotNull(save);
		
		Legend findById = legendDAO.findById(save.getId());
		assertTrue(20 == findById.getId());
		assertTrue(findById.getName().equals("Test1"));
		assertTrue(findById.getGender().equals("Unknown"));
		assertTrue(findById.getYear() == 2015);
		for (int i = 0; i < findById.getWeapons().length; i++) {
			assertTrue(findById.getWeapons()[i].equals(weapons[i]));
		}
		
		for (int i = 0; i < findById.getRaces().size(); i++) {
			assertTrue(findById.getRaces().get(i).equals(races.get(i)));
		}
		
		legend = new Legend(null, "Test2", races, "Unknown", 2014, weapons);
		
		save = legendDAO.save(legend);
		assertNotNull(save);
		
		findById = legendDAO.findById(save.getId());
		assertTrue(save.getId() == findById.getId());
		assertTrue(findById.getName().equals("Test2"));
		assertTrue(findById.getGender().equals("Unknown"));
		assertTrue(findById.getYear() == 2014);
		for (int i = 0; i < findById.getWeapons().length; i++) {
			assertTrue(findById.getWeapons()[i].equals(weapons[i]));
		}
		
		for (int i = 0; i < findById.getRaces().size(); i++) {
			assertTrue(findById.getRaces().get(i).equals(races.get(i)));
		}
	}
	
	@Test
	@Order(5)
	void testSaveWeapon() {
		
	}
	
	@Test
	@Order(6)
	void testSaveYear() {
		
	}
	
	@Test
	@Order(7)
	void testDelete() {
		
	}
	
	@Test
	@Order(8)
	void testDeleteWeapon() {
		
	}
	
	@Test
	@Order(9)
	void testUpdate() {
		
	}
	
	@Test
	@Order(10)
	void testUpdateWeapon() {
		
	}
}
