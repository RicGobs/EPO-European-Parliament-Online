package com.euparliament.rest.citizen;

public class CitizenUserNotFoundException extends Exception {

	private static final long serialVersionUID = 483157205705728539L;
	
	public CitizenUserNotFoundException(String id) {
		super(String.format("Citizen with national ID %s not found", id));
	}
}
