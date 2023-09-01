package com.euparliament.broadcast.model;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.Gson;

public class ReferendumAnswer {

	private String title; 
	private Integer status; 
	private String nation; //nation which answers
	private String answer;
    private String dateStart; // = dateStartConsensusProposal of Referendum Class

	public ReferendumAnswer(@Value("${queue.name}") String nationName) {
        this.nation=nationName;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getNation() {
		return this.nation;
	}

	public String getAnswer() {
		return this.answer;
	}

    public String getDateStart() {
		return this.dateStart;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

    public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
  
	public static Referendum toReferendum(String message) {
		Gson gson = new Gson();
		return gson.fromJson(message, Referendum.class);
	}
}