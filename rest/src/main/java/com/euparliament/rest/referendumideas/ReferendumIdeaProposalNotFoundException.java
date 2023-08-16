package com.euparliament.rest.referendumideas;

public class ReferendumIdeaProposalNotFoundException extends Exception {

	private static final long serialVersionUID = 483157205705728539L;
	
	public ReferendumIdeaProposalNotFoundException(Long id) {
		super(String.format("Id %d not fount", id));
	}
}