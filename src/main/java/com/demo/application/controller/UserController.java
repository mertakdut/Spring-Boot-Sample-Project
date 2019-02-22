package com.demo.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.application.model.User;
import com.demo.application.service.ContactRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	// @AutoWired
	// private UserService userService;
	
	@Autowired
	private ContactRepository contactRepository;

	@PostMapping(value = "/get")
	public String user(@Validated User user, Model model) {
		System.out.println("User Page Requested");
		model.addAttribute("userName", contactRepository.get(28).getName());
		return "user/showUser";
	}

	@PostMapping("/remove/{id}")
	public String removeReview(@PathVariable("id") int id) {
		// this.userService.remove(id);
		return "redirect:/user/showUser";
	}

}
