package com.mstradingcards.ServiceAuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.mstradingcards.ServiceAuth"})
@EntityScan("com.mstradingcards.ServiceAuth.models")
@EnableJpaRepositories("com.mstradingcards.ServiceAuth.repository")
@ComponentScan(basePackages = "com.mstradingcards.ServiceAuth.config")
public class ServiceAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceAuthApplication.class, args);
	}

}
