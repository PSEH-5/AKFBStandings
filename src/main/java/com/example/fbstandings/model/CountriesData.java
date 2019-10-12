package com.example.fbstandings.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesData {
	private List<CountryData> countries;

	public List<CountryData> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryData> countries) {
		this.countries = countries;
	}
	
}
