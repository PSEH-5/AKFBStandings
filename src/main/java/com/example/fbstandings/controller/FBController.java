package com.example.fbstandings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fbstandings.model.Result;
import com.example.fbstandings.service.FBService;

//Author - Arun

@RestController
@Validated
@ResponseBody
public class FBController {
	@Autowired
	FBService service;
	
	@GetMapping("/{cName}/{lName}/{tName}/{key}")
	public ResponseEntity<Result> getStanding(@PathVariable String cName, 
			@PathVariable String lName, @PathVariable String tName,
			@PathVariable String key) {
		Result result = service.getStanding(
				key, cName, lName, tName);
		if(result != null)
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		else
			return new ResponseEntity<Result>(HttpStatus.BAD_REQUEST);
	}
}
