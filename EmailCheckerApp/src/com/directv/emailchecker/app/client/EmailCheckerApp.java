/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.client;

import com.directv.emailchecker.app.client.bulkupload.BulkUploadScreen;
import com.directv.emailchecker.app.client.dialog.LoadingDialogBox;
import com.directv.emailchecker.app.client.ec.EmailCheckerScreen;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class EmailCheckerApp.
 */
public class EmailCheckerApp implements EntryPoint {

	/** The Constant CONTAINER. */
	private static final String CONTAINER = "container";

	/**
	 * Overridden Method
	 */
	public void onModuleLoad() {

		LoadingDialogBox loadingDialogBox = new LoadingDialogBox("Loading.....", "Loading page..... Please wait for few seconds.....");

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(5);

		Image headerImage = new Image();
		headerImage.setUrl("images/header.jpg");
		headerImage.setHeight("85px");
		headerImage.setWidth("1000px");

		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("1000px");
		tabPanel.setAnimationEnabled(true);

		tabPanel.add(new EmailCheckerScreen(), "Email Verifier");
		tabPanel.add(new BulkUploadScreen(), "Bulk upload");

		tabPanel.selectTab(0);

		vPanel.add(headerImage);
		vPanel.add(tabPanel);

		RootPanel.get(CONTAINER).add(vPanel);

		loadingDialogBox.hideLoaderDialog();
	}

}
