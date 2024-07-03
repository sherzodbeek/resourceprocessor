package com.epam.resourceprocessor;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients
@EnableRetry
public class ResourceprocessorApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ResourceprocessorApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
}
