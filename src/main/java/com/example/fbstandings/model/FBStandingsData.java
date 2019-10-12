package com.example.fbstandings.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FBStandingsData {
	private List<StandingData> standingData;

	public List<StandingData> getStandingData() {
		return standingData;
	}

	public void setStandingData(List<StandingData> standingData) {
		this.standingData = standingData;
	}

}
