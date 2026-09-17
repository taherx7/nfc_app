package com.id4a.telco_backoffice;

import com.id4a.telco_backoffice.model.Admin;
import com.id4a.telco_backoffice.model.ClientFinal;
import com.id4a.telco_backoffice.repository.AdminRepository;
import com.id4a.telco_backoffice.repository.ClientFinalRepository;
import java.time.LocalDateTime;
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

	@Bean
	CommandLineRunner initClientFinalData(ClientFinalRepository repo) {
		return args -> {
			if (repo.count() == 0) {
				repo.save(buildClient("25123456", ClientFinal.Operateur.ORANGE, "04A1B2C3D4",
						LocalDateTime.of(2026, 7, 13, 12, 20, 38)));
				repo.save(buildClient("51770530", ClientFinal.Operateur.ORANGE, "04A1B2C3D3",
						LocalDateTime.of(2026, 7, 19, 21, 35, 10)));
				repo.save(buildClient("51770531", ClientFinal.Operateur.ORANGE, "22480201",
						LocalDateTime.of(2026, 7, 21, 8, 59, 57)));
				repo.save(buildClient("10100100", ClientFinal.Operateur.OOREDOO, "04A1B2C3D2",
						LocalDateTime.of(2026, 7, 23, 9, 15, 30)));
				repo.save(buildClient("90100200", ClientFinal.Operateur.OOREDOO, "01020304",
						LocalDateTime.of(2026, 7, 31, 16, 15, 36)));
				repo.save(buildClient("50000000", ClientFinal.Operateur.ORANGE, "53A5334611ED79AD",
						LocalDateTime.of(2026, 8, 5, 14, 47, 8)));
			}
		};
	}

	private ClientFinal buildClient(String numeroTelephone, ClientFinal.Operateur operateur, String codeNfc,
			LocalDateTime dateCreation) {
		ClientFinal client = new ClientFinal();
		client.setNumeroTelephone(numeroTelephone);
		client.setOperateur(operateur);
		client.setCodeNfc(codeNfc);
		client.setDateCreation(dateCreation);
		return client;
	}
}