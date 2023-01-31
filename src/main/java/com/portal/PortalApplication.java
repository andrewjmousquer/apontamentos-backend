package com.portal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;

@SpringBootApplication
@EnableCaching
public class PortalApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(PortalApplication.class);

	public static String systemName = "Carbon Apontamentos";
	public static String versionNumber = "1.0.0";
	public static String versionDate = "10/06/22";

	public static void main(String[] args) throws AppException, BusException {
		logger.info("{} - {} - {}", systemName, versionNumber, versionDate);
		SpringApplication.run(PortalApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}
}
