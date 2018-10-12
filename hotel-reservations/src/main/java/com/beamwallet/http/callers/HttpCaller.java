package com.beamwallet.http.callers;

import com.beamwallet.exceptions.CommunationFailedException;

/***
 * <p>
 * This Class make the HTTP Calles to an API
 * 
 * @author Anas
 *
 */
public interface HttpCaller {

	/***
	 * This method used to perform the HTTP call, it receives and returns JSON
	 * as String value
	 * 
	 * 
	 * @param jsonString
	 * @return String
	 * @throws CommunationFailedException 
	 */
	public String performHttpRequest(String jsonString, String url) throws CommunationFailedException;
}
