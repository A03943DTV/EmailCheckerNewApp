/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.util;

import java.util.List;

import com.directv.emailchecker.app.shared.BUUser;

// TODO: Auto-generated Javadoc
/**
 * The Class FormCSVData.
 */
public class FormCSVData {

	/**
	 * Form data.
	 *
	 * @param headerDataArray the header data array
	 * @param userList the user list
	 * @return the string buffer
	 */
	public static StringBuffer formData(String[] headerDataArray, List<BUUser> userList) {

		StringBuffer stringBuilder = new StringBuffer();
		for (String headerData : headerDataArray) {

			stringBuilder.append(headerData);
			stringBuilder.append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1); // remove last ","
		stringBuilder.append("\n");

		for (BUUser user : userList) {

			stringBuilder.append(user.getEmailId());
			stringBuilder.append(",");
			stringBuilder.append(user.getStatus());
			stringBuilder.append(",");
			stringBuilder.append(user.getIsValid());
			stringBuilder.append(",");
			stringBuilder.append(user.getStatusNo());
			stringBuilder.append(",");
			stringBuilder.append(user.getDescription());
			stringBuilder.append(",");
			stringBuilder.append(user.getErrorMessage());
			stringBuilder.append("\n");
		}

		return stringBuilder;
	}
}
