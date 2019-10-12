package com.example.fbstandings.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import com.example.fbstandings.configuration.FBStandPropsConfiguration;
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

	private FBStandPropsConfiguration props;

	private static final String APIURL = "https://apiv2.apifootball.com/?action=";

	public FBService(RestOperations rest, FBStandPropsConfiguration props) {
		this.rest = rest;
		this.props = props;
	}

	public Result getStanding(String countryName, String leagueName, String teamName) {
		String key = props.getKey();
		String countryId = getCountryId(countryName, key);
		if (countryId != null) {
			String leagueId = getLeagueId(leagueName, countryId, key);
			if (leagueId != null) {
				String teamId = getTeamId(teamName, leagueId, key);
				if (teamId != null) {
					URI uri = URI.create(props.getHost() + "get_standings&league_id=" + leagueId + "&APIkey=" + key);
					StandingData[] resp = rest.exchange(uri, HttpMethod.GET, null, StandingData[].class).getBody();
					FBStandingsData stds = new FBStandingsData();
					stds.setStandingData(Arrays.asList(resp));
					List<StandingData> data = stds.getStandingData().stream()
							.filter(st -> st.getLeague_id().equals(leagueId) && st.getTeam_id().equals(teamId))
							.collect(Collectors.toList());
					if (!data.isEmpty()) {
						StandingData val = data.get(0);
						Result result = new Result();
						BeanUtils.copyProperties(val, result);
						result.setCountry_id(countryId);
						result.setCountry_name(countryName);
						result.setLeague_name(leagueName);
						result.setTeam_name(teamName);
						result.setTeam_key(teamId);
						return result;
					}
				}
			}

		}
		return null;

	}

	String getCountryId(String name, String key) {
		URI uri = URI.create(props.getHost() + "get_countries&APIkey=" + key);
		ResponseEntity<CountryData[]> countryArr = rest.exchange(uri, HttpMethod.GET, null, CountryData[].class);
		if (countryArr != null) {
			CountriesData countries = new CountriesData();
			countries.setCountries(Arrays.asList(countryArr.getBody()));
			List<CountryData> country = countries.getCountries().stream()
					.filter(c -> c.getCountry_name().equalsIgnoreCase(name)).collect(Collectors.toList());
			if (!country.isEmpty())
				return country.get(0).getCountry_id();
		}
		return null;
	}

	String getLeagueId(String name, String countryId, String key) {
		URI uri = URI.create(props.getHost() + "get_leagues&country_id=" + countryId + "&APIkey=" + key);
		ResponseEntity<LeagueData[]> leaguesArr = rest.exchange(uri, HttpMethod.GET, null, LeagueData[].class);
		if (leaguesArr != null) {
			LeaguesData leagues = new LeaguesData();
			leagues.setLeagues(Arrays.asList(leaguesArr.getBody()));
			List<LeagueData> league = leagues.getLeagues().stream()
					.filter(c -> c.getLeague_name().equalsIgnoreCase(name)).collect(Collectors.toList());
			if (!league.isEmpty()) {
				return league.get(0).getLeague_id();
			}
		}
		return null;
	}

	String getTeamId(String name, String leagueId, String key) {
		URI uri = URI.create(props.getHost() + "get_teams&league_id=" + leagueId + "&APIkey=" + key);
		ResponseEntity<TeamData[]> teamsArr = rest.exchange(uri, HttpMethod.GET, null, TeamData[].class);
		if (teamsArr != null) {
			TeamsData teams = new TeamsData();
			teams.setTeams(Arrays.asList(teamsArr.getBody()));

			List<TeamData> team = teams.getTeams().stream().filter(c -> c.getTeam_name().equalsIgnoreCase(name))
					.collect(Collectors.toList());
			if (!team.isEmpty())
				return team.get(0).getTeam_key();
		}
		return null;
	}
}
