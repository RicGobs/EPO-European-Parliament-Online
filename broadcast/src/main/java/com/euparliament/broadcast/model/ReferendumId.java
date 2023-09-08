package com.euparliament.broadcast.model;

public class ReferendumId{

	private String title;
	private String dateStartConsensusProposal;
	
	public ReferendumId() {}
	
	public ReferendumId(String title, String dateStartConsensusProposal) {
		this.title = title;
		this.dateStartConsensusProposal = dateStartConsensusProposal;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDateStartConsensusProposal() {
		return this.dateStartConsensusProposal;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDateStartConsensusProposal(String dateStartConsensusProposal) {
		this.dateStartConsensusProposal = dateStartConsensusProposal;
	}
	
	@Override
	public String toString() { 
	    return "\"title\": \"" + this.title + "\", \"dateStart\": \"" + this.dateStartConsensusProposal + "\"";
	}
}