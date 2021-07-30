package com.jphf.cloud.history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroserviceHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceHistoryApplication.class, args);
	}

}
