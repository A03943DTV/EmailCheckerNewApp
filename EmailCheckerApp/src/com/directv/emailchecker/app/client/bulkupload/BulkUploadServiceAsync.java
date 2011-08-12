/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.client.bulkupload;

import java.util.List;

import com.directv.emailchecker.app.shared.BulkUploadDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface BulkUploadServiceAsync.
 */
public interface BulkUploadServiceAsync {

	/**
	 * Process bulk upload.
	 *
	 * @param callback the callback
	 */
	void processBulkUpload(AsyncCallback<Void> callback);

	/**
	 * Dummy.
	 *
	 * @param bulkUploadDto the bulk upload dto
	 * @param callback the callback
	 */
	void dummy(BulkUploadDto bulkUploadDto, AsyncCallback<Void> callback);

	/**
	 * Gets the bulk upload results.
	 *
	 * @param callback the callback
	 * @return the bulk upload results
	 */
	void getBulkUploadResults(AsyncCallback<List<BulkUploadDto>> callback);

	/**
	 * Gets the bulk upload by id.
	 *
	 * @param id the id
	 * @param callback the callback
	 * @return the bulk upload by id
	 */
	void getBulkUploadById(Long id, AsyncCallback<BulkUploadDto> callback);

}
