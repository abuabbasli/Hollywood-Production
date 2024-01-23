package com.example.hollywoodProduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EntityScan("com.example.hollywoodProduction.Entity")
public class HollywoodProductionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HollywoodProductionApplication.class, args);
	}

}
