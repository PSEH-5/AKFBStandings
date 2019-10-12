package com.example.fbstandings.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import com.example.fbstandings.model.CountriesData;
import com.example.fbstandings.model.CountryData;
import com.example.fbstandings.model.FBStandingsData;
import com.example.fbstandings.model.LeagueData;
import com.example.fbstandings.model.LeaguesData;
import com.example.fbstandings.model.Result;
import com.example.fbstandings.model.StandingData;
import com.example.fbstandings.model.TeamData;
import com.example.fbstandings.model.TeamsData;

public class FBService {

	private RestOperations rest;

	private static final String APIURL = "https://apiv2.apifootball.com/?action=";

	public FBService(RestOperations rest) {
		this.rest = rest;
	}

	public Result getStanding(String key, String countryName, String leagueName, String teamName) {
		String countryId = getCountryId(countryName, key);
		if(countryId != null) {
			String leagueId = getLeagueId(leagueName, countryId, key);
			if(leagueId != null) {
				String teamId = getTeamId(teamName, leagueId, key);
				if(teamId != null) {
					URI uri = URI.create(APIURL + "get_standings&league_id=" + leagueId + "&APIkey=" + key);
					FBStandingsData resp = rest.exchange(uri, HttpMethod.GET, null, FBStandingsData.class).getBody();
					StandingData data = resp.getStandingData().stream()
							.filter(st -> st.getLeague_id().equals(leagueId) && st.getTeam_id().equals(teamId))
							.collect(Collectors.toList()).get(0);
					Result result = new Result();
					BeanUtils.copyProperties(data, result);
					result.setCountry_id(countryId);
					result.setCountry_name(countryName);
					result.setLeague_name(leagueName);
					result.setTeam_name(teamName);
					return result;
				}
			}

		}
		return null;
		
	}

	String getCountryId(String name, String key) {
		URI uri = URI.create(APIURL + "get_countries&APIkey=" + key);
		ResponseEntity<CountryData[]> countryArr = rest.exchange(uri, HttpMethod.GET, null, CountryData[].class);
		CountriesData countries = new CountriesData();
		countries.setCountries(Arrays.asList(countryArr.getBody()));
		List<CountryData> country = countries.getCountries().stream()
				.filter(c -> c.getCountry_name().equalsIgnoreCase(name)).collect(Collectors.toList());
		if (!country.isEmpty())
			return country.get(0).getCountry_id();
		return null;
	}

	String getLeagueId(String name, String countryId, String key) {
		URI uri = URI.create(APIURL + "get_leagues&country_id=" + countryId + "&APIkey=" + key);
		ResponseEntity<LeagueData[]> leaguesArr = rest.exchange(uri, HttpMethod.GET, null, LeagueData[].class);
		LeaguesData leagues = new LeaguesData();
		leagues.setLeagues(Arrays.asList(leaguesArr.getBody()));
		List<LeagueData> league = leagues.getLeagues().stream().filter(c -> c.getLeague_name().equalsIgnoreCase(name))
				.collect(Collectors.toList());
		if (!league.isEmpty()) {
			return league.get(0).getLeague_id();
		}
		return null;
	}

	String getTeamId(String name, String leagueId, String key) {
		URI uri = URI.create(APIURL + "get_teams&league_id=" + leagueId + "&APIkey=" + key);
		ResponseEntity<TeamData[]> teamsArr = rest.exchange(uri, HttpMethod.GET, null, TeamData[].class);
		TeamsData teams = new TeamsData();
		teams.setTeams(Arrays.asList(teamsArr.getBody()));

		List<TeamData> team =  teams.getTeams().stream().filter(c -> c.getTeam_name().equalsIgnoreCase(name))
				.collect(Collectors.toList());
		if(!team.isEmpty())
			return team.get(0).getTeam_key();
		return null;
	}
}
