package com.workshop.Lisa;

import com.workshop.Lisa.Dao.UserDao;
import com.workshop.Lisa.Entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LisaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LisaApplication.class, args);
	}

//	@Bean
//	PasswordEncoder passwordEncoder(){
//		return new BCryptPasswordEncoder();
//	}

//	@Bean
//	CommandLineRunner run(UserDao userDao){
//
//		return args -> {
//
//			userDao.save(new User(null,"test", "test", "test", "test", "test", "USER"));
//			userDao.save(new User(null, "test", "test", "test", "test", "test", "USER"));
//			userDao.save(new User(null,"test", "test", "test", "test", "test", "USER"));
//
//		};
//	}
}
