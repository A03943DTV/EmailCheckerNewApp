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
 * The Class EmailChecker.
 */
@XmlRootElement(name = "emailchecker")
public class EmailChecker implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4827383878395740874L;

	/** The statusno. */
	private long statusno;

	/** The description. */
	private String description;

	/** The isvalid. */
	private boolean isvalid;

	/**
	 * Instantiates a new email checker.
	 */
	public EmailChecker() {

	}

	/**
	 * Instantiates a new email checker.
	 *
	 * @param statusno the statusno
	 * @param description the description
	 */
	public EmailChecker(long statusno, String description) {
		super();
		this.statusno = statusno;
		this.description = description;
	}

	/**
	 * Checks if is isvalid.
	 *
	 * @return true, if is isvalid
	 */
	@XmlElement
	public boolean isIsvalid() {
		return isvalid;
	}

	/**
	 * Sets the isvalid.
	 *
	 * @param isvalid the new isvalid
	 */
	public void setIsvalid(boolean isvalid) {
		this.isvalid = isvalid;
	}

	/**
	 * Gets the statusno.
	 *
	 * @return the statusno
	 */
	@XmlElement
	public long getStatusno() {
		return statusno;
	}

	/**
	 * Sets the statusno.
	 *
	 * @param statusno the new statusno
	 */
	public void setStatusno(long statusno) {
		this.statusno = statusno;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	@XmlElement
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
