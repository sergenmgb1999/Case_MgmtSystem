package com.team2.inventory;

import com.team2.inventory.model.Role;
import com.team2.inventory.model.User;
import com.team2.inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class InventoryMSApplication {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(InventoryMSApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			User user = userRepository.findByUsername("admin");
			
			if (user == null) {
				user = new User("admin user","admin@mail.com", "admin", "password", Role.ADMIN);
				String pwd = user.getPassword();
				String encryptPwd = bCryptPasswordEncoder.encode(pwd);
				user.setPassword(encryptPwd);
				userRepository.save(user);
			}
		};
	}
}
