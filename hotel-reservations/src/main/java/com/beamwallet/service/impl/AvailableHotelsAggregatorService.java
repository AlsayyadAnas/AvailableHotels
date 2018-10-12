package com.beamwallet.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beamwallet.application.enums.HotelProviders;
import com.beamwallet.configuration.holder.ApplicationUrlsHolder;
import com.beamwallet.exceptions.BusinessException;
import com.beamwallet.exceptions.CommunationFailedException;
import com.beamwallet.exceptions.ErrorObj;
import com.beamwallet.http.callers.HttpCaller;
import com.beamwallet.rest.controller.AvailableHotelsController;
import com.beamwallet.rest.model.AvailableHotelRequest;
import com.beamwallet.rest.model.AvailableHotelsResponse;
import com.beamwallet.rest.model.mappers.ProvidersHotelResponseMapper;
import com.beamwallet.service.IAvailableHotelsService;
import com.beamwallet.utils.ApplicationConstants;
import com.google.gson.Gson;

/**
 * An implementation of {@link IAvailableHotelsService}, it follows
 * <b>Aggregator</b> pattern.
 * <p>
 * It will call all available <b>Providers</b>, aggregate their results to an
 * list of available hotels.
 * <p>
 * <b>Aggregator</b> is a simple service that invokes multiple services to
 * achieve the functionality required by the application.
 * 
 * @author Anas
 * @see {@link http://blog.arungupta.me/microservice-design-patterns/}
 */
@Service("availableHotelsService")
public class AvailableHotelsAggregatorService implements IAvailableHotelsService {

	final static Logger logger = Logger.getLogger(AvailableHotelsAggregatorService.class);

	HttpCaller httpCaller;

	@Autowired
	public AvailableHotelsAggregatorService(HttpCaller httpCaller) {
		super();
		this.httpCaller = httpCaller;
	}

	/***
	 * <p>
	 * Returns a list of the available hotels according to the user search data
	 * 
	 * @param <b>AvailableHotelRequest</b>
	 *            contains the user search details
	 * @return A list of the available hotel according to the user search
	 * @throws <b>CommunationFailedException</b>
	 *             in case something wrong happen during service call
	 * @throws <b>BusinessException</b>
	 *             in case runtime error occur
	 */
	@Override
	public List<AvailableHotelsResponse> getAvailableHotels(AvailableHotelRequest request)
			throws CommunationFailedException, BusinessException {

		List<AvailableHotelsResponse> hotelResponses = new ArrayList<AvailableHotelsResponse>();
		if (request == null) {
			throw new IllegalArgumentException("Invalid request data");
		}
		try {

			Gson gson = new Gson();
			String jsonRequestString = gson.toJson(request);

			logger.info("AvailableHotelRequest Json String  " + jsonRequestString);

			ApplicationUrlsHolder applicationUrlsHolder = new ApplicationUrlsHolder();
			Map<HotelProviders, String> providersUrls = applicationUrlsHolder.getHotelProvidersUrls();

			for (Entry<HotelProviders, String> entry : providersUrls.entrySet()) {
				HotelProviders hotelProvider = entry.getKey();
				String providerUrl = entry.getValue();

				String jsonResponse = httpCaller.performHttpRequest(jsonRequestString, providerUrl);

				hotelResponses.addAll(ProvidersHotelResponseMapper.mappingHotelResponse(hotelProvider, jsonResponse));
			}

			Collections.sort(hotelResponses);

		} catch (BusinessException e) {

		} catch (CommunationFailedException e) {
			throw e;
		} catch (Exception e) {

			ErrorObj errorObj = new ErrorObj();
			errorObj.setErrorCode(ApplicationConstants.UNKNOWN_EXCEPTION);
			errorObj.setErrorMessage("Sorry, Service currently unavailable");

			throw new BusinessException(errorObj);
		}

		return hotelResponses;
	}

	public static String formatDate(Date date) {

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		String dateString = dateFormat.format(date);
		return dateString;

	}

	// private String getJsonValue(AvailableHotelRequest request){
	//
	// LocalDate fromDate = LocalDate.now();
	// LocalDate toDate = LocalDate.now();
	//
	// String b = fromDate.format(DateTimeFormatter.ISO_DATE);
	//
	// return "";
	// }

}
