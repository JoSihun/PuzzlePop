package com.ssafy.puzzlepop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PuzzlePopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuzzlePopApplication.class, args);
	}

}
