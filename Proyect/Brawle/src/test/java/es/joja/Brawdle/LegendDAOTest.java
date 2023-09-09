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
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	@Order(1)
	void testFindAll() {
		ArrayList<Legend> legends = legendDAO.findAll();
		assertNotNull(legends);
	}

}
