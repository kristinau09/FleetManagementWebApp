package com.example.fleetManagementWebApp.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.fleetManagementWebApp.controllers.PositionOfVehicle;

@FeignClient(name = "fleetman-position-tracker")
public interface RemotePositionMicroserviceCalls {
	
	//Feign is going to automatically convert this into a rest call whch replaces the rest template
	@RequestMapping(method = RequestMethod.GET, value = "/vehicles/{vehicleName}")
	public PositionOfVehicle getLatestPositionForVehicle(@PathVariable("vehicleName") String vehicleName);
	

}
