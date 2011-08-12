/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.shared;

import com.directv.emailchecker.app.util.ECException;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldVerifier.
 */
public class FieldVerifier {

	/**
	 * Validate.
	 *
	 * @param emailId the email id
	 * @throws ECException the eC exception
	 */
	public static void validate(String emailId) throws ECException {

		if (emailId == null || emailId.trim().equals("")) {
			throw new ECException("EmailId field can't be empty");
		}
	}
}
