package com.bytech.demo.service;

import com.bytech.demo.dao.PersonRepository;
import com.bytech.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(String account, String name) {
        Person person = new Person();
        person.setAccount(account);
        person.setName(name);
        return personRepository.save(person);
    }

    public Person update(Person person, String name) {
        person.setName(name);
        return personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }
}

