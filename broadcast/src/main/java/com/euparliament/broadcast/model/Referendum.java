package com.euparliament.broadcast.model;

import java.util.Objects;

import com.google.gson.Gson;

public class Referendum {

	private String title;
	private String status;

	public Referendum() {}

	public Referendum(String title, String status) {

		this.title = title;
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean equals(Object o) {

	    if (this == o)
	      return true;
	    if (!(o instanceof Referendum))
	      return false;
	    Referendum referendum = (Referendum) o;
	    return Objects.equals(this.title, referendum.title) && Objects.equals(this.status, referendum.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.title, this.status);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this).toString();
	}
  
	public static Referendum toReferendum(String message) {
		Gson gson = new Gson();
		return gson.fromJson(message, Referendum.class);
	}
}