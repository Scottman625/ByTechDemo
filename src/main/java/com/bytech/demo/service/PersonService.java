package com.bytech.demo.service;

import com.bytech.demo.dao.PersonRepository;
import com.bytech.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Cacheable(value = "personCache", key = "#personId")
    public Person findById(Integer personId){
        return personRepository.findById(personId).orElse(null);
    }

    @Cacheable(value = "personCache", key = "#account")
    public Person findByAccount(String account) {
        return personRepository.findByAccount(account).orElse(null);
    }
    @Cacheable(value = "personCache")
    public List<Person> findAll() {
        return personRepository.findAll();
    }
}

