package com.example.fleetManagementWebApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.fleetManagementWebApp.controllers.PositionOfVehicle;
import com.example.fleetManagementWebApp.dao.VehicleRepository;
import com.example.fleetManagementWebApp.domain.Vehicle;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@Service
public class PositionTrackingExternalService {
	
	/*
	 * this object will call eureka, and when it gets more than one instance back
	 * from Eureka this object will choose one of those
	 * instances, not randomly, but using algorithm called round robin algorithm
	 */
	@Autowired
	private LoadBalancerClient balancer;
	
	@Autowired
	private VehicleRepository dao;
	
	/*
	 * With the annotation, hystrix knows that this is a method that we want to provide a fallback for.
	 * The fallback method handleExternalServiceDown() will run whenever this method throws an exception inside it
	 * The fallback method handleExternalServiceDown() needs to be in same class and the signature including
	 *       return type needs to be same as this method
	 *       
	 * If the situation becomes dire, then this method will be automatically skipped and Hystrix will immediately go to the fallback   
	 */

	@HystrixCommand(fallbackMethod="handleExternalServiceDown")
	public PositionOfVehicle getLatestPositionForVehicleFromRemoteMicroservice(String vehicleName) {
		
		System.out.println("\nATTEMPTING TO CALL REMOTE SERVICE::::::");

		//get the current position for this vehicle from the microservice
		RestTemplate restTemplate = new RestTemplate();

		ServiceInstance service = balancer.choose("fleetman-position-tracker");
		if(service == null) {
			
			System.out.println("NO SERVICES AVAILABLE:::::");
			//service has crashed
			throw new RuntimeException("Position tracker has crashed!");
		}
		
		System.out.println("Found Service.............");

		//call uri which returns the physical address where the service is located
		String physicalLocation = service.getUri().toString();

		/*
		 * making a rest request of current position of that vehicle, network connection
		 * is going to be made here and remote server is being called
		 */
		PositionOfVehicle response = restTemplate.getForObject(physicalLocation + "/vehicles/" + vehicleName, PositionOfVehicle.class);
		response.setUpToDate(true);
		
		System.out.println("SUCCESS!!!!!");
		
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
