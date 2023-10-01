package com.euparliament.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WebController {
	
	@GetMapping("/")
	public String index(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "index";
	}
	
	@GetMapping("/citizen")
	public String citizen_index(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "citizen_web/index";
	}
	
	@GetMapping("/citizen/login")
	public String citizen_login(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "citizen_web/login";
	}
	
	@GetMapping("/citizen/registration")
	public String citizen_registration(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "citizen_web/registration";
	}
	
	@GetMapping("/citizen/home")
	public String citizen_home(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "citizen_web/home";
	}
	
	@GetMapping("/citizen/referendum")
	public String citizen_referendum(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "citizen_web/referendum";
	}
	
	@GetMapping("/citizen/results")
	public String citizen_referendum_results(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "citizen_web/referendum_results";
	}
	
	@GetMapping("/citizen/vote")
	public String citizen_referendum_vote(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "citizen_web/referendum_vote";
	}
	
	@GetMapping("/inst/login")
	public String inst_login(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "inst_web/login";
	}
	
	@GetMapping("/inst/home")
	public String inst_home(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "inst_web/home";
	}
	
	@GetMapping("/inst/referendum")
	public String inst_referendum(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "inst_web/referendum";
	}
	
	@GetMapping("/inst/propose")
	public String inst_propose(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "inst_web/propose_referendum";
	}
	
	@GetMapping("/inst/results")
	public String inst_referendum_results(
			@RequestParam(name="name", required=false, defaultValue="") String name,
			Model model
	){
		model.addAttribute("name", name);
		return "inst_web/referendum_results";
	}
	
}
