package com.example.fbstandings.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaguesData {
	private List<LeagueData> leagues;

	public List<LeagueData> getLeagues() {
		return leagues;
	}

	public void setLeagues(List<LeagueData> leagues) {
		this.leagues = leagues;
	}
}
