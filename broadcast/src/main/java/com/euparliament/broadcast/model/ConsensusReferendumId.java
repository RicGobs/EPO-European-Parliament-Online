package com.euparliament.broadcast.model;

public class ConsensusReferendumId{

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
	
	@Override
	public String toString() { 
	    return "\"title\": \"" + this.title + "\", \"dateStart\": \"" + this.dateStart + "\"";
	}
}