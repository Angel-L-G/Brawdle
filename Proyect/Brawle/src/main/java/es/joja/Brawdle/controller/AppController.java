package es.joja.Brawdle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.joja.Brawdle.dao.GameDAO;
import es.joja.Brawdle.dao.LegendDAO;
import es.joja.Brawdle.dao.UserDAO;
import es.joja.Brawdle.entity.Game;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

public class AppController {
	
	@Autowired
	UserDAO userDAO;
	@Autowired
	LegendDAO legendDAO;
	@Autowired
	GameDAO gameDAO;
	
	@RequestMapping("/")
	public ModelAndView index(
			//@RequestParam(value = "nombre", required = false) String nom
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		ServletContext app = request.getServletContext();
		Game game =(Game) app.getAttribute("game");
		
		if (game==null) {
			//game = gameDAO.getLast();
		}
		
		modelAndView.setViewName("login.jsp");
		
		return modelAndView;
	}
	
}
