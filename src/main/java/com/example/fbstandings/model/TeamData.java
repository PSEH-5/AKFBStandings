package com.example.fbstandings.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamData {
	private String team_key;
	private String team_name;
	public String getTeam_key() {
		return team_key;
	}
	public void setTeam_key(String team_key) {
		this.team_key = team_key;
	}
	public String getTeam_name() {
		return team_name;
	}
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	}
