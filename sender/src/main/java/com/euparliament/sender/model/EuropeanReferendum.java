package com.euparliament.sender.model;

public class EuropeanReferendum {
	
	private String title;
	
	public EuropeanReferendum() {}
	
	public EuropeanReferendum(String title) {
		this.title = title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		return "EuropeanReferendum{" + "title='" + this.title + '\'' + '}';
	}
		
}
