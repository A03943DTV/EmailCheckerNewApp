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
 * The Class Response.
 */
@XmlRootElement(name = "response")
public class Response implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3268420841818772612L;

	/** The Constant EMAIL_CHECKER. */
	public static final String EMAIL_CHECKER = "emailchecker";

	/** The Constant ERROR_MESSAGE. */
	public static final String ERROR_MESSAGE = "error";

	/** The emailid. */
	private String emailid;

	/** The status. */
	private String status;

	/** The emailchecker. */
	private EmailChecker emailchecker;

	/** The error. */
	private ErrorMessage error;

	/**
	 * Instantiates a new response.
	 */
	public Response() {
		super();
	}

	/**
	 * Instantiates a new response.
	 *
	 * @param emailid the emailid
	 * @param status the status
	 * @param emailchecker the emailchecker
	 * @param error the error
	 */
	public Response(String emailid, String status, EmailChecker emailchecker, ErrorMessage error) {
		super();
		this.emailid = emailid;
		this.status = status;
		this.emailchecker = emailchecker;
		this.error = error;
	}

	/**
	 * Gets the emailid.
	 *
	 * @return the emailid
	 */
	@XmlElement
	public String getEmailid() {
		return emailid;
	}

	/**
	 * Sets the emailid.
	 *
	 * @param emailid the new emailid
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	@XmlElement
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the emailchecker.
	 *
	 * @return the emailchecker
	 */
	@XmlElement
	public EmailChecker getEmailchecker() {
		return emailchecker;
	}

	/**
	 * Sets the emailchecker.
	 *
	 * @param emailchecker the new emailchecker
	 */
	public void setEmailchecker(EmailChecker emailchecker) {
		this.emailchecker = emailchecker;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	@XmlElement
	public ErrorMessage getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(ErrorMessage error) {
		this.error = error;
	}

}
