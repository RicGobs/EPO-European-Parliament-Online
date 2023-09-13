package com.euparliament.broadcast.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ReferendumMessage {

	private String title;  //title
	private Integer status;//2 -> proposal answer message (1^ consensus)
						   //4 -> referendum answer message (2^ consensus)

	private String nationSourceAnswer; //Nation which has sent the answer
	private Boolean answer;
	private String proposals;
	private Integer round;
	private Boolean isDecision; // true: DECIDED message
								// false: PROPOSE message

	private String dateStartConsensusProposal; //first date for vote the proposal
	
    public ReferendumMessage() {}
    
    public ReferendumMessage(
    	String title,
    	Integer status,
    	String nationSourceAnswer,
    	Boolean answer,
    	String proposals,
    	Integer round,
    	Boolean isDecision,
    	String dateStartConsensusProposal
    ) {
    	this.title = title;
    	this.status = status;
    	this.nationSourceAnswer = nationSourceAnswer;
    	this.answer = answer;
    	this.proposals = proposals;
    	this.round = round;
    	this.isDecision = isDecision;
    	this.dateStartConsensusProposal = dateStartConsensusProposal;
    }
   
	public String getTitle() {
		return this.title;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getNationSourceAnswer() {
		return this.nationSourceAnswer;
	}

	public Boolean getAnswer() {
		return this.answer;
	}

	public String getDateStartConsensusProposal() {
		return this.dateStartConsensusProposal;
	}
	
	public String getProposals() {
		return this.proposals;
	}
	
	public Integer getRound() {
		return this.round;
	}

	public Boolean getIsDecision() {
		return isDecision;
	}
     
    // ------------------------------------------------------

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNationSourceAnswer(String nationSourceAnswer) {
		this.nationSourceAnswer = nationSourceAnswer;
	}

	public void setAnswer(Boolean answer) {
		this.answer = answer;
	}

	public void setDateStartConsensusProposal(String dateStartConsensusProposal) {
		this.dateStartConsensusProposal = dateStartConsensusProposal;
	}
	
	public void setProposals(String proposals) {
		this.proposals = proposals;
	}
	
	public void setRound(Integer round) {
		this.round = round;
	}
	
	public void setIsDecision(Boolean isDecision) {
		this.isDecision = isDecision;
	}
	
	public String printIsDecision() {
		if(this.isDecision) {
			return "DECIDED";
		}
		return "PROPOSAL";
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
  
	public static ReferendumMessage toReferendumMessage(String message) throws JsonSyntaxException{
		Gson gson = new Gson();
		ReferendumMessage referendumMessage = gson.fromJson(message, ReferendumMessage.class);
		return referendumMessage;
	}

	public String printMessage() {
		if(this.isDecision) {
			return "Type : " + this.printIsDecision() + ", Decision: " + this.getAnswer() + "\n";
		}
		return "Type : " + this.printIsDecision() + ", Round :" + this.getRound() + ", Proposals: " + this.getProposals() + "\n";
	}

}