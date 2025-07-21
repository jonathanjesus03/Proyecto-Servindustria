package com.Servindustria.WebPage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling; //agrege esto 

@EnableAsync
@ConfigurationProperties
@SpringBootApplication
@EnableScheduling //agrege esto 
public class WebPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebPageApplication.class, args);
	}

}
