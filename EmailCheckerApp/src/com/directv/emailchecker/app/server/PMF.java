/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PMF.
 */
public final class PMF {

	/** The Constant pmfInstance. */
	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	/**
	 * Instantiates a new pMF.
	 */
	private PMF() {
	}

	/**
	 * Gets the.
	 *
	 * @return the persistence manager factory
	 */
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}

	/**
	 * Gets the persistence manager factory.
	 *
	 * @return the persistence manager factory
	 */
	public static PersistenceManagerFactory getPersistenceManagerFactory() {
		return pmfInstance;
	}
}
