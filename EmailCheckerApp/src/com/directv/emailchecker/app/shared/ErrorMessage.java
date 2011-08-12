/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.shared;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ErrorMessage.
 */
@XmlRootElement(name = "error")
public class ErrorMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5599946033931386482L;

	/** The errormessage. */
	private String errormessage;

	/**
	 * Instantiates a new error message.
	 */
	public ErrorMessage() {
		super();
	}

	/**
	 * Instantiates a new error message.
	 *
	 * @param errormessage the errormessage
	 */
	public ErrorMessage(String errormessage) {
		super();
		this.errormessage = errormessage;
	}

	/**
	 * Gets the errormessage.
	 *
	 * @return the errormessage
	 */
	@XmlElement
	public String getErrormessage() {
		return errormessage;
	}

	/**
	 * Sets the errormessage.
	 *
	 * @param errormessage the new errormessage
	 */
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

}
