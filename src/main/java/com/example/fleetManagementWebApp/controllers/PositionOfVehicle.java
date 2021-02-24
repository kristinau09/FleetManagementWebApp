package com.example.fleetManagementWebApp.controllers;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PositionOfVehicle {
	
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Date timeStamp;
	
	public BigDecimal getlatitude() {
		return latitude;
	}
	public void setlatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
