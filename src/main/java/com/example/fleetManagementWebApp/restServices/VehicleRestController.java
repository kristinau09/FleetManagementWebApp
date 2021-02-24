package com.example.fleetManagementWebApp.restServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.fleetManagementWebApp.dao.VehicleRepository;
import com.example.fleetManagementWebApp.domain.Vehicle;

@RestController
@RequestMapping("/")
public class VehicleRestController {
	
	@Autowired
	private VehicleRepository dao;
	
	@RequestMapping(value="/vehicles", produces=MediaType.APPLICATION_XML_VALUE)
	public VehicleList allVehicles() {
		
		List<Vehicle> listOfVehicles = dao.findAll();
		return new VehicleList(listOfVehicles);
	}
	/*
	 * @RequestBody---> Spring will automatically take contents of the request body provided by user and convert it
	 * either from JSON or XML depending on what the client has sent into a job
	 * object, in this case it would be a vehicle.
	 */
	
	@RequestMapping(value="/vehicles", method=RequestMethod.POST)
	public ResponseEntity<Vehicle> createNewVehicle(@RequestBody Vehicle vehicle) { 		
		
		System.out.println(vehicle.toString());
		dao.save(vehicle);
		return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
	}

}
