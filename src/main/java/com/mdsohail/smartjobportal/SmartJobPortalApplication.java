package com.mdsohail.smartjobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SmartJobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartJobPortalApplication.class, args);
	}
@Bean
	public RestTemplate RestTemplate(){return new RestTemplate();}
}
