package com.euparliament.broadcast.model;

class ConsensusReferendum {

  	private Integer status; // 2 for proposal -  4 for referendum results
  	private String title;
	private String dateStart;

	private String correct;
	private Boolean decision; //answer to proposal or to referendum
	private Integer round;
	private String proposals;
	private String receivedFrom;

	public ConsensusReferendum() {
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getDateStart() {
		return this.dateStart;
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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
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