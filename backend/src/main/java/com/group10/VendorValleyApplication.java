package com.group10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class VendorValleyApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendorValleyApplication.class, args);
	}

}
