/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.shared;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BUUser.
 */
public class BUUser implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1131455123225608846L;

	/** The email id. */
	private String emailId;

	/** The status. */
	private String status;

	/** The is valid. */
	private String isValid;

	/** The status no. */
	private String statusNo;

	/** The description. */
	private String description;

	/** The error message. */
	private String errorMessage;

	/** The Constant STATUS_SUCCESS. */
	public static final String STATUS_SUCCESS = "Success";

	/** The Constant STATUS_ERROR. */
	public static final String STATUS_ERROR = "Error";

	/**
	 * Instantiates a new bU user.
	 */
	public BUUser() {

	}

	/**
	 * Instantiates a new bU user.
	 *
	 * @param emailId the email id
	 * @param status the status
	 * @param isValid the is valid
	 * @param statusNo the status no
	 * @param description the description
	 * @param errorMessage the error message
	 */
	public BUUser(String emailId, String status, String isValid, String statusNo, String description, String errorMessage) {
		super();
		this.emailId = emailId;
		this.status = status;
		this.isValid = isValid;
		this.statusNo = statusNo;
		this.description = description;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the email id.
	 *
	 * @return the email id
	 */
	public String getEmailId() {
		return emailId != null ? emailId.trim() : "";
	}

	/**
	 * Sets the email id.
	 *
	 * @param emailId the new email id
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status != null ? status.trim() : "";
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
	 * Gets the checks if is valid.
	 *
	 * @return the checks if is valid
	 */
	public String getIsValid() {
		return isValid != null ? isValid.trim() : "";
	}

	/**
	 * Sets the checks if is valid.
	 *
	 * @param isValid the new checks if is valid
	 */
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	/**
	 * Gets the status no.
	 *
	 * @return the status no
	 */
	public String getStatusNo() {
		return statusNo != null ? statusNo.trim() : "";
	}

	/**
	 * Sets the status no.
	 *
	 * @param statusNo the new status no
	 */
	public void setStatusNo(String statusNo) {
		this.statusNo = statusNo;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description != null ? description.trim() : "";
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage != null ? errorMessage.trim() : "";
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
