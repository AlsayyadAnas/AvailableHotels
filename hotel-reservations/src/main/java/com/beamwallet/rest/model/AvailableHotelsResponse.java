package com.beamwallet.rest.model;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AvailableHotelsResponse implements Comparable<AvailableHotelsResponse>, Serializable {

	private static final long serialVersionUID = 1L;
	private String provider;
	private String hotelname;
	private String fare;
	private String[] amenities;
	
	@JsonIgnore
	private Integer rate;
	
	public AvailableHotelsResponse() {
		super();
	}
	
	public AvailableHotelsResponse(String provider, String hotelname, String fare, String[] amenities, Integer rate) {
		super();
		this.provider = provider;
		this.hotelname = hotelname;
		this.fare = fare;
		this.amenities = amenities;
		this.rate = rate;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getHotelname() {
		return hotelname;
	}
	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	public String[] getAmenities() {
		return amenities;
	}
	public void setAmenities(String[] amenities) {
		this.amenities = amenities;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	
	@Override
	public String toString() {
		return "AvailableHotelsResponse [provider=" + provider + ", hotelname=" + hotelname + ", fare=" + fare
				+ ", amenities=" + Arrays.toString(amenities) + ", rate=" + rate + "]";
	}

	@Override
	public int compareTo(AvailableHotelsResponse o) {
		return Integer.compare(o.rate,this.rate);
	}
	
}
