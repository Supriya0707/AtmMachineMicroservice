package com.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import com.atm.atmmachine.environment.ATMMachine;

/**
 *  * @author Supriya Jangale, 
 */
@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"com.atm.atmmachine.configuration", "com.atm.atmmachine.http.controller"})
@EnableConfigurationProperties(ATMMachine.class)
@EnableAutoConfiguration
public class ATMMachineApplication
{
    public static void main( String[] args )
    {
        System.out.println("Starting ATMApplication..." );
        
        SpringApplication.run(ATMMachineApplication.class, args);
    }
}
