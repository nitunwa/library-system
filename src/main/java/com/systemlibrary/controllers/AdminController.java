package com.systemlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemlibrary.models.UserDao;
import com.systemlibrary.models.User;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/showDashboard",method=RequestMethod.GET)
	public String showDashboard(Model model) {
		return "admin/showDashboard";
	}
	
	@RequestMapping(value="/createuser",method=RequestMethod.GET)
	public String createUser(Model model) {
		return "admin/createUser";
	}
	
	@RequestMapping(value = "/createuser",method=RequestMethod.POST)
	@ResponseBody
	public String create(Model model, @RequestParam(value = "name") String name,@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,@RequestParam(value = "role") String role) {
		try {
			User user = new User(name,email,password,role);
			userDao.create(user);
		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "User succesfully created!";
	}
}
