package com.epam.resourceprocessor;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ResourceprocessorApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ResourceprocessorApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
}
