package com.euparliament.rest.referendum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Referendum {

    private @Id @GeneratedValue Long id;
  	private String title;
  	private String status;

    private String argument; //what is about the referendum
	private String firstNation; //Nation which has done the proposal
	private String dateStartConsensusProposal; //first date for vote the proposal
	private String dateEndConsensusProposal; //last date for vote the consensus in the proposal
	private String dateEndResult; //last date for vote the referendum (proposal has passed)
    private String dateEndConsensusResult; //last date for vote the consensus in the referendum (proposal has passed)

	public Referendum() {}

	public Referendum(String title, String status, String argument, String firstNation, String dateStartConsensusProposal, String dateEndConsensusProposal, String dateEndResult, String dateEndConsensusResult) {

		this.title = title;
		this.status = status;
		this.argument = argument; 
		this.firstNation = firstNation; 
		this.dateStartConsensusProposal = dateStartConsensusProposal; 
		this.dateEndConsensusProposal = dateEndConsensusProposal; 
		this.dateEndResult = dateEndResult; 
    	this.dateEndConsensusResult = dateEndConsensusResult;
	}

	public String getTitle() {
		return this.title;
	}

	public String getStatus() {
		return this.status;
	}

	public String getArgument() {
		return this.argument;
	}

	public String getFirstNation() {
		return this.firstNation;
	}

	public String getDateStartConsensusProposal() {
		return this.dateStartConsensusProposal;
	}

	public String getDateEndConsensusProposal() {
		return this.dateEndConsensusProposal;
	}

	public String getDateEndResult() {
		return this.dateEndResult;
	}

	public String getDateEndConsensusResult() {
		return this.dateEndConsensusResult;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	public void setFirstNation(String firstNation) {
		this.firstNation = firstNation;
	}

  public void setDateStartConsensusProposal(String dateStartConsensusProposal) {
		this.dateStartConsensusProposal = dateStartConsensusProposal;
	}

	public void setDateEndConsensusProposal(String dateEndConsensusProposal) {
		this.dateEndConsensusProposal = dateEndConsensusProposal;
	}

	public void setDateEndResult(String dateEndResult) {
		this.dateEndResult = dateEndResult;
	}

	public void setDateEndConsensusResult(String dateEndConsensusResult) {
		this.dateEndConsensusResult = dateEndConsensusResult;
	}

}