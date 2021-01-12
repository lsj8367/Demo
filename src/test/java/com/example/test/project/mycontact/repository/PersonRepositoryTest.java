package com.example.test.project.mycontact.repository;

import com.example.test.project.mycontact.domain.Person;
import com.example.test.project.mycontact.domain.dto.Birthday;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();

        person.setName("john");
        person.setAge(10);
        person.setBloodType("B");
        personRepository.save(person);

        List<Person> people = personRepository.findByName("john");

        Assertions.assertThat(people.size()).isEqualTo(1);
        Assertions.assertThat(people.get(0).getName()).isEqualTo("john");
        Assertions.assertThat(people.get(0).getAge()).isEqualTo(10);
        Assertions.assertThat(people.get(0).getBloodType()).isEqualTo("B");
    }

    @Test
    void findByBloodType(){
        List<Person> result = personRepository.findByBloodType("A");

        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("martin");
        Assertions.assertThat(result.get(1).getName()).isEqualTo("benny");
    }

    @Test
    void findByBirthDayBetween(){
        List<Person> result = personRepository.findByMonthOfBirthday(8); //8월 생일자 추출

        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result.get(0).getName()).isEqualTo("martin");
        Assertions.assertThat(result.get(1).getName()).isEqualTo("sophia");
    }
}