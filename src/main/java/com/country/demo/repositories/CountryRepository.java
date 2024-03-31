package com.country.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.country.demo.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
	
}
