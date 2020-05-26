package app.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dto.CoordinatesDTO;
import app.dto.RadarBodyDTO;
import app.interfaces.ServicioDroideInterface;


@RestController
@RequestMapping("")
public class RadarRestController 
{
	@Autowired
    private ServicioDroideInterface service;
	
	@CrossOrigin("*")
	@RequestMapping(value = "/radar", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<CoordinatesDTO> getUsers(@RequestBody RadarBodyDTO body) {
		return new ResponseEntity<>(service.getNextPointToDestroy(body),HttpStatus.OK);
    }
}
