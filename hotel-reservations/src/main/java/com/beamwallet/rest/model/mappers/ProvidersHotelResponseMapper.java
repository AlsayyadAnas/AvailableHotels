package com.beamwallet.rest.model.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.beamwallet.application.enums.HotelProviders;
import com.beamwallet.exceptions.BusinessException;
import com.beamwallet.exceptions.ErrorObj;
import com.beamwallet.rest.model.AvailableHotelsResponse;
import com.beamwallet.rest.model.BestHotelResponse;
import com.beamwallet.rest.model.CrazyHotelResponse;
import com.beamwallet.utils.ApplicationConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProvidersHotelResponseMapper {

	final static Logger logger = Logger.getLogger(ProvidersHotelResponseMapper.class);
	
	/***
	 * Mapping from <b>BestHotelResponse</b> to <b>AvailableHotelsResponse</b> and return them as List of AvailableHotelsResponses
	 * @param bestHotelResponseList
	 * @param providerName
	 * @return List<AvailableHotelsResponse>
	 */
	public static List<AvailableHotelsResponse> mappingBestHotelResponse(List<BestHotelResponse> bestHotelResponseList,
			String providerName) {

		List<AvailableHotelsResponse> availableHotelsResponses = new ArrayList<>();

		for (BestHotelResponse bestHotelResponse : bestHotelResponseList) {
			String hotelName = bestHotelResponse.getHotel();
			String fare = bestHotelResponse.getHotelFare();
			String[] aminities = bestHotelResponse.getRoomAmenities().split(",");
			int rate = bestHotelResponse.getHotelRate();
			AvailableHotelsResponse availableResponse = new AvailableHotelsResponse(providerName, hotelName, fare,
					aminities, rate);
			availableHotelsResponses.add(availableResponse);
		}
		return availableHotelsResponses;
	}

	/***
	 * Mapping from <b>CrazyHotelResponse</b> to <b>AvailableHotelsResponse</b> and return them as List of AvailableHotelsResponses
	 * @param crazyHotelResponseList
	 * @param providerName
	 * @return List<AvailableHotelsResponse>
	 */
	public static List<AvailableHotelsResponse> mappingCrazyHotelResponse(
			List<CrazyHotelResponse> crazyHotelResponseList, String providerName) {

		List<AvailableHotelsResponse> availableHotelsResponses = new ArrayList<>();

		for (CrazyHotelResponse crazyHotelResponse : crazyHotelResponseList) {
			String hotelName = crazyHotelResponse.getHotelName();
			String fare = crazyHotelResponse.getPrice();
			String[] aminities = crazyHotelResponse.getAmenities();
			int rate = getRateValue(crazyHotelResponse.getRate());

			AvailableHotelsResponse availableResponse = new AvailableHotelsResponse(providerName, hotelName, fare,
					aminities, rate);

			availableHotelsResponses.add(availableResponse);
		}

		return availableHotelsResponses;
	}

	/***
	 * This method is responsible of mapping each provider result as AvailableHotelsResponse
	 * @param hotelProvider, the name of the provider 
	 * @param jsonResponse, the response from the provider service as JSON
	 * @return List<AvailableHotelsResponse> 
	 * @throws BusinessException
	 */
	public static List<AvailableHotelsResponse> mappingHotelResponse(HotelProviders hotelProvider,
			String jsonResponse) throws BusinessException {

		logger.info("AvailableHotelResponse Json String  " + jsonResponse);
		
		List<AvailableHotelsResponse> availableHotelsResponseList = new ArrayList<>();		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			if (hotelProvider == HotelProviders.BEST_HOTEL) {

				List<BestHotelResponse> bestHotelResponseList = mapper.readValue(jsonResponse,
						new TypeReference<List<BestHotelResponse>>() {});
				
				availableHotelsResponseList.addAll(ProvidersHotelResponseMapper
						.mappingBestHotelResponse(bestHotelResponseList, ApplicationConstants.BEST_HOTEL_PROVIDER));
			}

			if (hotelProvider == HotelProviders.CRAZY_HOTEL) {
				List<CrazyHotelResponse> crazyHotelResponseList = mapper.readValue(jsonResponse,
						new TypeReference<List<CrazyHotelResponse>>() {});

				availableHotelsResponseList.addAll(ProvidersHotelResponseMapper.mappingCrazyHotelResponse(crazyHotelResponseList,
						ApplicationConstants.CRAZY_HOTEL_PROVIDER));
			}
		} catch (Exception e) {
			
			logger.error("mappingHotelResponse exception  " + Arrays.toString(e.getStackTrace()));
			
			ErrorObj errorObj = new ErrorObj();
			errorObj.setErrorCode(ApplicationConstants.BUSINESS_EXCEPTION);
			errorObj.setErrorMessage(ApplicationConstants.USER_GENERAL_EXCEPTION_MEG);
			throw new BusinessException(errorObj);
		}

		return availableHotelsResponseList;
	}

	/***
	 * Convert the rate starts from the symbol <b>*</b> to an int value 
	 * @param rate, String contains * symbol
	 * @return rate as int value
	 */
	private static int getRateValue(String rate){
		
		if(rate == null || rate.length() > 5){
			throw new IllegalArgumentException("Invalid hotel rate value");
		}		
		return rate.length();
	}
}
