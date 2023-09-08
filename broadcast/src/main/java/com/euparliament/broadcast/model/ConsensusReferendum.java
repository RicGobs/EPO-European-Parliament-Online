package com.euparliament.broadcast.model;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ConsensusReferendum {

  	private Integer status; // 2 for proposal -  4 for referendum results
  	private ConsensusReferendumId id;

	private String correct;
	private Boolean decision; //answer to proposal or to referendum
	private Integer round;
	private String proposals;
	private String receivedFrom;

	public ConsensusReferendum() {}
	
	public ConsensusReferendum(String title, String dateStart, Integer status) {
		this.id = new ConsensusReferendumId(title, dateStart);
		this.status = status;
		
		this.correct = "ita,ger,fra"; // TODO: get from docker-compose-all.yaml
		this.decision = null;
		this.round = 1;
		this.proposals = "";
		this.receivedFrom = "";
	}

	public ConsensusReferendumId getId() {
		return this.id;
	}
	
	public void setId(ConsensusReferendumId id) {
		this.id = id;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public String getCorrect() {
		return this.correct;
	}

	public Boolean getDecision() {
		return this.decision;
	}

	public Integer getRound() {
		return this.round;
	}

	public String getProposals() {
		return this.proposals;
	}

	public String getReceivedFrom() {
		return this.receivedFrom;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public void setDecision(Boolean decision) {
		this.decision = decision;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public void setProposals(String proposals) {
		this.proposals = proposals;
	}

	public void setReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

	public void addProposalToRound(Boolean proposal, Integer round) {
		// get rounds
		List<String> rounds = Arrays.asList(this.proposals.split(";"));
		// append vote to the first round
		String newProposal = proposal.toString();
		if(rounds.get(0).length() != 0) {
			newProposal = "," + newProposal;
		}
		rounds.set(0, rounds.get(0).concat(newProposal));
		this.proposals = String.join(";", rounds);
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
  
	public static ConsensusReferendum toConsensusReferendum(String message) throws JsonSyntaxException{
		Gson gson = new Gson();
		ConsensusReferendum consensusReferendum = gson.fromJson(message, ConsensusReferendum.class);
		return consensusReferendum;
	}

}