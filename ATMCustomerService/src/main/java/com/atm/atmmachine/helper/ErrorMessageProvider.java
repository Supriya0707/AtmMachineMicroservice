package com.atm.atmmachine.helper;

import java.util.ResourceBundle;

public class ErrorMessageProvider {

	private ResourceBundle messageBundle;

	public ErrorMessageProvider(String messageFilePath) {
		messageBundle = ResourceBundle.getBundle(messageFilePath);
	}
	
	public ResourceBundle getMessageBundle() {
		return messageBundle;
	}

	public String getErrorMessageForKey(String key) {
		return getMessageBundle().getString(key);
	}
	
}
