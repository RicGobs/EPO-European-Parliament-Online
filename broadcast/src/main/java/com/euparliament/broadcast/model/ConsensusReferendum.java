package com.euparliament.broadcast.model;

import java.util.List;

import com.euparliament.broadcast.utils.Parse;
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
	
	public ConsensusReferendum(String title, String dateStart, Integer status, String listNations) {
		this.id = new ConsensusReferendumId(title, dateStart);
		this.status = status;
		
		this.correct = listNations;
		this.decision = null;
		this.round = 1;
		
		List<String> nationsList = Parse.splitStringByComma(this.correct);
		this.proposals = "";
		this.receivedFrom = "";
		for(int i=0; i<nationsList.size()-1; i++) {
			this.proposals = this.proposals + ",";
			this.receivedFrom = this.receivedFrom + ",";
		}

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

	public void addProposalToRound(Boolean proposal, Integer round, String queueName) {
		// get position
		List<String> nationsList = Parse.splitStringByComma(this.correct);
		Integer position = nationsList.indexOf(queueName);
		// get rounds
		List<String> rounds = Parse.splitStringBySemicolon(this.proposals);
		// insert the new proposal
		String firstRound = rounds.get(0);
		List<String> nationsRoundList = Parse.splitStringByComma(firstRound);
		nationsRoundList.set(position, this.proposalToString(proposal));
		String newProposalsFirstRound = Parse.joinListByComma(nationsRoundList);
		// update the proposal
		rounds.set(0, newProposalsFirstRound);
		this.proposals = String.join(";", rounds);
	}
	

	private String proposalToString(Boolean proposal) {
		if(proposal == null) {
			return "";
		}
		return proposal.toString();
	}

	public void updateProposals(String proposals, Integer round) {
		// get rounds
		List<String> rounds = Parse.splitStringBySemicolon(this.proposals);
		String proposalRound = rounds.get(round-1);
		List<String> nationsRoundList = Parse.splitStringByComma(proposalRound);
		
		List<String> proposalsList = Parse.splitStringByComma(proposals);
		
		// compare and update this.proposals
		for(int i=0; i<nationsRoundList.size(); i++) {
			if(nationsRoundList.get(i).equals("") && !proposalsList.get(i).equals("")) {
				nationsRoundList.set(i, proposalsList.get(i));
			}
		}
		String newProposalsRound = Parse.joinListByComma(nationsRoundList);
		
		// update the proposals
		rounds.set(round-1, newProposalsRound);
		this.proposals = String.join(";", rounds);
	}
	

	public void updateRecivedFrom(String nationSourceAnswer, Integer round) {
		// get position
		List<String> nationsList = Parse.splitStringByComma(this.correct);
		Integer position = nationsList.indexOf(nationSourceAnswer);
		// get rounds
		List<String> rounds = Parse.splitStringBySemicolon(this.receivedFrom);
		// update receivedFrom[round]
		String receivedFromRound = rounds.get(round-1);
		List<String> nationsRoundList = Parse.splitStringByComma(receivedFromRound);
		nationsRoundList.set(position, nationSourceAnswer);
		String newReceivedFromsRound = Parse.joinListByComma(nationsRoundList);
		// update receviedFrom
		rounds.set(round-1, newReceivedFromsRound);
		this.receivedFrom = String.join(";", rounds);
	}
	
	private List<String> getReceivedFromByRound(Integer round) {
		List<String> rounds = Parse.splitStringBySemicolon(this.receivedFrom);
		String receivedFromRound = rounds.get(round-1);
		return Parse.splitStringByComma(receivedFromRound);
	}
	
	private List<String> getProposalsByRound(Integer round) {
		List<String> rounds = Parse.splitStringBySemicolon(this.proposals);
		String proposalsRound = rounds.get(round-1);
		return Parse.splitStringByComma(proposalsRound);
	}
	
	public boolean checkCorrectSubsetOfReceiveFrom() {
		List<String> correctNationsList = Parse.splitStringByComma(this.correct);
		List<String> receivedFromNationsList = this.getReceivedFromByRound(this.round);
		
		for(String nation: correctNationsList) {
			if(!receivedFromNationsList.contains(nation)) {
				return false;
			}
		}
		return true;
	}

	public boolean checkReceivedFromNotChanged() {
		if(this.round == 1) {
			return true;
		}
		List<String> receivedFromNationsListRound = this.getReceivedFromByRound(this.round);
		List<String> receivedFromNationsListRoundMinusOne = this.getReceivedFromByRound(this.round-1);
		for(int i=0; i<receivedFromNationsListRound.size(); i++) {
			if(!receivedFromNationsListRound.get(i)
					.equals(receivedFromNationsListRoundMinusOne.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean decide() {
		// decide true if the majority of proposals are true
		List<String> proposals = this.getProposalsByRound(this.round);
		List<String> correctNationsList = Parse.splitStringByComma(this.correct);
		Integer countTrue = 0;
		Integer countFalse = 0;
		for(String proposal: proposals) {
			if(proposal.equals("true")) {
				countTrue++;
			} else if(proposal.equals("false")) {
				countFalse++;
			}
		}
		Integer totalVotes = countTrue + countFalse;
		
		// check majority of voters
		if(totalVotes > correctNationsList.size()/2) {
			// check the decision result
			 if(countTrue > countFalse) {
				 return true;
			 } else {
				 return false;
			 }
		}
		// referendum is revoked
		return null;
	}
	
	public void incrementRound() {
		this.round++;
	}
	
	public boolean isCorrect(String nationSourceAnswer) {
		return Parse.splitStringByComma(this.correct).contains(nationSourceAnswer);
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