package com.euparliament.broadcast.model;

public class ConsensusReferendum {

  	private Integer status; // 2 for proposal -  4 for referendum results
  	private ConsensusReferendumId consensusReferendumid;

	private String correct;
	private Boolean decision; //answer to proposal or to referendum
	private Integer round;
	private String proposals;
	private String receivedFrom;

	public ConsensusReferendum() {}
	
	public ConsensusReferendum(String title, String dateStart, Integer status) {
		this.consensusReferendumid = new ConsensusReferendumId(title, dateStart);
		this.status = status;
		
		this.correct = "ita,ger,fra"; // TODO: get from docker-compose-all.yaml
		this.decision = null;
		this.round = 1;
		this.proposals = "";
		this.receivedFrom = "";
	}

	public ConsensusReferendumId getId() {
		return this.consensusReferendumid;
	}
	
	public void setId(ConsensusReferendumId id) {
		this.consensusReferendumid = id;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	public String getCorrect() {
		return this.correct;
	}

	public Boolean getDecision() {
		return this.decision;
	}

	public Integer getRound() {
		return this.round;
	}

	public String getProposals() {
		return this.proposals;
	}

	public String getReceivedFrom() {
		return this.receivedFrom;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public void setDecision(Boolean decision) {
		this.decision = decision;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public void setProposals(String proposals) {
		this.proposals = proposals;
	}

	public void getReceivedFrom(String receivedFrom) {
		this.receivedFrom = receivedFrom;
	}

}