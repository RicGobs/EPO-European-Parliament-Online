package com.euparliament.rest.consensusReferendum;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ConsensusReferendumId implements Serializable {
 
	private static final long serialVersionUID = -155154072775086372L;
	private String title;
	private String dateStart;
	
	public ConsensusReferendumId() {}
	
	public ConsensusReferendumId(String title, String dateStart) {
		this.title = title;
		this.dateStart = dateStart;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDateStart() {
		return this.dateStart;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
}