package com.euparliament.rest.referendum;

public class ReferendumNotFoundException extends Exception {

	private static final long serialVersionUID = 483157205705728539L;
	
	public ReferendumNotFoundException(Long id) {
		super(String.format("Id %d not fount", id));
	}
}