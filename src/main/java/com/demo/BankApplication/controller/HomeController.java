package com.demo.BankApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping(value = {"/", "/users", "/currency", "/transfer", "/history" })
	public String index() {
		return "index";
	}

}
