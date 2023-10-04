package com.euparliament.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.euparliament.web.utils.HttpRequest;
import com.euparliament.web.exception.NotFoundException;
import com.euparliament.web.model.CitizenUser;
import com.euparliament.web.model.InstUser;
import com.euparliament.web.model.Referendum;
import com.euparliament.web.model.ResourceMapping;


@Controller
public class WebController {

	@Autowired
	ResourceMapping resourceMapping;
	
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
			@RequestParam(name="uname", required=false, defaultValue="") String nationalID, 
			@RequestParam(name="psw", required=false, defaultValue="") String password,
			Model model
	){
		if (nationalID.equals("") && password.equals("")) {
			return "citizen_web/login";
		}
		try {
			// get citizen data structure from the database
			CitizenUser citizen;
			citizen = HttpRequest.getCitizenUser(
					nationalID,
					password,
					resourceMapping
			);
			if (citizen == null) {
				return "citizen_web/login";
			}
			return "citizen_web/home";
		} catch (NotFoundException e) {
			// citizen not found
			return "citizen_web/login";
		}
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
			@RequestParam(name="title", required=false, defaultValue="") String title,
			@RequestParam(name="date", required=false, defaultValue="") String date,
			Model model
	){
		try {
			// get referendum data structure from the database
			Referendum referendum;
			referendum = HttpRequest.getReferendum(
					title,
					date,
					resourceMapping
			);
			model.addAttribute("title", title);
			return "citizen_web/referendum_vote";
		} catch (NotFoundException e) {
			// representative not found
			return "citizen_web/referendum";
		}
	}

	@GetMapping("/inst/login")
	public String inst_login(
			@RequestParam(name="uname", required=false, defaultValue="") String representativeID, 
			@RequestParam(name="psw", required=false, defaultValue="") String password,
			Model model
	){
		if (representativeID.equals("") && password.equals("")) {
			return "citizen_web/login";
		}
		try {
			// get representative data structure from the database
			InstUser representative;
			representative = HttpRequest.getInstUser(
					representativeID,
					password,
					resourceMapping
			);
			if (representative == null) {
				return "inst_web/login";
			}
			return "inst_web/home";
		} catch (NotFoundException e) {
			// representative not found
			return "inst_web/login";
		}
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
