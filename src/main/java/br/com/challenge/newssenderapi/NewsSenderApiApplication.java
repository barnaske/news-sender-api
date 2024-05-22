package br.com.challenge.newssenderapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsSenderApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(NewsSenderApiApplication.class, args);
	}
}
