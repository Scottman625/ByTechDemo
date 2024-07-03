package com.bytech.demo;

import com.bytech.demo.dao.PersonRepository;
import com.bytech.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// 保存一些用户
		personRepository.save(new Person("scottman608","Scott Fang" ));
		personRepository.save(new Person("scottman625", "Scott Jie"));

		// 从数据库中检索所有用户
		personRepository.findAll().forEach(user -> {
			System.out.println(user.getName());
		});
	}

}
