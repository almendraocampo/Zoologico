package com.zoologico.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		return "home.html";
	}
	
	@GetMapping("/imagenes")
	public String imagenes(Model model) {
		return "galeria.html";
	}
	
}
