/**
 * 
 */
package com.arkami.myidkey.activity.customuicomponents;

/**
 * @author sbahdikyan
 * 
 */
public enum FieldNamesEnum {
	// XXX add all later
	NOTE("Notes", FieldTypeEnum.TEXT, null),
	//
	URL("URL", FieldTypeEnum.TEXT, null),
	//
	USER_NAME("Username", FieldTypeEnum.TEXT, null),
	//
	PASSWORD("Password", FieldTypeEnum.PASSWORD, null),
	//
	NAME_ON_CARD("Name on Card", FieldTypeEnum.TEXT, null),
	//
	TYPE(
			"Type",
			FieldTypeEnum.SPINNER,
			"Visa:American Express:BC Card:Mastercard:Carte Blanche:China Union Pay:Discover:Diners Club:Carta Si:Carte Bleue:Dankort:Delta:Electron:Japan Credit Bureau:Maestro:Switch"),
	//
	NUMBER("Number", FieldTypeEnum.NUMERICAL, null),
	//
	LICENSE_CLASS("License Class", FieldTypeEnum.TEXT, null),
	//
	NAME("Name", FieldTypeEnum.TEXT, null),
	//
	ADDRESS("Address", FieldTypeEnum.TEXT, null),
	//
	STATE("State", FieldTypeEnum.TEXT, null),
	//
	COUNTRY("Country", FieldTypeEnum.TEXT, null),
	//
	SEX("Sex", FieldTypeEnum.TEXT, null),
	//
	HEIGHT("Height", FieldTypeEnum.TEXT, null),
	//
	HOSTNAME("Hostname", FieldTypeEnum.TEXT, null),
	//
	DATABASE("Database", FieldTypeEnum.TEXT, null),
	//
	SID("SID", FieldTypeEnum.TEXT, null),
	//
	ALIAS("Alias", FieldTypeEnum.TEXT, null),
	//
	SERVER("Server", FieldTypeEnum.TEXT, null),
	//
	PORT("Port", FieldTypeEnum.NUMERICAL, null),
	//
	SMTP_SERVER("SMTP Server", FieldTypeEnum.TEXT, null),
	//
	SMTP_PORT("SMPT Port", FieldTypeEnum.NUMERICAL, null),
	//
	TITLE("Title", FieldTypeEnum.TEXT, null),
	//
	FIRST_NAME("First Name", FieldTypeEnum.TEXT, null),
	//
	MIDDLE_NAME("Middle Name", FieldTypeEnum.TEXT, null),
	//
	LAST_NAME("Last Name", FieldTypeEnum.TEXT, null),
	//
	GENDER("Gender", FieldTypeEnum.TEXT, null),
	//
	BIRTH_DAY("Birthday", FieldTypeEnum.DATE, null),
	//
	SSN("SSN", FieldTypeEnum.NUMERICAL, null),
	//
	COMPANY("Company", FieldTypeEnum.TEXT, null),
	//
	ADDRESS_1("Address 1", FieldTypeEnum.TEXT, null),
	//
	ADDRESS_2("Address 2", FieldTypeEnum.TEXT, null),
	//
	ADDRESS_3("Address 3", FieldTypeEnum.TEXT, null),
	//
	SECURITY_CODE("Security Code", FieldTypeEnum.NUMERICAL, null),
	//
	START_DATE("Start Date", FieldTypeEnum.DATE, null),
	//
	DEFAULT("default", FieldTypeEnum.TEXT, null),
	//
	EXPIRATION("Expiration", FieldTypeEnum.DATE, null),
	//
	DATE_OF_BIRTH("Date of Birth", FieldTypeEnum.DATE, null),
	//
	END_DATE("End Date", FieldTypeEnum.DATE, null),
	//
	ISSUED_DATE("Issued Date", FieldTypeEnum.DATE, null),
	//
	CITY("City", FieldTypeEnum.TEXT, null),
	//
	ZIP("Zip", FieldTypeEnum.TEXT, null),
	//
	WORK_ADDRESS_1("Work Address 1", FieldTypeEnum.TEXT, null),
	//
	WORK_ADDRESS_2("Work Address 2", FieldTypeEnum.TEXT, null),
	//
	WORK_ADDRESS_3("Work Address 3", FieldTypeEnum.TEXT, null),
	//
	WORK_CITY("Work City", FieldTypeEnum.TEXT, null),
	//
	WORK_COUNTRY("Work Country", FieldTypeEnum.TEXT, null),
	//
	WORK_STATE("Work State", FieldTypeEnum.TEXT, null),
	//
	WORK_ZIP("Work ZIP", FieldTypeEnum.TEXT, null),
	//
	SHIPPING_ADDRESS_1("Shipping Address 1", FieldTypeEnum.TEXT, null),
	//
	SHIPPING_ADDRESS_2("Shipping Address 2", FieldTypeEnum.TEXT, null),
	//
	SHIPPING_ADDRESS_3("Shipping Address 3", FieldTypeEnum.TEXT, null),
	//
	SHIPPING_COUNTRY("Shipping Country", FieldTypeEnum.TEXT, null),
	//
	SHIPPING_STATE("Shipping State", FieldTypeEnum.TEXT, null),
	//
	SHIPPING_ZIP("Shipping ZIP", FieldTypeEnum.TEXT, null),
	//
	TIME_ZONE("Time Zone", FieldTypeEnum.TEXT, null),
	//
	EMAIL("Email", FieldTypeEnum.TEXT, null),
	//
	MOBILE_EMAIL_ADDRESS("Mobile Email Address", FieldTypeEnum.TEXT, null),
	//
	PHONE_NUMBER("Phone Number", FieldTypeEnum.TEXT, null),
	//
	FAX_NUMBER("Fax Number", FieldTypeEnum.NUMERICAL, null),
	//
	EVENING_NUMBER("Evening Number", FieldTypeEnum.NUMERICAL, null),
	//
	MOBILE_NUMBER("Mobile Number", FieldTypeEnum.NUMERICAL, null),
	//
	CARD_TYPE("Card Type", FieldTypeEnum.TEXT, null),
	//
	CARD_NUMBER("Card Number", FieldTypeEnum.NUMERICAL, null),
	//
	PIN("PIN", FieldTypeEnum.NUMERICAL, null),
	//
	ISSUE_NUMBER("Issue Number", FieldTypeEnum.NUMERICAL, null),
	//
	BANK_NAME("Bank name", FieldTypeEnum.TEXT, null),
	//
	ACCOUNT_NUMBER("Account Number", FieldTypeEnum.TEXT, null),
	//
	ROUTING_NUMBER("Routing number", FieldTypeEnum.NUMERICAL, null),
	//
	POLICY_TYPE("Policy Type", FieldTypeEnum.TEXT, null),
	//
	POLICY_NUMBER("Policy Number", FieldTypeEnum.TEXT, null),
	//
	AGENT_NAME("Agent Name", FieldTypeEnum.TEXT, null),
	//
	ORGANIZATION("Organization", FieldTypeEnum.TEXT, null),
	//
	MEMBERSHIP_NUMBER("Membership Number", FieldTypeEnum.TEXT, null),
	//
	MEMBER_NAME("Member Name", FieldTypeEnum.TEXT, null),
	//
	WEBSITE("Website", FieldTypeEnum.TEXT, null),
	//
	TELEPHONE("Telephone", FieldTypeEnum.TEXT, null),
	//
	NATIONALITY("Nationality", FieldTypeEnum.TEXT, null),
	//
	ISSUING_AUTHORITY("Issuing Authority", FieldTypeEnum.TEXT, null),
	//
	DRUG_NAME("Drug Name", FieldTypeEnum.TEXT, null),
	//
	AMOUNT("Amount", FieldTypeEnum.TEXT, null),
	//
	GENERIC_FOR("Generic For", FieldTypeEnum.TEXT, null),
	//
	WHEN_TO_TAKE("When To Take", FieldTypeEnum.TEXT, null),
	//
	BRAND("Brand", FieldTypeEnum.TEXT, null),
	//
	PURCHASE_DATE("Purchase Date", FieldTypeEnum.DATE, null),
	//
	Pharmacy("Pharmacy", FieldTypeEnum.TEXT, null),
	//
	DOCTOR("Doctor", FieldTypeEnum.TEXT, null),
	//
	DOCTOR_PHONE("Doctor Phone", FieldTypeEnum.TEXT, null),
	//
	RX_NUMBER("RX Number", FieldTypeEnum.NUMERICAL, null),
	//
	NUMBER_OF_REFILLS("Number of Refills", FieldTypeEnum.NUMERICAL, null),
	//
	EXPIRATION_DATE("Expiration Date", FieldTypeEnum.DATE, null),
	//
	ACCOUNT_TYPE("Account type", FieldTypeEnum.TEXT, null),
	//
	SWIFT_CODE("SWIFT Code", FieldTypeEnum.TEXT, null),
	//
	IBAN_NUMBER("IBAN Number", FieldTypeEnum.TEXT, null),
	//
	BRANCH_ADDRESS("Branch Address", FieldTypeEnum.TEXT, null),
	//
	BRANCH_PHONE("Branch Phone", FieldTypeEnum.TEXT, null),
	//
	SIZES_FOR("Sizes For", FieldTypeEnum.TEXT, null),
	//
	SUIT("Suit", FieldTypeEnum.TEXT, null),
	//
	WAIST("Waist", FieldTypeEnum.TEXT, null),
	//
	BUST("Bust", FieldTypeEnum.TEXT, null),
	//
	INSEAM("Inseam", FieldTypeEnum.TEXT, null),
	//
	SHOE("Shoe", FieldTypeEnum.TEXT, null),
	//
	PANTS("Pants", FieldTypeEnum.TEXT, null),
	//
	SKIRT("Skirt", FieldTypeEnum.TEXT, null),
	//
	NECK("Neck", FieldTypeEnum.TEXT, null),
	//
	GLOVE("Glove", FieldTypeEnum.TEXT, null),
	//
	TYPE_NO_VALUES("Type", FieldTypeEnum.TEXT, null),
	//
	AGENT_PHONE("Agent Phone", FieldTypeEnum.TEXT, null),
	//
	COMPANY_NAME("Company Name", FieldTypeEnum.TEXT, null),
	//
	MEMBER_SINCE("Member Since", FieldTypeEnum.DATE, null),
	//
	SSID("SSID", FieldTypeEnum.TEXT, null),
	//
	CONNECTION_TYPE("Connection Type", FieldTypeEnum.TEXT, null),
	//
	CONNECTION_MODE("Connection Mode", FieldTypeEnum.TEXT, null),
	//
	AUTHENTICATION("Authentication", FieldTypeEnum.TEXT, null),
	//
	ENCRIPTION("Encription", FieldTypeEnum.TEXT, null),
	//
	USE_802_1X("Use 802.1X", FieldTypeEnum.TEXT, null),
	//
	FIPS_MODE("FIPS Mode", FieldTypeEnum.TEXT, null),
	//
	KEY_TYPE("Key Type", FieldTypeEnum.TEXT, null),
	//
	PROTECTED("Protected", FieldTypeEnum.TEXT, null),
	//
	KEY_INDEX("Key Index", FieldTypeEnum.TEXT, null),
	//
	LICENSE_KEY("License Key", FieldTypeEnum.TEXT, null),
	//
	VERSION("Version", FieldTypeEnum.TEXT, null),
	//
	PUBLISHER("Publisher", FieldTypeEnum.TEXT, null),
	//
	SUPPORT_EMAIL("Support Email", FieldTypeEnum.TEXT, null),
	//
	ORDER_NUMBER("Order Number", FieldTypeEnum.TEXT, null),
	//
	NUMBER_OF_LICENSES("Number of Licenses", FieldTypeEnum.TEXT, null),
	//
	ORDER_TOTAL("Order Total", FieldTypeEnum.TEXT, null),
	//
	PRICE("Price", FieldTypeEnum.TEXT, null),
	//
	PLATE_NUMBER("Plate Number", FieldTypeEnum.TEXT, null),
	//
	MAKE("Make", FieldTypeEnum.TEXT, null),
	//
	MODEL("Model", FieldTypeEnum.TEXT, null),
	//
	YEAR("Year", FieldTypeEnum.DATE, null),
	//
	VIN("VIN", FieldTypeEnum.TEXT, null),
	//
	HOME_ADDRESS("Home Address", FieldTypeEnum.TEXT, null),
	//
	BANK_INFORMATION("Bank Information", FieldTypeEnum.TEXT, null),
	//
	WORK_ADDRESS("Work Address", FieldTypeEnum.TEXT, null),
	//
	SHIPPING_ADDRESS("Shipping Address", FieldTypeEnum.TEXT, null),
	//
	COSTOMER_SERVICE_PHONE_NUMBER("Customer Service Phone Number",
			FieldTypeEnum.TEXT, null),
	//
	RESERVATIONS_PHONE_NUMBER("Reservations Phone Number", FieldTypeEnum.TEXT,
			null);

	private String name;
	private FieldTypeEnum fieldType;
	private String possibleSelections;

	/**
	 * @param name
	 * @param fieldType
	 * @param possibleSelections
	 */
	FieldNamesEnum(String name, FieldTypeEnum fieldType,
			String possibleSelections) {
		this.name = name;
		this.fieldType = fieldType;
		this.setPossibleSelections(possibleSelections);
	}

	/**
	 * @param name
	 * @return
	 */
	public static FieldNamesEnum getByName(String name) {
		for (FieldNamesEnum feildEnum : FieldNamesEnum.values()) {
			if (feildEnum.getName().equals(name)) {
				return feildEnum;
			}
		}
		return FieldNamesEnum.DEFAULT;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fieldType
	 */
	public FieldTypeEnum getFieldType() {
		return fieldType;
	}

	/**
	 * @param fieldType
	 *            the fieldType to set
	 */
	public void setFieldType(FieldTypeEnum fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return the possibleSelections
	 */
	public String getPossibleSelections() {
		return possibleSelections;
	}

	/**
	 * @param possibleSelections
	 *            the possibleSelections to set
	 */
	public void setPossibleSelections(String possibleSelections) {
		this.possibleSelections = possibleSelections;
	}
}