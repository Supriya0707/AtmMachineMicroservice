package com.atm.atmmachine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.dao.CustomerAccountDaoImpl;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.helper.ErrorMessageProvider;
import com.atm.atmmachine.http.controller.ControllerHelper;
import com.atm.atmmachine.manager.ATMMachineRequestManager;
import com.atm.atmmachine.service.GetATMMachineService;
import com.atm.atmmachine.validator.ATMMachineRequestValidator;

@Configuration
public class GeneralBeanConfiguration {

	@Bean
	public ApplicationData applicationData() {
		return new ApplicationData();
	}
	
	@Bean
	public GetATMMachineService getATMMachineService() {
		return new GetATMMachineService();
	}
	
	
	@Bean
	public ATMMachineRequestManager atmMachineRequestManager() {
		return new ATMMachineRequestManager();
	}
	
	@Bean
	public CustomerAccountDao customerAccountDao() {
		return new CustomerAccountDaoImpl();
	}
	
	@Bean
	public ATMMachineRequestValidator atmMachineRequestValidator() {
		return new ATMMachineRequestValidator();
	}
	
	@Bean
	public ErrorMessageProvider errorMessageProvider() {
		return new ErrorMessageProvider("errorMessages");
	}
	
	@Bean
	public ControllerHelper controllerHelper() {
		return new ControllerHelper();
	}
	
}
