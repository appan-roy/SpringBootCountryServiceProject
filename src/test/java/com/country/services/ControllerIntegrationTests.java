package com.country.services;

import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.country.demo.beans.Country;

@TestMethodOrder(OrderAnnotation.class) // this is used to execute the JUnit tests in mentioned order in this class
@SpringBootTest(classes = { ControllerIntegrationTests.class })
public class ControllerIntegrationTests {

	@Test
	@Order(1)
	public void integrationTest_getAllCountries() throws JSONException {
		String expected_response = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 1,\r\n"
				+ "        \"countryName\": \"Brazil\",\r\n"
				+ "        \"countryCapital\": \"Rio de Janeiro\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 2,\r\n"
				+ "        \"countryName\": \"Russia\",\r\n"
				+ "        \"countryCapital\": \"Moscow\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 3,\r\n"
				+ "        \"countryName\": \"Germany\",\r\n"
				+ "        \"countryCapital\": \"Berlin\"\r\n"
				+ "    }\r\n"
				+ "]";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> actual_response = testRestTemplate.getForEntity("http://localhost:8080/getcountries", String.class);
		
		System.out.println(actual_response.getStatusCode());
		System.out.println(actual_response.getBody());
		
		Assertions.assertEquals(HttpStatus.FOUND, actual_response.getStatusCode());
		JSONAssert.assertEquals(expected_response, actual_response.getBody(), false);
	}
	
	@Test
	@Order(2)
	public void integrationTest_getCountryById() throws JSONException {
		String expected_response = "{\r\n"
				+ "    \"id\": 3,\r\n"
				+ "    \"countryName\": \"Germany\",\r\n"
				+ "    \"countryCapital\": \"Berlin\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> actual_response = testRestTemplate.getForEntity("http://localhost:8080/getcountries/3", String.class);
		
		System.out.println(actual_response.getStatusCode());
		System.out.println(actual_response.getBody());
		
		Assertions.assertEquals(HttpStatus.FOUND, actual_response.getStatusCode());
		JSONAssert.assertEquals(expected_response, actual_response.getBody(), false);
	}
	
	@Test
	@Order(3)
	public void integrationTest_getCountryByName() throws JSONException {
		String expected_response = "{\r\n"
				+ "    \"id\": 1,\r\n"
				+ "    \"countryName\": \"Brazil\",\r\n"
				+ "    \"countryCapital\": \"Rio de Janeiro\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> actual_response = testRestTemplate.getForEntity("http://localhost:8080/getcountries/countryname?name=Brazil", String.class);
		
		System.out.println(actual_response.getStatusCode());
		System.out.println(actual_response.getBody());
		
		Assertions.assertEquals(HttpStatus.FOUND, actual_response.getStatusCode());
		JSONAssert.assertEquals(expected_response, actual_response.getBody(), false);
	}
	
	@Test
	@Order(4)
	public void integrationTest_addCountry() throws JSONException {
		
		Country country = new Country(4, "France", "Paris");
		
		String expected_response = "{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"France\",\r\n"
				+ "    \"countryCapital\": \"Paris\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, httpHeaders);
		ResponseEntity<String> actual_response = testRestTemplate.postForEntity("http://localhost:8080/addcountry", request, String.class);
		
		System.out.println(actual_response.getStatusCode());
		System.out.println(actual_response.getBody());
		
		Assertions.assertEquals(HttpStatus.CREATED, actual_response.getStatusCode());
		JSONAssert.assertEquals(expected_response, actual_response.getBody(), false);
	}
	
	@Test
	@Order(5)
	public void integrationTest_updateCountry() throws JSONException {
		
		Country country = new Country(4, "Japan", "Tokyo");
		
		String expected_response = "{\r\n"
				+ "    \"id\": 4,\r\n"
				+ "    \"countryName\": \"Japan\",\r\n"
				+ "    \"countryCapital\": \"Tokyo\"\r\n"
				+ "}";
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, httpHeaders);
		ResponseEntity<String> actual_response = testRestTemplate.exchange("http://localhost:8080/updatecountry/4", HttpMethod.PUT, request, String.class);
		
		System.out.println(actual_response.getStatusCode());
		System.out.println(actual_response.getBody());
		
		Assertions.assertEquals(HttpStatus.OK, actual_response.getStatusCode());
		JSONAssert.assertEquals(expected_response, actual_response.getBody(), false);
	}
	
	@Test
	@Order(6)
	public void integrationTest_deleteCountry() throws JSONException {
		
		Country country = new Country(4, "Japan", "Tokyo");
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<Country> request = new HttpEntity<Country>(country, httpHeaders);
		ResponseEntity<String> actual_response = testRestTemplate.exchange("http://localhost:8080/deletecountry/4", HttpMethod.DELETE, request, String.class);
		
		System.out.println(actual_response.getStatusCode());
		
		Assertions.assertEquals(HttpStatus.NO_CONTENT, actual_response.getStatusCode());
	}
	
}
