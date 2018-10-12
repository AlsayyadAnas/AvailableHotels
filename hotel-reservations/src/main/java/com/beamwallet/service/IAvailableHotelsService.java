package com.beamwallet.service;

import java.util.List;

import com.beamwallet.exceptions.BusinessException;
import com.beamwallet.exceptions.CommunationFailedException;
import com.beamwallet.rest.model.AvailableHotelRequest;
import com.beamwallet.rest.model.AvailableHotelsResponse;

public interface IAvailableHotelsService {

	public List<AvailableHotelsResponse> getAvailableHotels(AvailableHotelRequest request) throws CommunationFailedException, BusinessException ;
}
