package com.techfusionlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author steven
 */
@SpringBootApplication
public class TechFusionLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechFusionLabApplication.class, args);
	}

}
