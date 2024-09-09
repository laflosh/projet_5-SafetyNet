package com.safetynet.projet_5_safetynet_api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Projet5SafetynetApiApplication {

	private static final Logger logger = LogManager.getLogger("Projet5SafetynetApiApplication");
	
	public static void main(String[] args) {
		
		logger.info("Initializing API SafetyNet.");
		SpringApplication.run(Projet5SafetynetApiApplication.class, args);
		
	}

}
