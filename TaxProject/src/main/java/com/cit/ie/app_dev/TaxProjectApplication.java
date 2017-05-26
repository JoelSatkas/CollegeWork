package com.cit.ie.app_dev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cit.ie.app_dev.cmd.CommandLineRunnerImpl;

@SpringBootApplication
public class TaxProjectApplication {
	
	/***
	 * Author: Joel Satkauskas
	 * R00116315
	 */

	@Autowired
	private CommandLineRunnerImpl cmd;
	
	public static void main(String[] args) {
		SpringApplication.run(TaxProjectApplication.class, args);
	}
	

}
