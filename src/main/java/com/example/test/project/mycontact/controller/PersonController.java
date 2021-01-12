package com.example.test.project.mycontact.controller;

import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.repository.PersonRepository;
import com.example.test.project.mycontact.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController //restful api 동작을 하는 controller 명시
@RequestMapping(value = "/api/person")
@Slf4j
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){ // ?id=1 이런식이 아니라 /1 이런식으로 호출할수 있게된다.
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201번 response 신호를 보내줌
    public void postPerson(@RequestBody Person person){
        personService.put(person); // person객체 저장

        log.info("person -> {}", personRepository.findAll());
    }



}