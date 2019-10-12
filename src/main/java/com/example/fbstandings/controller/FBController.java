package com.example.fbstandings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fbstandings.model.Result;
import com.example.fbstandings.service.FBService;

//Author - Arun Kumar

@RestController
@Validated
@ResponseBody
public class FBController {
	@Autowired
	FBService service;
	
	@GetMapping("/{cName}/{lName}/{tName}")
	public ResponseEntity<Result> getStanding(@PathVariable @NonNull String cName, 
			@NonNull @PathVariable String lName, @PathVariable @NonNull String tName) {
		Result result = service.getStanding(
				cName, lName, tName);
		if(result != null)
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		else
			return new ResponseEntity<Result>(HttpStatus.BAD_REQUEST);
	}
}
