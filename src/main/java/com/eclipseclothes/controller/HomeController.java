package com.eclipseclothes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "home/index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "home/register";
	}
	
	@GetMapping("/login")
	public String login() {
		return "home/login";
	}
	
	
}
