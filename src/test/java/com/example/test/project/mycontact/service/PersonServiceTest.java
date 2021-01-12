package com.example.test.project.mycontact.service;

import com.example.test.project.mycontact.domain.Block;
import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.repository.BlockRepository;
import com.example.test.project.mycontact.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Test
    void getPeopleExcludeBlocks(){
        List<Person> result = personService.getPeopleExcludeBlocks(); //차단되지 않은 유저들

        result.forEach(System.out::println);

        Assertions.assertThat(result.size()).isEqualTo(3);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("martin");
        Assertions.assertThat(result.get(1).getName()).isEqualTo("david");
        Assertions.assertThat(result.get(2).getName()).isEqualTo("benny");
    }

    @Test
    void getPeopleByName(){
        List<Person> result = personService.getPeopleByName("martin"); //martin이라는 이름을 가진 데이터 가져옴

        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("martin");
    }
    @Test
    void getPerson(){
        Person person = personService.getPerson(3L);

        Assertions.assertThat(person.getName()).isEqualTo("dennis");
    }

}