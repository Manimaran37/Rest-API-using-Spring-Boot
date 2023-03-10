package com.restservice.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restservice.demo.beans.Country;
import com.restservice.demo.services.CountryService;

@RestController
public class CountryController {
	
	@Autowired
	CountryService countryService;

	@GetMapping("/getcountries")
	public ResponseEntity<List<Country>> getCountries(){
		try {
			List<Country> countries = countryService.getCountries();
			return new ResponseEntity<>(countries, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	

	@GetMapping("/getcountries/{id}")
	public ResponseEntity<Country> getCountryByID(@PathVariable(value = "id") int id){
		try {
			Country country = countryService.getCountryById(id);
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getcountries/countryname")
	public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String name){
		try {
			Country country = countryService.getCountryByName(name);
			return new ResponseEntity<Country>(country, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addcountry")
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		try {
			country = countryService.addCountry(country);
			return new ResponseEntity<Country>(country, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
	}
	
	@PutMapping("/updatecountry/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id ,@RequestBody Country country) {
		try {
			Country existCountry = countryService.getCountryById(id);
			existCountry.setCountryName(country.getCountryName());
			existCountry.setCountryCapitalString(country.getCountryCapitalString());
			Country updatedCountry = countryService.updateCountry(existCountry);
			return new ResponseEntity<Country>(updatedCountry, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@DeleteMapping("/deletecountry/{id}")
	public ResponseEntity<Country> deleteCountry(@PathVariable(value = "id") int id) {
		Country country = null;
		try {
			country = countryService.getCountryById(id);
			countryService.deleteCountry(country);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(country, HttpStatus.OK);
	}
}
