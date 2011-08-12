/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.directv.emailchecker.app.client.bulkupload.BulkUploadService;
import com.directv.emailchecker.app.dao.BulkUpload;
import com.directv.emailchecker.app.dao.BulkUploadDaoImpl;
import com.directv.emailchecker.app.shared.BUUser;
import com.directv.emailchecker.app.shared.BulkUploadDto;
import com.directv.emailchecker.app.shared.Response;
import com.directv.emailchecker.app.util.ECException;
import com.directv.emailchecker.app.util.FormCSVData;
import com.google.appengine.api.datastore.Blob;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadServiceImpl.
 */
public class BulkUploadServiceImpl extends RemoteServiceServlet implements BulkUploadService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6014641590287705576L;

	/** The Constant HEADER_DATA. */
	//Don't change this order
	private static final String HEADER_DATA[] = new String[] { "EmailId", "Status", "IsValid", "StatusNo", "Description", "ErrorMessage" };

	/** The Constant COLUMN_COUNT. */
	//Don't change this order
	private static final int COLUMN_COUNT = (HEADER_DATA.length - 5);//Excluding {"Status", "IsValid", "StatusNo", "Description", "ErrorMessage" }

	/**
	 * Overridden Method
	 */
	@Override
	public void processBulkUpload() {

		List<BulkUpload> bulkUploadUnProcessedList = new BulkUploadDaoImpl().getUnProcessedBUList();

		for (BulkUpload bulkUpload : bulkUploadUnProcessedList) {

			bulkUpload.setProcessStatus(BulkUpload.PROCESS_STATUS_PROCESSING);
			bulkUpload.setProcessStratTime((new Timestamp(System.currentTimeMillis())).toString());
			new BulkUploadDaoImpl().updateBulkUpload(bulkUpload);

			bulkUpload = processRecord(bulkUpload);

			bulkUpload.setProcessStatus(BulkUpload.PROCESS_STATUS_COMPLETED);
			bulkUpload.setProcessEndTime((new Timestamp(System.currentTimeMillis())).toString());
			new BulkUploadDaoImpl().updateBulkUpload(bulkUpload);
		}
	}

	/**
	 * Process record.
	 *
	 * @param bulkUpload the bulk upload
	 * @return the bulk upload
	 */
	private BulkUpload processRecord(BulkUpload bulkUpload) {

		int noOfSuccessfullRecords = 0;
		int noOfUnSuccessFullRecords = 0;

		List<BUUser> userList = getUsersList(bulkUpload.getBlob().getBytes(), false);

		System.out.println("Total No Of Records to be processed : " + userList.size());

		int count = 1;
		for (BUUser user : userList) {

			try {

				System.out.println("Processing record : " + count + " : " + user.getEmailId());
				Response response = null;

				//Making 3 Attempts before throwing an error.
				try {
					System.out.println("Attempt No 1 for : " + user.getEmailId());
					response = new EmailCheckerServiceImpl().validateEmailId(bulkUpload.getServiceProvider(), user.getEmailId());
				} catch (Exception excep1) {
					delayBy(20000);
					try {
						System.out.println("Attempt No 2 for : " + user.getEmailId());
						response = new EmailCheckerServiceImpl().validateEmailId(bulkUpload.getServiceProvider(), user.getEmailId());
					} catch (Exception excep2) {
						delayBy(45000);
						System.out.println("Attempt No 3 for : " + user.getEmailId());
						response = new EmailCheckerServiceImpl().validateEmailId(bulkUpload.getServiceProvider(), user.getEmailId());
					}
				}

				if (response.getEmailchecker() != null) {
					user.setIsValid(String.valueOf(response.getEmailchecker().isIsvalid()));
					user.setStatusNo(String.valueOf(response.getEmailchecker().getStatusno()));
					user.setDescription(response.getEmailchecker().getDescription());
					noOfSuccessfullRecords++;
				}
				if (response.getError() != null) {
					user.setErrorMessage(response.getError().getErrormessage());
					noOfUnSuccessFullRecords++;
				}
				user.setStatus(response.getStatus());

			} catch (ECException e) {
				e.printStackTrace();
				user.setStatus(BUUser.STATUS_ERROR);
				user.setErrorMessage(e.getMessage());
				noOfUnSuccessFullRecords++;
			} catch (Throwable e) {
				e.printStackTrace();
				user.setStatus(BUUser.STATUS_ERROR);
				user.setErrorMessage("Unexpected exception occured");
				noOfUnSuccessFullRecords++;
			}
			count++;

		}
		bulkUpload.setNoOfSuccessRecords(new Long(noOfSuccessfullRecords));
		bulkUpload.setNoOfFailureRecords(new Long(noOfUnSuccessFullRecords));

		if (bulkUpload.getNoOfFailureRecords() == 0) {
			bulkUpload.setStatus(BulkUpload.STATUS_SUCCESS);
		} else {
			bulkUpload.setStatus(BulkUpload.STATUS_ERROR);
		}

		StringBuffer stringBuilder = FormCSVData.formData(HEADER_DATA, userList);
		bulkUpload.setBlob(new Blob(stringBuilder.toString().getBytes()));

		return bulkUpload;
	}

	/**
	 * Delay by.
	 *
	 * @param milliSeconds the milli seconds
	 */
	private void delayBy(int milliSeconds) {

		try {
			Thread.sleep(milliSeconds);
		} catch (Exception excep) {
			System.out.println("Thread Interuption Exception : " + excep.getMessage());
		}
	}

	/**
	 * Gets the single user data.
	 *
	 * @param line the line
	 * @param isInfoCheck the is info check
	 * @return the single user data
	 */
	private BUUser getSingleUserData(String line, boolean isInfoCheck) {

		String userInfoArray[] = null;
		if (!isInfoCheck) {
			userInfoArray = new String[COLUMN_COUNT];
		} else {
			userInfoArray = new String[HEADER_DATA.length];
		}
		String userInfoTempArray[] = line.split(",");
		for (int idx = 0; idx < userInfoTempArray.length; idx++) {
			userInfoArray[idx] = userInfoTempArray[idx];
		}

		BUUser user = new BUUser(userInfoArray[0], null, null, null, null, null);
		if (isInfoCheck) {
			user.setStatus(userInfoArray[1]);
			user.setIsValid(userInfoArray[2]);
			user.setStatusNo(userInfoArray[3]);
			user.setDescription(userInfoArray[4]);
			user.setErrorMessage(userInfoArray[5]);
		}
		return user;
	}

	/**
	 * Overridden Method
	 * @return
	 */
	@Override
	public List<BulkUploadDto> getBulkUploadResults() {

		List<BulkUploadDto> bulkuploadDtoList = new ArrayList<BulkUploadDto>();
		for (BulkUpload bulkUpload : new BulkUploadDaoImpl().getBulkUploadResults()) {
			bulkuploadDtoList.add(getBulkUploadDto(bulkUpload));
		}
		return bulkuploadDtoList;
	}

	/**
	 * Gets the bulk upload dto.
	 *
	 * @param bulkUpload the bulk upload
	 * @return the bulk upload dto
	 */
	private BulkUploadDto getBulkUploadDto(BulkUpload bulkUpload) {

		byte[] byteArray = bulkUpload.getBlob().getBytes();
		return new BulkUploadDto(bulkUpload.getId(), bulkUpload.getDescription(), byteArray, bulkUpload.getProcessStatus(), bulkUpload
				.getSubmittedTime(), bulkUpload.getProcessStratTime(), bulkUpload.getProcessEndTime(), bulkUpload.getNoOfSuccessRecords(), bulkUpload
				.getNoOfFailureRecords(), bulkUpload.getStatus(), bulkUpload.getServiceProvider());
	}

	/**
	 * Overridden Method
	 * @param id
	 * @return
	 */
	@Override
	public BulkUploadDto getBulkUploadById(Long id) {

		BulkUpload bulkUpload = new BulkUploadDaoImpl().getBulkUploadById(id);
		byte[] byteArray = bulkUpload.getBlob().getBytes();

		List<BUUser> userList = getUsersList(byteArray, true);

		BulkUploadDto bulkUploadDto = getBulkUploadDto(bulkUpload);
		bulkUploadDto.setUserList(userList);

		return bulkUploadDto;
	}

	/**
	 * Gets the users list.
	 *
	 * @param byteArray the byte array
	 * @param isInfoCheck the is info check
	 * @return the users list
	 */
	private List<BUUser> getUsersList(byte[] byteArray, boolean isInfoCheck) {

		InputStream is = new ByteArrayInputStream(byteArray);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;
		int count = 1;
		List<BUUser> userList = new ArrayList<BUUser>();
		try {
			while ((line = br.readLine()) != null) {
				if (count == 1) {
					System.out.println("Ignoring Header Information");
				} else {
					BUUser user = getSingleUserData(line, isInfoCheck);
					userList.add(user);
				}
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * Overridden Method
	 * @param bulkUploadDto
	 */
	@Override
	public void dummy(BulkUploadDto bulkUploadDto) {

	}
}
