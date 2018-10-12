package com.beamwallet.service;

import java.util.ArrayList;
import java.util.List;

import com.beamwallet.exceptions.BusinessException;
import com.beamwallet.exceptions.CommunationFailedException;
import com.beamwallet.rest.model.AvailableHotelRequest;
import com.beamwallet.rest.model.AvailableHotelsResponse;

import junit.framework.TestCase;

public class MockAvailableServiceImpl extends TestCase {

	public List<AvailableHotelsResponse> getAvailableHotels(AvailableHotelRequest request) throws CommunationFailedException, BusinessException {
		
		List<AvailableHotelsResponse> availableHotelsResponses = new ArrayList<>();
		String [] amenities = {"snacks","drinks"};	
		
		AvailableHotelsResponse availableHotelsResponse = new AvailableHotelsResponse();
		availableHotelsResponse.setRate(4);
		availableHotelsResponse.setAmenities(amenities);
		availableHotelsResponse.setFare("150");
		availableHotelsResponse.setHotelname("BlackNight");
		availableHotelsResponse.setProvider("BestHotel");
		
		AvailableHotelsResponse availableHotelsResponse2 = new AvailableHotelsResponse();
		availableHotelsResponse2.setRate(5);			
		availableHotelsResponse2.setAmenities(amenities);
		availableHotelsResponse2.setFare("190");
		availableHotelsResponse2.setHotelname("Starts");
		availableHotelsResponse2.setProvider("CrazyHotel");
		
		availableHotelsResponses.add(availableHotelsResponse);
		availableHotelsResponses.add(availableHotelsResponse2);
		
		return availableHotelsResponses;
	}
	
}
