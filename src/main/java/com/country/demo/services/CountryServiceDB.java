package com.country.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.country.demo.beans.Country;
import com.country.demo.controllers.AddResponse;
import com.country.demo.repositories.CountryRepository;

@Component
@Service
public class CountryServiceDB {
	
	@Autowired	// autowired with country respository
	CountryRepository countryRepository;
	
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}
	
	public Country getCountryById(int id) {
		return countryRepository.findById(id).get();
	}
	
	public Country getCountryByCountryId(int id) {
		List<Country> countries = countryRepository.findAll();
		Country country = null;
		for (Country con : countries) {
			if(con.getId() == id)
				country = con;
		}
		return country;
	}
	
	public Country getCountryByName(String countryName) {
		List<Country> countries = countryRepository.findAll();
		Country country = null;
		for (Country con : countries) {
			if(con.getCountryName().equalsIgnoreCase(countryName))
				country = con;
		}
		return country;
	}
	
	public int getNewId() {
		return countryRepository.findAll().size() + 1;
	}
	
	public Country addCountry(Country country) {
		country.setId(getNewId());
		countryRepository.save(country);
		return country;
	}
	
	public Country updateCountry(Country country) {
		countryRepository.save(country);
		return country;
	}
	
	public AddResponse deleteCountry(int id) {
		countryRepository.deleteById(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country deleted !!");
		res.setId(id);
		return res;
	}
	
	public void deleteCountry(Country country) {
		countryRepository.delete(country);
	}

}
