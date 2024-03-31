package com.country.demo.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Country")	// this name should match exactly with the DB table name
public class Country {

	@Id // this is for primary key mapping in the DB table
	@Column(name = "id")	// this name should match exactly with the DB table column name
	int id;

	@Column(name = "country_name")	// this name should match exactly with the DB table column name
	String countryName;

	@Column(name = "country_capital")	// this name should match exactly with the DB table column name
	String countryCapital;
	
	// default constructor
	public Country() {
		
	}

	public Country(int id, String countryName, String countryCapital) {
		this.id = id;
		this.countryName = countryName;
		this.countryCapital = countryCapital;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryCapital() {
		return countryCapital;
	}

	public void setCountryCapital(String countryCapital) {
		this.countryCapital = countryCapital;
	}

}
