package com.euparliament.rest.referendum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(ReferendumId.class)
class Referendum {

  	private @Id String title;
  	private @Id String dateStartConsensusProposal; //first date for vote the proposal
  	private Integer status;
    private String argument; //what is about the referendum
	private String nationCreator; //Nation which has done the proposal
	private Boolean result;
	private String dateEndConsensusProposal; //last date for vote the consensus in the proposal
	private String dateEndResult; //last date for vote the referendum (proposal has passed)
    private String dateEndConsensusResult; //last date for vote the consensus in the referendum (proposal has passed)

	public Referendum() {}

	public Referendum(String title, Integer status, String argument, String firstNation, String dateStartConsensusProposal, String dateEndConsensusProposal, String dateEndResult, String dateEndConsensusResult) {

		this.title = title;
		this.status = status;
		this.argument = argument; 
		this.nationCreator = firstNation; 
		this.dateStartConsensusProposal = dateStartConsensusProposal; 
		this.dateEndConsensusProposal = dateEndConsensusProposal; 
		this.dateEndResult = dateEndResult; 
    	this.dateEndConsensusResult = dateEndConsensusResult;
	}
	
	public ReferendumId getId() {
		return new ReferendumId(
					this.title,
					this.dateStartConsensusProposal					
				);
	}
	
	public void setId(ReferendumId id) {
		this.title = id.getTitle();
		this.dateStartConsensusProposal = id.getDateStartConsensusProposal();
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getArgument() {
		return this.argument;
	}

	public String getNationCreator() {
		return this.nationCreator;
	}

	public Boolean getResult() {
		return this.result;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	public void setNationCreator(String firstNation) {
		this.nationCreator = firstNation;
	}

	public void setResult(Boolean result) {
		this.result = result;
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