/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.util;

// TODO: Auto-generated Javadoc
/**
 * The Class ECException.
 */
public class ECException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new eC exception.
	 *
	 * @param message the message
	 */
	public ECException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new eC exception.
	 */
	public ECException() {

	}

	/**
	 * Overridden Method.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		String message = super.getMessage();
		return message;
	}
}
