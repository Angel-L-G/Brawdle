package es.joja.Brawdle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.joja.Brawdle.dao.GameDAO;
import es.joja.Brawdle.dao.LegendDAO;
import es.joja.Brawdle.dao.UserDAO;
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
		app.getAttribute()
		
		return modelAndView;
	}
	
}
