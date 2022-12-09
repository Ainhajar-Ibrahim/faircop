package com.emse.spring.faircorp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
@SpringBootApplication(exclude= SecurityAutoConfiguration.class)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Application {
	private static Logger logger = LogManager.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.debug("Debug log message");
		logger.info("Info log message");
		logger.error("Error log message");
		logger.trace("Trace Message!");
		logger.warn("Warn Message!");
		logger.fatal("Fatal Message!");
	}

}
