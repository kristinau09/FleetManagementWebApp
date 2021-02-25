package com.example.fleetManagementWebApp.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String vehicleName;
	private int odometer; //measure the distance of vehicle travel in its lifetime
	private String status; //status of the vehicle 
	private BigDecimal latitude;
	private BigDecimal longitude;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastRecordedPosition;
	private String currentDriver;
	private String vin; //vehicle identification number
	
	/*For HiberNate*/
	public Vehicle() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public int getOdometer() {
		return odometer;
	}

	public void setOdometer(int odometer) {
		this.odometer = odometer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public Date getLastRecordedPosition() {
		return lastRecordedPosition;
	}

	public void setLastRecordedPosition(Date lastRecordedPosition) {
		this.lastRecordedPosition = lastRecordedPosition;
	}

	public String getCurrentDriver() {
		return currentDriver;
	}

	public void setCurrentDriver(String currentDriver) {
		this.currentDriver = currentDriver;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", vehicleName=" + vehicleName + ", odometer=" + odometer + ", status=" + status
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", lastRecordedPosition="
				+ lastRecordedPosition + ", currentDriver=" + currentDriver + ", vin=" + vin + "]";
	}

	
	

}
