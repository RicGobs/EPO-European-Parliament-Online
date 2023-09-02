package com.euparliament.rest.consensusReferendum;

public class ConsensusReferendumNotFoundException extends Exception {

	private static final long serialVersionUID = 483157205705728539L;
	
	public ConsensusReferendumNotFoundException(Long id) {
		super(String.format("Id %d not fount", id));
	}
}