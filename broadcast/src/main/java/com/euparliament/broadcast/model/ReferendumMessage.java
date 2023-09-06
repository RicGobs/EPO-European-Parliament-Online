package com.euparliament.broadcast.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ReferendumMessage {

	private String title;  //title
	private Integer status; //1 -> request proposal: when a nation does the proposal
	                       //2 -> answer proposal: when all nations have to answer to the proposal (1^ consensus)
	                       //3 -> request consensus: when a citizen (after the positive proposal) can vote for the referendum
						   //4 -> answer consensus: when all nations have to answer to the referendum (2^ consensus)

	private String nationSourceAnswer; //Nation which has sent the answer
	private Boolean answer;

	private String dateStartConsensusProposal; //first date for vote the proposal
	
    public ReferendumMessage() {}
   
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
}