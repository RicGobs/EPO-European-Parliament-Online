package com.euparliament.broadcast.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class Referendum {

	private ReferendumId id;
	private Integer status;//1 -> request proposal: when a nation does the proposal
	                       //2 -> answer proposal sent (1^ consensus)
	                       //3 -> request consensus: when a citizen can vote for the referendum if the decided proposal is true
						   //4 -> answer referendum sent (2^ consensus)
						   //5 -> aborted by national representatives false > true
						   //6 -> aborted by not sufficient number of votes
						   //7 -> accepted

	private Integer votesTrue;
	private Integer votesFalse;
	private List<String> voteCitizens;
	private Integer population;

	private String argument; //what is about the referendum
	private String nationCreator; //Nation which has created the proposal
	private String dateEndConsensusProposal; //last date for vote the consensus in the proposal //1-2
	private String dateEndResult; //last date for vote the referendum (proposal has passed) //2-3
    private String dateEndConsensusResult; //last date for vote the consensus in the referendum (proposal has passed) //3-4
	
    public Referendum() {
		this.id = new ReferendumId();
    	this.status = 1;

		this.votesTrue = 0;
		this.votesFalse = 0;
		this.voteCitizens = new ArrayList<String>();
		this.population = 0;

		String pattern = "dd/MM/yyyy HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		Calendar c = Calendar.getInstance();
        Date today = c.getTime();  
		String todayAsString = df.format(today);
		
		this.id.setDateStartConsensusProposal(todayAsString); 
		
        c.add(Calendar.MINUTE, 3);
        String todayAsString1 = df.format(c.getTime());
        
		c.add(Calendar.MINUTE, 1);
        String todayAsString2 = df.format(c.getTime());
        
		c.add(Calendar.MINUTE, 1);
        String todayAsString3 = df.format(c.getTime());
        
		this.dateEndConsensusProposal = todayAsString1; 
		this.dateEndResult = todayAsString2; 
    	this.dateEndConsensusResult = todayAsString3;
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

	public String getDateEndConsensusProposal() {
		return this.dateEndConsensusProposal;
	}

	public String getDateEndResult() {
		return this.dateEndResult;
	}

	public String getDateEndConsensusResult() {
		return this.dateEndConsensusResult;
	}

	public ReferendumId getId() {
		return this.id;
	}

	public Integer getVotesTrue() {
		return votesTrue;
	}

	public Integer getVotesFalse() {
		return votesFalse;
	}
	
	public Integer getPopulation() {
		return this.population;
	}

    // ------------------------------------------------------

	public void setId(ReferendumId id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.id.setTitle(title);
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}
	
	public void setNationCreator(String nationCreator) {
		this.nationCreator = nationCreator;
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

	public void setVotesTrue(Integer votesTrue) {
		this.votesTrue = votesTrue;
	}

	public void setVotesFalse(Integer votesFalse) {
		this.votesFalse = votesFalse;
	}
	
	public void setPopulation(Integer population) {
		this.population = population;
	}

	public void setStatusByProposalConsensusDecision(Boolean decision) {
		if(decision == null) {
			// aborted
			this.status = 6;
		} else if (!decision) {
			// aborted
			this.status = 5;
		} else {
			// proposal accepted
			if(this.status == 2 || this.status == 1) {
				this.status = 3;
			} else {
				this.status = 7;
			}
		}
	}

	public Boolean decide() {
		// decide true if the majority of proposals are true		
		Integer totalVotes = votesTrue + votesFalse;
		
		// check majority of voters
		if(totalVotes > population/2) {
			// check the decision result
			 if(votesTrue > votesFalse) {
				 return true;
			 } else {
				 return false;
			 }
		}
		// referendum is revoked
		return null;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
  
	public static Referendum toReferendum(String message) throws JsonSyntaxException {
		Gson gson = new Gson();
		Referendum referendum = gson.fromJson(message, Referendum.class);
		if(
			// attribute in Referendum only and not in ReferendumMessage
			referendum.nationCreator == null
		) {
			throw new JsonSyntaxException("Message is not instanceof Referendum");
		}
		return referendum;
	}

	public List<String> getVoteCitizens() {
		return voteCitizens;
	}

	public void setVoteCitizens(List<String> voteCitizens) {
		this.voteCitizens = voteCitizens;
	}
}
