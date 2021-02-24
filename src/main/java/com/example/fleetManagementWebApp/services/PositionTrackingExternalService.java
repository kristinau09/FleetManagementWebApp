package com.example.fleetManagementWebApp.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.fleetManagementWebApp.controllers.PositionOfVehicle;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@Service
public class PositionTrackingExternalService {
	
	/*
	 * this object will call eureka, and when it gets more than one instance back
	 * from Eureka this object contains the intelligence to choose one of those
	 * instances, not randomly, but using algorithm called round robin algorithm
	 */
	@Autowired
	private LoadBalancerClient balancer;
	
	/*
	 * With the annotation, hystrix knows that this is a method that we want to provide a fallback for.
	 * The fallback method handleExternalServiceDown() will run whenever this method thrown an exception inside it
	 * The fallback method handleExternalServiceDown() needs to be in same class and the signature including
	 *       return type needs to be same athis method
	 */

	@HystrixCommand(fallbackMethod="handleExternalServiceDown")
	public PositionOfVehicle getLatestPositionForVehicleFromRemoteMicroservice(String vehicleName) {
		
		System.out.println("ATTEMPTING TO CALL REMOTE SERVICE::::::");

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

		//making a rest request of current position of that vehicle
		PositionOfVehicle response = restTemplate.getForObject(physicalLocation + "/vehicles/" + vehicleName, PositionOfVehicle.class);
		
		System.out.println("SUCCESS!!!!!");
		
		return response;

	}
	//fallback method
	public PositionOfVehicle handleExternalServiceDown(String vehicleName) {
		
		System.out.println("Running fallback method:::::");
		
		//for now
		PositionOfVehicle position = new PositionOfVehicle();
		position.setlatitude(new BigDecimal("423"));
		position.setLongitude(new BigDecimal("0.0"));
		position.setTimeStamp(new Date());
		
		return position;
	}

}
