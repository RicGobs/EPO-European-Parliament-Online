package com.euparliament.rest.referendum;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReferendumId implements Serializable {

	private static final long serialVersionUID = -8741030297231183373L;
	private String title;
	private String dateStartConsensusProposal;
	
	public ReferendumId() {}
	
	public ReferendumId(String title, String dateStart) {
		this.title = title;
		this.dateStartConsensusProposal = dateStart;
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
}