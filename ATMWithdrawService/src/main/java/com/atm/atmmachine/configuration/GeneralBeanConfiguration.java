package com.atm.atmmachine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atm.atmmachine.controller.ControllerHelper;
import com.atm.atmmachine.dao.ATMMachineDao;
import com.atm.atmmachine.dao.ATMMachineDaoImpl;
import com.atm.atmmachine.dao.CustomerAccountDao;
import com.atm.atmmachine.dao.CustomerAccountDaoImpl;
import com.atm.atmmachine.environment.ApplicationData;
import com.atm.atmmachine.helper.ErrorMessageProvider;
import com.atm.atmmachine.manager.WithdrawRequestManager;
import com.atm.atmmachine.service.WithdrawService;
import com.atm.atmmachine.validator.Validator;

@Configuration
public class GeneralBeanConfiguration {

	@Bean
	public ApplicationData applicationData() {
		return new ApplicationData();
	}

	@Bean
	public WithdrawService postATMMachineService() {
		return new WithdrawService();
	}
	
	@Bean
	public WithdrawRequestManager atmMachineRequestManager() {
		return new WithdrawRequestManager();
	}
	
	@Bean
	public ATMMachineDao atmMachineDao() {
		return new ATMMachineDaoImpl();
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
