package com.example.fbstandings.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.net.URI;

import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.example.fbstandings.configuration.FBStandPropsConfiguration;


public class FBServiceTest {

	RestOperations rest;
	FBStandPropsConfiguration config;
	FBService service;
	private static final String COUNTRY_NAME = "countryName";
	private static final String LEAGUE_NAME = "leagueName";
	private static final String TEAM_NAME = "teamName";
	private static final String ID = "id";
	private static final String KEY = "key";
	private static final String HOST = "host";
	
	@BeforeMethod
	public void init() {
		rest = mock(RestTemplate.class);
		config = mock(FBStandPropsConfiguration.class);
		service = new FBService(rest, config);
	}
	
	@Test
	public void testGetCountryId() {
		//Given
		when(config.getHost()).thenReturn(HOST);
		when(config.getKey()).thenReturn(KEY);
		//When
		String teamId = service.getTeamId(COUNTRY_NAME, ID, KEY);
		
		//Then
		Mockito.verify(rest).exchange(any(URI.class), any(HttpMethod.class), any(), any(Class.class));
		verifyNoMoreInteractions(rest);
	}
	
	@Test
	public void testGetLeagueId() {
		//Given
		when(config.getHost()).thenReturn(HOST);
		when(config.getKey()).thenReturn(KEY);
		//When
		String teamId = service.getTeamId(LEAGUE_NAME, ID, KEY);
		
		//Then
		Mockito.verify(rest).exchange(any(URI.class), any(HttpMethod.class), any(), any(Class.class));
		verifyNoMoreInteractions(rest);
	}
	
	@Test
	public void testgetTeamId() {
		//Given
		when(config.getHost()).thenReturn(HOST);
		when(config.getKey()).thenReturn(KEY);
		//When
		String teamId = service.getTeamId(TEAM_NAME, ID, KEY);
		
		//Then
		Mockito.verify(rest).exchange(any(URI.class), any(HttpMethod.class), any(), any(Class.class));
		verifyNoMoreInteractions(rest);
	}
}
