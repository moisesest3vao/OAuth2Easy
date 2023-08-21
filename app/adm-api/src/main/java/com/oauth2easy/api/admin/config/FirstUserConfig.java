package com.oauth2easy.api.admin.config;

import com.oauth2easy.api.admin.domain.user.UserEntity;
import com.oauth2easy.api.admin.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class FirstUserConfig implements ApplicationRunner {
	
	private final Logger logger = LoggerFactory.getLogger(FirstUserConfig.class);
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public FirstUserConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (userRepository.count() != 0) {
			return;
		}
		
		logger.info("Any user was found, creating default users.");

		userRepository.save(
				new UserEntity(
						"Alex Silva",
						"admin@email.com",
						passwordEncoder.encode("123456"),
						UserEntity.Type.ADMIN
				)
		);

		userRepository.save(
				new UserEntity(
						"João da Silva",
						"joao@email.com",
						passwordEncoder.encode("123456"),
						UserEntity.Type.USER
				)
		);
	}
}
