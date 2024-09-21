package com.subash.projects.url_shortner;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.subash.projects.url_shortner.repository")
public class UrlShortnerApplication {



	public static void main(String[] args) {

		// Load the .env file
		Dotenv dotenv = Dotenv.load();

		System.out.println("MONGODB_URI: ************************************  " + dotenv.get("MONGODB_URI"));

		SpringApplication.run(UrlShortnerApplication.class, args);

	}

}
