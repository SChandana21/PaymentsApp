package com.example.PaymentApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class PaymentAppApplication {

    @Component
    public class MongoPrinter {

        public MongoPrinter(@Value("${spring.mongodb.uri}") String uri) {
            System.out.println("Mongo URI: " + uri);
        }
    }

    @Bean
    public CommandLineRunner debugMongo(MongoTemplate mongoTemplate) {
        return args -> {
            System.out.println("- MONGO DEBUG -");
            System.out.println("Database Name: " + mongoTemplate.getDb().getName());
            System.out.println("Factory Class: " + mongoTemplate.getMongoDatabaseFactory().getClass());
            System.out.println(
                    mongoTemplate.getMongoDatabaseFactory()
                            .getMongoDatabase()
                            .getName()
            );
            System.out.println("-----------------------");
        };
    }


	public static void main(String[] args) {
		SpringApplication.run(PaymentAppApplication.class, args);
	}




}
