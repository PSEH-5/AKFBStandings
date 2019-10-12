package com.example.fbstandings.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamsData {
	private List<TeamData> teams;

	public List<TeamData> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamData> teams) {
		this.teams = teams;
	} 
}
