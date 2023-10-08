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
			Model model
	){ 
		String nation = resourceMapping.getNation();
		model.addAttribute("nation", nation);
		return "index";
	}
	
	@GetMapping("/citizen")
	public String citizen_index(
			@RequestParam(name="nation", required=false, defaultValue="") String nation,
			Model model
	){
		if (nation.equals("")) nation = resourceMapping.getNation();
		model.addAttribute("nation", nation);
		return "citizen_web/index";
	}
	
	@GetMapping("/citizen/login")
	public String citizen_login( 
			@RequestParam(name="nationalID", required=false, defaultValue="") String nationalID, 
			@RequestParam(name="psw", required=false, defaultValue="") String password,
			@RequestParam(name="nation", required=false, defaultValue="") String nation,
			Model model
	){
		if (nationalID.equals("") && password.equals("")) {
			model.addAttribute("nation", nation);
			return "citizen_web/login";
		}
		try {
			// get citizen data structure from the database
			CitizenUser citizen = HttpRequest.getCitizenUser(
					nationalID,
					password,
					resourceMapping
			);
			if (citizen == null) {
				model.addAttribute("nation", nation);
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
			@RequestParam(name="nationalID", required=false, defaultValue="") String nationalID,
			@RequestParam(name="nation", required=false, defaultValue="") String nation,
			Model model
	){
		if (!nationalID.equals("")) return "citizen_web/index";
		model.addAttribute("nation", nation);
		return "citizen_web/registration";
	}
	
	@GetMapping("/citizen/home")
	public String citizen_home(
			@RequestParam(name="nationalID", required=true, defaultValue="") String nationalID,
			Model model
	){
		try {
			// check if citizen is in the database
			HttpRequest.checkCitizenUser(
					nationalID,
					resourceMapping
			);
			return "citizen_web/home";
		} catch (NotFoundException e) {
			// citizen not found
			return "index";
		}
	}
	
	@GetMapping("/citizen/referendum")
	public String citizen_referendum(
			@RequestParam(name="nationalID", required=true, defaultValue="") String nationalID,
			Model model
	){
		try {
			// check if citizen is in the database
			HttpRequest.checkCitizenUser(
					nationalID,
					resourceMapping
			);
			return "citizen_web/referendum";
		} catch (NotFoundException e) {
			// citizen not found
			return "index";
		}
	}

	@GetMapping("/citizen/results")
	public String citizen_referendum_results(
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
			model.addAttribute("argument", referendum.getArgument());
			model.addAttribute("startDate", referendum.getDateEndConsensusProposal());
			model.addAttribute("status", referendum.getStatus());
			model.addAttribute("endDate", referendum.getDateEndConsensusResult());
			model.addAttribute("votesTrue", referendum.getVotesTrue());
			model.addAttribute("votesFalse", referendum.getVotesFalse());
			model.addAttribute("abstentions", referendum.getPopulation() - (referendum.getVotesFalse() + referendum.getVotesTrue()));
			return "citizen_web/referendum_results";
		} catch (NotFoundException e) {
			// referendum not found
			return "citizen_web/referendum";
		}
	}
	
	@GetMapping("/citizen/vote")
	public String citizen_referendum_vote( 
			@RequestParam(name="title", required=true, defaultValue="") String title,
			@RequestParam(name="date", required=true, defaultValue="") String date,
			@RequestParam(name="nationalID", required=true, defaultValue="") String nationalID,
			Model model
	){
		try {
			// check if citizen is in the database
			HttpRequest.checkCitizenUser(
					nationalID,
					resourceMapping
			);
			// get referendum data structure from the database
			Referendum referendum;
			referendum = HttpRequest.getReferendum(
					title,
					date,
					resourceMapping
			);
			model.addAttribute("title", title);
			model.addAttribute("argument", referendum.getArgument());
			model.addAttribute("startDate", referendum.getDateEndConsensusProposal());
			model.addAttribute("status", referendum.getStatus());
			model.addAttribute("endDate", referendum.getDateEndConsensusResult());
			model.addAttribute("votesTrue", referendum.getVotesTrue());
			model.addAttribute("votesFalse", referendum.getVotesFalse());
			model.addAttribute("abstentions", referendum.getPopulation() - (referendum.getVotesFalse() + referendum.getVotesTrue()));
			return "citizen_web/referendum_vote";
		} catch (NotFoundException e) {
			// representative not found
			return "citizen_web/referendum";
		}
	}


	@GetMapping("/inst/login")
	public String inst_login(
			@RequestParam(name="representativeID", required=false, defaultValue="") String representativeID, 
			@RequestParam(name="psw", required=false, defaultValue="") String password,
			@RequestParam(name="nation", required=false, defaultValue="") String nation,
			Model model
	){
		if (representativeID.equals("") && password.equals("")) {
			model.addAttribute("nation", nation);
			return "inst_web/login";
		}
		try {
			// get representative data structure from the database
			InstUser representative = HttpRequest.getInstUser(
					representativeID,
					password,
					resourceMapping
			);
			if (representative == null) {
				model.addAttribute("nation", nation);
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
			@RequestParam(name="representativeID", required=true, defaultValue="") String representativeID,
			Model model
	){
		try {
			// check if national representative is in the database
			HttpRequest.checkCitizenUser(
					representativeID,
					resourceMapping
			);
			return "inst_web/home";
		} catch (NotFoundException e) {
			// national representative not found
			return "index";
		}
	}
	
	@GetMapping("/inst/referendum")
	public String inst_referendum(
			@RequestParam(name="representativeID", required=true, defaultValue="") String representativeID,
			Model model
	){
		try {
			// check if national representative is in the database
			HttpRequest.checkCitizenUser(
					representativeID,
					resourceMapping
			);
			return "inst_web/referendum";
		} catch (NotFoundException e) {
			// national representative not found
			return "index";
		}
	}
	
	@GetMapping("/inst/propose")
	public String inst_propose(
			@RequestParam(name="representativeID", required=true, defaultValue="") String representativeID,
			@RequestParam(name="title", required=false, defaultValue="") String title,
			@RequestParam(name="message", required=false, defaultValue="") String message,
			Model model
	){
		try {
			// check if national representative is in the database
			HttpRequest.checkCitizenUser(
					representativeID,
					resourceMapping
			);
		if (!title.equals("") && !message.equals("")) {
			model.addAttribute("representativeID", representativeID);
			return "inst_web/home";
		}
		return "inst_web/propose_referendum";
		} catch (NotFoundException e) {
			// national representative not found
			return  "index";
		}
	}

	@GetMapping("/inst/vote")
	public String inst_vote(
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
			model.addAttribute("argument", referendum.getArgument());
			model.addAttribute("startDate", referendum.getDateEndConsensusProposal());
			model.addAttribute("status", referendum.getStatus());
			model.addAttribute("endDate", referendum.getDateEndConsensusResult());
			model.addAttribute("votesTrue", referendum.getVotesTrue());
			model.addAttribute("votesFalse", referendum.getVotesFalse());
			model.addAttribute("abstentions", referendum.getPopulation() - (referendum.getVotesFalse() + referendum.getVotesTrue()));
			return "inst_web/referendum_vote";
		} catch (NotFoundException e) {
			// referendum not found
			return "inst_web/referendum";
		}
	}

	@GetMapping("/inst/results")
	public String inst_referendum_results(
			@RequestParam(name="representativeID", required=true, defaultValue="") String representativeID,
			@RequestParam(name="title", required=false, defaultValue="") String title,
			@RequestParam(name="date", required=false, defaultValue="") String date,
			Model model
	){
		try {
			// check if national representative is in the database
			HttpRequest.checkCitizenUser(
					representativeID,
					resourceMapping
			);
			// get referendum data structure from the database
			Referendum referendum;
			referendum = HttpRequest.getReferendum(
					title,
					date,
					resourceMapping
			);
			model.addAttribute("title", title);
			model.addAttribute("argument", referendum.getArgument());
			model.addAttribute("startDate", referendum.getDateEndConsensusProposal());
			model.addAttribute("status", referendum.getStatus());
			model.addAttribute("endDate", referendum.getDateEndConsensusResult());
			model.addAttribute("votesTrue", referendum.getVotesTrue());
			model.addAttribute("votesFalse", referendum.getVotesFalse());
			model.addAttribute("abstentions", referendum.getPopulation() - (referendum.getVotesFalse() + referendum.getVotesTrue()));
			return "inst_web/referendum_results";
		} catch (NotFoundException e) {
			// referendum not found
			return "inst_web/referendum";
		}
	}
	
}
