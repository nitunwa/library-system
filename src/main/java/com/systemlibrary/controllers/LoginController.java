package com.systemlibrary.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemlibrary.models.UserDao;
import com.systemlibrary.models.Book;
import com.systemlibrary.models.User;

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

	@RequestMapping(value = "/singin", method = RequestMethod.POST)
	public String singin(Model model, @RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		logger.info("Verifiing user: " + email);
		try {
			User user = userDao.getByEmail(email);

			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				logger.info("valid user");

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
	
	@RequestMapping(value="/userReport",method=RequestMethod.GET)
	public String showUser(Model model) {
		List<User> userList = userDao.getAllUser();
		logger.info("user size: " +userList.size());
		model.addAttribute("userList", userList);
	
		return "auth/userReport";
	}
}
