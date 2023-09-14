package es.joja.Brawdle.controller;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.joja.Brawdle.dao.GameDAO;
import es.joja.Brawdle.dao.LegendDAO;
import es.joja.Brawdle.dao.UserDAO;
import es.joja.Brawdle.entity.Game;
import es.joja.Brawdle.entity.Legend;
import es.joja.Brawdle.entity.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	UserDAO userDAO;
	@Autowired
	LegendDAO legendDAO;
	@Autowired
	GameDAO gameDAO;

	@RequestMapping("/")
	public ModelAndView index(
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		Logger debug = Logger.getLogger("debug");

		ServletContext app = request.getServletContext();
		Game game = (Game) app.getAttribute("game");

		if (game == null) {
			// game = gameDAO.getLast();
		}

		modelAndView.setViewName("loginRegister.jsp");
		return modelAndView;
	}

	@RequestMapping("login")
	public ModelAndView login(
			HttpServletRequest request) {

		Logger debug = Logger.getLogger("debug");

		String nick = request.getParameter("nick");

		String plainPassword = request.getParameter("password");

		HttpSession session = request.getSession();
		User user = new User("Angel", "abc");
		// User user = userDAO.findByNick(nick);

		ModelAndView modelAndView = new ModelAndView();

		if (user != null) {
			if (BCrypt.checkpw(plainPassword, user.getPassword())) {

				session.setAttribute("user", user);
				modelAndView.setViewName("index.jsp");
			} else {

				modelAndView.addObject("mensaje", "pass de " + nick + " mal");
				modelAndView.setViewName("login.jsp");
			}
		} else {

			modelAndView.addObject("mensaje", "usuario " + nick + " no existe");
			modelAndView.setViewName("login.jsp");
		}
		return modelAndView;
	}

	@RequestMapping("/register")
	public ModelAndView register(
			HttpServletRequest request) {
		Logger debug = Logger.getLogger("debug");

		String nick = request.getParameter("nick");
		String password = request.getParameter("password");
		String gensalt = BCrypt.gensalt();
		String hashpw = BCrypt.hashpw(password, gensalt);
		User user = new User(nick, hashpw);
		user.setNick(nick);
		user.setPassword(hashpw);
		userDAO.save(user);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index.jsp");
		modelAndView.addObject("usuario", user);
		return modelAndView;
	}

	private void NewGame(HttpServletRequest request) {

		ArrayList<Legend> all = legendDAO.findAll();
		int rand = (int) (Math.random() * all.size() + 1);

		Game game = new Game(null, all.get(rand));
		gameDAO.save(game);

		ServletContext app = request.getServletContext();
		app.setAttribute("game", game);

		game.setLegend(legendDAO.findById(1));
		gameDAO.save(game);

	}
}