package com.euparliament.web.model;

import java.util.Objects;
import java.util.Date;

public class InstUser {

  private String name;
  private String surname;
  private String gender;
  private Date birthdate;
  private String nationOfBirth;
  private String regionOfBirth;
  private String cityOfBirth;
  private String region;
  private String city;
  private String representativeID;
  private String email;
  private int cellular;
  private String password;
  
  InstUser() {}

  InstUser(String name, String surname, String gender, Date birthdate, String nationOfBirth, String regionOfBirth, 
		  String cityOfBirth, String region, String city, String reprsentativeID, String email, int cellular, String password) {

	  this.name = name;
	  this.surname = surname;
	  this.gender = gender;
	  this.birthdate = birthdate;
	  this.nationOfBirth = regionOfBirth;
	  this.regionOfBirth = regionOfBirth;
	  this.cityOfBirth = cityOfBirth;
	  this.region = region;
	  this.city = city;
	  this.representativeID = representativeID;
	  this.email = email;
	  this.cellular = cellular;
	  this.password = password;
  }
  
  // Cellular is optional
  InstUser(String name, String surname, String gender, Date birthdate, String nationOfBirth, String regionOfBirth,
			String cityOfBirth, String region, String city, String representativeID, String email, String password) {

		  this.name = name;
		  this.surname = surname;
		  this.gender = gender;
		  this.birthdate = birthdate;
		  this.nationOfBirth = regionOfBirth;
		  this.regionOfBirth = regionOfBirth;
		  this.cityOfBirth = cityOfBirth;
		  this.region = region;
		  this.city = city;
		  this.representativeID = representativeID;
		  this.email = email;
		  this.password = password;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return this.surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getBirthdate() {
    return this.birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public String getNationOfBirth() {
    return this.nationOfBirth;
  }

  public void setNationOfBirth(String nationOfBirth) {
    this.nationOfBirth = nationOfBirth;
  }

  public String getRegionOfBirth() {
    return this.regionOfBirth;
  }

  public void setRegionOfBirth(String regionOfBirth) {
    this.regionOfBirth = regionOfBirth;
  }

  public String getCityOfBirth() {
    return this.cityOfBirth;
  }

  public void setCityOfBirth(String cityOfBirth) {
    this.cityOfBirth = cityOfBirth;
  }

  public String getRegion() {
    return this.region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getRepresentativeID() {
    return this.representativeID;
  }

  public void setRepresentativeID(String representativeID) {
    this.representativeID = representativeID;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getCellular() {
    return this.cellular;
  }

  public void setCellular(int cellular) {
    this.cellular = cellular;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof InstUser)) {
            return false;
        }
        InstUser instUser = (InstUser) o;
        return Objects.equals(name, instUser.name) && Objects.equals(surname, instUser.surname) && Objects.equals(gender, instUser.gender) && Objects.equals(birthdate, instUser.birthdate) && Objects.equals(nationOfBirth, instUser.nationOfBirth) && Objects.equals(regionOfBirth, instUser.regionOfBirth) && Objects.equals(cityOfBirth, instUser.cityOfBirth) && Objects.equals(region, instUser.region) && Objects.equals(city, instUser.city) && Objects.equals(representativeID, instUser.representativeID) && Objects.equals(email, instUser.email) && cellular == instUser.cellular && Objects.equals(password, instUser.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, gender, birthdate, nationOfBirth, regionOfBirth, cityOfBirth, region, city, representativeID, email, cellular, password);
  }

  @Override
  public String toString() {
    return "{" +
      " name='" + getName() + "'" +
      ", surname='" + getSurname() + "'" +
      ", gender='" + getGender() + "'" +
      ", birthdate='" + getBirthdate() + "'" +
      ", nationOfBirth='" + getNationOfBirth() + "'" +
      ", regionOfBirth='" + getRegionOfBirth() + "'" +
      ", cityOfBirth='" + getCityOfBirth() + "'" +
      ", region='" + getRegion() + "'" +
      ", city='" + getCity() + "'" +
      ", representativeID='" + getRepresentativeID() + "'" +
      ", email='" + getEmail() + "'" +
      ", cellular='" + getCellular() + "'" +
      ", password='" + getPassword() + "'" +
      "}";
  }

}
