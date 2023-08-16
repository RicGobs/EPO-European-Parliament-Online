package com.euparliament;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EuparliamentController {
	
	@GetMapping("/")
	public String index(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		return "index";
	}
	
	@GetMapping("/login")
	public String login(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		return "login";
	}
}