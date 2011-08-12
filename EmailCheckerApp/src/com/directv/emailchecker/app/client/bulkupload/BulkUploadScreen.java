/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.emailchecker.app.client.bulkupload;

import java.util.ArrayList;
import java.util.List;

import com.directv.emailchecker.app.client.dialog.BuInfoDialogBox;
import com.directv.emailchecker.app.client.dialog.LoadingDialogBox;
import com.directv.emailchecker.app.client.dialog.NormalDialogBox;
import com.directv.emailchecker.app.client.table.CustomizedImageCell;
import com.directv.emailchecker.app.client.table.TableResources;
import com.directv.emailchecker.app.shared.BUUser;
import com.directv.emailchecker.app.shared.BulkUploadDto;
import com.directv.emailchecker.app.util.ECConstants;
import com.directv.emailchecker.app.util.ErrorMessageUtil;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.CellTable.Resources;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadScreen.
 */
@SuppressWarnings("deprecation")
public class BulkUploadScreen extends Composite {

	/** The bulk upload service. */
	private final BulkUploadServiceAsync bulkUploadService = GWT.create(BulkUploadService.class);

	/** The form. */
	private final FormPanel form = new FormPanel();

	/** The submit button. */
	private Button submitButton = new Button("Submit");

	/** The clear button. */
	private Button clearButton = new Button("Clear");

	/** The refresh grid button. */
	private Button refreshGridButton = new Button("Refresh Grid");

	/** The description text field. */
	private TextBox descriptionTextField = new TextBox();

	/** The file upload. */
	private FileUpload fileUpload = new FileUpload();

	/** The file lable. */
	private Label fileLable = new Label("File");

	/** The description lable. */
	private Label descriptionLable = new Label("Description");

	/** The bu table. */
	private CellTable<BulkUploadDto> buTable = new CellTable<BulkUploadDto>(8000, (Resources) GWT.create(TableResources.class));

	/** The data provider. */
	private ListDataProvider<BulkUploadDto> dataProvider = new ListDataProvider<BulkUploadDto>();

	/** The list bu main from web service. */
	private List<BulkUploadDto> listBuMainFromWebService = new ArrayList<BulkUploadDto>();

	/** The download lable. */
	private Label downloadLable = new Label("If you don't have the bulk upload template please");

	/** The anchor template. */
	private Anchor anchorTemplate = new Anchor("clickhere");

	/** The service provider label. */
	private Label serviceProviderLabel = new Label("Service Provider");

	/** The ip address radio button. */
	private RadioButton ipAddressRadioButton = new RadioButton("serviceProvider", ECConstants.IPADDRESS_ORG);

	/** The strike iron radio button. */
	private RadioButton strikeIronRadioButton = new RadioButton("serviceProvider", ECConstants.STRIKE_IRON);

	/** The sp hidden text field. */
	private TextBox spHiddenTextField = new TextBox();

	/** The user table. */
	private CellTable<BUUser> userTable = new CellTable<BUUser>(8000, (Resources) GWT.create(TableResources.class));

	/** The user data provider. */
	private ListDataProvider<BUUser> userDataProvider = new ListDataProvider<BUUser>();

	/** The v table panel info. */
	private VerticalPanel vTablePanelInfo = new VerticalPanel();

	/** The loading dialog box. */
	protected LoadingDialogBox loadingDialogBox;

