package com.project.s5;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class S5Application {

	@Bean
	CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();

		// Configure allowed origins, methods, headers, etc.
		config.addAllowedOrigin("*"); // Replace with your allowed origins
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	public static void main(String[] args) {

		// // Two timestamp instances
		// Timestamp timestamp1 = Timestamp.valueOf("2022-01-20 00:00:00");
		// Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());

		// // Convert Timestamp to LocalDateTime
		// LocalDateTime localDateTime1 = timestamp1.toLocalDateTime();
		// LocalDateTime localDateTime2 = timestamp2.toLocalDateTime();

		// System.out.println(localDateTime1);
		// System.out.println(localDateTime2);
		// // Calculate the difference in years
		// long yearsDifference = ChronoUnit.YEARS.between(localDateTime1,
		// localDateTime2);

		// // Output the difference in years
		// System.out.println("Time difference in years: " + yearsDifference);

		SpringApplication.run(S5Application.class, args);
	}

}
