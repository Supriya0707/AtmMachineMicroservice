package com.atm.atmmachine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atm.atmmachine.controller.ControllerHelper;
import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.dao.CustomerAccountDaoImpl;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.helper.ErrorMessageProvider;
import com.atm.atmmachine.manager.ATMMachineRequestManager;
import com.atm.atmmachine.service.CustomerService;
import com.atm.atmmachine.validator.Validator;

@Configuration
public class GeneralBeanConfiguration {

	@Bean
	public ApplicationData applicationData() {
		return new ApplicationData();
	}
	
	@Bean
	public CustomerService getATMMachineService() {
		return new CustomerService();
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
	public Validator atmMachineRequestValidator() {
		return new Validator();
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
