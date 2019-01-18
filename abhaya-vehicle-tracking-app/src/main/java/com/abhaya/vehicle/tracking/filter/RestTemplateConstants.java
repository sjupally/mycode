package com.abhaya.vehicle.tracking.filter;


public enum RestTemplateConstants {


	HEADER_KEY("Content-Disposition",""),
	ALLOW_ORIGIN("Access-Control-Allow-Origin", "*"),
	ALLOW_METHODS("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE,OPTIONS"),
	ALLOW_CREDENTIALS("Access-Control-Allow-Credentials", "true"),
	ALLOW_HEADERS("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization"),
	ACCEPT("Accept", "application/json"),
	X_MS_CORELATION_ID("MS-CorrelationId",""),
	MS_CONTRACT_VERSION("MS-Contract-Version","v1"),
	X_MS_TRACKING_ID("MS-RequestId", ""),
	API_VERSION("api-version", "2015-03-31"),
	CONTENT_TYPE_URL_ENCODED("Content-Type", "application/x-www-form-urlencoded"),
	CONTENT_TYPE_JSON("Content-Type", "application/json"),
	X_AUTH_TOKEN("X-Auth-Token", ""),
	X_LOCALE("X-Locale", "en-US");

	private String name;
	private String value;

	RestTemplateConstants(final String name, final String value) {
		this.name= name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return this.getValue();
	}

}
