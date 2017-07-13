package com.systemlibrary.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.systemlibrary.models.AuthenticationDto;
import com.systemlibrary.models.User;
import com.systemlibrary.models.UserDao;
import com.systemlibrary.utility.BookErrorType;
import com.systemlibrary.utility.LoginError;

@Controller
@RequestMapping("/auth")
public class LoginController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/singin", method = RequestMethod.GET)
	public String singin(Model model) {
		return "singin";
	}
	@RequestMapping(value = "/singinRest", method = RequestMethod.GET)
	public String singinRest(Model model) {
		return "singinRest";
	}

	@RequestMapping(value = "/singout", method = RequestMethod.GET)
	public String singout(Model model, HttpSession httpSession) {
		httpSession.setAttribute("loginUser", null);
		return "/auth/singin";
	}

	@RequestMapping(value = "/singin", method = RequestMethod.POST)
	public String singin(Model model, @RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, HttpSession httpSession) {
		logger.info("Verifiing user: " + email);
		try {
			User user = userDao.getByEmail(email);

			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				logger.info("valid user");
				httpSession.setAttribute("loginUser", user);
				if (user.getRole().equals("admin")) {
					logger.info("valid role");
					return "redirect:/admin/showDashboard";

				} else if (user.getRole().equals("librarian")) {
					logger.info("valid librarian");
					return "redirect:/library/showDashboard";
				} else {
					return "redirect:/member/borrowBook";
				}
			} else {
				logger.info("invalid user");
				return "redirect:/signin";
			}
		} catch (Exception ex) {
			logger.error("Login Fail", ex);
			return "redirect:/login";
		}

	}
	// #################################

//	@RequestMapping(value = "/index/{email}/{password}", method = RequestMethod.POST)
//	public ResponseEntity<?> checkEmail(@PathVariable(value = "email") String email,
//			@PathVariable(value = "password") String password, HttpSession httpSession) {
//		AuthenticationDto authDto = new AuthenticationDto();
//		User user = userDao.getByEmail(email);
//		logger.info("Valid email : " + email);
//		try {
//			if (user.getPassword().equals(password)) {
//				logger.info("Valid user : ");
//				httpSession.setAttribute("loginUser", user);
//				authDto.setStatus(true);
//
//				if (user.getRole().equals("admin")) {
//					logger.info("Valid Admin user : ");
//					authDto.setRole("admin");
//				} else if (user.getRole().equals("Librarian")) {
//					logger.info("Valid labrarian");
//					authDto.setRole("Librarian");
//				} else {
//					logger.info("Valid user");
//					authDto.setRole("user");
//				}
//				return new ResponseEntity<>(HttpStatus.OK);
//			} else {
//				logger.info("Invalid user");
//				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//			}
//
//		}
//
//		catch (Exception e) {
//			logger.error("Login fail");
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//
//	}

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// rest service

	AuthenticationDto authDto = new AuthenticationDto();

	@RequestMapping(value = "/loginInfo/{email}/{password}", method = RequestMethod.POST)
	public ResponseEntity<?> login(@PathVariable(value = "email") String email,
			@PathVariable(value = "password") String password, HttpSession httpSession) {

		logger.info("Verifiing user: " + email);
		try {
			User user = userDao.getByEmail(email);

			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {

				logger.info("valid user");
				httpSession.setAttribute("loginUser", user);
				;
				authDto.setStatus(true);
				if (user.getRole().equals("admin")) {
					logger.info("admin role");
					authDto.setRole("Admin");

				} else if (user.getRole().equals("librarian")) {
					logger.info("librarian role");
					authDto.setRole("librarian");
					Thread.sleep(10000);
				} else {
					logger.info("user role");
					authDto.setRole("User");
				}
				return new ResponseEntity(authDto, HttpStatus.OK);
			} else {
				logger.info("invalid user");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			logger.error("Login Fail", ex);

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	@RequestMapping(value = "/userReport", method = RequestMethod.GET)
	public String showUser(Model model) {
		List<User> userList = userDao.getAllUser();
		logger.info("user size: " + userList.size());
		model.addAttribute("userList", userList);

		return "auth/userReport";
	}
}
