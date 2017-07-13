package com.systemlibrary.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.systemlibrary.models.State;

@Controller
@RequestMapping("/test")
public class TestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/scopeTest", method = RequestMethod.GET)
	@ResponseBody
	public String showDemoScop(Model model, @RequestParam(value = "name", required = false) String name) {
		String scopeName = "sujan";
		if (name != null && !name.isEmpty()) {
			scopeName = name;
		}
		return scopeName;
	}

	@RequestMapping(value = "/testpage", method = RequestMethod.GET)
	public String testPage(Model model, @RequestParam(value = "email") String email,
			@RequestParam(value = "pass") String pass) {
		logger.info("email : " + email + " pass=" + pass);
		model.addAttribute("email", email);
		model.addAttribute("pass", pass);
		return "test/testpage";
	}

	@RequestMapping(value = "/librarytest", method = RequestMethod.GET)

	public String librarytest(Model model, @RequestParam(value = "email") String email,
			@RequestParam(value = "pass") String pass, @RequestParam(value = "address") String address) {
		logger.info("email : " + email + " pass=" + pass + "address:" + address);
		model.addAttribute("email", email);
		model.addAttribute("pass", pass);
		model.addAttribute("address", address);
		return "test/librarytest";
	}
	public List<State> createState() {

		List<State> mylist = new ArrayList<State>();
		State wa = new State("wa","Washington");
		State cal = new State("cal","California");
		mylist.add(wa);
		mylist.add(cal);
		
		return mylist;
	}
	@RequestMapping(value = "/demoIndex", method = RequestMethod.GET)
	public String showDemo(Model model) {
		List<State> list =createState();
		logger.info("list  size:::" +list.size());
		model.addAttribute("mylist",list);
		model.addAttribute("state", "cal");
		return "test/demoIndex";
	}

	

	@RequestMapping(value = "/demoIndex", method = RequestMethod.POST)
	public String createDamo(Model model, @RequestParam(value = "demoname") String name,
			@RequestParam(value = "demoemail") String email, @RequestParam(value = "demopass") String pass) {
		logger.info("demoname : " + name + " demoemail=" + email + "demopass" + pass);
		model.addAttribute("demoname", name);
		model.addAttribute("demoemail", email);
		model.addAttribute("demopass", pass);
		
		
		return "test/demoResult";
	}

}
