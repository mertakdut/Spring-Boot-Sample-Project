package com.demo.application.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.application.model.Contact;
import com.demo.application.service.IUserService;

@Controller
@RequestMapping("/")
public class HomeController {

	// @Autowired
	// private ContactRepository contactRepository;

	@Autowired
	private IUserService userService;

	@GetMapping
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);
		// model.addAttribute("fileContents", fileReader.getFileContents());
		// model.addAttribute("contactList", contactRepository.list());
		// model.addAttribute("myContact", contactRepository.get(28));
		model.addAttribute("myContact", userService.getUser());

		Contact contact = new Contact();
		contact.setAddress("adres");
		contact.setName(formattedDate);
		contact.setEmail("emayýl");
		contact.setTelephone("05336867023");
		// contactRepository.save(contact);

		return "home";
	}
}
