package com.country.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.country.demo.beans.Country;
import com.country.demo.controllers.CountryController;
import com.country.demo.services.CountryServiceDB;

@TestMethodOrder(OrderAnnotation.class) // this is used to execute the JUnit tests in mentioned order in this class
@SpringBootTest(classes = { ControllerMockitoTests.class })
public class ControllerMockitoTests {

	@Mock
	CountryServiceDB countryServiceDB;

	@InjectMocks
	CountryController countryController;

	@Test
	@Order(1)
	public void test_getAllCountries() {
		// mock data
		List<Country> myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "Brazil", "Rio de Janeiro"));
		myCountries.add(new Country(2, "Russia", "Moscow"));
		myCountries.add(new Country(3, "Germany", "Berlin"));

		// mocking
		when(countryServiceDB.getAllCountries()).thenReturn(myCountries);

		ResponseEntity<List<Country>> res = countryController.getCountries();

		// assertion
		Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode());
		Assertions.assertEquals(3, res.getBody().size());
	}

	@Test
	@Order(2)
	public void test_getCountryById() {
		// mock data
		Country country = new Country(4, "Japan", "Tokyo");
		int countryId = 4;

		// mocking
		when(countryServiceDB.getCountryById(countryId)).thenReturn(country);

		ResponseEntity<Country> res = countryController.getCountryById(countryId);

		// assertion
		Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode());
		Assertions.assertEquals(countryId, res.getBody().getId());
	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		// mock data
		Country country = new Country(5, "Germany", "Berlin");
		String countryName = "Germany";

		// mocking
		when(countryServiceDB.getCountryByName(countryName)).thenReturn(country);

		ResponseEntity<Country> res = countryController.getCountryByName(countryName);

		// assertion
		Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode());
		Assertions.assertEquals(countryName, res.getBody().getCountryName());
	}

	@Test
	@Order(4)
	public void test_addCountry() {
		// mock data
		Country country = new Country(6, "Portugal", "Lisbon");

		// mocking
		when(countryServiceDB.addCountry(country)).thenReturn(country);

		ResponseEntity<Country> res = countryController.addCountry(country);

		// assertion
		Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode());
		Assertions.assertEquals(country, res.getBody());
	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		// mock data
		Country country = new Country(7, "France", "Paris");
		int countryId = 7;

		// mocking
		when(countryServiceDB.getCountryById(countryId)).thenReturn(country);
		when(countryServiceDB.updateCountry(country)).thenReturn(country);

		ResponseEntity<Country> res = countryController.updateCountry(countryId, country);

		// assertion
		Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
		Assertions.assertEquals(7, res.getBody().getId());
		Assertions.assertEquals("France", res.getBody().getCountryName());
		Assertions.assertEquals("Paris", res.getBody().getCountryCapital());
	}

	@Test
	@Order(6)
	public void test_deleteCountry() {
		// mock data
		Country country = new Country(8, "Australia", "Melbourne");
		int countryId = 8;

		// mocking
		when(countryServiceDB.getCountryById(countryId)).thenReturn(country);

		ResponseEntity<Country> res = countryController.deleteCountry(countryId);

		// assertion
		Assertions.assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
	}

}
