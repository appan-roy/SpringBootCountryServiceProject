package com.country.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.country.demo.beans.Country;
import com.country.demo.repositories.CountryRepository;
import com.country.demo.services.CountryServiceDB;

@TestMethodOrder(OrderAnnotation.class)		// this is used to execute the JUnit tests in mentioned order in this class
@SpringBootTest(classes = { ServiceMockitoTests.class })
public class ServiceMockitoTests {

	@Mock
	CountryRepository countryRepository;

	@InjectMocks
	CountryServiceDB countryServiceDB;

	@Test
	@Order(1)
	public void test_getAllCountriesSize() {
		// mock data
		List<Country> myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "Brazil", "Rio de Janeiro"));
		myCountries.add(new Country(2, "Russia", "Moscow"));
		myCountries.add(new Country(3, "Germany", "Berlin"));

		// mocking
		when(countryRepository.findAll()).thenReturn(myCountries);

		// assertion
		Assertions.assertEquals(3, countryServiceDB.getAllCountries().size());
	}

	@Test
	@Order(2)
	public void test_getCountryByCountryId() {
		// mock data
		List<Country> myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "Brazil", "Rio de Janeiro"));
		myCountries.add(new Country(2, "Russia", "Moscow"));
		myCountries.add(new Country(3, "Germany", "Berlin"));
		int countryId = 3;

		// mocking
		when(countryRepository.findAll()).thenReturn(myCountries);

		// assertion
		Assertions.assertEquals(countryId, countryServiceDB.getCountryByCountryId(countryId).getId());
	}

	@Test
	@Order(3)
	public void test_getCountryByName() {
		// mock data
		List<Country> myCountries = new ArrayList<Country>();
		myCountries.add(new Country(1, "Brazil", "Rio de Janeiro"));
		myCountries.add(new Country(2, "Russia", "Moscow"));
		myCountries.add(new Country(3, "Germany", "Berlin"));
		String countryName = "Brazil";

		// mocking
		when(countryRepository.findAll()).thenReturn(myCountries);

		// assertion
		Assertions.assertEquals(countryName, countryServiceDB.getCountryByName(countryName).getCountryName());
	}

	@Test
	@Order(4)
	public void test_addCountry() {
		// mock data
		Country country = new Country(4, "Japan", "Tokyo");

		// mocking
		when(countryRepository.save(country)).thenReturn(country);

		// assertion
		Assertions.assertEquals(country, countryServiceDB.addCountry(country));
	}

	@Test
	@Order(5)
	public void test_updateCountry() {
		// mock data
		Country country = new Country(4, "Japan", "Tokyo");

		// mocking
		when(countryRepository.save(country)).thenReturn(country);

		// assertion
		Assertions.assertEquals(country, countryServiceDB.updateCountry(country));
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() {
		// mock data
		Country country = new Country(4, "Japan", "Tokyo");
		
		countryServiceDB.deleteCountry(country);

		// mocking + assertion, it checks that the delete call happened only i time
		verify(countryRepository, times(1)).delete(country);
	}

}
