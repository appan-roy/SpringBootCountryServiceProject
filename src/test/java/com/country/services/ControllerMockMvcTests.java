package com.country.services;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.country.demo.beans.Country;
import com.country.demo.controllers.CountryController;
import com.country.demo.services.CountryServiceDB;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class) // this is used to execute the JUnit tests in mentioned order in this class
@ComponentScan(basePackages = {"com.country.demo"} )
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { ControllerMockMvcTests.class })
public class ControllerMockMvcTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Mock
	CountryServiceDB countryServiceDB;
	
	@InjectMocks
	CountryController countryController;
	
	List<Country> myCountries = new ArrayList<Country>();
	Country country;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
	}
	
	@Test
	@Order(1)
	public void test_getAllCountries() throws Exception {
		// mock data
		myCountries.add(new Country(1, "Brazil", "Rio de Janeiro"));
		myCountries.add(new Country(2, "Russia", "Moscow"));
		myCountries.add(new Country(3, "Germany", "Berlin"));
		
		// mocking
		when(countryServiceDB.getAllCountries()).thenReturn(myCountries);
		
		this.mockMvc.perform(get("/getcountries"))
		.andExpect(status().isFound())
		.andDo(print());
	}
	
	@Test
	@Order(2)
	public void test_getCountryById() throws Exception {
		// mock data
		country = new Country(4, "China", "Beijing");
		int countryId = 4;
		
		// mocking
		when(countryServiceDB.getCountryById(countryId)).thenReturn(country);
		
		this.mockMvc.perform(get("/getcountries/{id}", countryId))
		.andExpect(status().isFound())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(4))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("China"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Beijing"))
		.andDo(print());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() throws Exception {
		// mock data
		country = new Country(5, "Switzerland", "Zurich");
		String countryName = "Switzerland";
		
		// mocking
		when(countryServiceDB.getCountryByName(countryName)).thenReturn(country);
		
		this.mockMvc.perform(get("/getcountries/countryname").param("name", "Switzerland"))
		.andExpect(status().isFound())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(5))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Switzerland"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Zurich"))
		.andDo(print());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() throws Exception {
		// mock data
		country = new Country(6, "Bangladesh", "Dhaka");
		
		// mocking
		when(countryServiceDB.addCountry(country)).thenReturn(country);
		
		// convert java object to JSON object
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(country);
		
		this.mockMvc.perform(post("/addcountry").content(jsonBody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andDo(print());
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() throws Exception {
		// mock data
		country = new Country(7, "Italy", "Rome");
		int countryId = 7;
		
		// mocking
		when(countryServiceDB.getCountryById(countryId)).thenReturn(country);
		when(countryServiceDB.updateCountry(country)).thenReturn(country);
		
		// convert java object to JSON object
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonBody = objectMapper.writeValueAsString(country);
		
		this.mockMvc.perform(put("/updatecountry/{id}", countryId).content(jsonBody).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Italy"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Rome"))
		.andDo(print());
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() throws Exception {
		// mock data
		country = new Country(8, "Sri Lanka", "Colombo");
		int countryId = 7;
		
		// mocking
		when(countryServiceDB.getCountryById(countryId)).thenReturn(country);
		
		this.mockMvc.perform(delete("/deletecountry/{id}", countryId))
		.andExpect(status().isNoContent());
	}

}
