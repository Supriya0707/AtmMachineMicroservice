package com.atm.atmmachine.constants;

public enum ATMMachineErrorKeys {

	ATM_INSUFFICIENT_BALANCE("101"),
	ATM_OUT_OF_SERVICE("102"),
	ATM_MANAGED_ERROR("103"),
	BAD_REQUEST("201"),
	AUTHENTICATION_ERROR("202"),
	BAD_REQUEST_INVALID_AMOUNT("203"),
	CUSTOMER_INSUFFICIENT_BALANCE("301"),
	CUSTOMER_DAILY_LIMIT_WITHDRAWAL_REACHED("302");
	
	private String errorKey;

	private ATMMachineErrorKeys(String errorKey) {
		this.errorKey = errorKey;
	}

	public String getErrorKey() {
		return errorKey;
	}
	
}
