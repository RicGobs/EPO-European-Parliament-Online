package com.euparliament.rest.inst;

public class InstUserNotFoundException extends Exception {

	private static final long serialVersionUID = 483157205705728539L;
	
	public InstUserNotFoundException(String id) {
		super(String.format("Representative with ID %s not found", id));
	}
}