	/**
	 * Instantiates a new bulk upload screen.
	 */
	public BulkUploadScreen() {

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setSpacing(5);

		anchorTemplate.setHref("templates/EmailInfo.csv");

		HorizontalPanel hDownloadPanel = new HorizontalPanel();
		hDownloadPanel.add(downloadLable);
		hDownloadPanel.add(anchorTemplate);
		hDownloadPanel.setSpacing(5);

		form.setAction("/userBulkUpload");
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		form.setWidget(vPanel);

		fileUpload.setName("uploadFormField");
		descriptionTextField.setName("description");
		spHiddenTextField.setName("spHiddenTextField");

		Grid grid = getFormGrid();

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(submitButton);
		hPanel.add(clearButton);
		hPanel.add(refreshGridButton);
		hPanel.setSpacing(5);

		//Loading DB data and Setting the Columns,Values and Pagination for BU Table
		VerticalPanel vTablePanel = loadDataAndSetBuTableValues();

		//Adding scroller to the user table
		ScrollPanel scrollerPanel = new ScrollPanel(userTable);
		scrollerPanel.setWidth("950px");
		scrollerPanel.setHeight("260px");
		vTablePanelInfo.add(scrollerPanel);
		vTablePanelInfo.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		vPanel.add(grid);
		vPanel.add(hDownloadPanel);
		vPanel.add(hPanel);
		vPanel.add(vTablePanel);

		submitButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.submit();
			}
		});

		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearFormFields();
			}
		});

		refreshGridButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				listBuInfo();
			}
		});

		// Add an event handler to the form.
		form.addFormHandler(new FormHandler() {

			public void onSubmit(FormSubmitEvent event) {
				// This event is fired just before the form is submitted. We can take
				// this opportunity to perform validation.
				String serviceProvider = ipAddressRadioButton.isChecked() ? ECConstants.IPADDRESS_ORG : ECConstants.STRIKE_IRON;
				spHiddenTextField.setValue(serviceProvider);
			}

			public void onSubmitComplete(FormSubmitCompleteEvent event) {

				String errorMessage = ErrorMessageUtil.getErrorMessage(event.getResults());
				if (errorMessage != null) {
					new NormalDialogBox("Validation Error", errorMessage);
					return;
				}

				clearFormFields();
				listBuInfo();

				bulkUploadService.processBulkUpload(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Bulk Upload Processing Error : " + caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						System.out.println("Bulk Upload Processing Success");
					}
				});
			}
		});

		initWidget(form);
	}

	/**
	 * Load data and set bu table values.
	 *
	 * @return the vertical panel
	 */
	private VerticalPanel loadDataAndSetBuTableValues() {

		initializeUserInfoContents();

		//Load the BulkUpload data's from DB/WebService
		listBuInfo();

		return setBuTableValues();
	}

	/**
	 * Initialize user info contents.
	 */
	@SuppressWarnings("unused")
	private void initializeUserInfoContents() {

		userTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		//Adding the columns to userTable
		Column<BUUser, String> emailIdColumn = generateEmailResultEmailIdColumn();
		Column<BUUser, String> isValidColumn = generateEmailResultIsValidColumn();
		Column<BUUser, String> statusNoColumn = generateEmailResultStatusNoColumn();
		Column<BUUser, String> descriptionColumn = generateEmailResultDescriptionColumn();
		Column<BUUser, String> statusColumn = generateEmailResultStatusColumn();
		Column<BUUser, String> errorMessageColumn = generateEmailResultErrorMessageColumn();

		userDataProvider.addDataDisplay(userTable);
	}

	/**
	 * List bu info.
	 */
	private void listBuInfo() {

		loadingDialogBox = new LoadingDialogBox("Loading.....", "Loading BUInfo..... Please wait for few seconds.....");

		bulkUploadService.getBulkUploadResults(new AsyncCallback<List<BulkUploadDto>>() {

			@Override
			public void onFailure(Throwable caught) {
				loadingDialogBox.hideLoaderDialog();
				System.out.println("Bulk Upload List Fetching Error : " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<BulkUploadDto> result) {
				System.out.println("Bulk Upload List Fetching Successfull : " + result.size());
				listBuMainFromWebService = result;
				dataProvider.getList().clear();
				List<BulkUploadDto> dataProviderList = dataProvider.getList();
				for (BulkUploadDto buDto : listBuMainFromWebService) {
					dataProviderList.add(buDto);
				}
				dataProvider.refresh();
				buTable.redraw();
				loadingDialogBox.hideLoaderDialog();
			}
		});
	}

	/**
	 * Sets the bu table values.
	 *
	 * @return the vertical panel
	 */
	@SuppressWarnings("unused")
	private VerticalPanel setBuTableValues() {

		buTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		//Adding the columns to buTable
		Column<BulkUploadDto, String> infoColumn = generateInfoColumn();
		Column<BulkUploadDto, String> downloadColumn = generateDownloadColumn();
		Column<BulkUploadDto, String> processStatusColumn = generateProcessStatusColumn();
		Column<BulkUploadDto, String> idColumn = generateIdColumn();
		Column<BulkUploadDto, String> descriptionColumn = generateDescriptionColumn();
		Column<BulkUploadDto, String> submittedTimeColumn = generateSubmittedTimeColumn();
		Column<BulkUploadDto, String> processStartTimeColumn = generateProcessStartTimeColumn();
		Column<BulkUploadDto, String> processEndTimeColumn = generateProcessEndTimeColumn();
		Column<BulkUploadDto, String> noOfFailureRecordsColumn = generateNoOfFailureRecordsColumn();
		Column<BulkUploadDto, String> noOfSuccessRecordsColumn = generateNoOfSuccessRecordsColumn();
		Column<BulkUploadDto, String> statusColumn = generateStatusColumn();
		Column<BulkUploadDto, String> serviceProviderColumn = generateServiceProviderColumn();

		dataProvider.addDataDisplay(buTable);
		List<BulkUploadDto> dataProviderList = dataProvider.getList();
		for (BulkUploadDto buDto : listBuMainFromWebService) {
			dataProviderList.add(buDto);
		}

		//Adding scroller to the table
		ScrollPanel scrollerPanel = new ScrollPanel(buTable);
		scrollerPanel.setWidth("950px");
		scrollerPanel.setHeight("260px");

		VerticalPanel vTablePanel = new VerticalPanel();
		vTablePanel.add(scrollerPanel);
		vTablePanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		return vTablePanel;
	}

	/**
	 * Download bu result template.
	 *
	 * @param index the index
	 * @param object the object
	 */
	protected void downloadBuResultTemplate(int index, BulkUploadDto object) {

		try {
			Window.open(GWT.getModuleBaseURL() + "buDownloadServlet?id=" + object.getId(), "", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clear form fields.
	 */
	protected void clearFormFields() {

		form.reset();
	}

	/**
	 * Gets the form grid.
	 *
	 * @return the form grid
	 */
	private Grid getFormGrid() {

		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(ipAddressRadioButton);
		hPanel.add(strikeIronRadioButton);
		hPanel.setSpacing(5);

		Grid formGrid = new Grid(3, 3);

		fileUpload.setWidth("305px");
		fileUpload.setHeight("25px");
		descriptionTextField.setWidth("215px");
		ipAddressRadioButton.setChecked(true);
		spHiddenTextField.setVisible(false);

		formGrid.setWidget(0, 0, fileLable);
		formGrid.setWidget(0, 1, fileUpload);
		formGrid.setWidget(1, 0, serviceProviderLabel);
		formGrid.setWidget(1, 1, hPanel);
		formGrid.setWidget(2, 0, descriptionLable);
		formGrid.setWidget(2, 1, descriptionTextField);
		formGrid.setWidget(2, 2, spHiddenTextField);

		formGrid.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);
		formGrid.getCellFormatter().setAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_MIDDLE);

		formGrid.setCellSpacing(5);

		return formGrid;
	}

	/**
	 * Generate status column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateStatusColumn() {

		Column<BulkUploadDto, String> statusColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getStatus();
			}
		};
		buTable.addColumn(statusColumn, "Result");
		return statusColumn;
	}

	/**
	 * Generate service provider column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateServiceProviderColumn() {

		Column<BulkUploadDto, String> serviceProviderColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getServiceProvider();
			}
		};
		buTable.addColumn(serviceProviderColumn, "ServiceProvider");
		return serviceProviderColumn;
	}

	/**
	 * Generate process status column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateProcessStatusColumn() {

		Column<BulkUploadDto, String> processStatusColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getProcessStatus();
			}
		};
		buTable.addColumn(processStatusColumn, "ProcessStatus");
		return processStatusColumn;
	}

	/**
	 * Generate no of success records column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateNoOfSuccessRecordsColumn() {

		Column<BulkUploadDto, String> noOfSuccessRecordsColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getNoOfSuccessRecords() != null ? object.getNoOfSuccessRecords().toString() : String.valueOf(0);
			}
		};
		buTable.addColumn(noOfSuccessRecordsColumn, "SuccessCount");
		return noOfSuccessRecordsColumn;
	}

	/**
	 * Generate no of failure records column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateNoOfFailureRecordsColumn() {

		Column<BulkUploadDto, String> noOfFailureRecordsColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getNoOfFailureRecords() != null ? object.getNoOfFailureRecords().toString() : String.valueOf(0);
			}
		};
		buTable.addColumn(noOfFailureRecordsColumn, "FailureCount");
		return noOfFailureRecordsColumn;
	}

	/**
	 * Generate process end time column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateProcessEndTimeColumn() {

		Column<BulkUploadDto, String> processEndTimeColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getProcessEndTime();
			}
		};
		buTable.addColumn(processEndTimeColumn, "ProcessEndTime");
		return processEndTimeColumn;
	}

	/**
	 * Generate process start time column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateProcessStartTimeColumn() {

		Column<BulkUploadDto, String> processStartTimeColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getProcessStratTime();
			}
		};
		buTable.addColumn(processStartTimeColumn, "ProcessStartTime");
		return processStartTimeColumn;
	}

	/**
	 * Generate submitted time column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateSubmittedTimeColumn() {

		Column<BulkUploadDto, String> submittedTimeColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getSubmittedTime();
			}
		};
		buTable.addColumn(submittedTimeColumn, "SubmittedTime");
		return submittedTimeColumn;
	}

	/**
	 * Generate description column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateDescriptionColumn() {

		Column<BulkUploadDto, String> descriptionColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getDescription();
			}
		};
		buTable.addColumn(descriptionColumn, "Description");
		return descriptionColumn;
	}

	/**
	 * Generate id column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateIdColumn() {

		Column<BulkUploadDto, String> idColumn = new Column<BulkUploadDto, String>(new TextCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return object.getId().toString();
			}
		};
		buTable.addColumn(idColumn, "Id");
		return idColumn;
	}

	/**
	 * Generate download column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateDownloadColumn() {

		Column<BulkUploadDto, String> downloadButtonColumn = new Column<BulkUploadDto, String>(new CustomizedImageCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return "images/csvsmall.png";
			}
		};
		downloadButtonColumn.setFieldUpdater(new FieldUpdater<BulkUploadDto, String>() {
			@Override
			public void update(int index, BulkUploadDto object, String value) {
				if (object.getProcessStatus().equals(BulkUploadDto.PROCESS_STATUS_COMPLETED)) {
					downloadBuResultTemplate(index, object);
				} else {
					new NormalDialogBox("In Process",
							"Your Template data in process. Click on Refresh Grid button to know the updated Process Status.");
				}
			}
		});
		buTable.addColumn(downloadButtonColumn, "Download");
		return downloadButtonColumn;
	}

	/**
	 * Generate info column.
	 *
	 * @return the column
	 */
	private Column<BulkUploadDto, String> generateInfoColumn() {

		Column<BulkUploadDto, String> infoButtonColumn = new Column<BulkUploadDto, String>(new CustomizedImageCell()) {
			@Override
			public String getValue(BulkUploadDto object) {
				return "images/info.png";
			}
		};
		infoButtonColumn.setFieldUpdater(new FieldUpdater<BulkUploadDto, String>() {
			@Override
			public void update(int index, BulkUploadDto object, String value) {
				if (object.getProcessStatus().equals(BulkUploadDto.PROCESS_STATUS_COMPLETED)) {
					showInfo(index, object);
				} else {
					new NormalDialogBox("In Process",
							"Your Template data in process. Click on Refresh Grid button to know the updated Process Status.");
				}
			}
		});
		buTable.addColumn(infoButtonColumn, "Info");
		return infoButtonColumn;
	}

	/**
	 * Show info.
	 *
	 * @param index the index
	 * @param object the object
	 */
	protected void showInfo(int index, final BulkUploadDto object) {

		loadingDialogBox = new LoadingDialogBox("Loading.....", "Loading informations..... Please wait for few seconds.....");

		bulkUploadService.getBulkUploadById(object.getId(), new AsyncCallback<BulkUploadDto>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Bulk Upload Info Displaying Error : " + caught.getMessage());
				loadingDialogBox.hideLoaderDialog();
				new NormalDialogBox("Error", "Exception occured while retreving user info from BulkUpload template for Id : " + object.getId());
			}

			@Override
			public void onSuccess(BulkUploadDto result) {
				System.out.println("Bulk Upload Info Displaying Successfull");
				userDataProvider.getList().clear();
				for (BUUser user : result.getUserList()) {
					userDataProvider.getList().add(user);
				}
				userDataProvider.refresh();
				userTable.redraw();
				loadingDialogBox.hideLoaderDialog();
				String title = "Bulk Upload Id : " + object.getId() + " / Description : " + object.getDescription();
				new BuInfoDialogBox(title, vTablePanelInfo);
			}
		});
	}

	/**
	 * Generate email result error message column.
	 *
	 * @return the column
	 */
	private Column<BUUser, String> generateEmailResultErrorMessageColumn() {

		Column<BUUser, String> emailResultErrorMessageColumn = new Column<BUUser, String>(new TextCell()) {
			@Override
			public String getValue(BUUser object) {
				return object.getErrorMessage();
			}
		};
		userTable.addColumn(emailResultErrorMessageColumn, "ErrorMessage");
		return emailResultErrorMessageColumn;
	}

	/**
	 * Generate email result description column.
	 *
	 * @return the column
	 */
	private Column<BUUser, String> generateEmailResultDescriptionColumn() {

		Column<BUUser, String> emailResultDescriptionColumn = new Column<BUUser, String>(new TextCell()) {
			@Override
			public String getValue(BUUser object) {
				return object.getDescription();
			}
		};
		userTable.addColumn(emailResultDescriptionColumn, "Description");
		return emailResultDescriptionColumn;
	}

	/**
	 * Generate email result status no column.
	 *
	 * @return the column
	 */
	private Column<BUUser, String> generateEmailResultStatusNoColumn() {

		Column<BUUser, String> emailResultStatusNoColumn = new Column<BUUser, String>(new TextCell()) {
			@Override
			public String getValue(BUUser object) {
				return object.getStatusNo();
			}
		};
		userTable.addColumn(emailResultStatusNoColumn, "StatusNo");
		return emailResultStatusNoColumn;
	}

	/**
	 * Generate email result is valid column.
	 *
	 * @return the column
	 */
	private Column<BUUser, String> generateEmailResultIsValidColumn() {

		Column<BUUser, String> emailResultIsValidColumn = new Column<BUUser, String>(new CustomizedImageCell()) {
			@Override
			public String getValue(BUUser object) {
				if (Boolean.valueOf(object.getIsValid())) {
					return "images/correctsmall.png";
				} else {
					return "images/wrongsmall.png";
				}
			}
		};
		userTable.addColumn(emailResultIsValidColumn, "IsValid");
		return emailResultIsValidColumn;
	}

	/**
	 * Generate email result status column.
	 *
	 * @return the column
	 */
	private Column<BUUser, String> generateEmailResultStatusColumn() {

		Column<BUUser, String> emailResultStatusColumn = new Column<BUUser, String>(new TextCell()) {
			@Override
			public String getValue(BUUser object) {
				return object.getStatus();
			}
		};
		userTable.addColumn(emailResultStatusColumn, "Status");
		return emailResultStatusColumn;
	}

	/**
	 * Generate email result email id column.
	 *
	 * @return the column
	 */
	private Column<BUUser, String> generateEmailResultEmailIdColumn() {

		Column<BUUser, String> emailResultEmailIdColumn = new Column<BUUser, String>(new TextCell()) {
			@Override
			public String getValue(BUUser object) {
				return object.getEmailId();
			}
		};
		userTable.addColumn(emailResultEmailIdColumn, "EmailId");
		return emailResultEmailIdColumn;
	}
}
