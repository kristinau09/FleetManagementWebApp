package com.example.fleetManagementWebApp.restServices;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.example.fleetManagementWebApp.domain.Vehicle;

@XmlRootElement(name="vehicles") //rendering vehicle list as outermost tag for xml will be vehicles
public class VehicleList {
	
	private List<Vehicle> vehicles;
	
	public VehicleList() {}
	
	public VehicleList(List<Vehicle> vehicles) {
		this.vehicles=vehicles;
	}	

	@XmlElement(name="vehicle") //without singular vehicle tag, this method would return whatever it finds which would be vehicles
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	

}
