package com.id4a.telco_backoffice;

import com.id4a.telco_backoffice.model.Admin;
import com.id4a.telco_backoffice.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TelcoBackofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelcoBackofficeApplication.class, args);
	}

	@Bean
	CommandLineRunner initAdmin(AdminRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByUsername("admin").isEmpty()) {
				Admin a = new Admin();
				a.setUsername("admin");
				a.setEmail("admin@id4a.com");
				a.setMotDePasse(encoder.encode("admin123"));
				repo.save(a);
			}
		};
	}
}