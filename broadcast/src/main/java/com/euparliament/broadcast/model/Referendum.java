package com.euparliament.broadcast.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Referendum {

	private String title;  //title
	private Integer status; //1 -> request proposal: when a nation does the proposal
	                       //2 -> answer proposal: when all nations have to answer to the proposal (1^ consensus)
	                       //3 -> request consensus: when a citizen (after the positive proposal) can vote for the referendum
						   //4 -> answer consensus: when all nations have to answer to the referendum (2^ consensus)
    
	private String argument; //what is about the referendum
	private String nationCreator; //Nation which has created the proposal
	private Boolean result;

	private String dateStartConsensusProposal; //first date for vote the proposal
	private String dateEndConsensusProposal; //last date for vote the consensus in the proposal
	private String dateEndResult; //last date for vote the referendum (proposal has passed)
    private String dateEndConsensusResult; //last date for vote the consensus in the referendum (proposal has passed)
	
    public Referendum() {
    	this.result = null; //initialize the answer with a null value
    	this.status = 1;

		String pattern = "dd/MM/yyyy HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
        Date today = c.getTime();  
		String todayAsString = df.format(today);

		this.dateStartConsensusProposal = todayAsString; 

        c.add(Calendar.MINUTE, 1);
        String todayAsString1 = df.format(c.getTime());
        
		c.add(Calendar.MINUTE, 1);
        String todayAsString2 = df.format(c.getTime());

		c.add(Calendar.MINUTE, 1);
        String todayAsString3 = df.format(c.getTime());

		this.dateEndConsensusProposal = todayAsString1; 
		this.dateEndResult = todayAsString2; 
    	this.dateEndConsensusResult = todayAsString3;
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
     
    // ------------------------------------------------------

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}
	
	public void setNationCreator(String nationCreator) {
		this.nationCreator = nationCreator;
	}

	public void setResult(Boolean result) {
		this.result = result;
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


	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
  
	public static Referendum toReferendum(String message) throws JsonSyntaxException{
		Gson gson = new Gson();
		Referendum referendum = gson.fromJson(message, Referendum.class);
		return referendum;
	}
}