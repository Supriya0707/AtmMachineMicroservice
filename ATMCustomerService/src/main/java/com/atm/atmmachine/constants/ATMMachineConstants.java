package com.atm.atmmachine.constants;

public class ATMMachineConstants {

	public static final int CUSTOMER_ACCOUNT_NUMBER_LENGTH = 9;
	
	public static final int CUSTOMER_ACCOUNT_PIN_LENGTH = 4;
	
	public static final String ATMMACHINE_GET_BALANCE_ENQUIRY_ENDPOINT = "/balanceEnquiry";
	
	public static final String ATMMACHINE_FIND_CUSTOMER_ENDPOINT = "/findCustomer";
	
	public static final String ATMMACHINE_UPDATE_CUSTOMER_BALANCE_ENDPOINT = "/updateCustomerBalance";
	
	public static enum ATMMachineErrorKeys {

		ATM_INSUFFICIENT_BALANCE("5001"),
		ATM_OUT_OF_SERVICE("5002"),
		ATM_MANAGED_ERROR("5003"),
		BAD_REQUEST("4004"),
		AUTHENTICATION_ERROR("4005"),
		BAD_REQUEST_INVALID_AMOUNT("4006"),
		CUSTOMER_INSUFFICIENT_BALANCE("3007"),
		CUSTOMER_DAILY_LIMIT_WITHDRAWAL_REACHED("3008");
		
		private String errorKey;

		private ATMMachineErrorKeys(String errorKey) {
			this.errorKey = errorKey;
		}

		public String getErrorKey() {
			return errorKey;
		}
		
	}
}
