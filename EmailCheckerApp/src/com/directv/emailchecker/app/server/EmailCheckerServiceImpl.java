/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.directv.emailchecker.app.client.ec.EmailCheckerService;
import com.directv.emailchecker.app.shared.EmailChecker;
import com.directv.emailchecker.app.shared.ErrorMessage;
import com.directv.emailchecker.app.shared.Response;
import com.directv.emailchecker.app.util.ECConstants;
import com.directv.emailchecker.app.util.ECException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailCheckerServiceImpl.
 */
@SuppressWarnings("serial")
public class EmailCheckerServiceImpl extends RemoteServiceServlet implements EmailCheckerService {

	/** The Constant TARGET_URL. */
	private static final String TARGET_URL = "http://208.115.106.197:9099/EmailCheckerServiceApache/rest/directv/emailchecker/";

	/** The Constant STRIKEIRON_TARGET_URL. */
	private static final String STRIKEIRON_TARGET_URL = "http://208.115.106.197:9099/EmailCheckerServiceApache/rest/strikeirondirectv/emailchecker/";

	/**
	 * Overridden Method
	 * @param emailId
	 * @return
	 * @throws ECException 
	 */
	@Override
	public Response validateEmailId(String serviceProvider, String emailId) throws ECException {

		URL url = null;
		HttpURLConnection connection = null;
		Response response = null;
		InputStream is = null;
		BufferedReader br = null;
		String urlTemp = null;

		try {

			if (serviceProvider.equals(ECConstants.IPADDRESS_ORG)) {
				url = new URL(TARGET_URL + emailId);
				urlTemp = TARGET_URL;
			} else if (serviceProvider.equals(ECConstants.STRIKE_IRON)) {
				url = new URL(STRIKEIRON_TARGET_URL + emailId);
				urlTemp = STRIKEIRON_TARGET_URL;
			}
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			System.setProperty("streambase.tuple-connection-timeout", "30000");

			System.out.println("Connection Timeout in milliseconds : " + connection.getConnectTimeout());
			System.out.println("Read Timeout in milliseconds : " + connection.getReadTimeout());

			int statusCode = connection.getResponseCode();
			if (statusCode != 200) {
				throw new ECException("Invalid HTTP response status code " + statusCode + " from web service server.");
			}

			//Get Response	
			is = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			String jsonData = br.readLine();
			response = getBeanFromJson(jsonData);

		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof ECException) {
				throw new ECException(e.getMessage());
			} else {
				throw new ECException("Exception occured while connecting to URL : " + urlTemp);
			}
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return response;
	}

	/**
	 * Gets the bean from json.
	 *
	 * @param jsonData the json data
	 * @return the bean from json
	 */
	@SuppressWarnings("unchecked")
	private Response getBeanFromJson(String jsonData) {

		Map mappingResources = new HashMap();
		mappingResources.put(Response.EMAIL_CHECKER, EmailChecker.class);
		mappingResources.put(Response.ERROR_MESSAGE, ErrorMessage.class);
		return (Response) JSONObject.toBean(JSONObject.fromObject(jsonData), Response.class, mappingResources);
	}

}
