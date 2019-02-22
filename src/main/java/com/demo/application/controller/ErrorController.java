package com.demo.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	// Should I create a seperate Constants class for these kind of constants?
	public static final String VIEW_INTERNAL_SERVER_ERROR = "error/error";
	public static final String VIEW_NOT_FOUND = "error/404";

	public static final String URL_USER_NOT_FOUND_ERROR = "/error/user_not_found";
	public static final String URL_INTERNAL_SERVER_ERROR = "/error/error";

	@GetMapping(value = URL_USER_NOT_FOUND_ERROR)
	public String showUserNotFoundPage() {
		return VIEW_NOT_FOUND;
	}

	@GetMapping(value = URL_INTERNAL_SERVER_ERROR)
	public String showInternalServerErrorPage() {
		return VIEW_INTERNAL_SERVER_ERROR;
	}
}
