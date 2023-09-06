package com.euparliament.rest.consensusReferendum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(ConsensusReferendumId.class)
public class ConsensusReferendum {

  	private Integer status; // 2 for proposal -  4 for referendum results
	private @Id String title;
	private @Id String dateStart;
  	
	private String correct; // the set of nations
	private Boolean decision; //answer to proposal or to referendum; null -no decision, else the decision
	private Integer round; // 1 if there are no crashes
	private String proposals; // votes received
	private String receivedFrom; // list of nations who has sent the vote

	public ConsensusReferendum() {}

	public ConsensusReferendumId getId() {
		return new ConsensusReferendumId(
					this.title,
					this.dateStart					
				);
	}
	
	public void setId(ConsensusReferendumId id) {
		this.title = id.getTitle();
		this.dateStart = id.getDateStart();
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