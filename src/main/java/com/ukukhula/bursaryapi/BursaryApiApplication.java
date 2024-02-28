package com.ukukhula.bursaryapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AzureBlobProperties.class})
public class BursaryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BursaryApiApplication.class, args);
	}

}
