/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.client.table;

import com.google.gwt.user.cellview.client.CellTable;

// TODO: Auto-generated Javadoc
/**
 * The Interface TableResources.
 */
public interface TableResources extends CellTable.Resources {

	/**
	 * The Interface TableStyle.
	 */
	interface TableStyle extends CellTable.Style {
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	@Source( { CellTable.Style.DEFAULT_CSS, "CellTable.css" })
	TableStyle cellTableStyle();

}