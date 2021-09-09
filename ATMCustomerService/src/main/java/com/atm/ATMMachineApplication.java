package com.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *  * @author Supriya Jangale, 
 */

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class ATMMachineApplication
{
    public static void main( String[] args )
    {
        System.out.println("Starting ATMApplication..." );
        
        SpringApplication.run(ATMMachineApplication.class, args);
    }
}
