package com.bytech.demo.controller;

import com.bytech.demo.dao.PersonRepository;
import com.bytech.demo.entity.ApiResponse;
import com.bytech.demo.entity.Person;
import com.bytech.demo.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);


    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping()
    public ResponseEntity<?> getPersons(){
        List<Person> persons = personRepository.findAll();
        if (!persons.isEmpty()){
            return ResponseEntity.ok(persons);
        }
        return new ResponseEntity<>(new ApiResponse<>(null, "No Person exist."), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{personId}/")
    public ResponseEntity<ApiResponse<Person>> getPerson(
            @PathVariable Integer personId) {

        if (personId != null){
            Optional<Person> person = personRepository.findById(personId);
            return person.map(value -> ResponseEntity.ok(new ApiResponse<>(value, "Find Person success"))).orElseGet(() -> new ResponseEntity<>(new ApiResponse<>(null, "This id doesn't exist."), HttpStatus.UNAUTHORIZED));

        }

        return new ResponseEntity<>(new ApiResponse<>(null, "Find Person failed."), HttpStatus.UNAUTHORIZED);

    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Person>> post(@RequestParam String account,@RequestParam String name) {
        Optional<Person> existingPerson = personRepository.findByAccount(account);
        if (existingPerson.isPresent()) {
            return new ResponseEntity<>(new ApiResponse<>(null, "This account is already been used."), HttpStatus.UNAUTHORIZED);
        }
        Person newPerson = personService.createPerson(account, name);
        return ResponseEntity.ok(new ApiResponse<>(newPerson,"Create person"));
    }

    @PutMapping("/update/{personId}/")
    public ResponseEntity<?> updatePerson(@RequestParam String name, HttpServletRequest request, @PathVariable Integer personId
    ){
        try{
            Optional<Person> person = personRepository.findById(personId);
            if (person.isPresent()){
                Person updatePerson = personService.update(person.get(),name);
                return ResponseEntity.ok(updatePerson);
            }
            return new ResponseEntity<>(new ApiResponse<>(null, "This Person ID is not exist."), HttpStatus.UNAUTHORIZED);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Person failed");
        }
    }

    @DeleteMapping("/delete/{personId}/")
    public ResponseEntity<?> deletePerson(HttpServletRequest request,@PathVariable Integer personId
    ){
        try{
            Optional<Person> person = personRepository.findById(personId);
            if (person.isPresent()){
                personService.delete(person.get());
                return ResponseEntity.ok("Delete Person success");
            }
            return new ResponseEntity<>(new ApiResponse<>(null, "This Person ID is not exist."), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete Person failed");
        }
    }
}
