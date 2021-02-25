package com.example.fleetManagementWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.fleetManagementWebApp.controllers.PositionOfVehicle;
import com.example.fleetManagementWebApp.dao.VehicleRepository;
import com.example.fleetManagementWebApp.domain.Vehicle;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class PositionTrackingExternalService {
	
	@Autowired
	private VehicleRepository dao;
	
	@Autowired
	//Feign will automatically implement this interface
	private RemotePositionMicroserviceCalls remoteService;
	
	/*     
	 * If the situation becomes dire, then this method will be automatically skipped and Hystrix will immediately go to the fallback   
	 */

	@HystrixCommand(fallbackMethod="handleExternalServiceDown")
	public PositionOfVehicle getLatestPositionForVehicleFromRemoteMicroservice(String vehicleName) {
		
		//remote call
		PositionOfVehicle response = remoteService.getLatestPositionForVehicle(vehicleName);
		response.setUpToDate(true);
		
		return response;

	}
	//fallback method
	public PositionOfVehicle handleExternalServiceDown(String vehicleName) {
		
		System.out.println("Running fallback method:::::");
		
		//read the last known position for this vehicle
		PositionOfVehicle position = new PositionOfVehicle();
		//find the vehicle
		Vehicle vehicle = dao.findByVehicleName(vehicleName);
		System.out.println("\n Vehicle name: " + vehicle);		
		
		position.setLatitude(vehicle.getLatitude());
		position.setLongitude(vehicle.getLongitude());
		position.setTimeStamp(vehicle.getLastRecordedPosition());
		position.setUpToDate(false);
		return position;
	}

}
